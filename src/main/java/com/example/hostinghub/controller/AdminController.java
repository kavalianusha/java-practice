package com.example.hostinghub.controller;

import com.example.hostinghub.repository.AdminRepository;
import com.example.hostinghub.request.AdminRequest;
import com.example.hostinghub.request.AdminUpdateRequest;
import com.example.hostinghub.response.AdminResponse;
import com.example.hostinghub.response.ResultResponse;
import com.example.hostinghub.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins="*")
@Slf4j
public class AdminController {

    // Autowire the AdminRepository and AdminService
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminService adminService;

/*@Autowired
    private AuthenticationManager authenticationManager;*/

   /* @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;*/

    // Handle POST request to register an admin
    @PostMapping("/admin/add")
    public ResponseEntity<?> registerUser(@RequestBody AdminRequest admin) {
        return adminService.registerAdmin(admin);
    }

    // Handle POST request to send OTP
    @PostMapping("/admin/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody AdminRequest request) {
        try {
            // Try to send OTP using adminService
            ResponseEntity<?> response = adminService.sendOtp(request);
            return response;
        } catch (IllegalArgumentException e) {
            // Handle IllegalArgumentException
            log.warn("error on login: {}", e.getMessage(), e);
            ResultResponse result = new ResultResponse();
            result.setResult(e.getMessage());
            return ResponseEntity.badRequest().body(result);
        } catch (Exception e) {
            // Handle other exceptions
            log.warn("error on login: {}", e.getMessage(), e);
            ResultResponse result = new ResultResponse();
            result.setResult("Failed to send OTP");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    // Handle POST request to verify OTP
    @PostMapping("/admin/login")
    public ResponseEntity<?> verifyOtp(@RequestBody AdminRequest request) {
        try {
            return adminService.verifyOtp(request);
        } catch (Exception e) {
            // Handle exceptions during OTP verification
            ResultResponse response = new ResultResponse();
            response.setResult("An error occurred while verifying OTP for Admin");
            log.error("An error occurred while verifying OTP for Admin", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Handle GET request to retrieve all admins
    @GetMapping("/admin/all")
    public ResponseEntity<List<AdminResponse>> getAllAdmins() {
        List<AdminResponse> adminResponses = adminService.getAllAdmins();
        return ResponseEntity.ok(adminResponses);
    }

    // Handle GET request to retrieve admin by ID
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<AdminResponse> getAdminById(@PathVariable String adminId) {
        AdminResponse admin = adminService.getAdminById(adminId);
        try {
            if (admin == null) {
                // Return 404 if admin is not found
                log.info("give me the correct adminId to get the details {} : ",adminId);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            // Handle exceptions during admin retrieval
            e.printStackTrace();
            log.info("give me the correct adminId to get the details {} : ",adminId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Handle PUT request to update admin
    @PutMapping("/admin/{adminId}")
    public ResponseEntity<?> updateAdmin(
            @PathVariable String adminId,
            @RequestBody AdminUpdateRequest adminRequest) {
        try {
            // Check if the admin with the given ID exists in the database
            if (!adminService.existsById(adminId)) {
                ResultResponse result = new ResultResponse();
                result.setResult("Admin with ID " + adminId + " not found.");
                log.info("The Admin with the given id is not found {} : ",adminId);
                return ResponseEntity.ok(result);
            }
            // Perform the update operation using the service
            adminService.updateAdmin(adminId, adminRequest);
            ResultResponse result = new ResultResponse();
            result.setResult("Admin with ID " + adminId + " has been updated.");
            log.info("The Admin details are updated successfully!! " + adminId);
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            // Log the exception
            ex.printStackTrace();

            ResultResponse result = new ResultResponse();
            result.setResult("An error occurred while updating the admin: " + ex.getMessage());
            log.info("An error occurred while updating the admin {}: ", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    // Handle DELETE request to delete admin by ID
    @DeleteMapping("/admin/{adminId}")
    public ResponseEntity<?> deleteAdminById(@PathVariable String adminId) {
        try {
            // Try to delete admin by ID
            adminService.deleteAdminById(adminId);
            ResultResponse result = new ResultResponse();
            result.setResult("Admin with ID " + adminId + " has been deleted.");
            log.info("The Admin is deleted successfully!!");
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            // Handle exceptions during admin deletion
            ResultResponse result = new ResultResponse();
            result.setResult("Admin with ID: " + adminId + " not found");
            log.info("The Admin id is not found in the db {} ", adminId);
            return ResponseEntity.ok(result);
        }
    }
 }

