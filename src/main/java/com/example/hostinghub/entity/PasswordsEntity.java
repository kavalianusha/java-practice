package com.example.hostinghub.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="PasswordsTable")
@AllArgsConstructor
@NoArgsConstructor
public class PasswordsEntity {

    @Id
    public String passwordId;
    public String userName;
    public String password;
    public String registrationDate;
    public String expiryDate;
    public String daysLeft;

   /* @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domainId", referencedColumnName = "domainId")
    @JsonIgnore
    private DomainEntity domainEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hostingId") // Name of the foreign key column in PasswordsEntity
    @JsonIgnore
    private HostingEntity hostingEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId") // Name of the foreign key column in PasswordsEntity
    @JsonIgnore
    private ItReturnsEntity itReturnsEntity;*/

}
