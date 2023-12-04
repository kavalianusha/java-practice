package com.example.hostinghub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "itreturns")
@Data
public class ItReturnsEntity {
    @Id
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
    private String createdDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passwordId", referencedColumnName = "passwordId")
    private PasswordsEntity passwordsEntity;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

   /* @Override
    public String toString() {
        return "ItReturnsEntity{" +
                "  customerId='" + customerId + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", registeredEmail='" + registeredEmail + '\'' +
                ", registeredMobileNo'"+registeredMobileNo+'\''+
                ", userName'"+userName+'\''+
                ", password'"+password+'\''+
                ", registrationDate'"+registrationDate+'\''+
                ", expiryDate'"+expiryDate+'\''+
                ", loginUrl'"+loginUrl+'\''+
                ", createdBy'"+createdBy+'\''+
                ", createdDate"+createdDate+'\''+
                // Do not include passwordsEntity here to break the cycle
                '}';
    }*/
}
