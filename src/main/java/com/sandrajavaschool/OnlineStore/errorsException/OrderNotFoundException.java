package com.sandrajavaschool.OnlineStore.errorsException;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(String message) {
        super(message);
    }
}
