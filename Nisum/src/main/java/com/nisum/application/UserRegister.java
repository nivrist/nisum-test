package com.nisum.application;


import com.nisum.application.dto.ApiResponse;
import com.nisum.application.dto.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserRegister {
    public ResponseEntity<ApiResponse<Object>> userCreate(UserRequest user);
}
