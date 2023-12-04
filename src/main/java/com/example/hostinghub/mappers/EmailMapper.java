package com.example.hostinghub.mappers;

import java.util.List;

import com.example.hostinghub.request.EmailUpdateRequest;
import org.mapstruct.Mapper;

import com.example.hostinghub.entity.EmailEntity;
import com.example.hostinghub.request.EmailRequest;
import com.example.hostinghub.response.EmailResponse;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring") // Marks this interface as a MapStruct mapper and specifies component model

public interface EmailMapper {

    // Maps an EmailEntity to an EmailRequest
    EmailRequest requestToEntity(EmailEntity emailEntity);

    // Maps an EmailRequest to an EmailEntity
    EmailEntity entityToRequest(EmailRequest request);

    // Maps a list of EmailEntities to a list of EmailResponses
    List<EmailResponse> responseToEntity(List<EmailEntity> req);

    // Maps an EmailResponse to an EmailEntity
    EmailEntity entityToResponse(EmailResponse response);
    EmailEntity updateEntityFromRequest(EmailUpdateRequest emailUpdateRequest, @MappingTarget EmailEntity emailEntity);





}
