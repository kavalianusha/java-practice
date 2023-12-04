package com.example.hostinghub.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Marks this class as a JPA entity, making it eligible for database persistence
@Table(name = "domainTable") // Maps this entity to a database table named "domaintable"
@Data // Lombok annotation to generate boilerplate code (e.g., getters, setters)
@AllArgsConstructor
@NoArgsConstructor
public class DomainEntity {

    @Id // Denotes that the 'domainId' field serves as the primary key in the database
    private String domainId; // Represents the unique identifier for a domain entity

    private String domainName; // Holds the name of the domain
    private String providerName; // Stores the name of the domain provider
    private String domainUrl; // Contains the URL associated with the domain
    private String userName; // Holds the username for accessing the domain
    private String password; // Stores the password for accessing the domain
    private String registrationDate; // Holds the date of domain registration
    private String expiryDate; // Stores the date of domain expiry
    private String clientName; // Holds the name of the client associated with the domain
    private String duration; // Stores the duration of the domain registration
    private String registrationName; // Holds the name used for domain registration
    private String registrationMobileNumber; // Stores the mobile number used for domain registration
    private String registrationEmail; // Holds the email used for domain registration

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passwordId", referencedColumnName = "passwordId")
    private PasswordsEntity passwordsEntity;

}
