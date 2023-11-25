package com.sandrajavaschool.OnlineStore.security.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String name;
    private String surname;
    private String birthdate;
    private String email;
    private String pass;
    private String enabled;
}
