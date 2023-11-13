package com.sandrajavaschool.OnlineStore.security.dto;

import lombok.Data;

//Devuelve la informacion con el token y el tipo que tenga este
@Data
public class AuthResponseDto {

    private String accessToken;
    private String tokenType = "Bearer "; //asi mandamos al postman un extracto con caracter que es el token que generamos

    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
