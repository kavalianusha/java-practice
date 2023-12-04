package com.example.hostinghub.mappers;

import com.example.hostinghub.entity.DomHostMapExpiryEntity;
import com.example.hostinghub.request.DomHostMapExpiryRequest;
import com.example.hostinghub.request.HostDomainMapUpdateRequest;
import com.example.hostinghub.response.DomHostMapExpiryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DomHostExpiryMapper {
    DomHostExpiryMapper INSTANCE = Mappers.getMapper(DomHostExpiryMapper.class);


    @Mappings({
            @Mapping(target = "expiryId", source = "request.expiryId"),
            @Mapping(target = "domainName", source = "request.domainName"),
            @Mapping(target = "hostProvider", source = "request.hostProvider"),
            @Mapping(target = "payment", source = "request.payment"),
            @Mapping(target = "registrationDate", source = "request.registrationDate"),
            @Mapping(target = "expiryDate", source = "request.expiryDate"),
            @Mapping(target = "duration", source = "request.duration"),
            @Mapping(target = "daysLeft",source = "request.daysLeft"), // You may need custom logic to calculate daysLeft
    })
    DomHostMapExpiryEntity requestToEntity(DomHostMapExpiryRequest request);

    @Mappings({
            @Mapping(target = "expiryId", source = "entities.expiryId"),
            @Mapping(target = "domainName", source = "entities.domainName"),
            @Mapping(target = "hostProvider", source = "entities.hostProvider"),
            @Mapping(target = "payment", source = "entities.payment"),
            @Mapping(target = "registrationDate", source = "entities.registrationDate"),
            @Mapping(target = "expiryDate", source = "entities.expiryDate"),
            @Mapping(target = "duration", source = "entities.duration"),
            @Mapping(target = "daysLeft", source = "entities.daysLeft"),
    })
    List<DomHostMapExpiryResponse> entitiesToResponses(List<DomHostMapExpiryEntity> entities);


    DomHostMapExpiryEntity updateToEntity(HostDomainMapUpdateRequest hostDomainMapRequest);


}
