// Import necessary classes and annotations
package com.example.hostinghub.repository;

import com.example.hostinghub.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Define this interface as a Spring Repository
@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, String> {

    // Find an AdminEntity by emailId
    AdminEntity findByEmailId(String emailId);

    // Custom query to find the maximum adminId
    @Query("SELECT MAX(a.adminId) FROM AdminEntity a")
    String findHighestAdminId();

    // Find an AdminEntity by OTP
    AdminEntity findByOtp(String otp);

    // Find an AdminEntity by adminId
    AdminEntity findByAdminId(String adminId);

    // Find an AdminEntity by emailId or userName
    AdminEntity findByEmailIdOrUserName(String emailId, String userName);

    Optional<AdminEntity> findByUserName(String userName);


}
