package com.example.hostinghub.repository;

import com.example.hostinghub.entity.ItReturnsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItReturnsRepository extends JpaRepository<ItReturnsEntity, String> {

    // Custom query to find the highest customer ID
    @Query("SELECT MAX(i.customerId) FROM ItReturnsEntity i")
    String findHighestCustomerId();

    // Find an IT returns entity by its customer ID
    ItReturnsEntity findByCustomerId(String customerId);

    // Delete an IT returns entity by its customer ID
    void deleteByCustomerId(String customerId);
}
