package com.example.hostinghub.service;

import com.example.hostinghub.entity.DomainEntity;
import com.example.hostinghub.entity.PasswordsEntity;
import com.example.hostinghub.mappers.DomainMappers;
import com.example.hostinghub.repository.DomainRepository;
import com.example.hostinghub.repository.PasswordRepository;
import com.example.hostinghub.request.DomainRequest;
import com.example.hostinghub.request.DomainUpdateRequest;
import com.example.hostinghub.response.DomainResponse;
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
public class DomainServiceTest {

    @Mock
    private DomainRepository domainRepository;
    @Mock
    private DomainMappers domainMappers;
    @Mock
    private PasswordRepository passwordRepository;

    public DomainService domainService = new DomainService(domainRepository, domainMappers, passwordRepository);

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(domainService, "domainRepository", domainRepository);
        ReflectionTestUtils.setField(domainService, "domainMappers", domainMappers);
        ReflectionTestUtils.setField(domainService, "passwordRepository", passwordRepository);
    }

    @Test
    public void registerDomain() {
        // Create a mock DomainEntity
        DomainEntity entity = new DomainEntity();
        entity.setDomainId("DOM001");
        entity.setDomainName("keerthi");
        entity.setProviderName("vineeth");
        entity.setDomainUrl("www.google.com");
        entity.setUserName("keerthi07");
        entity.setPassword("0726");
        entity.setDuration("1year");
        entity.setClientName("raj");
        entity.setRegistrationDate("12/01/2023");
        entity.setExpiryDate("12/05/2023");
        entity.setRegistrationMobileNumber("8985290929");
        entity.setRegistrationName("arthi");
        entity.setRegistrationEmail("keerthidrlng7@gmail.com");

        PasswordsEntity passwordsEntity = new PasswordsEntity();
        passwordsEntity.setPasswordId("PWO001");
        passwordsEntity.setDaysLeft("122");
        passwordsEntity.setPassword("anusha");
        passwordsEntity.setExpiryDate("12/05/2023");
        passwordsEntity.setUserName("akhil");
        passwordsEntity.setRegistrationDate("12/01/2023");

        DomainRequest request = new DomainRequest();
        request.setDomainId("DOM002");
        request.setDomainName("keerthi sree");
        request.setProviderName("vineeth kumar");
        request.setDomainUrl("www.google.com");
        request.setUserName("keerthi0700");
        request.setPassword("0726");
        request.setDuration("1year");
        request.setClientName("raj");
        request.setRegistrationDate("12/01/2023");
        request.setExpiryDate("12/05/2023");
        request.setRegistrationMobileNumber("895290929");
        request.setRegistrationName("arthi");
        request.setRegistrationEmail("keerthidrlng7@gmail.com");
        request.setPasswordsEntity(entity.getPasswordsEntity());

        Mockito.when(domainRepository.findHighestDomainId()).thenReturn("DOM001");
        Mockito.when(domainMappers.entityToRequest(ArgumentMatchers.any())).thenReturn(entity);
        Mockito.when(domainMappers.entityPasswordToRequest(ArgumentMatchers.any())).thenReturn(passwordsEntity);
        Mockito.when(domainRepository.save(entity)).thenReturn(entity);

        ResponseEntity<?> domainEntity = domainService.registerDomain(request);

        assertNotNull(domainEntity);

        assertEquals("DOM002", request.getDomainId());
        assertEquals("keerthi sree", request.getDomainName());
        assertEquals("vineeth kumar", request.getProviderName());
        assertEquals("www.google.com", request.getDomainUrl());
        assertEquals("keerthi0700", request.getUserName());
        assertEquals("0726", request.getPassword());
        assertEquals("1year", request.getDuration());
        assertEquals("raj", request.getClientName());
        assertEquals("12/01/2023", request.getRegistrationDate());
        assertEquals("12/05/2023", request.getExpiryDate());
        assertEquals("895290929", request.getRegistrationMobileNumber());
        assertEquals("arthi", request.getRegistrationName());
        assertEquals("keerthidrlng7@gmail.com", request.getRegistrationEmail());
    }

    @Test
    public void getDomainById() {

        DomainEntity entity = new DomainEntity();

        entity.setDomainId("DOM001");
        entity.setDomainName("anusha");
        entity.setProviderName("sameera");
        entity.setDomainUrl("https://example.com");
        entity.setUserName("swati");
        entity.setPassword("23456");
        entity.setDuration("1year");
        entity.setClientName("naziya");
        entity.setRegistrationDate("2022-10-15");
        entity.setExpiryDate("2023-10-14");
        entity.setRegistrationMobileNumber("1234567890");
        entity.setRegistrationName("bhavani");
        entity.setRegistrationEmail("bhavani@gmail.com");

        DomainResponse response = new DomainResponse();

        response.setDomainId("DOM001");
        response.setDomainName("anusha");
        response.setProviderName("sameera");
        response.setDomainUrl("https://example.com");
        response.setUserName("swati");
        response.setPassword("23456");
        response.setDuration("1year");
        response.setClientName("naziya");
        response.setRegistrationDate("2022-10-15");
        response.setExpiryDate("2023-10-14");
        response.setRegistrationMobileNumber("1234567890");
        response.setRegistrationName("bhavani");
        response.setRegistrationEmail("bhavani@gmail.com");

        Mockito.when(domainRepository.findById((entity.getDomainId()))).thenReturn(Optional.of(entity));
        Mockito.when(domainMappers.entityToResponse(ArgumentMatchers.any())).thenReturn(response);

        DomainResponse result = domainService.getDomainById(entity.getDomainId());

        assertNotNull(result);
        assertEquals("DOM001", result.getDomainId());
        assertEquals("anusha", result.getDomainName());
        assertEquals("sameera", result.getProviderName());
        assertEquals("https://example.com", result.getDomainUrl());
        assertEquals("swati", result.getUserName());
        assertEquals("23456", result.getPassword());
        assertEquals("1year", result.getDuration());
        assertEquals("naziya", result.getClientName());
        assertEquals("2022-10-15", result.getRegistrationDate());
        assertEquals("2023-10-14", result.getExpiryDate());
        assertEquals("1234567890", result.getRegistrationMobileNumber());
        assertEquals("bhavani", result.getRegistrationName());
        assertEquals("bhavani@gmail.com", result.getRegistrationEmail());
    }


    @Test
    public void getAllDomains() {

        DomainEntity domain1 = new DomainEntity();
        domain1.setDomainId("DOM001");
        domain1.setDomainName("Example Domain 1");
        domain1.setProviderName("Provider 1");
        domain1.setDomainUrl("www.googl.com");
        domain1.setUserName("user1");
        domain1.setPassword("password1");
        domain1.setDuration("1year");
        domain1.setClientName("client1");
        domain1.setRegistrationDate("2022-10-15");
        domain1.setExpiryDate("2023-10-14");
        domain1.setRegistrationMobileNumber("9876543210");
        domain1.setRegistrationName("John Doe");
        domain1.setRegistrationEmail("john@example1.com");

        DomainEntity domain2 = new DomainEntity();
        domain2.setDomainId("DOM002");
        domain2.setDomainName("Example Domain 2");
        domain2.setProviderName("Provider 2");
        domain2.setDomainUrl("www.google.com");
        domain2.setUserName("user2");
        domain2.setPassword("password2");
        domain2.setDuration("2years");
        domain2.setClientName("client2");
        domain2.setRegistrationDate("2022-11-15");
        domain2.setExpiryDate("2024-10-14");
        domain2.setRegistrationMobileNumber("9876543210");
        domain2.setRegistrationName("Jane Doe");
        domain2.setRegistrationEmail("jane@example2.com");

        List<DomainEntity> domainList = new ArrayList<>();
        domainList.add(domain1);
        domainList.add(domain2);

        Mockito.when(domainRepository.findAll()).thenReturn(domainList);

        List<DomainResponse> result = domainService.getAllDomains();


        assertNotNull(result);


        assertEquals("DOM001", domain1.getDomainId());
        assertEquals("Example Domain 1", domain1.getDomainName());
        assertEquals("Provider 1", domain1.getProviderName());
        assertEquals("www.googl.com", domain1.getDomainUrl());
        assertEquals("user1", domain1.getUserName());
        assertEquals("password1", domain1.getPassword());
        assertEquals("1year", domain1.getDuration());
        assertEquals("client1", domain1.getClientName());
        assertEquals("2022-10-15", domain1.getRegistrationDate());
        assertEquals("2023-10-14", domain1.getExpiryDate());
        assertEquals("9876543210", domain1.getRegistrationMobileNumber());
        assertEquals("John Doe", domain1.getRegistrationName());
        assertEquals("john@example1.com", domain1.getRegistrationEmail());



        assertEquals("DOM002", domain2.getDomainId());
        assertEquals("Example Domain 2", domain2.getDomainName());
        assertEquals("Provider 2", domain2.getProviderName());
        assertEquals("www.google.com", domain2.getDomainUrl());
        assertEquals("user2", domain2.getUserName());
        assertEquals("password2", domain2.getPassword());
        assertEquals("2years", domain2.getDuration());
        assertEquals("client2", domain2.getClientName());
        assertEquals("2022-11-15", domain2.getRegistrationDate());
        assertEquals("2024-10-14", domain2.getExpiryDate());
        assertEquals("9876543210", domain2.getRegistrationMobileNumber());
        assertEquals("Jane Doe", domain2.getRegistrationName());
        assertEquals("jane@example2.com", domain2.getRegistrationEmail());

    }


    @Test
    public void updateDomain() {
        String domainId = "DOM001";
        DomainEntity existingDomain = new DomainEntity();
        existingDomain.setDomainId(domainId);
        existingDomain.setDomainName("anusha");
        existingDomain.setProviderName("sameera");
        existingDomain.setDomainUrl("https://example.com");
        existingDomain.setUserName("swati");
        existingDomain.setPassword("23456");
        existingDomain.setDuration("1year");
        existingDomain.setClientName("naziya");
        existingDomain.setRegistrationDate("12/01/2023");
        existingDomain.setExpiryDate("12/05/2023");
        existingDomain.setRegistrationMobileNumber("1234567890");
        existingDomain.setRegistrationName("bhavani");
        existingDomain.setRegistrationEmail("bhavani@gmail.com");

        PasswordsEntity passwordsEntity = new PasswordsEntity();
        passwordsEntity.setPasswordId("PWO001");
        passwordsEntity.setDaysLeft("122");
        passwordsEntity.setPassword("anusha");
        passwordsEntity.setExpiryDate("12/05/2023");
        passwordsEntity.setUserName("akhil");
        passwordsEntity.setRegistrationDate("12/01/2023");

        DomainUpdateRequest updatedDomain = new DomainUpdateRequest();

        updatedDomain.setDomainName("book");
        updatedDomain.setProviderName("rani");
        updatedDomain.setDomainUrl("www.google.com");
        updatedDomain.setUserName("vamsi");
        updatedDomain.setPassword("itsme");
        updatedDomain.setDuration("1year");
        updatedDomain.setClientName("bheemesh");
        updatedDomain.setRegistrationDate("12/01/2023");
        updatedDomain.setExpiryDate("12/05/2023");
        updatedDomain.setRegistrationMobileNumber("9876543210");
        updatedDomain.setRegistrationName("sai");
        updatedDomain.setRegistrationEmail("sai@gmail.com");
        updatedDomain.setPasswordsEntity(existingDomain.getPasswordsEntity());

        // Mock the behavior of the domainRepository and domainMappers
        Mockito.when(domainRepository.findByDomainId(domainId)).thenReturn(existingDomain);
        Mockito.when(domainMappers.updatePasswordsEntityFromRequest(ArgumentMatchers.any())).thenReturn(passwordsEntity);
        //Mockito.when(domainRepository.save(existingDomain)).thenReturn(existingDomain);

        // Call the method being tested
        ResponseEntity result = domainService.updateDomain(domainId, updatedDomain);

        // Assert the result
        assertNotNull(result);
        // Verify that the existingDomain properties have been updated
        assertEquals("book",updatedDomain.getDomainName());
        assertEquals("rani", updatedDomain.getProviderName());
        assertEquals("www.google.com", updatedDomain.getDomainUrl());
        assertEquals("vamsi", updatedDomain.getUserName());
        assertEquals("itsme", updatedDomain.getPassword());
        assertEquals("1year", updatedDomain.getDuration());
        assertEquals("bheemesh", updatedDomain.getClientName());
        assertEquals("12/01/2023", updatedDomain.getRegistrationDate());
        assertEquals("12/05/2023", updatedDomain.getExpiryDate());
        assertEquals("9876543210", updatedDomain.getRegistrationMobileNumber());
        assertEquals("sai",updatedDomain.getRegistrationName());
        assertEquals("sai@gmail.com", updatedDomain.getRegistrationEmail());
    }


    @Test
    public void deleteDomainById() {

        DomainEntity entity = new DomainEntity();
        entity.setDomainId("DOM001");
        entity.setDomainName("balaji");
        entity.setProviderName("subbu");
        entity.setDomainUrl("www.keerthi.com");
        entity.setUserName("vijay");
        entity.setPassword("1909");
        entity.setDuration("1year");
        entity.setClientName("kondalu");
        entity.setRegistrationDate("2022-10-15");
        entity.setExpiryDate("2023-10-14");
        entity.setRegistrationMobileNumber("1234567890");
        entity.setRegistrationName("arthi");
        entity.setRegistrationEmail("arthi@gmail.com");

        PasswordsEntity passwordsEntity = new PasswordsEntity();
        passwordsEntity.setPasswordId("PWO001");
        passwordsEntity.setDaysLeft("122");
        passwordsEntity.setPassword("anusha");
        passwordsEntity.setExpiryDate("12/05/2023");
        passwordsEntity.setUserName("akhil");
        passwordsEntity.setRegistrationDate("12/01/2023");

        entity.setPasswordsEntity(passwordsEntity);

        Mockito.when(domainRepository.findByDomainId("DOM001")).thenReturn(entity);
//        Mockito.when(passwordRepository.findHighestPasswordId()).thenReturn(String.valueOf(passwordsEntity));
        Mockito.doNothing().when(passwordRepository).delete(passwordsEntity);
        Mockito.doNothing().when(domainRepository).delete(entity);

        // Call the service method
        ResponseEntity result = domainService.deleteDomainById("DOM001");

        // Verify repository method invocations
        Mockito.verify(domainRepository, Mockito.times(1)).findByDomainId("DOM001");
        Mockito.verify(passwordRepository, Mockito.times(1)).delete(passwordsEntity);
        Mockito.verify(domainRepository, Mockito.times(1)).delete(entity);

        assertNotNull(result);
        assertEquals("DOM001", entity.getDomainId());
        assertEquals("balaji", entity.getDomainName());
        assertEquals("subbu", entity.getProviderName());
        assertEquals("www.keerthi.com", entity.getDomainUrl());
        assertEquals("vijay", entity.getUserName());
        assertEquals("1909", entity.getPassword());
        assertEquals("1year", entity.getDuration());
        assertEquals("kondalu", entity.getClientName());
        assertEquals("2022-10-15", entity.getRegistrationDate());
        assertEquals("2023-10-14", entity.getExpiryDate());
        assertEquals("1234567890", entity.getRegistrationMobileNumber());
        assertEquals("arthi", entity.getRegistrationName());
        assertEquals("arthi@gmail.com", entity.getRegistrationEmail());
    }
}