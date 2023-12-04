package com.example.hostinghub.service;

import com.example.hostinghub.entity.DomainEntity;
import com.example.hostinghub.entity.HostingEntity;
import com.example.hostinghub.entity.PasswordsEntity;
import com.example.hostinghub.mappers.HostingMapper;
import com.example.hostinghub.repository.HostingRepository;
import com.example.hostinghub.repository.PasswordRepository;
import com.example.hostinghub.request.DomainUpdateRequest;
import com.example.hostinghub.request.HostingRequest;
import com.example.hostinghub.request.HostingUpdateRequest;
import com.example.hostinghub.response.HostingResponse;
import com.example.hostinghub.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HostingService {

    // Autowired HostingRepository and HostingMapper
    private HostingRepository hostingAddRepo;
    private HostingMapper hostAddMapping;

    private PasswordRepository passwordRepository;

    // Constructor with autowired dependencies
    @Autowired
    public  HostingService(HostingRepository hostingAddRepo,
                           HostingMapper hostAddMapping,
                           PasswordRepository passwordRepository){
        this.hostAddMapping = hostAddMapping;
        this.hostingAddRepo = hostingAddRepo;
        this.passwordRepository = passwordRepository;
    }

    // Method to generate a new Hosting ID
    public String generateHostingId() {
        String hostIdDetails = hostingAddRepo.findHighestHostId();

        int numericPart = 1;

        if (hostIdDetails != null) {
            numericPart = Integer.parseInt(hostIdDetails.substring(2)) + 1;
        }

        String idFormat = "HA%03d";

        return String.format(idFormat, numericPart);
    }

    // Method to retrieve all Hosting records
    public List<HostingResponse> getAllHosting() {
        try {
            List<HostingEntity> host = hostingAddRepo.findAll();

            log.info("The size of the hosting is {} : ", host.size());

            log.info("Retrieved hosting information : {} ", host);

            return hostAddMapping.responseToEntity(host);

        } catch (Exception e){
            log.error("Error retrieving hosting information" , e);

            return Collections.emptyList();
        }
    }

    // Method to retrieve a Hosting record by ID
    public HostingResponse getHostingById(String hostingId) {
        try {
            Optional<HostingEntity> hostingAdd = hostingAddRepo.findById(hostingId);
            if (hostingAdd.isPresent()) {
                HostingEntity hosting = hostingAdd.get();
                log.info("retrieving hosting information by ID {} : ", hosting);

                return hostAddMapping.requestToEntity(hostingAdd.get());
            } else {
                log.error("Error retrieving hosting information by ID: " + hostingId);
                return null;
            }
        } catch (Exception e){
            log.error("Error retrieving hosting information by ID: " +  e);

            return null;
        }
    }

    // Method to save a new Hosting record
    public ResponseEntity<?> saveHosting(HostingRequest reqDto) {
        try {
            String id = generateHostingId();
            reqDto.setHostingId(id);

            HostingEntity hostingEntity = hostAddMapping.entityToRequest(reqDto);

            PasswordsEntity passwordsEntity = hostAddMapping.requestToPasswordEntity(reqDto);

            String highestPasswordId = passwordRepository.findHighestPasswordId();
            int numericPartpwd = 1;

            if (highestPasswordId != null) {
                numericPartpwd = Integer.parseInt(highestPasswordId.substring(3)) + 1;
            }

            String idFormatPwd = "PWD%03d";
            String passwordId = String.format(idFormatPwd, numericPartpwd);
            passwordsEntity.setPasswordId(passwordId);
            reqDto.setPasswordsEntity(passwordsEntity);

            LocalDate registrationDate = LocalDate.parse(reqDto.getRegistrationDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate expiryDate = LocalDate.parse(reqDto.getExpiryDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            long daysLeft = ChronoUnit.DAYS.between(registrationDate, expiryDate);
            passwordsEntity.setDaysLeft(String.valueOf(daysLeft));

            //passwordEntity.setHostingEntity(hostingEntity);
            hostingEntity.setPasswordsEntity(passwordsEntity);

            hostingAddRepo.save(hostingEntity);

            ResultResponse responseResult = new ResultResponse();
            log.info("Adding new hosting information {} : ",hostingEntity);

            responseResult.setResult("Registration successfully for hosting");
            return ResponseEntity.ok(responseResult);
        } catch (Exception e) {
            log.error("Error saving hosting information",  e);

            ResultResponse errorResponse = new ResultResponse();
            errorResponse.setResult("Error saving hosting information");
            return ResponseEntity.ok(errorResponse);
        }
    }

    // Method to update an existing Hosting record
    public ResultResponse updateHosting(String hostingId, HostingUpdateRequest hostingUpdateRequest) {
        try {
            HostingEntity hostingEntity = hostingAddRepo.findByHostingId(hostingId);

            if (hostingEntity != null) {
                // Use domainMappers to update the properties
                HostingEntity entity = hostAddMapping.updateEntityFromRequest(hostingEntity,hostingUpdateRequest);
                PasswordsEntity existingPasswordsEntity = hostingEntity.getPasswordsEntity();


                PasswordsEntity passwordEntity = hostAddMapping.requestToUpdatePasswordEntity(hostingUpdateRequest);

                passwordEntity.setPasswordId(existingPasswordsEntity.getPasswordId());

                LocalDate registrationDate = LocalDate.parse(hostingUpdateRequest.getRegistrationDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate expiryDate = LocalDate.parse(hostingUpdateRequest.getExpiryDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                long daysLeft = ChronoUnit.DAYS.between(registrationDate, expiryDate);
                passwordEntity.setDaysLeft(String.valueOf(daysLeft));

                entity.setPasswordsEntity(passwordEntity);

                hostingAddRepo.save(entity);

                ResultResponse result = new ResultResponse();
                log.info("Update successful for hosting {}. Updated hosting: {}", hostingId, hostingUpdateRequest);
                result.setResult("Update successful");

                return result;
            } else {
                ResultResponse result = new ResultResponse();
                log.info("hosting not found for ID: {}", hostingId);
                result.setResult("hosting not found");

                return result;
            }
        } catch (Exception e) {
            log.error("Error occurred during hosting update: " + e.getMessage());
            ResultResponse errorResult = new ResultResponse();
            errorResult.setResult("Error occurred during hosting update");

            return errorResult;
        }
    }

    // Method to delete a Hosting record by ID
    public ResponseEntity<?> deleteHostingById(String hostingId) {
        HostingEntity hostingEntity = hostingAddRepo.findByHostingId(hostingId);

        if (hostingEntity == null) {
            ResultResponse response = new ResultResponse();
            response.setResult("Hosting with " + hostingId + " is not found in the database.");
            log.info("Hosting with " + hostingId + " is not found in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Delete associated PasswordsEntity
        PasswordsEntity passwordsEntity = hostingEntity.getPasswordsEntity();
        if (passwordsEntity != null) {
            passwordRepository.delete(passwordsEntity);
        }

        // Delete DomainEntity
        hostingAddRepo.delete(hostingEntity);

        ResultResponse response = new ResultResponse();
        response.setResult("Hosting with " + hostingId + " is deleted successfully.");
        log.info("Hosting with " + hostingId + " is deleted successfully.");
        return ResponseEntity.ok(response);
    }
}
