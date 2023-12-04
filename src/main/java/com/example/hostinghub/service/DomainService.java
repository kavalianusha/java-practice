package com.example.hostinghub.service;

import com.example.hostinghub.entity.DomainEntity;
import com.example.hostinghub.entity.PasswordsEntity;
import com.example.hostinghub.exception.AdminException;
import com.example.hostinghub.mappers.DomainMappers;
import com.example.hostinghub.repository.DomainRepository;
import com.example.hostinghub.repository.PasswordRepository;
import com.example.hostinghub.request.DomainRequest;
import com.example.hostinghub.request.DomainUpdateRequest;
import com.example.hostinghub.response.DomainResponse;
import com.example.hostinghub.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DomainService {

    // Injecting DomainRepository and DomainMappers
    private final DomainRepository domainRepository;
    private final DomainMappers domainMappers;

    private final PasswordRepository passwordRepository;

    @Autowired
    public DomainService(DomainRepository domainRepository,
                         DomainMappers domainMappers,
                         PasswordRepository passwordRepository) {
        this.domainRepository = domainRepository;
        this.domainMappers = domainMappers;
        this.passwordRepository = passwordRepository;
    }

    public ResponseEntity<?> registerDomain(DomainRequest domainRequest) {
        try {
            // Logic for generating a unique domain ID
            String highestDomainId = domainRepository.findHighestDomainId();
            int numericPart = 1;

            if (highestDomainId != null) {
                numericPart = Integer.parseInt(highestDomainId.substring(3)) + 1;
            }

            String idFormat = "DOM%03d";
            String domainId = String.format(idFormat, numericPart);
            domainRequest.setDomainId(domainId);

            DomainEntity domainEntity = domainMappers.entityToRequest(domainRequest);

            PasswordsEntity passwordsEntity = domainMappers.entityPasswordToRequest(domainRequest);

            String highestPasswordId = passwordRepository.findHighestPasswordId();
            int numericPartpwd = 1;

            if (highestPasswordId != null) {
                numericPartpwd = Integer.parseInt(highestPasswordId.substring(3)) + 1;
            }

            String idFormatPwd = "PWD%03d";
            String passwordId = String.format(idFormatPwd, numericPartpwd);
            passwordsEntity.setPasswordId(passwordId);

            domainRequest.setPasswordsEntity(passwordsEntity);

            LocalDate registrationDate = LocalDate.parse(domainRequest.getRegistrationDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate expiryDate = LocalDate.parse(domainRequest.getExpiryDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            long daysLeft = ChronoUnit.DAYS.between(registrationDate, expiryDate);
            passwordsEntity.setDaysLeft(String.valueOf(daysLeft));

            domainEntity.setPasswordsEntity(passwordsEntity);

            domainRepository.save(domainEntity);

            ResultResponse result = new ResultResponse();
            log.info("Registration successful for domain {}. Retrieved user: {}", domainEntity.getRegistrationEmail(), domainEntity);
            result.setResult("Registration successful");

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Handling exceptions and logging an error message
            log.error("Error occurred during domain registration: " + e.getMessage());
            ResultResponse errorResult = new ResultResponse();
            errorResult.setResult("Error occurred during domain registration");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

    // Method to get a domain by its ID
    public DomainResponse getDomainById(String domainId) {

        Optional<DomainEntity> domainOptional = domainRepository.findById(domainId);

        if (domainOptional.isPresent()) {
            log.info("The size of the admin is: {}", domainOptional.isPresent());
           // log.info("The retrieved admin is: {}", domainOptional);
            return domainMappers.entityToResponse(domainOptional.get());
        } else {
            return null;
        }
    }

    // Method to get a list of all domains
    public List<DomainResponse> getAllDomains() {
        try {
            // Finding all domain entities
            List<DomainEntity> domains = domainRepository.findAll();
            log.info("the size of the domains is {} : ", domains.size());
            //log.info("the retrieved domains are {} : ", domains);

            // Converting and returning domain entities to domain responses using the mapper
            return domainMappers.responseToEntity(domains);
        } catch (Exception e) {
            // Handle any exceptions and log an error message
            log.error("Error occurred while retrieving all domains: " + e.getMessage());
            return null;
        }
    }

    //method for update a domain

    // Method to delete a domain
    public ResponseEntity<?> deleteDomainById(String domainId) {
        DomainEntity domainEntity = domainRepository.findByDomainId(domainId);

        if (domainEntity == null) {
            ResultResponse response = new ResultResponse();
            response.setResult("Domain with " + domainId + " is not found in the database.");
            log.info("Domain with " + domainId + " is not found in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Delete associated PasswordsEntity
        PasswordsEntity passwordsEntity = domainEntity.getPasswordsEntity();
        if (passwordsEntity != null) {
            passwordRepository.delete(passwordsEntity);
        }

        // Delete DomainEntity
        domainRepository.delete(domainEntity);

        ResultResponse response = new ResultResponse();
        response.setResult("Domain with " + domainId + " is deleted successfully.");
        log.info("Domain with " + domainId + " is deleted successfully.");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> updateDomain(String domainId, DomainUpdateRequest updatedDomainRequest) {
        try {
            DomainEntity existingDomainEntity = domainRepository.findByDomainId(domainId);

            if (existingDomainEntity == null) {
                log.info("The Domain id is not found in the db: {}", domainId);
                ResultResponse response = new ResultResponse();
                response.setResult("Domain with ID " + domainId + " not found.");
                throw new AdminException(response);
            }

            DomainEntity updatedDomainEntity = domainMappers.updateEntityFromRequest(existingDomainEntity, updatedDomainRequest);

            // Retrieve existing password entity
            PasswordsEntity existingPasswordsEntity = existingDomainEntity.getPasswordsEntity();

            // Update password entity from request
            PasswordsEntity updatedPasswordsEntity = domainMappers.updatePasswordsEntityFromRequest(updatedDomainRequest);

            // Set the existing passwordId in the updated password entity
            updatedPasswordsEntity.setPasswordId(existingPasswordsEntity.getPasswordId());

            LocalDate registrationDate = LocalDate.parse(updatedDomainRequest.getRegistrationDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate expiryDate = LocalDate.parse(updatedDomainRequest.getExpiryDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            long daysLeft = ChronoUnit.DAYS.between(registrationDate, expiryDate);
            updatedPasswordsEntity.setDaysLeft(String.valueOf(daysLeft));

            // Set the updated password entity in the updated domain entity
            updatedDomainEntity.setPasswordsEntity(updatedPasswordsEntity);

            // Save the updated entities
            domainRepository.save(updatedDomainEntity);

            ResultResponse response = new ResultResponse();
            log.info("Domain with Id " + domainId + " updated successfully.......");
            response.setResult("Domain with ID " + domainId + " updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            // Handling exceptions and logging an error message
            log.error("Error occurred during domain update: " + e.getMessage());
            ResultResponse errorResult = new ResultResponse();
            errorResult.setResult("Error occurred during domain update");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }
}



