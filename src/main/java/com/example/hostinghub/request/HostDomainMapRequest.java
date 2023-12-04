package com.example.hostinghub.request;

// Import Lombok's Data annotation for generating boilerplate code
import com.example.hostinghub.entity.DomHostMapExpiryEntity;
import lombok.Data;

// Use Lombok's Data annotation to generate getters, setters, equals, and hashCode
@Data
// Define the class for handling host domain map requests
public class HostDomainMapRequest {

        // Fields for admin request data
        private String hostDomainId;

        private String domainName;

        private String hostProvider;

        private String payment;

        private String registrationDate;

        private String duration;

        private String expiryDate;

        private DomHostMapExpiryEntity domHostMapExpiryEntity;

}
