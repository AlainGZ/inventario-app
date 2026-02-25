package com.app.inventario.infrastructure.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationErrors(
			MethodArgumentNotValidException ex){

		Map<String, String> errores = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->
				errores.put(error.getField(), error.getDefaultMessage())
		);

		Map<String, Object> respuesta = new HashMap<>();
		respuesta.put("timestamp", LocalDateTime.now());
		respuesta.put("status", 400);
		respuesta.put("errores", errores);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleIllegalArgument(
			IllegalArgumentException ex){
		Map<String, Object> respuesta = new HashMap<>();
		respuesta.put("timestamp", LocalDateTime.now());
		respuesta.put("status", 409);
		respuesta.put("error", ex.getMessage());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
	}

}
