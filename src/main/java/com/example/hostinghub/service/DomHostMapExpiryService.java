package com.example.hostinghub.service;

import com.example.hostinghub.entity.DomHostMapExpiryEntity;
import com.example.hostinghub.mappers.DomHostExpiryMapper;
import com.example.hostinghub.response.DomHostMapExpiryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.example.hostinghub.repository.DomHostMapExpiryRepository;

import java.util.List;

@Service
@Slf4j
public class DomHostMapExpiryService {

        // private final DomHostMapExpiryResponse domHostMapExpiryResponse;
        private final DomHostMapExpiryRepository domHostMapExpiryRepository;
        private final DomHostExpiryMapper domhostExpiryMapper;

        @Autowired
        public DomHostMapExpiryService(DomHostMapExpiryRepository domHostMapExpiryRepository, DomHostExpiryMapper domhostExpiryMapper) {
            this.domHostMapExpiryRepository = domHostMapExpiryRepository;
            this.domhostExpiryMapper = domhostExpiryMapper;
//        this.domHostMapExpiryResponse= domHostMapExpiryResponse;
        }

        public List<DomHostMapExpiryResponse> getAllDomHost() {
            try {
                List<DomHostMapExpiryEntity> hostMapExpiryEntities = domHostMapExpiryRepository.findAll();
                log.info("The size of the domains is {} : ", hostMapExpiryEntities.size());
                log.info("The retrieved domains are {} : ", hostMapExpiryEntities);

                // Use Stream API for mapping
                return domhostExpiryMapper.entitiesToResponses(hostMapExpiryEntities);
            } catch (DataAccessException e) {
                log.error("Data access error occurred while retrieving all domains: " + e.getMessage(), e);
                // Consider throwing a custom exception or let it propagate
                throw new RuntimeException("Error retrieving domains from the database", e);
            } catch (Exception e) {
                log.error("Unexpected error occurred while retrieving all domains: " + e.getMessage(), e);
                // Consider throwing a custom exception or let it propagate
                throw new RuntimeException("Unexpected error retrieving domains", e);
            }
        }
}
