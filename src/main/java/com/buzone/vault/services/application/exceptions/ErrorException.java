package com.buzone.vault.services.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class ErrorException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private Integer code;

	public ErrorException(Integer code, String message) {
        super(message);
        this.code = code;
    }

	public Integer getCode() {
		return code;
	}
}
