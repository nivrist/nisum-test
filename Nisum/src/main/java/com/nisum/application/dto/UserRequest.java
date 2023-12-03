package com.nisum.application.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
public class UserRequest {
    @NotBlank(message = " El campo es requerido")
    private String name;
    @NotBlank(message = " El campo es requerido")
    private String email;
    @NotBlank(message = " El campo es requerido")
    private String password;
    private List<PhonesDTO> phones;

}
