package com.example.hostinghub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity // Marks this class as a JPA entity, making it eligible for database persistence
@Table(name = "emailTable") // Maps this entity to a database table named "emailtable"
@Data // Lombok annotation to generate boilerplate code (e.g., getters, setters)
public class EmailEntity {
    @Id // Denotes that the 'emailId' field serves as the primary key in the database
    private String emailId; // Represents the unique identifier for an email entity

    private String email; // Holds the email address associated with this entity
    private String username; // Stores the username linked to the email
    private String password; // Holds the password corresponding to the email account
}
