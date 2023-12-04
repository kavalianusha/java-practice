package com.example.hostinghub.request;

// Import Lombok's Data annotation for generating boilerplate code
import com.example.hostinghub.entity.PasswordsEntity;
import lombok.Data;

// Use Lombok's Data annotation to generate getters, setters, equals, and hashCode
@Data
// Define the class for handling host domain map requests
public class HostingRequest {

    // Fields for admin request data
    private String hostingId;
    private String hostingProvider;
    private String url;
    private String login;
    private String password;
    private String registrationEmailId;
    private String registrationPhoneNumber;
    private String registrationDomain;
    private String registrationDate; // Holds the date of domain registration
    private String expiryDate;
    private String hostingCpannelUrl;
    private String userName;
    private String hostingDnsName;
    private PasswordsEntity passwordsEntity;

}
