// Import necessary classes and annotations
package com.example.hostinghub.repository;

import com.example.hostinghub.entity.HostDomainMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// Define this interface as a Spring Repository
@Repository
public interface HostDomainMapRepository extends JpaRepository<HostDomainMapEntity, String> {

    // Find a HostDomainMapEntity by hostDomainId
    HostDomainMapEntity findByHostDomainId(String hostDomainId);

    // Custom query to find the maximum hostDomainId
    @Query("SELECT MAX(h.hostDomainId) FROM HostDomainMapEntity h")
    String findHighestHostDomainId();
}
