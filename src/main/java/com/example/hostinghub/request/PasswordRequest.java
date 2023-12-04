package com.example.hostinghub.request;

import com.example.hostinghub.entity.DomainEntity;
import lombok.Data;

@Data
public class PasswordRequest {

    public long id;
    public String userName;
    public String password;
    public String registrationDate;
    public String expiryDate;
    public String daysLeft;
}
