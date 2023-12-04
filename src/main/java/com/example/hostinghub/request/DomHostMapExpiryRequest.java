package com.example.hostinghub.request;

import com.example.hostinghub.entity.HostDomainMapEntity;
import lombok.Data;

@Data
public class DomHostMapExpiryRequest {
    private String expiryId;

    private String domainName;
    private String hostProvider;
    private String payment;
    private String registrationDate;
    private String expiryDate;
    private String duration;
    private long daysLeft;
    private String hostDomainId;

    private HostDomainMapEntity hostDomainMapEntity;
}