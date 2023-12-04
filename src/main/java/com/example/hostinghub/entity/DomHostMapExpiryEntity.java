package com.example.hostinghub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "DomHostMapExpiryTable")
@AllArgsConstructor
@NoArgsConstructor
public class DomHostMapExpiryEntity {

    @Id
    private String expiryId;

    private String domainName;
    private String hostProvider;
    private String payment;
    private String registrationDate;
    private String expiryDate;
    private String duration;
    private long daysLeft;


}
