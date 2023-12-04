package com.example.hostinghub.controller;

import com.example.hostinghub.entity.ItReturnsEntity;
import com.example.hostinghub.request.ItReturnsRequest;
import com.example.hostinghub.request.ItReturnsUpdateRequest;
import com.example.hostinghub.response.AdminResponse;
import com.example.hostinghub.response.ItReturnsResponse;
import com.example.hostinghub.response.ResultResponse;
import com.example.hostinghub.service.ItReturnsService;
import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "*")
public class ItReturnsController {

    @Autowired
    private ItReturnsService itReturnsService;

    // Endpoint to register a new IT return
    @PostMapping("/api/itreturns")
    public ResponseEntity<?> registerItReturns(@RequestBody ItReturnsRequest itReturnsRequest) {
        return itReturnsService.registerItReturns(itReturnsRequest);
    }

    // Endpoint to get an IT return by its customer ID
    @GetMapping("/api/itreturns/{customerId}")
    public ResponseEntity<ItReturnsEntity> getItReturnsByCustomerId(@PathVariable String customerId) {
        ItReturnsEntity itReturnsEntity = itReturnsService.getItReturnsByCustomerId(customerId);

        if (itReturnsEntity != null) {
            return ResponseEntity.ok(itReturnsEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to get all IT returns
 /*   @GetMapping("/api/itreturns")
    public List<ItReturnsResponse> getAllItReturns() {
        try {
            return itReturnsService.getAllItReturns();
        } catch (Exception e) {
            // Handle exceptions appropriately and consider returning a meaningful error response.
            e.printStackTrace(); // Logging the exception for debugging purposes.
            return null;
        }
    }*/
    @GetMapping("/api/itreturns")
    public ResponseEntity<List<ItReturnsResponse>> getAllItReturns() {
        List<ItReturnsResponse> itResponses = itReturnsService.getAllItReturns();
        return ResponseEntity.ok(itResponses);
    }
    // Endpoint to update an IT return
    @PutMapping("/api/itreturns/{customerId}")
    public ResponseEntity<ResultResponse> updateItReturns(@PathVariable String customerId, @RequestBody ItReturnsUpdateRequest updatedItReturns) {
        ResultResponse result = itReturnsService.updateItReturns(customerId, updatedItReturns);

        if (result.getResult().equals("Update successful")) {
            return ResponseEntity.ok(result);
        } else if (result.getResult().equals("IT Returns not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    // Endpoint to delete an IT return
    @DeleteMapping("/api/itreturns/{customerId}")
        public ResponseEntity<?> deleteItReturns(@PathVariable String customerId) {
            return itReturnsService.deleteItreturnsById(customerId);
        }

    }
