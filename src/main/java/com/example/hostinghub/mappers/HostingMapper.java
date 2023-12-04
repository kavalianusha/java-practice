// Import necessary classes and annotations
package com.example.hostinghub.mappers;

import com.example.hostinghub.entity.HostingEntity;
import com.example.hostinghub.entity.PasswordsEntity;
import com.example.hostinghub.request.HostingRequest;
import com.example.hostinghub.request.HostingUpdateRequest;
import com.example.hostinghub.response.HostingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

// Define this interface as a MapStruct Mapper
@Mapper(componentModel = "spring")
public interface HostingMapper {

        @Mappings({
                @Mapping(target = "hostingId", source = "reqDto.hostingId"),
                @Mapping(target = "hostingProvider", source = "reqDto.hostingProvider"),
                @Mapping(target = "url", source = "reqDto.url"),
                @Mapping(target = "login", source = "reqDto.login"),
                @Mapping(target = "password", source = "reqDto.password"),
                @Mapping(target = "registrationEmailId", source = "reqDto.registrationEmailId"),
                @Mapping(target = "registrationPhoneNumber", source = "reqDto.registrationPhoneNumber"),
                @Mapping(target = "registrationDomain", source = "reqDto.registrationDomain"),
                @Mapping(target = "registrationDate", source = "reqDto.registrationDate"),
                @Mapping(target = "expiryDate", source = "reqDto.expiryDate"),
                @Mapping(target = "hostingCpannelUrl", source = "reqDto.hostingCpannelUrl"),
                @Mapping(target = "userName", source = "reqDto.userName"),
                @Mapping(target = "hostingDnsName", source = "reqDto.hostingDnsName")
        })
        HostingEntity entityToRequest(HostingRequest reqDto);

        @Mappings({
                @Mapping(target = "hostingProvider", source = "hostingEntity.hostingProvider"),
                @Mapping(target = "url", source = "hostingEntity.url"),
                @Mapping(target = "login", source = "hostingEntity.login"),
                @Mapping(target = "password", source = "hostingEntity.password"),
                @Mapping(target = "registrationEmailId", source = "hostingEntity.registrationEmailId"),
                @Mapping(target = "registrationPhoneNumber", source = "hostingEntity.registrationPhoneNumber"),
                @Mapping(target = "registrationDomain", source = "hostingEntity.registrationDomain"),
                @Mapping(target = "registrationDate", source = "hostingEntity.registrationDate"),
                @Mapping(target = "expiryDate", source = "hostingEntity.expiryDate"),
                @Mapping(target = "hostingCpannelUrl", source = "hostingEntity.hostingCpannelUrl"),
                @Mapping(target = "userName", source = "hostingEntity.userName"),
                @Mapping(target = "hostingDnsName", source = "hostingEntity.hostingDnsName"),
                @Mapping(target = "passwordsEntity", source = "hostingEntity.passwordsEntity")
        })
        HostingResponse requestToEntity(HostingEntity hostingEntity);

        @Mappings({
                @Mapping(target = "hostingProvider", source = "host.hostingProvider"),
                @Mapping(target = "url", source = "host.url"),
                @Mapping(target = "login", source = "host.login"),
                @Mapping(target = "password", source = "host.password"),
                @Mapping(target = "registrationEmailId", source = "host.registrationEmailId"),
                @Mapping(target = "registrationPhoneNumber", source = "host.registrationPhoneNumber"),
                @Mapping(target = "registrationDomain", source = "host.registrationDomain"),
                @Mapping(target = "registrationDate", source = "host.registrationDate"),
                @Mapping(target = "expiryDate", source = "host.expiryDate"),
                @Mapping(target = "hostingCpannelUrl", source = "host.hostingCpannelUrl"),
                @Mapping(target = "userName", source = "host.userName"),
                @Mapping(target = "hostingDnsName", source = "host.hostingDnsName"),
                @Mapping(target = "passwordsEntity", source = "host.passwordsEntity")
        })
        List<HostingResponse> responseToEntity(List<HostingEntity> host);

        @Mappings({
                @Mapping(target = "hostingProvider", source = "hostingUpdateRequest.hostingProvider"),
                @Mapping(target = "url", source = "hostingUpdateRequest.url"),
                @Mapping(target = "login", source = "hostingUpdateRequest.login"),
                @Mapping(target = "password", source = "hostingUpdateRequest.password"),
                @Mapping(target = "registrationEmailId", source = "hostingUpdateRequest.registrationEmailId"),
                @Mapping(target = "registrationPhoneNumber", source = "hostingUpdateRequest.registrationPhoneNumber"),
                @Mapping(target = "registrationDomain", source = "hostingUpdateRequest.registrationDomain"),
                @Mapping(target = "registrationDate", source = "hostingUpdateRequest.registrationDate"),
                @Mapping(target = "expiryDate", source = "hostingUpdateRequest.expiryDate"),
                @Mapping(target = "hostingCpannelUrl", source = "hostingUpdateRequest.hostingCpannelUrl"),
                @Mapping(target = "userName", source = "hostingUpdateRequest.userName"),
                @Mapping(target = "hostingDnsName", source = "hostingUpdateRequest.hostingDnsName"),
                @Mapping(target = "passwordsEntity", source = "hostingUpdateRequest.passwordsEntity")
        })
        HostingEntity updateEntityFromRequest(HostingEntity hostingEntity, HostingUpdateRequest hostingUpdateRequest);
        PasswordsEntity requestToUpdatePasswordEntity(HostingUpdateRequest hostingUpdateRequest);
        PasswordsEntity requestToPasswordEntity(HostingRequest request);
}
