package com.sandrajavaschool.OnlineStore.authHandler.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityMixin {

    //Constructor por defecto cuando se crean los objetos authorities del json
    @JsonCreator
    public SimpleGrantedAuthorityMixin(@JsonProperty("authority") String role) {
    }
}
