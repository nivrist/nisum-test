package com.nisum.infrastructure;

import com.nisum.application.UserRegister;
import com.nisum.application.dto.ApiResponse;
import com.nisum.application.dto.UserRequest;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@Validated
@RequestMapping(value = "/api/v1")
@Api(value = "UserController")
public class UserController {


	private final UserRegister userRegister;

	public UserController(UserRegister userRegister) {
		this.userRegister = userRegister;
	}

	@PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<Object>> registerUser(@Valid @RequestBody UserRequest request) {
		return userRegister.userCreate(request);

	}
}