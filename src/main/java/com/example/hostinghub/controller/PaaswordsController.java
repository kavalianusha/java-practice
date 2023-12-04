package com.example.hostinghub.controller;

import com.example.hostinghub.entity.PasswordsEntity;
import com.example.hostinghub.response.PasswordsResponse;
import com.example.hostinghub.service.PasswordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api") // Add a base mapping for your API@CrossOrigin(origins = "*")
public class PaaswordsController {

    @Autowired
    private PasswordsService passwordsService;

    @GetMapping("/passwords/all")
    public List<PasswordsResponse> getAllPasswords() {
        System.out.println("Request reached PasswordsController");
        List<PasswordsResponse> responses = passwordsService.getAllPasswords();
        System.out.println("PasswordsResponse size: " + responses.size());
        return responses;
    }

}

