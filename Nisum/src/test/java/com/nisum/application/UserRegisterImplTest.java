package com.nisum.application;

import com.nisum.application.dto.ApiResponse;
import com.nisum.application.dto.PhonesDTO;
import com.nisum.application.dto.UserRequest;
import com.nisum.domain.UsersRepository;
import com.nisum.domain.model.UserPhones;
import com.nisum.domain.model.Users;
import com.nisum.domain.utilities.Utils;
import com.nisum.infrastructure.config.jwt.JwtGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserRegisterImplTest {
    @Mock
    private Utils utils;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private JwtGenerator jwtGenerator;

    @MockBean
    private UserRegister userRegister;

    @Value("${app.password-pattern}")
    private String passwordPattern;

    @Value("${app.email-pattern}")
    private String emailPattern;

    @Test
    void userCreate_ValidUserRequest_ReturnsOkResponse() {

        UserRequest userRequest = UserRequest.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.cl")
                .password("Hunter2#")
                .phones(Collections.emptyList())
                .build();


        when(utils.validarExpresionRegular(userRequest.getEmail(), emailPattern)).thenReturn(true);
        when(utils.validarExpresionRegular(userRequest.getPassword(), passwordPattern)).thenReturn(true);
        when(usersRepository.findByEmail(userRequest.getEmail())).thenReturn(null);
        when(jwtGenerator.generateToken(userRequest.getEmail())).thenReturn("mocked-token");

        when(userRegister.userCreate(any(UserRequest.class))).thenReturn(ResponseEntity.ok(ApiResponse.builder().build()));


        ResponseEntity<ApiResponse<Object>> response = userRegister.userCreate(userRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

    }


    @Test
    void userCreate_ExistingUser_ReturnsBadRequestResponse() {

        PhonesDTO phDt = PhonesDTO.builder()
                .citycode("1")
                .contrycode("57")
                .number("1234567")
                .build();
        List<PhonesDTO> listPh = new ArrayList<>();
        listPh.add(phDt);
        UserRequest userRequest = UserRequest.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.cl")
                .password("Hunter2#")
                .phones(listPh)
                .build();

        Users usr = new Users();
        usr.setName("Juan Rodriguez");
        usr.setEmail("juan@rodriguez.cl");
        usr.setCreated(new Date());
        usr.setToken("uryeryuihjkdhfkdf");
        usr.setId(UUID.randomUUID().toString());
        UserPhones ph = new UserPhones();
        ph.setContryCode("10");
        ph.setId(1);
        ph.setCityCode("57");
        List<UserPhones> lsPh = new ArrayList<>();
        lsPh.add(ph);
        usr.setUserPhonesCollection(lsPh);



        when(usersRepository.findByEmail(userRequest.getEmail())).thenReturn(usr);

        doReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder().mensaje("El usuario ya existe.").build()))
                .when(userRegister).userCreate(any(UserRequest.class));

        ResponseEntity<ApiResponse<Object>> response = userRegister.userCreate(userRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El usuario ya existe.", response.getBody().getMensaje());
    }


    @Test
    void userCreate_InvalidEmail_ReturnsBadRequestResponse() {
        UserRequest userRequest = UserRequest.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.org")
                .password("Hunter2#")
                .phones(Collections.emptyList())
                .build();


        when(utils.validarExpresionRegular(userRequest.getEmail(), emailPattern)).thenReturn(false);

        doReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder().mensaje("El correo electrónico no es válido.").build()))
                .when(userRegister).userCreate(any(UserRequest.class));

        ResponseEntity<ApiResponse<Object>> response = userRegister.userCreate(userRequest);


        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El correo electrónico no es válido.", response.getBody().getMensaje());
    }


    @Test
    void userCreate_InvalidPassword_ReturnsBadRequestResponse() {
        UserRequest userRequest = UserRequest.builder()
                .name("Juan Rodriguez")
                .email("juan@rodriguez.cl")
                .password("holaPass")
                .phones(Collections.emptyList())
                .build();


        when(utils.validarExpresionRegular(userRequest.getPassword(), passwordPattern)).thenReturn(false);

        doReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder().mensaje("La contraseña no cumple con los requisitos mínimos.").build()))
                .when(userRegister).userCreate(any(UserRequest.class));

        ResponseEntity<ApiResponse<Object>> response = userRegister.userCreate(userRequest);


        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("La contraseña no cumple con los requisitos mínimos.", response.getBody().getMensaje());
    }

}