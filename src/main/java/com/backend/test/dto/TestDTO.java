package com.backend.test.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TestDTO {
    private String username;
    private String useridcard;
    private String unit_code;
    private String unit_name;
    private String token;
    private TestInfoDTO imginfo;
}
