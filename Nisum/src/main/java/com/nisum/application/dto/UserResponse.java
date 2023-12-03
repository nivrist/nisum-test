package com.nisum.application.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String id;
    private String created;
    private String modified;
    private String last_login;
    private String token;
    private boolean isActive;


}
