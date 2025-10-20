package com.Estudo.DesafioTecnico.exceptions.handler;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {

	public ErrorResponse(int value, String string, String details, String replace) {
		this(LocalDateTime.now(), value, string, details, replace);
	}

}
