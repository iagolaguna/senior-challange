package br.com.iagolaguna.senior.challange.pojo;

import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

public class DefaultResponse {
    private final int code;
    private final String message;

    public DefaultResponse(@NotNull HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.message = message;
    }

}
