package com.example.hostinghub.response;

import lombok.Data;

@Data
public class DomHostMapExpiryResponse {

    private String expiryId;
    private String domainName;
    private String hostProvider;
    private String payment;
    private String registrationDate;
    private String expiryDate;
    private String duration;
    private long daysLeft;

}
