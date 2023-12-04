package com.example.hostinghub.service;


import com.example.hostinghub.entity.ItReturnsEntity;
import com.example.hostinghub.entity.PasswordsEntity;
import com.example.hostinghub.mappers.ItReturnsMapper;
import com.example.hostinghub.repository.ItReturnsRepository;
import com.example.hostinghub.repository.PasswordRepository;
import com.example.hostinghub.request.ItReturnsRequest;
import com.example.hostinghub.request.ItReturnsUpdateRequest;
import com.example.hostinghub.response.ItReturnsResponse;
import com.example.hostinghub.response.ResultResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class ItReturnsServiceTest {

    @Mock
    private ItReturnsRepository itReturnsRepository;

    @Mock
    private ItReturnsMapper itReturnsMapper;

    @Mock
    private PasswordRepository passwordRepository;

    public ItReturnsService itReturnsService = new ItReturnsService(itReturnsRepository, itReturnsMapper,passwordRepository);

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(itReturnsService, "itReturnsRepository", itReturnsRepository);
        ReflectionTestUtils.setField(itReturnsService, "itReturnsMapper", itReturnsMapper);
        ReflectionTestUtils.setField(itReturnsService, "passwordRepository",passwordRepository);
    }

    @Test
    public void registerItReturns() {

        ItReturnsEntity entity = new ItReturnsEntity();
        entity.setCustomerId("CUST001");
        entity.setServiceType("ServiceType");
        entity.setRegisteredEmail("test@example.com");
        entity.setRegisteredMobileNo("1234567890");
        entity.setRegistrationDate("12/01/2023");
        entity.setExpiryDate("12/02/2023");
        entity.setLoginUrl("www.example.com");
        entity.setUserName("user");
        entity.setPassword("password");
        entity.setCreatedBy("admin");
        entity.setCreatedDate("12/01/2023");

        ItReturnsRequest request = new ItReturnsRequest();
        request.setCustomerId("CUST001");
        request.setServiceType("ServiceType");
        request.setRegisteredEmail("test@example.com");
        request.setRegisteredMobileNo("1234567890");
        request.setRegistrationDate("12/01/2023");
        request.setExpiryDate("12/02/2023");
        request.setCreatedDate("12/02/2023");
        request.setLoginUrl("www.example.com");
        request.setUserName("user");
        request.setPassword("password");
        request.setCreatedBy("admin");

        PasswordsEntity passwordsEntity = new PasswordsEntity();
        passwordsEntity.setPasswordId("PWO001");
        passwordsEntity.setDaysLeft("122");
        passwordsEntity.setPassword("anusha");
        passwordsEntity.setExpiryDate("12/05/2023");
        passwordsEntity.setUserName("akhil");
        passwordsEntity.setRegistrationDate("12/01/2023");

        request.setPasswordsEntity(passwordsEntity);

        Mockito.when(itReturnsRepository.findHighestCustomerId()).thenReturn("CUST001");
        Mockito.when(itReturnsMapper.requestToEntity(ArgumentMatchers.any())).thenReturn(entity);
        Mockito.when(itReturnsMapper.requestToPasswordEntity(ArgumentMatchers.any())).thenReturn(passwordsEntity);
        Mockito.when(itReturnsRepository.save(entity)).thenReturn(entity);

        ResponseEntity<?> itReturnsEntity = itReturnsService.registerItReturns(request);

        assertNotNull(itReturnsEntity);

        assertEquals("CUST002", request.getCustomerId());
        assertEquals("ServiceType", request.getServiceType());
        assertEquals("test@example.com", request.getRegisteredEmail());
        assertEquals("1234567890", request.getRegisteredMobileNo());
        assertEquals("12/01/2023", request.getRegistrationDate());
        assertEquals("12/02/2023",request.getExpiryDate());
        assertEquals("www.example.com", request.getLoginUrl());
        assertEquals("user", request.getUserName());
        assertEquals("password", request.getPassword());
        assertEquals("admin", request.getCreatedBy());
        assertEquals("12/02/2023", request.getCreatedDate());

    }


    @Test
    public void getllItReturns(){
        ItReturnsEntity itReturns1=new ItReturnsEntity();

        itReturns1.setCustomerId("CUST001");
        itReturns1.setServiceType("ServiceType");
        itReturns1.setRegisteredEmail("keerth@gamil.com");
        itReturns1.setRegisteredMobileNo("000000000");
        itReturns1.setRegistrationDate("2023-01-01");
        itReturns1.setLoginUrl("www.example.com");
        itReturns1.setUserName("user");
        itReturns1.setPassword("password");
        itReturns1.setCreatedBy("admin");
        itReturns1.setCreatedDate("2023-01-01");

        ItReturnsEntity itReturns2=new ItReturnsEntity();
        itReturns2.setCustomerId("CUST002");
        itReturns2.setServiceType("ServiceType");
        itReturns2.setRegisteredEmail("keerth@gamil.com");
        itReturns2.setRegisteredMobileNo("000000000");
        itReturns2.setRegistrationDate("2023-01-01");
        itReturns2.setLoginUrl("www.example.com");
        itReturns2.setUserName("user");
        itReturns2.setPassword("password1");
        itReturns2.setCreatedBy("admin1");
        itReturns2.setCreatedDate("2023-01-01");


        List<ItReturnsEntity> itReturnsList=new ArrayList<>();
        itReturnsList.add(itReturns1);
        itReturnsList.add(itReturns2);

        Mockito.when(itReturnsRepository.findAll()).thenReturn(itReturnsList);

        List<ItReturnsResponse> result = itReturnsService.getAllItReturns();

        assertNotNull(result);

        assertEquals("CUST001" ,itReturns1.getCustomerId());
        assertEquals("ServiceType" ,itReturns1.getServiceType());
        assertEquals("keerth@gamil.com" ,itReturns1.getRegisteredEmail());
        assertEquals("000000000" ,itReturns1.getRegisteredMobileNo());
        assertEquals("2023-01-01" ,itReturns1.getRegistrationDate());
        assertEquals("www.example.com",itReturns1.getLoginUrl());
        assertEquals("user" ,itReturns1.getUserName());
        assertEquals("password" ,itReturns1.getPassword());
        assertEquals("admin",itReturns1.getCreatedBy());
        assertEquals("2023-01-01",itReturns1.getCreatedDate());

        assertEquals("CUST002" ,itReturns2.getCustomerId());
        assertEquals("ServiceType" ,itReturns2.getServiceType());
        assertEquals("keerth@gamil.com" ,itReturns2.getRegisteredEmail());
        assertEquals("000000000" ,itReturns2.getRegisteredMobileNo());
        assertEquals("2023-01-01" ,itReturns2.getRegistrationDate());
        assertEquals("www.example.com",itReturns2.getLoginUrl());
        assertEquals("user" ,itReturns2.getUserName());
        assertEquals("password1" ,itReturns2.getPassword());
        assertEquals("admin1",itReturns2.getCreatedBy());
        assertEquals("2023-01-01",itReturns2.getCreatedDate());

    }

    @Test
    public void getItReturnsByCustomerId(){
        ItReturnsEntity entity=new ItReturnsEntity();
        entity.setCustomerId("CUST001");
        entity.setServiceType("Service");
        entity.setRegisteredEmail("keerth@gmail.com");
        entity.setRegisteredMobileNo("9090909090");
        entity.setRegistrationDate("2023-10-10");
        entity.setLoginUrl("www.example.com");
        entity.setUserName("usre");
        entity.setPassword("password");
        entity.setCreatedBy("admin");
        entity.setCreatedDate("2023-10-10");

        Mockito.when(itReturnsRepository.findById(entity.getCustomerId())).thenReturn(Optional.of(entity));

        ItReturnsEntity result =itReturnsService.getItReturnsByCustomerId(entity.getCustomerId());

        assertNotNull(result);
        assertEquals("CUST001" ,result.getCustomerId());
        assertEquals("Service",result.getServiceType());
        assertEquals("keerth@gmail.com",result.getRegisteredEmail());
        assertEquals("9090909090",result.getRegisteredMobileNo());
        assertEquals("2023-10-10",result.getRegistrationDate());
        assertEquals("www.example.com",result.getLoginUrl());
        assertEquals("usre",result.getUserName());
        assertEquals("password",result.getPassword());
        assertEquals("admin",result.getCreatedBy());
        assertEquals("2023-10-10",result.getCreatedDate());




    }

    @Test
    public void updateItReturns(){

        ItReturnsEntity existingItReturn =new ItReturnsEntity();
        existingItReturn.setCustomerId("CUST001");
        existingItReturn.setServiceType("service");
        existingItReturn.setRegisteredEmail("keerthi@gmail.com");
        existingItReturn.setRegisteredMobileNo("9090909090");
        existingItReturn.setRegistrationDate("10/10/2023");
        existingItReturn.setExpiryDate("10/02/2024");
        existingItReturn.setLoginUrl("www.example.com");
        existingItReturn.setUserName("user1");
        existingItReturn.setPassword("password");


        ItReturnsUpdateRequest updateItReturns= new ItReturnsUpdateRequest();
        updateItReturns.setServiceType("service");
        updateItReturns.setRegisteredEmail("keerthi@gmail.com");
        updateItReturns.setRegisteredMobileNo("9090909090");
        updateItReturns.setRegistrationDate("10/10/2023");
        updateItReturns.setExpiryDate("10/02/2024");
        updateItReturns.setLoginUrl("www.example.com");
        updateItReturns.setUserName("user1");
        updateItReturns.setPassword("password");

        PasswordsEntity passwordsEntity = new PasswordsEntity();
        passwordsEntity.setPasswordId("PWO001");
        passwordsEntity.setDaysLeft("122");
        passwordsEntity.setPassword("anusha");
        passwordsEntity.setExpiryDate("10/02/2024");
        passwordsEntity.setUserName("akhil");
        passwordsEntity.setRegistrationDate("10/10/2023");

        updateItReturns.setPasswordsEntity(passwordsEntity);

        Mockito.when(itReturnsRepository.findByCustomerId(existingItReturn.getCustomerId())).thenReturn(existingItReturn);
        Mockito.when(itReturnsMapper.updateEntityFromRequest(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(existingItReturn);
        Mockito.when(itReturnsMapper.requestToUpdatePasswordEntity(ArgumentMatchers.any())).thenReturn(passwordsEntity);
//        Mockito.when(itReturnsRepository.save(existingItReturn)).thenReturn(existingItReturn);

        ResultResponse result = itReturnsService.updateItReturns(existingItReturn.getCustomerId(), updateItReturns);

        assertNotNull(result);

        assertEquals("service",updateItReturns.getServiceType());
        assertEquals("keerthi@gmail.com",updateItReturns.getRegisteredEmail());
        assertEquals("9090909090",updateItReturns.getRegisteredMobileNo());
        assertEquals("10/10/2023",updateItReturns.getRegistrationDate());
        assertEquals("10/02/2024",updateItReturns.getExpiryDate());
        assertEquals("www.example.com",updateItReturns.getLoginUrl());
        assertEquals("user1",updateItReturns.getUserName());
        assertEquals("password",updateItReturns.getPassword());

    }

    @Test
    public void deleteItreturnsById(){

        ItReturnsEntity entity = new ItReturnsEntity();
        entity.setCustomerId("CUST001");
        entity.setServiceType("service");
        entity.setRegisteredEmail("keerthi@gmail.com");
        entity.setRegisteredMobileNo("8989898989");
        entity.setRegistrationDate("12/11/2023");
        entity.setLoginUrl("www.example.com");
        entity.setUserName("user1");
        entity.setPassword("oops");
        entity.setCreatedBy("admin");
        entity.setExpiryDate("12/02/2024");
        entity.setCreatedDate("12/11/2023");

        PasswordsEntity passwordsEntity = new PasswordsEntity();
        passwordsEntity.setPasswordId("PWD001");
        passwordsEntity.setDaysLeft("122");
        passwordsEntity.setPassword("anusha");
        passwordsEntity.setExpiryDate("12/02/2024");
        passwordsEntity.setUserName("akhil");
        passwordsEntity.setRegistrationDate("12/11/2023");

        entity.setPasswordsEntity(passwordsEntity);

        Mockito.when(itReturnsRepository.findByCustomerId("CUST001")).thenReturn(entity);
//        Mockito.when(passwordRepository.findHighestPasswordId()).thenReturn(String.valueOf(passwordsEntity));
        Mockito.doNothing().when(passwordRepository).delete(passwordsEntity);
        Mockito.doNothing().when(itReturnsRepository).delete(entity);

        // Call the service method
        ResponseEntity result = itReturnsService.deleteItreturnsById("CUST001");

        // Verify repository method invocations
        Mockito.verify(itReturnsRepository, Mockito.times(1)).findByCustomerId("CUST001");
        Mockito.verify(passwordRepository, Mockito.times(1)).delete(passwordsEntity);
        Mockito.verify(itReturnsRepository, Mockito.times(1)).delete(entity);

        assertNotNull(result);

        assertEquals("CUST001", entity.getCustomerId());
        assertEquals("service", entity.getServiceType());
        assertEquals("keerthi@gmail.com", entity.getRegisteredEmail());
        assertEquals("8989898989", entity.getRegisteredMobileNo());
        assertEquals("12/11/2023", entity.getRegistrationDate());
        assertEquals("www.example.com", entity.getLoginUrl());
        assertEquals("user1", entity.getUserName());
        assertEquals("oops", entity.getPassword());
        assertEquals("admin", entity.getCreatedBy());
        assertEquals("12/11/2023", entity.getCreatedDate());
    }


}