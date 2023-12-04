package com.example.hostinghub.service;


import com.example.hostinghub.entity.AdminEntity;
import com.example.hostinghub.entity.HostingEntity;
import com.example.hostinghub.entity.ItReturnsEntity;
import com.example.hostinghub.entity.PasswordsEntity;
import com.example.hostinghub.mappers.ItReturnsMapper;
import com.example.hostinghub.repository.ItReturnsRepository;
import com.example.hostinghub.repository.PasswordRepository;
import com.example.hostinghub.request.ItReturnsRequest;
import com.example.hostinghub.request.ItReturnsUpdateRequest;
import com.example.hostinghub.response.AdminResponse;
import com.example.hostinghub.response.ItReturnsResponse;
import com.example.hostinghub.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItReturnsService {

    // Injecting ItReturnsRepository and ItReturnsMappers
    private final ItReturnsRepository itReturnsRepository;
    private final ItReturnsMapper itReturnsMapper;

    private final PasswordRepository passwordRepository;

    @Autowired
    public ItReturnsService(ItReturnsRepository itReturnsRepository,
                            ItReturnsMapper itReturnsMapper,
                            PasswordRepository passwordRepository) {
        this.itReturnsRepository = itReturnsRepository;
        this.itReturnsMapper = itReturnsMapper;
        this.passwordRepository = passwordRepository;
    }

    // Endpoint for registering a new IT return
    public ResponseEntity<?> registerItReturns(ItReturnsRequest itReturnsRequest) {
        try {
            // Logic for generating a unique customer ID
            String highestCustomerId = itReturnsRepository.findHighestCustomerId();
            int numericPart = 1;

            if (highestCustomerId != null) {
                numericPart = Integer.parseInt(highestCustomerId.substring(4)) + 1;
            }

            String idFormat = "CUST%03d";
            String customerId = String.format(idFormat, numericPart);

            itReturnsRequest.setCustomerId(customerId);

            // Explicitly set the created date using LocalDate
            ItReturnsEntity itReturnsEntity = itReturnsMapper.requestToEntity(itReturnsRequest);
            itReturnsEntity.setCreatedDate(LocalDate.now().toString());
            PasswordsEntity passwordsEntity = itReturnsMapper.requestToPasswordEntity(itReturnsRequest);

            String highestPasswordId = passwordRepository.findHighestPasswordId();
            int numericPartpwd = 1;

            if (highestPasswordId != null) {
                numericPartpwd = Integer.parseInt(highestPasswordId.substring(3)) + 1;
            }

            String idFormatPwd = "PWD%03d";
            String passwordId = String.format(idFormatPwd, numericPartpwd);
            passwordsEntity.setPasswordId(passwordId);

            itReturnsRequest.setPasswordsEntity(passwordsEntity);

            LocalDate registrationDate = LocalDate.parse(itReturnsRequest.getRegistrationDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate expiryDate = LocalDate.parse(itReturnsRequest.getExpiryDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            long daysLeft = ChronoUnit.DAYS.between(registrationDate, expiryDate);
            passwordsEntity.setDaysLeft(String.valueOf(daysLeft));

           // passwordEntity.setItReturnsEntity(itReturnsEntity);
            itReturnsEntity.setPasswordsEntity(passwordsEntity);

            itReturnsRepository.save(itReturnsEntity);

            ResultResponse result = new ResultResponse();
            log.info("Registration successful for customer {}. Retrieved IT return: {}",
                    itReturnsRequest.getRegisteredEmail(), itReturnsRequest);
            log.info("Registration is succcessful for the customer it returns");
            result.setResult("Registration successful");

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Handling exceptions and logging an error message
            log.error("Error occurred during IT return registration: " + e.getMessage());
            ResultResponse errorResult = new ResultResponse();
            errorResult.setResult("Error occurred during IT return registration");

            return ResponseEntity.ok(errorResult);
        }
    }

    // Method to get an IT return by its customer ID
    public ItReturnsEntity getItReturnsByCustomerId(String customerId) {
        try {
            Optional<ItReturnsEntity> itReturnsOptional = itReturnsRepository.findById(customerId);

            if (itReturnsOptional.isPresent()) {
                ItReturnsEntity itReturnsEntity = itReturnsOptional.get();
                log.info("Retrieved IT return by customer ID {}. Retrieved IT return: {}", customerId, itReturnsEntity);
                return itReturnsEntity;
            } else {
                log.info("IT return not found for customer ID: {}", customerId);
                return null;
            }
        } catch (Exception e) {
            log.error("Error occurred while retrieving IT return by customer ID: " + e.getMessage());
            return null;
        }
    }

    // Method to get a list of all IT returns
/*    public List<ItReturnsResponse> getAllItReturns() {
        try {
            List<ItReturnsEntity> itReturnsList = itReturnsRepository.findAll();
            log.info("The size of the IT returns is {} : ", itReturnsList.size());
            log.info("The retrieved IT returns are {} : ", itReturnsList);
            // You can perform additional business logic if needed.

            return itReturnsMapper.responseToEntity(itReturnsList);
        } catch (Exception e) {
            log.error("Error occurred while retrieving all IT returns: " + e.getMessage());

            // Handle exceptions appropriately and consider returning a meaningful error response.
            e.printStackTrace(); // Logging the exception for debugging purposes.
            return null;
        }
    }*/

    public List<ItReturnsResponse> getAllItReturns() {
        List<ItReturnsEntity> itReturnsEntityList = itReturnsRepository.findAll();
        log.info("The size of the it retruns list is: {}", itReturnsEntityList.size());
        log.info("The retrieved it returns are: {}", itReturnsEntityList);
        return  itReturnsMapper.responseToEntity(itReturnsEntityList);
    }

    public ResultResponse updateItReturns(String customerId, ItReturnsUpdateRequest updatedItReturns) {
        try {
            // Logic for updating an IT return
            ItReturnsEntity existingItReturns = itReturnsRepository.findByCustomerId(customerId);

            if (existingItReturns != null) {
                // Log the state before update
                log.info("Before Update - Existing IT return: {}", existingItReturns);

                // Use itReturnsMappers to update the properties
                ItReturnsEntity entity = itReturnsMapper.updateEntityFromRequest(updatedItReturns, existingItReturns);
                PasswordsEntity existingPasswordsEntity = existingItReturns.getPasswordsEntity();


                PasswordsEntity passwordsEntity = itReturnsMapper.requestToUpdatePasswordEntity(updatedItReturns);

                passwordsEntity.setPasswordId(existingPasswordsEntity.getPasswordId());

                LocalDate registrationDate = LocalDate.parse(updatedItReturns.getRegistrationDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate expiryDate = LocalDate.parse(updatedItReturns.getExpiryDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                long daysLeft = ChronoUnit.DAYS.between(registrationDate, expiryDate);
                passwordsEntity.setDaysLeft(String.valueOf(daysLeft));

                //passwordsEntity.setItReturnsEntity(entity);
                 entity.setPasswordsEntity(passwordsEntity);

                itReturnsRepository.save(entity);

                ResultResponse result = new ResultResponse();
                log.info("Update successful for IT return {}. Updated IT return: {}", customerId, updatedItReturns);
                result.setResult("Update successful");

                return result;
            } else {
                ResultResponse result = new ResultResponse();
                log.info("IT return not found for customer ID: {}", customerId);
                result.setResult("IT return not found");

                return result;
            }
        } catch (Exception e) {
            log.error("Error occurred during IT return update: " + e.getMessage(), e);
            ResultResponse errorResult = new ResultResponse();
            errorResult.setResult("Error occurred during IT return update");

            return errorResult;
        }
    }

    // Method to delete an IT return
    public ResponseEntity<?> deleteItreturnsById(String customerId) {
        ItReturnsEntity itReturnsEntity = itReturnsRepository.findByCustomerId(customerId);

        if (itReturnsEntity == null) {
            ResultResponse response = new ResultResponse();
            response.setResult("It returns with " + customerId + " is not found in the database.");
            log.info("It returns with " + customerId + " is not found in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Delete associated PasswordsEntity
        PasswordsEntity passwordsEntity = itReturnsEntity.getPasswordsEntity();
            passwordRepository.delete(passwordsEntity);

        // Delete DomainEntity
        itReturnsRepository.delete(itReturnsEntity);

        ResultResponse response = new ResultResponse();
        response.setResult("Itreturns with " + customerId + " is deleted successfully.");
        log.info("It returns with " + customerId + " is deleted successfully.");
        return ResponseEntity.ok(response);
    }
}


