// Import necessary annotations and classes for JPA entity
package com.example.hostinghub.entity;

// Define this class as a JPA entity
// Import Lombok's Data annotation for generating boilerplate code

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



// Define this class as an entity
@Entity
// Generate boilerplate code for getters, setters, equals, and hashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
// Define the name of the table in the database
@Table(name="AdminTable")
public class AdminEntity {

    // Define the field as the primary key
    @Id
    private String adminId;

    private String name;
    private String userName;
    private String emailId;
    private String password;
    private String phoneNo;
    private String role;
    private String otp;

}
