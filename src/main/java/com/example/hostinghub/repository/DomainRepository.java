package com.example.hostinghub.repository;

import com.example.hostinghub.entity.DomainEntity;
import com.example.hostinghub.response.DomainResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository  // Marks this interface as a Spring repository
public interface DomainRepository extends JpaRepository<DomainEntity, String> {

    // Define a custom query using the Query annotation.
    // This query finds the maximum domainId in the DomainEntity table.
    @Query("SELECT MAX(d.domainId) FROM DomainEntity d")
    String findHighestDomainId();  // Method to execute the custom query.

    // Method to find a domain entity by its domainId.

    DomainEntity findByDomainId(String domainId);

    // Method to delete a domain entity by its domainId.
    void deleteByDomainId(String domainId);

    @Query("SELECT h.domainName FROM DomainEntity h")
    List<String> findAllDomainNames();
}