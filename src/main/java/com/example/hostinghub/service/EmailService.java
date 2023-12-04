package com.example.hostinghub.service;

import com.example.hostinghub.entity.EmailEntity;
import com.example.hostinghub.mappers.EmailMapper;
import com.example.hostinghub.repository.EmailRepository;
import com.example.hostinghub.request.EmailRequest;
import com.example.hostinghub.request.EmailUpdateRequest;
import com.example.hostinghub.response.EmailResponse;
import com.example.hostinghub.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;  // Injecting the EmailRepository

    @Autowired
    private EmailMapper emailMapper;  // Injecting the EmailMapper

    // Method to register a new email
    public ResultResponse registerEmail(EmailRequest emailEntity) {
        try {
            // Get the highest email ID
            String highestEmailId = emailRepository.findHighestEmailId();

            int numericPart = 1;

            // Calculate the numeric part for the new email ID
            if (highestEmailId != null) {
                numericPart = Integer.parseInt(highestEmailId.substring(2)) + 1;
            }

            // Format the new email ID
            String idFormat = "EM%03d";
            String emailId = String.format(idFormat, numericPart);

            // Set the email ID for the emailEntity
            emailEntity.setEmailId(emailId);

            // Convert the EmailRequest to EmailEntity using the mapper
            EmailEntity req = emailMapper.entityToRequest(emailEntity);

            // Save the emailEntity in the repository
            emailRepository.save(req);

            // Create a success response
            ResultResponse result = new ResultResponse();

            // Log the success message with email information
            log.info("Registration successful for email with ID: {}. Details: Email={}, Username={}, Password={}",
                    emailId, emailEntity.getEmail(), emailEntity.getUsername(), emailEntity.getPassword());

            result.setResult("Registration successful");  // Set the result message

            return result;  // Return the result
        } catch (Exception e) {
            // Handle any exceptions and log an error message
            log.error("Error occurred during email registration: " + e.getMessage());

            // Create an error response
            ResultResponse errorResult = new ResultResponse();
            errorResult.setResult("Error occurred during email registration");

            return errorResult;  // Return the error result
        }
    }
    //
    public EmailEntity getEmailById(String emailId) {
        try {
            // Try to find the email by its ID
            Optional<EmailEntity> emailOptional = emailRepository.findById(emailId);

            if (emailOptional.isPresent()) {
                // If found, get the email entity
                EmailEntity email = emailOptional.get();

                // Log the retrieved email information
                log.info("Retrieved email by ID: {}. Retrieved email: {}", emailId, email);

                return email;  // Return the email entity
            } else {
                // If not found, log a message and return null
                log.info("No email found with ID: {}", emailId);
                return null;  // Or you can throw a custom exception here if you prefer
            }
        } catch (Exception e) {
            // Handle any exceptions and log an error message
            log.error("Error occurred while retrieving email by ID: " + e.getMessage());
            return null;
        }
    }

    // Method to get all emails
    public List<EmailResponse> getAllEmails() {
        try {
            // Find all email entities
            List<EmailEntity> req = emailRepository.findAll();

            // Log the size and retrieved emails
            log.info("The size of the emails is {} : ", req.size());
            log.info("The retrieved emails are : {}", req);

            // Convert and return email entities to email responses using the mapper
            return emailMapper.responseToEntity(req);
        } catch (Exception e) {
            // Handle any exceptions and log an error message
            log.error("Error occurred while retrieving all emails: " + e.getMessage());
            return null;
        }
    }
    public ResultResponse updateEmail(String emailId, EmailUpdateRequest updatedEmail) {
        try {
            // Logic for updating an email
            EmailEntity existingEmail = emailRepository.findByEmailId(emailId);

            if (existingEmail != null) {
                EmailEntity updatedEmailEntity = emailMapper.updateEntityFromRequest(updatedEmail, existingEmail);
                // Update existingEmail with fields from updatedEmailEntity
                // existingEmail.setEmail(updatedEmailEntity.getEmail());
                // existingEmail.setUsername(updatedEmailEntity.getUsername());
                // existingEmail.setPassword(updatedEmailEntity.getPassword());

                // Save the updated email entity in the repository
                emailRepository.save(existingEmail);

                ResultResponse result = new ResultResponse();
                log.info("Update successful for email {}. Updated email: {}", emailId, updatedEmail);
                result.setResult("Update successful");

                return result;
            }

            ResultResponse result = new ResultResponse();
            log.info("Email ID not found in the DB");
            result.setResult("Email not found");

            return result;
        } catch (Exception e) {
            // Handle any exceptions and log an error message
            log.error("Error occurred during email update: " + e.getMessage());
            ResultResponse errorResult = new ResultResponse();
            errorResult.setResult("Error occurred during email update");

            return errorResult;
        }
    }
    // Method to delete an email
    public ResultResponse deleteEmail(String emailId) {
        try {
            // Try to find the email by its ID
            Optional<EmailEntity> emailOptional = emailRepository.findById(emailId);

            if (emailOptional.isPresent()) {
                // If found, delete the email by its ID
                emailRepository.deleteById(emailId);

                // Create a success response
                ResultResponse result = new ResultResponse();

                // Log the successful deletion
                log.info("Deleted email with ID: {}", emailId);

                result.setResult("Delete successful");  // Set the result message

                return result;  // Return the result
            } else {
                // If not found, create a result indicating it
                ResultResponse result = new ResultResponse();
                result.setResult("Email not found");
                log.info("Id not found in the DB");

                return result;  // Return the result
            }
        } catch (Exception e) {
            // Handle any exceptions and log an error message
            log.error("Error occurred during email deletion: " + e.getMessage());

            // Create an error response
            ResultResponse errorResult = new ResultResponse();
            errorResult.setResult("Error occurred during email deletion");

            return errorResult;  // Return the error result
        }
    }
}


