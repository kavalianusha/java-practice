package com.example.hostinghub.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "HostDomainMapTable")
@AllArgsConstructor
@NoArgsConstructor
public class HostDomainMapEntity {

    @Id
    private String hostDomainId;

    private String domainName;
    private String hostProvider;
    private String payment;
    private String registrationDate;
    private String duration;
    private String expiryDate;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "expiryId", referencedColumnName = "expiryId")
    private DomHostMapExpiryEntity domHostMapExpiryEntity;
}