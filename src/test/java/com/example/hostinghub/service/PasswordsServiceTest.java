package com.example.hostinghub.service;


import com.example.hostinghub.entity.PasswordsEntity;
import com.example.hostinghub.mappers.PasswordsMapper;
import com.example.hostinghub.repository.PasswordRepository;
import com.example.hostinghub.response.ItReturnsResponse;
import com.example.hostinghub.response.PasswordsResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class PasswordsServiceTest {
    
    @Mock
    private PasswordRepository passwordRepository;
    @Mock
    private PasswordsMapper passwordsMapper;
    
    public PasswordsService passwordsService = new PasswordsService(passwordRepository,passwordsMapper);
    
    @Before
    public void setUp(){
        ReflectionTestUtils.setField(passwordsService, "passwordRepository", passwordRepository);
        ReflectionTestUtils.setField(passwordsService, "passwordsMapper", passwordsMapper);   
    }

    @Test
    public void getAllPasswords() {
        PasswordsEntity passwordsResponse = new PasswordsEntity();
        passwordsResponse.setPasswordId("PWD001");
        passwordsResponse.setDaysLeft("122");
        passwordsResponse.setPassword("anusha");
        passwordsResponse.setExpiryDate("12/05/2023");
        passwordsResponse.setUserName("akhil");
        passwordsResponse.setRegistrationDate("12/01/2023");

        PasswordsEntity passwordsResponse1 = new PasswordsEntity();
        passwordsResponse1.setPasswordId("PWD001");
        passwordsResponse1.setDaysLeft("122");
        passwordsResponse1.setPassword("anusha");
        passwordsResponse1.setExpiryDate("12/05/2023");
        passwordsResponse1.setUserName("akhil");
        passwordsResponse1.setRegistrationDate("12/01/2023");

        PasswordsResponse response = new PasswordsResponse();

        List<PasswordsEntity> passwords = new ArrayList<>();
        passwords.add(passwordsResponse);
        passwords.add(passwordsResponse1);

        Mockito.when(passwordRepository.findAll()).thenReturn(passwords);
        Mockito.when(passwordsMapper.entitiesToResponses(passwords)).thenReturn(new ArrayList<>());


        List<PasswordsResponse> result = passwordsService.getAllPasswords();

        assertNotNull(result);

        assertEquals("PWD001",passwordsResponse.getPasswordId());
        assertEquals("122",passwordsResponse.getDaysLeft());
        assertEquals("anusha",passwordsResponse.getPassword());
        assertEquals("12/05/2023",passwordsResponse.getExpiryDate());
        assertEquals("akhil",passwordsResponse.getUserName());
        assertEquals("12/01/2023",passwordsResponse.getRegistrationDate());
    }
    
}