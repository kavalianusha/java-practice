package com.example.hostinghub.request;

// Import Lombok's Data annotation for generating boilerplate code
import lombok.Data;

// Use Lombok's Data annotation to generate getters, setters, equals, and hashCode
@Data

// Define the class for handling admin requests
public class AdminRequest {

    // Fields for admin request data
    private String adminId;
    private String name;
    private String userName;
    private String emailId;
    private String password;
    private String phoneNo;
    private String role;
    private String otp;
}
