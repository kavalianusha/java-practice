package com.example.hostinghub.response;

import com.example.hostinghub.entity.DomainEntity;
import com.example.hostinghub.entity.HostingEntity;
import com.example.hostinghub.entity.ItReturnsEntity;
import lombok.Data;

@Data
public class PasswordsResponse {

    public String passwordId;
    public String userName;
    public String password;
    public String registrationDate;
    public String expiryDate;
    public String daysLeft;
}
