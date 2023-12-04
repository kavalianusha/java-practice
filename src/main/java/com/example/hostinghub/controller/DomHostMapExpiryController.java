package com.example.hostinghub.controller;

import com.example.hostinghub.response.DomHostMapExpiryResponse;
import com.example.hostinghub.service.DomHostMapExpiryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/domhostmapexpiry")
public class DomHostMapExpiryController {

    @Autowired
    private DomHostMapExpiryService service;

    @GetMapping("/all")
    public ResponseEntity<List<DomHostMapExpiryResponse>> getAllDomains() {
        try {
            List<DomHostMapExpiryResponse> domhostexpiry = service.getAllDomHost();
            log.info("Controller - Received response from service: {}", domhostexpiry);
            return new ResponseEntity<>(domhostexpiry, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Controller - Error occurred: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
