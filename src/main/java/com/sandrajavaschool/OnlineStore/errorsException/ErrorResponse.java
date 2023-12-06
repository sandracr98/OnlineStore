package com.sandrajavaschool.OnlineStore.errorsException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private Long entityId;

}
