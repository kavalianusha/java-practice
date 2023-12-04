package com.example.hostinghub.controller;

import com.example.hostinghub.request.DomainRequest;
import com.example.hostinghub.request.DomainUpdateRequest;
import com.example.hostinghub.response.DomainResponse;
import com.example.hostinghub.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="*")
public class DomainController {

    @Autowired
    private DomainService domainService;

    // Endpoint to register a new domain
    @PostMapping("/api/domain")
    public ResponseEntity<?> registerDomain(@RequestBody DomainRequest domainRequest) {
        return domainService.registerDomain(domainRequest);
    }

    // Endpoint to get a domain by its ID
    @GetMapping("/api/domain/{domainId}")
    public ResponseEntity<DomainResponse> getDomainById(@PathVariable String domainId) {
        DomainResponse domainResponse = domainService.getDomainById(domainId);

        if (domainResponse != null) {
            return ResponseEntity.ok(domainResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to get all domains
    @GetMapping("/api/domain")
    public List<DomainResponse> getAllDomains() {
        // Call the service to get all domains
        return domainService.getAllDomains();
    }

    // Endpoint to update a domain
    @PutMapping("/api/domain/{domainId}")
    public ResponseEntity<?> updateDomain(@PathVariable String domainId, @RequestBody DomainUpdateRequest updatedDomain) {
        return domainService.updateDomain(domainId, updatedDomain);
    }

    // Endpoint to delete a domain
    @DeleteMapping("/api/domain/{domainId}")
    public ResponseEntity<?> deleteDomain(@PathVariable String domainId) {
        return domainService.deleteDomainById(domainId);
    }

}
