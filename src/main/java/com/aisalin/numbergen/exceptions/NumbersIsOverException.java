package com.aisalin.numbergen.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Numbers is over in this region")
public class NumbersIsOverException extends RuntimeException {
    public NumbersIsOverException(String message) {
        super(message);
    }
}
