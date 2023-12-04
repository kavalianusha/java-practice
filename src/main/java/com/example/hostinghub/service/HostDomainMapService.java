package com.example.hostinghub.service;

import com.example.hostinghub.entity.DomHostMapExpiryEntity;
import com.example.hostinghub.entity.HostDomainMapEntity;
import com.example.hostinghub.exception.AdminException;
import com.example.hostinghub.mappers.DomHostExpiryMapper;
import com.example.hostinghub.mappers.HostDomainMapMapper;
import com.example.hostinghub.repository.DomHostMapExpiryRepository;
import com.example.hostinghub.repository.DomainRepository;
import com.example.hostinghub.repository.HostDomainMapRepository;
import com.example.hostinghub.repository.HostingRepository;
import com.example.hostinghub.request.HostDomainMapRequest;
import com.example.hostinghub.request.HostDomainMapUpdateRequest;
import com.example.hostinghub.response.HostDomainMapResponse;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class HostDomainMapService {

    private DomHostMapExpiryRepository domHostMapExpiryRepository;
    private HostDomainMapRepository hostDomainMapRepository;

    private DomHostExpiryMapper domhostExpiryMapper;
    protected HostDomainMapMapper hostDomainMapMapper;

    private DomainRepository domainRepository;

    private HostingRepository hostingRepository;

    // Constructor with autowired dependencies
    @Autowired
    public HostDomainMapService(
            HostDomainMapRepository hostDomainMapRepository,
            HostDomainMapMapper hostDomainMapMapper,
            DomainRepository domainRepository,
            HostingRepository hostingRepository,
            DomHostMapExpiryRepository domHostMapExpiryRepository,
            DomHostExpiryMapper domhostExpiryMapper

    ) {
        this.hostDomainMapRepository = hostDomainMapRepository;
        this.hostDomainMapMapper = hostDomainMapMapper;
        this.domainRepository = domainRepository;
        this.domHostMapExpiryRepository = domHostMapExpiryRepository;
        this.hostingRepository = hostingRepository;
        this.domhostExpiryMapper =domhostExpiryMapper;
    }

    // Assuming this is a method in your HostDomainMapService class
    public ResponseEntity<?> addHostDomainAdd(HostDomainMapRequest hostDomainMapRequest) {
        // Generate a new hostDomainId
        String highestHostDomainId = hostDomainMapRepository.findHighestHostDomainId();
        int numericPart = 1;

        if (highestHostDomainId != null && highestHostDomainId.length() > 4) {
            numericPart = Integer.parseInt(highestHostDomainId.substring(4)) + 1;
        }

        String idFormat = "HD%03d"; // The %03d means a 3-digit zero-padded number
        String highestId = String.format(idFormat, numericPart);
        hostDomainMapRequest.setHostDomainId(highestId);

        // Convert request to entity
        HostDomainMapEntity hostDomainMapEntity = hostDomainMapMapper.requestToEntity(hostDomainMapRequest);

        DomHostMapExpiryEntity domHostMapExpiryEntity = hostDomainMapMapper.createDomHostMapExpiryEntity(hostDomainMapRequest);
        String highestExpiryId = domHostMapExpiryRepository.findHighestExpiryId();
        int numericPart1 = 1;

        if (highestHostDomainId != null && highestHostDomainId.length() > 4) {
            numericPart1 = Integer.parseInt(highestHostDomainId.substring(4)) + 1;
        }

        String idFormat1 = "EX%03d"; // The %03d means a 3-digit zero-padded number
        String highestId1 = String.format(idFormat1, numericPart1);
        domHostMapExpiryEntity.setExpiryId(highestId1);

        LocalDate registrationDate = LocalDate.parse(hostDomainMapRequest.getRegistrationDate(), DateTimeFormatter.ofPattern(("dd/MM/yyyy")));
        LocalDate expiry = LocalDate.parse(hostDomainMapEntity.getExpiryDate(),DateTimeFormatter.ofPattern(("dd/MM/yyyy")));
        long daysLeft = ChronoUnit.DAYS.between(registrationDate, expiry);
        domHostMapExpiryEntity.setDaysLeft(daysLeft);

        hostDomainMapEntity.setDomHostMapExpiryEntity(domHostMapExpiryEntity);

        hostDomainMapRepository.save(hostDomainMapEntity);

        ResultResponse result = new ResultResponse();
        result.setResult("Hosting and Domain registered successfully with payment of: " +
                hostDomainMapRequest.getPayment());
        log.info("Hosting and Domain registered successfully with payment of : {}",
                hostDomainMapRequest.getPayment());
        return ResponseEntity.ok(result);
    }


    // Method for retrieving all Host-Domain mappings
    public List<HostDomainMapResponse> getAllHostDomains() {
        List<HostDomainMapEntity> hostDomainMapEntities = hostDomainMapRepository.findAll();
        log.info("The size of the host domain mappers is {} : ", hostDomainMapEntities.size());
        log.info("The retrieved host domain mappers are {} : ", hostDomainMapEntities);
        return hostDomainMapEntities.stream()
                .map(hostDomainMapMapper::entityToResponse)
                .collect(Collectors.toList());
    }


    // Method for retrieving a Host-Domain mapping by its ID
    public HostDomainMapResponse getAllHostDomainsById(String hostDomainId) {
        Optional<HostDomainMapEntity> hostDomainMapOptional = hostDomainMapRepository.findById(hostDomainId);

        if (hostDomainMapOptional.isPresent()) {
            log.info("The size of the host domain is {} : ", hostDomainMapOptional.isPresent());
            log.info("The retrieved host domain are {} : ", hostDomainMapOptional);
            return hostDomainMapMapper.entityToResponse(hostDomainMapOptional.get());// Return the mapped result
        } else {
            return null; // Return null if the customer is not found
        }
    }

    // Method for checking if a Host-Domain mapping with a specified ID exists
    public boolean existsById(String hostDomainId) {
        return hostDomainMapRepository.existsById(hostDomainId);
    }
    //upadte
    public HostDomainMapEntity updateHostDomain(String hostDomainId, HostDomainMapUpdateRequest hostDomainMapRequest) {
        // Retrieve the existing HostDomainMapEntity
        HostDomainMapEntity existingHostDomain = hostDomainMapRepository.findByHostDomainId(hostDomainId);

        if (existingHostDomain == null) {
            log.info("The Host Domain id is not found in the db {}", hostDomainId);
            ResultResponse response = new ResultResponse();
            response.setResult("Host Domain with ID " + hostDomainId + " not found.");
            throw new AdminException(response);
        }

        // Log existing entity
        log.info("Existing HostDomainMapEntity: {}", existingHostDomain);

        // Update the existing HostDomainMapEntity with new values
        HostDomainMapEntity updatedHostDomain = hostDomainMapMapper.updateEntityFromRequest(hostDomainMapRequest, existingHostDomain);

        // Log updated entity
        log.info("Updated HostDomainMapEntity: {}", updatedHostDomain);

        // Save the updatedHostDomain and check for null
        hostDomainMapRepository.save(updatedHostDomain);

        // Log saved entity
        log.info("Saved HostDomainMapEntity: {}", updatedHostDomain);

        // Check if the save operation was successful
        if (updatedHostDomain == null) {
            log.error("Failed to save updated HostDomainMapEntity for Host Domain ID: {}", hostDomainId);
            // You might want to throw an exception or handle it appropriately based on your use case.
        } else {
            // Retrieve the existing DomHostMapExpiryEntity
            DomHostMapExpiryEntity domHostMapExpiryEntity=domhostExpiryMapper.updateToEntity(hostDomainMapRequest);
            if (domHostMapExpiryEntity != null) {
                // Log existing DomHostMapExpiryEntity
                log.info("Existing DomHostMapExpiryEntity: {}", domHostMapExpiryEntity);

                // Update the existing DomHostMapExpiryEntity with new values
                domHostMapExpiryEntity = hostDomainMapMapper.updateDomEntityFromRequest(hostDomainMapRequest, domHostMapExpiryEntity);

                // Recalculate days left based on the updated registration and expiry dates
                LocalDate updatedRegistration = LocalDate.parse(updatedHostDomain.getRegistrationDate(),DateTimeFormatter.ofPattern(("dd/MM/yyyy")));
                LocalDate updatedExpiry = LocalDate.parse(updatedHostDomain.getExpiryDate(),DateTimeFormatter.ofPattern(("dd/MM/yyyy")));
                long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), updatedExpiry);
                domHostMapExpiryEntity.setDaysLeft(daysLeft);

                // Set the reference to the updated HostDomainMapEntity
                updatedHostDomain.setDomHostMapExpiryEntity(domHostMapExpiryEntity);

                domHostMapExpiryRepository.save(domHostMapExpiryEntity);

                // Log updated DomHostMapExpiryEntity
                log.info("Updated DomHostMapExpiryEntity: {}", domHostMapExpiryEntity);
            } else {
                // Handle the case where domHostMapExpiryEntity is null
                log.error("DomHostMapExpiryEntity not found for Host Domain ID: {}", hostDomainId);
                // You might want to throw an exception or handle it appropriately based on your use case.
            }
        }

        return updatedHostDomain;
    }

    // Method for updating an existing Host-Domain mapping



    public ResponseEntity<?> deletehostDomainById(String hostDomainId) {
        HostDomainMapEntity hostDomainMapEntity = hostDomainMapRepository.findByHostDomainId(hostDomainId);

        if (hostDomainMapEntity == null) {
            ResultResponse response = new ResultResponse();
            response.setResult("Hosting with " + hostDomainId + " is not found in the database.");
            log.info("Hosting with " + hostDomainId + " is not found in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        }

        // Delete associated PasswordsEntity
        DomHostMapExpiryEntity domHostMapExpiryEntity = hostDomainMapEntity.getDomHostMapExpiryEntity();
        if (domHostMapExpiryEntity != null) {
            domHostMapExpiryRepository.delete(domHostMapExpiryEntity);
        }

        // Delete DomainEntity
        hostDomainMapRepository.delete(hostDomainMapEntity);

        ResultResponse response = new ResultResponse();
        response.setResult("Hosting with " + hostDomainId + " is deleted successfully.");
        log.info("Hosting with " + hostDomainId + " is deleted successfully.");
        return ResponseEntity.ok(response);
    }


    // Method for retrieving all domain names
    public List<String> getAllDomainNames() {
        return domainRepository.findAllDomainNames();
    }

    // Method for retrieving all hosting providers
    public List<String> getAllHostingProviders() {
        return hostingRepository.findAllHostingProvider();
    }
}