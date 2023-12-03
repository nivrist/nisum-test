package com.nisum.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhonesDTO {
    private String number;
    private String citycode;
    private String contrycode;
}
