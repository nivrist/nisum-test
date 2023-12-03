package com.nisum.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.application.dto.ApiResponse;
import com.nisum.application.dto.PhonesDTO;
import com.nisum.application.dto.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerUser_ValidUserRequest_ReturnsCreatedResponse() throws Exception {
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

        String jsonRequest = objectMapper.writeValueAsString(userRequest);


        ResultActions resultActions = mockMvc.perform(post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());


        MvcResult mvcResult = resultActions.andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<Object> apiResponse = objectMapper.readValue(content, ApiResponse.class);


        assertNotNull(apiResponse);
        assertNotNull(apiResponse.getData());


    }


}