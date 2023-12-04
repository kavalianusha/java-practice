package com.example.hostinghub.response;

import java.time.LocalDate;

import com.example.hostinghub.entity.PasswordsEntity;
import lombok.Data;

@Data
public class ItReturnsResponse {
    private String customerId;

    private String serviceType;
    private String registeredEmail;
    private String registeredMobileNo;
    private String registrationDate; // Holds the date of domain registration
    private String expiryDate;
    private String loginUrl;
    private String userName;
    private String password;
    private String createdBy;
    private LocalDate createdDate;
    private PasswordsEntity passwordsEntity;

}
