package com.example.hostinghub.repository;

import com.example.hostinghub.entity.DomHostMapExpiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DomHostMapExpiryRepository  extends JpaRepository<DomHostMapExpiryEntity, String> {

    @Query("SELECT MAX(e.expiryId) FROM DomHostMapExpiryEntity e")
    String findHighestExpiryId();
}
