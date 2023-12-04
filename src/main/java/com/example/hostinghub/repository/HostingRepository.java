// Import necessary classes and annotations
package com.example.hostinghub.repository;

import com.example.hostinghub.entity.HostingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// Define this interface as a Spring Repository
@Repository
public interface HostingRepository extends JpaRepository<HostingEntity, String> {

    // Custom query to find the maximum hosting ID
    @Query("SELECT MAX(h.hostingId) FROM HostingEntity h")
    String findHighestHostId();

    // Custom query to retrieve all hosting providers
    @Query("SELECT h.hostingProvider FROM HostingEntity h")
    List<String> findAllHostingProvider();

    HostingEntity findByHostingId(String hostingId);
}
