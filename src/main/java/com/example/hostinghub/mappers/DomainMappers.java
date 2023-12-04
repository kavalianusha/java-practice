package com.example.hostinghub.mappers;

import java.util.List;

import com.example.hostinghub.entity.PasswordsEntity;
import com.example.hostinghub.request.DomainUpdateRequest;
import org.mapstruct.Mapper;

import com.example.hostinghub.request.DomainRequest;
import com.example.hostinghub.entity.DomainEntity;
import com.example.hostinghub.response.DomainResponse;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring") // Marks this interface as a MapStruct mapper and specifies component model

public interface DomainMappers {
    
   

    @Mappings({
            @Mapping(target = "domainId", source = "request.domainId"),
            @Mapping(target = "domainName", source = "request.domainName"),
            @Mapping(target = "clientName",source = "request.clientName"),
            @Mapping(target = "duration",source = "request.duration"),
            @Mapping(target = "providerName", source = "request.providerName"),
            @Mapping(target = "domainUrl", source = "request.domainUrl"),
            @Mapping(target = "userName", source = "request.userName"),
            @Mapping(target = "password", source = "request.password"),
            @Mapping(target = "expiryDate", source = "request.expiryDate"),
            @Mapping(target = "registrationName", source = "request.registrationName"),
            @Mapping(target = "registrationDate",source = "request.registrationDate"),
            @Mapping(target = "registrationMobileNumber", source = "request.registrationMobileNumber"),
            @Mapping(target = "registrationEmail",source = "request.registrationEmail")
    })
    DomainEntity entityToRequest(DomainRequest request);

    @Mappings({
            @Mapping(target = "domainName", source = "domainUpdateRequest.domainName"),
            @Mapping(target = "providerName", source = "domainUpdateRequest.providerName"),
            @Mapping(target = "clientName",source = "domainUpdateRequest.clientName"),
            @Mapping(target = "duration",source = "domainUpdateRequest.duration"),
            @Mapping(target = "domainUrl", source = "domainUpdateRequest.domainUrl"),
            @Mapping(target = "userName", source = "domainUpdateRequest.userName"),
            @Mapping(target = "password", source = "domainUpdateRequest.password"),
            @Mapping(target = "expiryDate", source = "domainUpdateRequest.expiryDate"),
            @Mapping(target = "registrationName", source = "domainUpdateRequest.registrationName"),
            @Mapping(target = "registrationDate",source = "domainUpdateRequest.registrationDate"),
            @Mapping(target = "registrationMobileNumber", source = "domainUpdateRequest.registrationMobileNumber"),
            @Mapping(target = "registrationEmail",source = "domainUpdateRequest.registrationEmail"),
            @Mapping(target = "passwordsEntity", source = "domainUpdateRequest.passwordsEntity")
    })
    DomainEntity updateEntityFromRequest(DomainEntity existingDomainEntity, DomainUpdateRequest domainUpdateRequest);

    @Mappings({
            @Mapping(target = "domainName", source = "domains.domainName"),
            @Mapping(target = "providerName", source = "domains.providerName"),
            @Mapping(target = "clientName",source = "domains.clientName"),
            @Mapping(target = "duration",source = "domains.duration"),
            @Mapping(target = "domainUrl", source = "domains.domainUrl"),
            @Mapping(target = "userName", source = "domains.userName"),
            @Mapping(target = "password", source = "domains.password"),
            @Mapping(target = "expiryDate", source = "domains.expiryDate"),
            @Mapping(target = "registrationName", source = "domains.registrationName"),
            @Mapping(target = "registrationDate",source = "domains.registrationDate"),
            @Mapping(target = "registrationMobileNumber", source = "domains.registrationMobileNumber"),
            @Mapping(target = "registrationEmail",source = "domains.registrationEmail"),
            @Mapping(target = "passwordsEntity", source = "domains.passwordsEntity")
    })
    List<DomainResponse> responseToEntity(List<DomainEntity> domains);


    PasswordsEntity entityPasswordToRequest(DomainRequest domainRequest);

    @Mappings({
            @Mapping(target = "domainName", source = "domainEntity.domainName"),
            @Mapping(target = "providerName", source = "domainEntity.providerName"),
            @Mapping(target = "clientName",source = "domainEntity.clientName"),
            @Mapping(target = "duration",source = "domainEntity.duration"),
            @Mapping(target = "domainUrl", source = "domainEntity.domainUrl"),
            @Mapping(target = "userName", source = "domainEntity.userName"),
            @Mapping(target = "password", source = "domainEntity.password"),
            @Mapping(target = "expiryDate", source = "domainEntity.expiryDate"),
            @Mapping(target = "registrationName", source = "domainEntity.registrationName"),
            @Mapping(target = "registrationDate",source = "domainEntity.registrationDate"),
            @Mapping(target = "registrationMobileNumber", source = "domainEntity.registrationMobileNumber"),
            @Mapping(target = "registrationEmail",source = "domainEntity.registrationEmail"),
            @Mapping(target = "passwordsEntity", source = "domainEntity.passwordsEntity")
    })
    DomainResponse entityToResponse(DomainEntity domainEntity);

    PasswordsEntity updatePasswordsEntityFromRequest(DomainUpdateRequest updatedDomainRequest);
}