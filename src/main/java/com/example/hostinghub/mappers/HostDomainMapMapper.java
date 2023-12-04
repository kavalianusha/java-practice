package com.example.hostinghub.mappers;

import com.example.hostinghub.entity.AdminEntity;
import com.example.hostinghub.entity.DomHostMapExpiryEntity;
import com.example.hostinghub.entity.HostDomainMapEntity;
import com.example.hostinghub.request.AdminUpdateRequest;
import com.example.hostinghub.request.HostDomainMapRequest;
import com.example.hostinghub.request.HostDomainMapUpdateRequest;
import com.example.hostinghub.response.HostDomainMapResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HostDomainMapMapper {

    HostDomainMapEntity requestToEntity(HostDomainMapRequest hostDomainMapRequest);

    HostDomainMapRequest entityToRequest(HostDomainMapEntity hostDomainMapEntity);

    List<HostDomainMapResponse> responseToEntity(List<HostDomainMapEntity> hostDomainMapEntities);

    HostDomainMapResponse entityToResponse(HostDomainMapEntity hostDomainMapEntity);

    HostDomainMapEntity updateEntityFromRequest(HostDomainMapUpdateRequest hostDomainMapUpdateRequest, @MappingTarget HostDomainMapEntity hostDomainMapEntity);

    DomHostMapExpiryEntity createDomHostMapExpiryEntity(HostDomainMapRequest hostDomainMapRequest);

    DomHostMapExpiryEntity updateDomEntityFromRequest(HostDomainMapUpdateRequest hostDomainMapUpdateRequest, @MappingTarget DomHostMapExpiryEntity domHostMapExpiryEntity);

    DomHostMapExpiryEntity requestToDomHostExpiryEntity(HostDomainMapRequest hostDomainMapRequest);

    DomHostMapExpiryEntity createDomHostMapExpiryEntity(HostDomainMapEntity hostDomainMapEntity);



}
