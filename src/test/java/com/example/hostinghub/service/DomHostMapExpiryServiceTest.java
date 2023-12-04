package com.example.hostinghub.service;

import com.example.hostinghub.entity.DomHostMapExpiryEntity;
import com.example.hostinghub.mappers.DomHostExpiryMapper;
import com.example.hostinghub.repository.DomHostMapExpiryRepository;
import com.example.hostinghub.response.DomHostMapExpiryResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DomHostMapExpiryServiceTest {

        @Mock
        private DomHostMapExpiryRepository domHostMapExpiryRepository;

        @Mock
        private DomHostExpiryMapper domhostExpiryMapper;


        private DomHostMapExpiryService domHostMapExpiryService = new DomHostMapExpiryService(domHostMapExpiryRepository,domhostExpiryMapper);

        @Before
        public void setUp() {
            ReflectionTestUtils.setField(domHostMapExpiryService,"domHostMapExpiryRepository",domHostMapExpiryRepository);
            ReflectionTestUtils.setField(domHostMapExpiryService ,"domhostExpiryMapper",domhostExpiryMapper);
        }

        @Test
        public void getAllDomHost() {
            // Create mock DomHostMapExpiryEntities
            DomHostMapExpiryEntity domHostEntity1 = new DomHostMapExpiryEntity();
            // domHostEntity1.setId(1L);
            domHostEntity1.setDomainName("example.com");
            domHostEntity1.setHostProvider("Provider1");
            domHostEntity1.setPayment("Payment1");
            domHostEntity1.setRegistrationDate("2023-01-01");
            domHostEntity1.setExpiryDate("2023-12-31");
            domHostEntity1.setDuration("11 months");
            domHostEntity1.setDaysLeft(30);


            DomHostMapExpiryEntity domHostEntity2 = new DomHostMapExpiryEntity();
            //domHostEntity2.setId(2L);
            domHostEntity2.setDomainName("example.org");
            domHostEntity2.setHostProvider("Provider2");
            domHostEntity2.setPayment("Payment2");
            domHostEntity2.setRegistrationDate("2023-02-01");
            domHostEntity2.setExpiryDate("2023-12-31");
            domHostEntity2.setDuration("11 months");
            domHostEntity2.setDaysLeft(20);
            // Set other properties...

            DomHostMapExpiryResponse response = new DomHostMapExpiryResponse();



            List<DomHostMapExpiryEntity> domHostEntities = new ArrayList<>();
            domHostEntities.add(domHostEntity1);
            domHostEntities.add(domHostEntity2);

            Mockito.when(domHostMapExpiryRepository.findAll()).thenReturn(domHostEntities);
            Mockito.when(domhostExpiryMapper.entitiesToResponses(domHostEntities)).thenReturn(new ArrayList<>());

            List<DomHostMapExpiryResponse> result = domHostMapExpiryService.getAllDomHost();

            // Assert
            assertNotNull(result);
            // assertEquals(1L,domHostEntity1.getId());
            assertEquals("example.com",domHostEntity1.getDomainName());
            assertEquals("Provider1",domHostEntity1.getHostProvider());
            assertEquals("Payment1",domHostEntity1.getPayment());
            assertEquals("2023-01-01",domHostEntity1.getRegistrationDate());
            assertEquals("2023-12-31",domHostEntity1.getExpiryDate());
            assertEquals("11 months",domHostEntity1.getDuration());
            assertEquals(30,domHostEntity1.getDaysLeft());

    }
}
