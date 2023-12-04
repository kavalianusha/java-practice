package com.example.hostinghub.service;

import com.example.hostinghub.entity.AdminEntity;
import com.example.hostinghub.mappers.AdminMapper;
import com.example.hostinghub.repository.AdminRepository;
import com.example.hostinghub.request.AdminRequest;
import com.example.hostinghub.request.AdminUpdateRequest;
import com.example.hostinghub.response.AdminResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;
    @Mock
    private AdminMapper adminMapper;
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private Map<String, LocalDateTime>  otpExpiryMap;


    private AdminService service = new AdminService(adminRepository,
             adminMapper,javaMailSender);

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(service, "adminRepository", adminRepository);
        ReflectionTestUtils.setField(service, "adminMapper", adminMapper);
        ReflectionTestUtils.setField(service, "javaMailSender", javaMailSender);
        ReflectionTestUtils.setField(service, "otpExpiryMap", otpExpiryMap);
    }
/*   @Before
   public void init() {
       MockitoAnnotations.initMocks(this);
   }*/

    @Test
    public void registerAdmin() {
        // Arrange
        AdminRequest request = new AdminRequest();
        request.setAdminId("AD003");
        request.setName("anusha");
        request.setRole("hosting");
        request.setPassword("aki");
        request.setPhoneNo("9878987656");
        request.setEmailId("anushak@pathbreakertech.in");
        request.setUserName("kavali");

        AdminEntity entity = new AdminEntity();
        entity.setAdminId("AD003");
        entity.setName("anusha");
        entity.setRole("hosting");
        entity.setPassword("aki");
        entity.setPhoneNo("9878987656");
        entity.setEmailId("anushak@pathbreakertech.in");
        entity.setUserName("kavali");
        System.out.println(request);
        System.out.println(entity);

        Mockito.when(adminMapper.entityToRequest(ArgumentMatchers.any())).thenReturn(entity);
//        Mockito.when(adminRepository.findByAdminId(ArgumentMatchers.anyString())).thenReturn(entity);
        Mockito.when(adminRepository.findHighestAdminId()).thenReturn("AD002");
        Mockito.when(adminRepository.save(entity)).thenReturn(entity);

        ResponseEntity<?> adminEntity = service.registerAdmin(request);

        assertNotNull(adminEntity);

        assertEquals("anusha", request.getName());
        assertEquals("hosting", request.getRole());
        assertEquals("aki", request.getPassword());
        assertEquals("9878987656", request.getPhoneNo());
        assertEquals("anushak@pathbreakertech.in", request.getEmailId());
        assertEquals("kavali", request.getUserName());
    }

    @Test
    public void sendOtp() {

        AdminRequest request = new AdminRequest();
        request.setAdminId("AD003");
        request.setName("anusha");
        request.setRole("hosting");
        request.setPassword("aki");
        request.setPhoneNo("9878987656");
        request.setEmailId("anushak@pathbreakertech.in");
        request.setUserName("kavali");

        AdminEntity entity = new AdminEntity();
        entity.setAdminId("AD003");
        entity.setName("anusha");
        entity.setRole("hosting");
        entity.setPassword("aki");
        entity.setPhoneNo("9878987656");
        entity.setEmailId("anushak@pathbreakertech.in");
        entity.setUserName("kavali");

        Mockito.when(adminRepository.findByEmailIdOrUserName(ArgumentMatchers.anyString(),ArgumentMatchers.anyString())).thenReturn(entity);
//        Mockito.when(adminRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(entity);
//        Mockito.when(adminRepository.findByEmailId(ArgumentMatchers.anyString())).thenReturn(entity);
          Mockito.when(adminRepository.save(entity)).thenReturn(entity);

        ResponseEntity<?> response = service.sendOtp(request);

        assertNotNull(response);

        assertEquals("anusha", request.getName());
        assertEquals("hosting", request.getRole());
        assertEquals("aki", request.getPassword());
        assertEquals("9878987656", request.getPhoneNo());
        assertEquals("anushak@pathbreakertech.in", request.getEmailId());
        assertEquals("kavali", request.getUserName());
    }

    @Test
    public void verifyOtp() {

        AdminRequest request = new AdminRequest();
        request.setAdminId("AD003");
        request.setName("anusha");
        request.setRole("hosting");
        request.setPassword("aki");
        request.setPhoneNo("9878987656");
        request.setEmailId("anushak@pathbreakertech.in");
        request.setUserName("kavali");
        request.setOtp("123456");

        AdminEntity entity = new AdminEntity();
        entity.setAdminId("AD003");
        entity.setName("anusha");
        entity.setRole("hosting");
        entity.setPassword("aki");
        entity.setPhoneNo("9878987656");
        entity.setEmailId("anushak@pathbreakertech.in");
        entity.setUserName("kavali");
        entity.setOtp("123456");

        LocalDateTime otpExpiryTime = LocalDateTime.now().plusMinutes(10);
        otpExpiryMap.put(entity.getEmailId(),otpExpiryTime);
        Mockito.when(adminRepository.findByOtp(ArgumentMatchers.anyString())).thenReturn(entity);

        ResponseEntity<?> response = service.verifyOtp(request);

        assertNotNull(response);

        assertEquals("anusha", request.getName());
        assertEquals("hosting", request.getRole());
        assertEquals("aki", request.getPassword());
        assertEquals("9878987656", request.getPhoneNo());
        assertEquals("anushak@pathbreakertech.in", request.getEmailId());
        assertEquals("kavali", request.getUserName());
        assertEquals("123456", request.getOtp());
    }

    @Test
    public void sendOtpByEmail() {
        String emailId = "test@example.com";
        String otp = "123456";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailId);
        mailMessage.setSubject("Hello Admin  Welcome to Hosting hub");
        mailMessage.setText("Your otp for Admin login is : " + otp);

        // Calling the method
        service.sendOtpByEmail(emailId, otp);
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void getAllAdmins() {
        AdminEntity request1 = new AdminEntity();
        request1.setAdminId("AD003");
        request1.setName("anusha");
        request1.setRole("hosting");
        request1.setPassword("aki");
        request1.setPhoneNo("9878987656");
        request1.setEmailId("anushak@pathbreakertech.in");
        request1.setUserName("kavali");

        AdminEntity request2 = new AdminEntity();
        request2.setAdminId("AD004");
        request2.setName("anusha");
        request2.setRole("hosting");
        request2.setPassword("aki");
        request2.setPhoneNo("9878987656");
        request2.setEmailId("anushak@pathbreakertech.in");
        request2.setUserName("kavali");

        AdminResponse adminRequest1 = new AdminResponse();
        adminRequest1.setAdminId("AD005");
        adminRequest1.setName("anusha");
        adminRequest1.setRole("hosting");
        adminRequest1.setPassword("aki");
        adminRequest1.setPhoneNo("9878987656");
        adminRequest1.setEmailId("anushak@pathbreakertech.in");
        adminRequest1.setUserName("kavali");

        AdminResponse adminRequest2 = new AdminResponse();
        adminRequest2.setAdminId("AD006");
        adminRequest2.setName("anusha");
        adminRequest2.setRole("hosting");
        adminRequest2.setPassword("aki");
        adminRequest2.setPhoneNo("9878987656");
        adminRequest2.setEmailId("anushak@pathbreakertech.in");
        adminRequest2.setUserName("kavali");

        List<AdminResponse> adminRequests = new ArrayList<>();
        adminRequests.add(adminRequest1);
        adminRequests.add(adminRequest2);

        List<AdminEntity> adminEntities = new ArrayList<>();
        adminEntities.add(request1);
        adminEntities.add(request2);

        Mockito.when(adminRepository.findAll()).thenReturn(adminEntities);
        Mockito.when(adminMapper.responseToEntityList(ArgumentMatchers.any())).thenReturn(adminRequests);

        List<AdminResponse> responseResult = service.getAllAdmins();

        assertNotNull(responseResult);

        assertEquals("anusha", responseResult.get(0).getName());
        assertEquals("hosting", responseResult.get(0).getRole());
        assertEquals("aki", responseResult.get(0).getPassword());
        assertEquals("9878987656", responseResult.get(0).getPhoneNo());
        assertEquals("anushak@pathbreakertech.in", responseResult.get(0).getEmailId());
        assertEquals("kavali", responseResult.get(0).getUserName());
    }

    @Test
    public void getAdminById() {
        AdminEntity request1 = new AdminEntity();
        request1.setAdminId("AD003");
        request1.setName("anusha");
        request1.setRole("hosting");
        request1.setPassword("aki");
        request1.setPhoneNo("9878987656");
        request1.setEmailId("anushak@pathbreakertech.in");
        request1.setUserName("kavali");

        AdminResponse adminRequest1 = new AdminResponse();
        adminRequest1.setAdminId("AD005");
        adminRequest1.setName("anusha");
        adminRequest1.setRole("hosting");
        adminRequest1.setPassword("aki");
        adminRequest1.setPhoneNo("9878987656");
        adminRequest1.setEmailId("anushak@pathbreakertech.in");
        adminRequest1.setUserName("kavali");

        Mockito.when(adminRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(request1));
        Mockito.when(adminMapper.responseToEntity(ArgumentMatchers.any())).thenReturn(adminRequest1);

        AdminResponse responseResult = service.getAdminById(adminRequest1.getAdminId());

        assertNotNull(responseResult);

        assertEquals("anusha", responseResult.getName());
        assertEquals("hosting", responseResult.getRole());
        assertEquals("aki", responseResult.getPassword());
        assertEquals("9878987656", responseResult.getPhoneNo());
        assertEquals("anushak@pathbreakertech.in", responseResult.getEmailId());
        assertEquals("kavali", responseResult.getUserName());

    }

    @Test
    public void existsById() {
        Mockito.when(adminRepository.existsById(ArgumentMatchers.anyString()))
                .thenReturn(Boolean.TRUE);

        Boolean response = service.existsById("MS002");
        System.out.println(response);
        assertNotNull(response);
        assertTrue(response);
    }

    @Test
    public void updateAdmin() {
        AdminUpdateRequest request = new AdminUpdateRequest();
        String adminId = "AD003";
        request.setName("anusha");
        request.setRole("hosting");
        request.setPassword("aki");
        request.setPhoneNo("9878987656");
        request.setEmailId("anushak@pathbreakertech.in");
        request.setUserName("kavali");

        AdminEntity entity = new AdminEntity();
        entity.setAdminId("AD003");
        entity.setName("anusha");
        entity.setRole("hosting");
        entity.setPassword("aki");
        entity.setPhoneNo("9878987656");
        entity.setEmailId("anushak@pathbreakertech.in");
        entity.setUserName("kavali");
        System.out.println(request);
        System.out.println(entity);

        Mockito.when(adminRepository.findByAdminId("AD003")).thenReturn(entity);
        Mockito.when(adminMapper.updateEntityFromRequest(request,entity)).thenReturn(entity);
        Mockito.when( adminRepository.save(entity)).thenReturn(entity);

        AdminEntity response = service.updateAdmin(adminId, request);
        assertNotNull(response);

        assertEquals("anusha", request.getName());
        assertEquals("hosting", request.getRole());
        assertEquals("aki", request.getPassword());
        assertEquals("9878987656", request.getPhoneNo());
        assertEquals("anushak@pathbreakertech.in", request.getEmailId());
        assertEquals("kavali", request.getUserName());

    }

    @Test
    public void deleteAdminById() {
        String adminId = "AD003";
        AdminEntity existingAdmin = new AdminEntity(); // Set up an existing admin
        existingAdmin.setAdminId(adminId);

        when(adminRepository.findByAdminId(adminId)).thenReturn(existingAdmin);

        // Act
        service.deleteAdminById(adminId);

        // Assert
        verify(adminRepository).deleteById(adminId);
    }
}