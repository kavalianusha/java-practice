package com.example.hostinghub.mappers;

import com.example.hostinghub.entity.PasswordsEntity;
import com.example.hostinghub.response.PasswordsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PasswordsMapper {

    @Mappings({
            @Mapping(target = "id", source = "entities.id"),
            @Mapping(target = "userName", source = "entities.userName"),
            @Mapping(target = "password", source = "entities.password"),
            @Mapping(target = "registrationDate", source = "entities.registrationDate"),
            @Mapping(target = "expiryDate", source = "entities.expiryDate"),
            @Mapping(target = "daysLeft", source = "entities.daysLeft"),
    })
    List<PasswordsResponse> entitiesToResponses(List<PasswordsEntity> entities);


    @Mappings({
            @Mapping(target = "passwordId", source = "entity.passwordId"),
            @Mapping(target = "userName", source = "entity.userName"),
            @Mapping(target = "password", source = "entity.password"),
            @Mapping(target = "registrationDate", source = "entity.registrationDate"),
            @Mapping(target = "expiryDate", source = "entity.expiryDate"),
            @Mapping(target = "daysLeft", source = "entity.daysLeft"),
            //@Mapping(target = "domainId", source = "entity.domainEntity.domainId"),
            //@Mapping(target = "hostingId", source = "entity.hostingEntity.hostingId"),
            //@Mapping(target = "customerId", source = "entity.itReturnsEntity.customerId")//Map domainId from domainEntity
    })
    PasswordsResponse entityToResponse(PasswordsEntity entity);

}
