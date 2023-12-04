package com.example.hostinghub.repository;

import com.example.hostinghub.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//Define an interface EmailRepository that extends JpaRepository.
//It works with EmailEntity and uses String as the ID type.
public interface EmailRepository extends JpaRepository<EmailEntity, String> {

    // Define a custom query using the Query annotation.
    // This query finds the maximum emailId in the EmailEntity table.
    @Query("SELECT max(e.emailId) FROM EmailEntity e")
    String findHighestEmailId();  // Method to execute the custom query.
    EmailEntity findByEmailId(String emailId);
}