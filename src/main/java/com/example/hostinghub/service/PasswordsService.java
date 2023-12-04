package com.example.hostinghub.service;

import com.example.hostinghub.entity.PasswordsEntity;
import com.example.hostinghub.mappers.PasswordsMapper;
import com.example.hostinghub.repository.PasswordRepository;
import com.example.hostinghub.response.PasswordsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PasswordsService {

    private PasswordRepository passwordRepository;

    private PasswordsMapper passwordsMapper;

    @Autowired
    public PasswordsService(PasswordRepository passwordRepository,
                            PasswordsMapper passwordsMapper){
        this.passwordRepository=passwordRepository;
        this.passwordsMapper=passwordsMapper;
    }

    public List<PasswordsResponse> getAllPasswords() {
        List<PasswordsEntity> passwords = passwordRepository.findAll();
         log.info("PasswordsEntity size: " + passwords.size());
        return passwordsMapper.entitiesToResponses(passwords);
    }




}
