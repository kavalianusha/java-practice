package com.example.hostinghub.controller;

import com.example.hostinghub.entity.EmailEntity;
import com.example.hostinghub.request.EmailRequest;
import com.example.hostinghub.request.EmailUpdateRequest;
import com.example.hostinghub.response.EmailResponse;
import com.example.hostinghub.response.ResultResponse;
import com.example.hostinghub.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // Marks this class as a Spring MVC Controller
@CrossOrigin(origins="*")  // Allows cross-origin requests from any origin
public class EmailController {

    @Autowired  // Injecting an instance of EmailService
    private EmailService emailService;

    // Endpoint for registering a new email
    @PostMapping("/api/email")
    public ResultResponse registerEmail(@RequestBody EmailRequest emailEntity) {
        return emailService.registerEmail(emailEntity);  // Delegates the request to the EmailService
    }

    // Endpoint for getting an email by its ID
    @GetMapping("/api/email/{emailId}")
    public ResponseEntity<EmailEntity> getEmailById(@PathVariable String emailId) {
        EmailEntity emailEntity = emailService.getEmailById(emailId);

        if (emailEntity != null) {
            return new ResponseEntity<>(emailEntity, HttpStatus.OK);  // Return email entity if found
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if email not found
        }
    }

    // Endpoint for getting a list of all emails
    @GetMapping("/api/email")
    public List<EmailResponse> getAllEmails() {
        return emailService.getAllEmails();  // Delegates the request to the EmailService
    }


    // Endpoint for updating an email by its ID
    @PutMapping("/api/email/{emailId}")
    public ResponseEntity<ResultResponse> updateEmail(@PathVariable String emailId, @RequestBody EmailUpdateRequest updatedEmail) {
        ResultResponse result = emailService.updateEmail(emailId, updatedEmail);
        if (result.getResult().equals("Update successful")) {
            return ResponseEntity.ok(result);  // Return 200 OK with result if update successful
        } else if (result.getResult().equals("Email not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);  // Return 404 if email not found
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);  // Return 500 Internal Server Error for other errors
        }
    }
    // Endpoint for deleting an email by its ID
    @DeleteMapping("/api/email/{emailId}")
    public ResponseEntity<ResultResponse> deleteEmail(@PathVariable String emailId) {
        ResultResponse result = emailService.deleteEmail(emailId);

        if (result.getResult().equals("Delete successful")) {
            return ResponseEntity.ok(result);  // Return 200 OK with result if delete successful
        } else if (result.getResult().equals("Email not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);  // Return 404 if email not found
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);  // Return 500 Internal Server Error for other errors
        }
    }

}
