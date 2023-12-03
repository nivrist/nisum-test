package com.nisum.application.exceptions;

import com.nisum.application.dto.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CustomExceptionHandlerTest {

    @InjectMocks
    private CustomExceptionHandler customExceptionHandler;

    @BeforeEach
    void setUp() {
        customExceptionHandler = new CustomExceptionHandler();

    }

    @Test
    void handleCustomException_ShouldReturnErrorResponse() {

        CustomException customException = new CustomException("Test Custom Exception", HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseEntity<ApiResponse<Object>> responseEntity = customExceptionHandler.handleCustomException(customException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Test Custom Exception", responseEntity.getBody().getMensaje());
    }


}