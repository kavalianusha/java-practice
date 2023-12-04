package com.example.hostinghub.response;

import lombok.Data;
@Data  // Lombok annotation to generate getters, setters, and other boilerplate code
public class EmailResponse {
    private String emailId;    // Declaring a field for emailId
    private String email;      // Declaring a field for email
    private String username;   // Declaring a field for username
    private String password;   // Declaring a field for password
}