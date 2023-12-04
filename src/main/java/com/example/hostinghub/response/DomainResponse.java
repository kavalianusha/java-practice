package com.example.hostinghub.response;

import com.example.hostinghub.entity.PasswordsEntity;
import lombok.Data;

@Data  // Lombok annotation to generate getters, setters, and other boilerplate code
public class DomainResponse {
    private String domainId;             // Declaring a field for domainId
    private String domainName;           // Declaring a field for domainName
    private String providerName;         // Declaring a field for providerName
    private String domainUrl;            // Declaring a field for domainUrl
    private String userName;             // Declaring a field for userName
    private String password;             // Declaring a field for password
    private String registrationDate;     // Declaring a field for registrationDate
    private String expiryDate;           // Declaring a field for expiryDate
    private String clientName;           // Declaring a field for clientName
    private String duration;             // Declaring a field for duration
    private String registrationName;     // Declaring a field for registrationName
    private String registrationMobileNumber;  // Declaring a field for registrationMobileNumber
    private String registrationEmail;         // Declaring a field for registrationEmail
    private PasswordsEntity passwordsEntity;
}



