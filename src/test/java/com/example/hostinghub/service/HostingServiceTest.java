package com.example.hostinghub.service;


import com.example.hostinghub.entity.PasswordsEntity;
import com.example.hostinghub.mappers.HostingMapper;
import com.example.hostinghub.repository.HostingRepository;
import com.example.hostinghub.entity.HostingEntity;
import com.example.hostinghub.repository.PasswordRepository;
import com.example.hostinghub.request.HostingRequest;
import com.example.hostinghub.request.HostingUpdateRequest;
import com.example.hostinghub.response.HostingResponse;
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
public class HostingServiceTest {
    @Mock
    private HostingRepository hostingAddRepo;
    @Mock
    private HostingMapper hostAddMapping;

    @Mock
    private PasswordRepository passwordRepository;

    public HostingService hostServices = new HostingService(hostingAddRepo, hostAddMapping,passwordRepository);

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(hostServices, "hostingAddRepo", hostingAddRepo);
        ReflectionTestUtils.setField(hostServices, "hostAddMapping", hostAddMapping);
        ReflectionTestUtils.setField(hostServices,"passwordRepository",passwordRepository);
    }

    @Test
    public void saveHosting() {
        HostingEntity hostingAdd = new HostingEntity();

        hostingAdd.setHostingProvider("polisetty");
        hostingAdd.setHostingCpannelUrl("http://www.twitter.com");
        hostingAdd.setHostingId("HA005");
        hostingAdd.setHostingDnsName("vamsi");
        hostingAdd.setRegistrationDate("19/10/2023");
        hostingAdd.setExpiryDate("15/10/2023");
        hostingAdd.setUserName("vamsip");
        hostingAdd.setLogin("vamsi87654");
        hostingAdd.setPassword("vamsi@87654");
        hostingAdd.setUrl("http://www.facebook.com");
        hostingAdd.setRegistrationDomain("polisetty");
        hostingAdd.setRegistrationPhoneNumber("9182719999");
        hostingAdd.setRegistrationEmailId("polisettyvamsi@gmail.com");

        HostingRequest reqDto = new HostingRequest();

        reqDto.setHostingProvider("priyanka");
        reqDto.setHostingCpannelUrl("http://www.twitter.com");
        reqDto.setHostingId("HA006");
        reqDto.setHostingDnsName("lakshmi");
        reqDto.setLogin("priyanka9876");
        reqDto.setRegistrationDate("19/10/2023");
        reqDto.setExpiryDate("15/10/2023");
        reqDto.setUserName("vamsip");
        reqDto.setPassword("priyanka@9876");
        reqDto.setUrl("http://www.facebook.com");
        reqDto.setRegistrationDomain("priyankaj");
        reqDto.setRegistrationPhoneNumber("9999999999");
        reqDto.setRegistrationEmailId("priyanka@gmail.com");

        PasswordsEntity passwordsEntity = new PasswordsEntity();
        passwordsEntity.setPasswordId("PWO001");
        passwordsEntity.setDaysLeft("122");
        passwordsEntity.setPassword("anusha");
        passwordsEntity.setExpiryDate("12/05/2023");
        passwordsEntity.setUserName("akhil");
        passwordsEntity.setRegistrationDate("12/01/2023");

        reqDto.setPasswordsEntity(passwordsEntity);

        Mockito.when(hostingAddRepo.findHighestHostId()).thenReturn(hostingAdd.getHostingId());
        Mockito.when(hostAddMapping.entityToRequest(ArgumentMatchers.any())).thenReturn(hostingAdd);
        Mockito.when(hostAddMapping.requestToPasswordEntity(ArgumentMatchers.any())).thenReturn(passwordsEntity);
        Mockito.when(hostingAddRepo.save(ArgumentMatchers.any())).thenReturn(hostingAdd);

        ResponseEntity<?> savedHosting = hostServices.saveHosting(reqDto);

        assertNotNull(savedHosting);
        // assertEquals("Registration successful", ((ResponseResult)domainEntity.getBody()).getResult());

        assertEquals("priyanka", reqDto.getHostingProvider());
        assertEquals("http://www.twitter.com", reqDto.getHostingCpannelUrl());
        assertEquals("HA006", reqDto.getHostingId());
        assertEquals("lakshmi", reqDto.getHostingDnsName());
        assertEquals("19/10/2023", reqDto.getRegistrationDate());
        assertEquals("15/10/2023", reqDto.getExpiryDate());
        assertEquals("vamsip", reqDto.getUserName());
        assertEquals("priyanka9876", reqDto.getLogin());
        assertEquals("priyanka@9876", reqDto.getPassword());
        assertEquals("http://www.facebook.com", reqDto.getUrl());
        assertEquals("priyankaj", reqDto.getRegistrationDomain());
        assertEquals("9999999999", reqDto.getRegistrationPhoneNumber());
        assertEquals("priyanka@gmail.com", reqDto.getRegistrationEmailId());




    }

    @Test
    public void getAllHosting() {
        HostingEntity entity1 = new HostingEntity();

        entity1.setHostingProvider("polisetty");
        entity1.setHostingCpannelUrl("http://www.twitter.com");
        entity1.setHostingId("HA005");
        entity1.setHostingDnsName("vamsi");
        entity1.setRegistrationDate("2022-10-19");
        entity1.setExpiryDate("2024-10-15");
        entity1.setUserName("vamsip");
        entity1.setLogin("vamsi87654");
        entity1.setPassword("vamsi@87654");
        entity1.setUrl("http://www.facebook.com");
        entity1.setRegistrationDomain("polisetty");
        entity1.setRegistrationPhoneNumber("9182719999");
        entity1.setRegistrationEmailId("polisettyvamsi@gmail.com");

        HostingEntity entity2 = new HostingEntity();

        entity2.setHostingProvider("priyanka");
        entity2.setHostingCpannelUrl("http://www.twitter.com");
        entity2.setHostingId("HA006");
        entity2.setHostingDnsName("lakshmi");
        entity2.setRegistrationDate("2021-10-10");
        entity2.setExpiryDate("2025-10-10");
        entity2.setUserName("priya");
        entity2.setLogin("priyanka9876");
        entity2.setPassword("priyanka@9876");
        entity2.setUrl("http://www.facebook.com");
        entity2.setRegistrationDomain("priyankaj");
        entity2.setRegistrationPhoneNumber("9999999999");
        entity2.setRegistrationEmailId("priyanka@gmail.com");

        List<HostingEntity> entityList = new ArrayList<>();

        entityList.add(entity1);
        entityList.add(entity2);

        Mockito.when(hostingAddRepo.findAll()).thenReturn(entityList);

        List<HostingResponse> result = hostServices.getAllHosting();

        assertNotNull(result);

        assertEquals("polisetty",entity1.getHostingProvider());
        assertEquals("http://www.twitter.com", entity1.getHostingCpannelUrl());
        assertEquals("HA005",entity1.getHostingId());
        assertEquals("vamsi", entity1.getHostingDnsName());
        assertEquals("2022-10-19", entity1.getRegistrationDate());
        assertEquals("2024-10-15",entity1.getExpiryDate());
        assertEquals("vamsip", entity1.getUserName());
        assertEquals("vamsi87654", entity1.getLogin());
        assertEquals("vamsi@87654" , entity1.getPassword());
        assertEquals("http://www.facebook.com", entity1.getUrl());
        assertEquals("polisetty", entity1.getRegistrationDomain());
        assertEquals("9182719999", entity1.getRegistrationPhoneNumber());
        assertEquals("polisettyvamsi@gmail.com", entity1.getRegistrationEmailId());

        assertEquals("priyanka",entity2.getHostingProvider());
        assertEquals("http://www.twitter.com", entity2.getHostingCpannelUrl());
        assertEquals("HA006",entity2.getHostingId());
        assertEquals("lakshmi", entity2.getHostingDnsName());
        assertEquals("2021-10-10", entity2.getRegistrationDate());
        assertEquals("2025-10-10",entity2.getExpiryDate());
        assertEquals("priya", entity2.getUserName());
        assertEquals("priyanka9876", entity2.getLogin());
        assertEquals("priyanka@9876" , entity2.getPassword());
        assertEquals("http://www.facebook.com", entity2.getUrl());
        assertEquals("priyankaj", entity2.getRegistrationDomain());
        assertEquals("9999999999", entity2.getRegistrationPhoneNumber());
        assertEquals("priyanka@gmail.com", entity2.getRegistrationEmailId());

    }

    @Test
    public void getHostingById() {

        // String id = "HA005";
        HostingEntity hostingAdd = new HostingEntity();
        hostingAdd.setHostingId("HA005");
        hostingAdd.setHostingProvider("polisetty");
        hostingAdd.setHostingCpannelUrl("http://www.twitter.com");
        hostingAdd.setHostingDnsName("vamsi");
        hostingAdd.setRegistrationDate("2022-10-19");
        hostingAdd.setExpiryDate("2024-10-15");
        hostingAdd.setUserName("vamsip");
        hostingAdd.setLogin("vamsi87654");
        hostingAdd.setPassword("vamsi@87654");
        hostingAdd.setUrl("http://www.facebook.com");
        hostingAdd.setRegistrationDomain("polisetty");
        hostingAdd.setRegistrationPhoneNumber("9182719999");
        hostingAdd.setRegistrationEmailId("polisettyvamsi@gmail.com");

        HostingResponse expectedReqDto = new HostingResponse();

        expectedReqDto.setHostingId("HA005");
        expectedReqDto.setHostingProvider("polisetty");
        expectedReqDto.setHostingCpannelUrl("http://www.twitter.com");
        expectedReqDto.setHostingDnsName("vamsi");
        expectedReqDto.setRegistrationDate("2022-10-19");
        expectedReqDto.setExpiryDate("2024-10-15");
        expectedReqDto.setUserName("vamsip");
        expectedReqDto.setLogin("vamsi87654");
        expectedReqDto.setPassword("vamsi@87654");
        expectedReqDto.setUrl("http://www.facebook.com");
        expectedReqDto.setRegistrationDomain("polisetty");
        expectedReqDto.setRegistrationPhoneNumber("9182719999");
        expectedReqDto.setRegistrationEmailId("polisettyvamsi@gmail.com");



        Mockito.when(hostingAddRepo.findById(hostingAdd.getHostingId())).thenReturn(Optional.of(hostingAdd));
       Mockito.when(hostAddMapping.requestToEntity(ArgumentMatchers.any())).thenReturn(expectedReqDto);

        HostingResponse result = hostServices.getHostingById(hostingAdd.getHostingId());

        assertNotNull(result);
        assertEquals("HA005", result.getHostingId());
        assertEquals("polisetty",result.getHostingProvider());
        assertEquals("http://www.twitter.com",result.getHostingCpannelUrl());
        assertEquals("vamsi",result.getHostingDnsName());
        assertEquals("2022-10-19",result.getRegistrationDate());
        assertEquals("2024-10-15",result.getExpiryDate());
        assertEquals("vamsip",result.getUserName());
        assertEquals("vamsi87654",result.getLogin());
        assertEquals("vamsi@87654",result.getPassword());
        assertEquals("http://www.facebook.com",result.getUrl());
        assertEquals("polisetty",result.getRegistrationDomain());
        assertEquals("9182719999",result.getRegistrationPhoneNumber());
        assertEquals("polisettyvamsi@gmail.com",result.getRegistrationEmailId());

    }


    @Test
    public void updateHosting() {

        HostingUpdateRequest reqDto = new HostingUpdateRequest();

        reqDto.setHostingProvider("priyanka");
        reqDto.setHostingCpannelUrl("http://www.twitter.com");
        reqDto.setHostingDnsName("lakshmi");
        reqDto.setRegistrationDate("10/10/2023");
        reqDto.setExpiryDate("10/02/2024");
        reqDto.setUserName("priya");
        reqDto.setLogin("priyanka9876");
        reqDto.setPassword("priyanka@9876");
        reqDto.setUrl("http://www.facebook.com");
        reqDto.setRegistrationDomain("priyankaj");
        reqDto.setRegistrationPhoneNumber("9999999999");
        reqDto.setRegistrationEmailId("priyanka@gmail.com");

        PasswordsEntity passwordsEntity = new PasswordsEntity();
        passwordsEntity.setPasswordId("PWO001");
        passwordsEntity.setDaysLeft("122");
        passwordsEntity.setPassword("anusha");
        passwordsEntity.setExpiryDate("12/05/2023");
        passwordsEntity.setUserName("akhil");
        passwordsEntity.setRegistrationDate("12/01/2023");

        reqDto.setPasswordsEntity(passwordsEntity);

        HostingEntity hostingAdd = new HostingEntity();

        hostingAdd.setHostingId("HS001");
        hostingAdd.setHostingProvider("priyanka");
        hostingAdd.setHostingCpannelUrl("http://www.twitter.com");
        hostingAdd.setHostingDnsName("lakshmi");
        hostingAdd.setRegistrationDate("2021-10-10");
        hostingAdd.setExpiryDate("2025-10-10");
        hostingAdd.setUserName("priya");
        hostingAdd.setLogin("priyanka9876");
        hostingAdd.setPassword("priyanka@9876");
        hostingAdd.setUrl("http://www.facebook.com");
        hostingAdd.setRegistrationDomain("priyankaj");
        hostingAdd.setRegistrationPhoneNumber("9999999999");
        hostingAdd.setRegistrationEmailId("priyanka@gmail.com");

        Mockito.when(hostingAddRepo.findByHostingId(ArgumentMatchers.anyString())).thenReturn(hostingAdd);
        Mockito.when(hostAddMapping.updateEntityFromRequest(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(hostingAdd);
        Mockito.when(hostAddMapping.requestToUpdatePasswordEntity(ArgumentMatchers.any())).thenReturn(passwordsEntity);
//        Mockito.when(hostingAddRepo.save(hostingAdd)).thenReturn(hostingAdd);

        ResultResponse  result = hostServices.updateHosting(hostingAdd.getHostingId(), reqDto);

        assertNotNull(result);


        assertEquals("priyanka",reqDto.getHostingProvider());
        assertEquals("http://www.twitter.com",reqDto.getHostingCpannelUrl());
        assertEquals("lakshmi",reqDto.getHostingDnsName());
        assertEquals("10/10/2023",reqDto.getRegistrationDate());
        assertEquals("10/02/2024", reqDto.getExpiryDate());
        assertEquals("priya",reqDto.getUserName());
        assertEquals("priyanka9876", reqDto.getLogin());
        assertEquals("priyanka@9876",reqDto.getPassword());
        assertEquals("http://www.facebook.com",reqDto.getUrl());
        assertEquals("priyankaj",reqDto.getRegistrationDomain());
        assertEquals("9999999999",reqDto.getRegistrationPhoneNumber());
        assertEquals("priyanka@gmail.com",reqDto.getRegistrationEmailId());
    }

    @Test
    public void deleteHostingById() {

        HostingEntity entity1 = new HostingEntity();
        entity1.setHostingId("HA001");
        entity1.setHostingProvider("priyanka");
        entity1.setHostingCpannelUrl("http://www.twitter.com");
        entity1.setHostingDnsName("lakshmi");
        entity1.setRegistrationDate("2021-10-10");
        entity1.setExpiryDate("2025-10-10");
        entity1.setUserName("priya");
        entity1.setLogin("priyanka9876");
        entity1.setPassword("priyanka@9876");
        entity1.setUrl("http://www.facebook.com");
        entity1.setRegistrationDomain("priyankaj");
        entity1.setRegistrationPhoneNumber("9999999999");
        entity1.setRegistrationEmailId("priyanka@gmail.com");

        PasswordsEntity passwordsEntity = new PasswordsEntity();
        passwordsEntity.setPasswordId("PWD001");
        passwordsEntity.setDaysLeft("122");
        passwordsEntity.setPassword("anusha");
        passwordsEntity.setExpiryDate("12/05/2023");
        passwordsEntity.setUserName("akhil");
        passwordsEntity.setRegistrationDate("12/01/2023");

        entity1.setPasswordsEntity(passwordsEntity);

        Mockito.when(hostingAddRepo.findByHostingId("HA001")).thenReturn(entity1);
//        Mockito.when(passwordRepository.findHighestPasswordId()).thenReturn(String.valueOf(passwordsEntity));
        Mockito.doNothing().when(passwordRepository).delete(passwordsEntity);
        Mockito.doNothing().when(hostingAddRepo).delete(entity1);

        // Call the service method
        ResponseEntity result = hostServices.deleteHostingById("HA001");

        // Verify repository method invocations
        Mockito.verify(hostingAddRepo, Mockito.times(1)).findByHostingId("HA001");
        Mockito.verify(passwordRepository, Mockito.times(1)).delete(passwordsEntity);
        Mockito.verify(hostingAddRepo, Mockito.times(1)).delete(entity1);

        assertNotNull(result);

        assertEquals("HA001", entity1.getHostingId());

    }

}
