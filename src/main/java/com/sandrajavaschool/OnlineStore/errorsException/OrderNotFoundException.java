package com.sandrajavaschool.OnlineStore.errorsException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderNotFoundException extends RuntimeException{

    private Long orderId;

    public OrderNotFoundException(String message, Long orderId) {
        super(message);
        this.orderId = orderId;
    }
}
