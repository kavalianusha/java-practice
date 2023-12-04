package com.example.hostinghub.service;

import com.example.hostinghub.entity.DomHostMapExpiryEntity;
import com.example.hostinghub.entity.HostDomainMapEntity;
import com.example.hostinghub.mappers.DomHostExpiryMapper;
import com.example.hostinghub.mappers.HostDomainMapMapper;
import com.example.hostinghub.repository.DomHostMapExpiryRepository;
import com.example.hostinghub.repository.DomainRepository;
import com.example.hostinghub.repository.HostDomainMapRepository;
import com.example.hostinghub.repository.HostingRepository;
import com.example.hostinghub.request.HostDomainMapRequest;
import com.example.hostinghub.request.HostDomainMapUpdateRequest;
import com.example.hostinghub.response.HostDomainMapResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class HostDomainMapServiceTest {

    @Mock
    private HostDomainMapRepository hostDomainMapRepository;

    @Mock
    private HostDomainMapMapper hostDomainMapMapper;

    @Mock
    private DomainRepository domainRepository;

    @Mock
    private HostingRepository hostingRepository;
    @Mock
    private DomHostMapExpiryRepository domHostMapExpiryRepository;

    @Mock
    private DomHostExpiryMapper domhostExpiryMapper;


    private HostDomainMapService service = new HostDomainMapService(hostDomainMapRepository,
            hostDomainMapMapper,domainRepository,hostingRepository,domHostMapExpiryRepository,domhostExpiryMapper);

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(service, "hostDomainMapRepository", hostDomainMapRepository);
        ReflectionTestUtils.setField(service, "hostDomainMapMapper", hostDomainMapMapper);
        ReflectionTestUtils.setField(service, "domainRepository", domainRepository);
        ReflectionTestUtils.setField(service, "hostingRepository", hostingRepository);
        ReflectionTestUtils.setField(service, "domHostMapExpiryRepository", domHostMapExpiryRepository);
        ReflectionTestUtils.setField(service,"domhostExpiryMapper",domhostExpiryMapper);

    }
    @Test
    public void addHostDomainAdd() {
        // Arrange
        HostDomainMapRequest request = new HostDomainMapRequest();
        request.setHostDomainId("HD001");
        request.setDomainName("domain1");
        request.setHostProvider("anusha");
        request.setPayment("900");
        request.setDuration("1 year");
        request.setRegistrationDate("13/10/2023");
        request.setExpiryDate("12/12/2023");

        DomHostMapExpiryEntity expiryEntity = new DomHostMapExpiryEntity();
        //expiryEntity.setId(1);
        expiryEntity.setDomainName("domain1");
        expiryEntity.setHostProvider("anusha");
        expiryEntity.setPayment("900");
        expiryEntity.setDuration("1 year");
        expiryEntity.setDaysLeft(Long.parseLong("366"));
        expiryEntity.setRegistrationDate("13/10/2023");
        expiryEntity.setExpiryDate("12/12/2023");

        request.setDomHostMapExpiryEntity(request.getDomHostMapExpiryEntity());

        HostDomainMapEntity entity = new HostDomainMapEntity();
        entity.setHostDomainId("HD001");
        entity.setDomainName("domain1");
        entity.setHostProvider("anusha");
        entity.setPayment("900");
        entity.setDuration("1 year");
        entity.setRegistrationDate("13/10/2023");
        entity.setExpiryDate("12/12/2023");

        Mockito.when(hostDomainMapRepository.findHighestHostDomainId()).thenReturn(request.getHostDomainId());
        Mockito.when(hostDomainMapMapper.requestToEntity(ArgumentMatchers.any())).thenReturn(entity);
        Mockito.when(hostDomainMapMapper.createDomHostMapExpiryEntity(request)).thenReturn(expiryEntity);
        Mockito.when(hostDomainMapRepository.save(ArgumentMatchers.any())).thenReturn(entity);

        ResponseEntity response = service.addHostDomainAdd(request);
        assertNotNull(response);

        // Additional assertions
        assertEquals("anusha", request.getHostProvider());
        assertEquals("HD002", request.getHostDomainId());
        assertEquals("domain1", request.getDomainName());
        assertEquals("900", request.getPayment());
        assertEquals("1 year", request.getDuration());
        assertEquals("13/10/2023", request.getRegistrationDate());
        assertEquals("12/12/2023", request.getExpiryDate());
    }
    @Test
    public void updateHostDomain() {
        // Arrange
        String hostDomainId = "HD001";

        HostDomainMapUpdateRequest updateRequest = new HostDomainMapUpdateRequest();
        updateRequest.setDomainName("updatedDomain");
        updateRequest.setHostProvider("updatedProvider");
        updateRequest.setPayment("1000");
        updateRequest.setDuration("2 years");
        updateRequest.setRegistrationDate("10/10/2023");
        updateRequest.setExpiryDate("10/12/2023");

        HostDomainMapEntity existingEntity = new HostDomainMapEntity();
        existingEntity.setHostDomainId(hostDomainId);
        existingEntity.setDomainName("domain1");
        existingEntity.setHostProvider("anusha");
        existingEntity.setPayment("900");
        existingEntity.setDuration("1 year");
        existingEntity.setRegistrationDate("10/10/2023");
        existingEntity.setExpiryDate("10/12/2023");

        DomHostMapExpiryEntity existingExpiryEntity = new DomHostMapExpiryEntity();
        //existingExpiryEntity.setId(1);
        existingExpiryEntity.setDomainName("domain1");
        existingExpiryEntity.setHostProvider("anusha");
        existingExpiryEntity.setPayment("900");
        existingExpiryEntity.setDuration("1 year");
        existingExpiryEntity.setDaysLeft(366L);
        existingExpiryEntity.setRegistrationDate("10/10/2023");
        existingExpiryEntity.setExpiryDate("10/12/2023");

        updateRequest.setDomHostMapExpiryEntity(existingExpiryEntity);

        Mockito.when(hostDomainMapRepository.findByHostDomainId(ArgumentMatchers.any())).thenReturn(existingEntity);
        Mockito.when(hostDomainMapMapper.updateEntityFromRequest(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(existingEntity);
        Mockito.when(hostDomainMapRepository.save(existingEntity)).thenReturn(existingEntity);

        Mockito.when(domhostExpiryMapper.updateToEntity(ArgumentMatchers.any())).thenReturn(existingExpiryEntity);
        Mockito.when(hostDomainMapMapper.updateDomEntityFromRequest(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(existingExpiryEntity);
        Mockito.when(domHostMapExpiryRepository.save(existingExpiryEntity)).thenReturn(existingExpiryEntity);

        HostDomainMapEntity result = service.updateHostDomain(hostDomainId, updateRequest);

        // Assert
        assertNotNull(result);

        assertEquals("domain1", existingExpiryEntity.getDomainName());
        assertEquals("anusha",existingExpiryEntity.getHostProvider());
        assertEquals("900",existingExpiryEntity.getPayment());
        assertEquals("1 year", existingExpiryEntity.getDuration());
        assertEquals(6, existingExpiryEntity.getDaysLeft());
        assertEquals("10/10/2023", existingEntity.getRegistrationDate());
        assertEquals("10/12/2023", existingEntity.getExpiryDate());
    }
    @Test
    public void getAllHostDomainsById() {

        HostDomainMapResponse response = new HostDomainMapResponse();
        response.setHostDomainId("HD001");
        response.setDomainName("domain1");
        response.setHostProvider("anusha");
        response.setPayment("900");
        response.setDuration("1 year");
        response.setRegistrationDate("12/10/23");
        response.setExpiryDate("12/10/24");

        HostDomainMapEntity entity = new HostDomainMapEntity();
        entity.setHostDomainId("HD001");
        entity.setDomainName("domain1");
        entity.setHostProvider("anusha");
        entity.setPayment("900");
        entity.setDuration("1 year");
        entity.setRegistrationDate("12/10/23");
        entity.setExpiryDate("12/10/24");

        Mockito.when(hostDomainMapRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(entity));
        Mockito.when(hostDomainMapMapper.entityToResponse(ArgumentMatchers.any())).thenReturn(response);

        HostDomainMapResponse hostDomainMapService = service.getAllHostDomainsById(entity.getHostDomainId());

        assertNotNull(hostDomainMapService);

        assertEquals("anusha", response.getHostProvider());
        assertEquals("HD001", response.getHostDomainId());
        assertEquals("domain1", response.getDomainName());
        assertEquals("900", response.getPayment());
        assertEquals("1 year", response.getDuration());
        assertEquals("12/10/23", response.getRegistrationDate());
        assertEquals("12/10/24", response.getExpiryDate());
    }


    @Test
    public void testDeleteHostDomainById() {

        HostDomainMapEntity hostDomainMapEntity = new HostDomainMapEntity();
        hostDomainMapEntity.setHostDomainId("HD001");
        hostDomainMapEntity.setDomainName("domain1");
        hostDomainMapEntity.setHostProvider("anusha");
        hostDomainMapEntity.setPayment("900");
        hostDomainMapEntity.setDuration("1 year");
        hostDomainMapEntity.setRegistrationDate("2023-10-14");
        hostDomainMapEntity.setExpiryDate("2024-10-15");

        DomHostMapExpiryEntity domHostMapExpiryEntity = new DomHostMapExpiryEntity();
        // domHostMapExpiryEntity.setId(1);
        domHostMapExpiryEntity.setDomainName("domain1");
        domHostMapExpiryEntity.setHostProvider("anusha");
        domHostMapExpiryEntity.setPayment("900");
        domHostMapExpiryEntity.setDuration("1 year");
        domHostMapExpiryEntity.setDaysLeft(366);
        domHostMapExpiryEntity.setRegistrationDate("10/10/2023");
        domHostMapExpiryEntity.setExpiryDate("10/10/2024");

        //domHostMapExpiryEntity.setHostDomainMapEntity(hostDomainMapEntity);
        hostDomainMapEntity.setDomHostMapExpiryEntity(domHostMapExpiryEntity);

        Mockito.when(hostDomainMapRepository.findByHostDomainId("HD001")).thenReturn(hostDomainMapEntity);
        Mockito.doNothing().when(domHostMapExpiryRepository).delete(domHostMapExpiryEntity);
        Mockito.doNothing().when(hostDomainMapRepository).delete(hostDomainMapEntity);

        ResponseEntity<?> response = service.deletehostDomainById(hostDomainMapEntity.getHostDomainId());

        // Verify interactions with mocks
        Mockito.verify(hostDomainMapRepository, times(1)).findByHostDomainId("HD001");
        Mockito.verify(domHostMapExpiryRepository, times(1)).delete(domHostMapExpiryEntity);
        Mockito.verify(hostDomainMapRepository, times(1)).delete(hostDomainMapEntity);

        assertNotNull(response);

        // Additional assertions for the state after deletion
        assertEquals("domain1", domHostMapExpiryEntity.getDomainName());
        assertEquals("anusha", domHostMapExpiryEntity.getHostProvider());
        assertEquals("900", domHostMapExpiryEntity.getPayment());
        assertEquals("1 year",domHostMapExpiryEntity.getDuration());
        assertEquals(366,domHostMapExpiryEntity.getDaysLeft());
        assertEquals("10/10/2023",domHostMapExpiryEntity.getRegistrationDate());
        assertEquals("10/10/2024",domHostMapExpiryEntity.getExpiryDate());
    }

    @Test
    public void getAllDomainNames() {

        List<String> sampleDomainNames = Arrays.asList("anusha", "keerthi", "mamatha");
        // Mock the behavior of domainRepository.findAllDomainNames() to return the sample domain names
        when(domainRepository.findAllDomainNames()).thenReturn(sampleDomainNames);
        // Call the method to be tested
        List<String> result = service.getAllDomainNames();
        System.out.println(result);
        // Verify that the domainRepository.findAllDomainNames() was called exactly once
        verify(domainRepository, times(1)).findAllDomainNames();
        // Assert that the returned list matches the sample domain names
        assertEquals(sampleDomainNames, result);
    }


    @Test
    public void getAllHostingProviders() {
        List<String> sampleHosting = Arrays.asList("aki", "anusha", "anshu");
        // Mock the behavior of domainRepository.findAllDomainNames() to return the sample domain names
        when(hostingRepository.findAllHostingProvider()).thenReturn(sampleHosting);
        // Call the method to be tested
        List<String> result = service.getAllHostingProviders();
        System.out.println(result);
        // Verify that the domainRepository.findAllDomainNames() was called exactly once
        verify(hostingRepository, times(1)).findAllHostingProvider();
        // Assert that the returned list matches the sample domain names
        assertEquals(sampleHosting, result);
    }
}