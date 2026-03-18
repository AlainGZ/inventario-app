package com.app.inventario.domain.port.out;

public interface TokenGenerator {

	String generarToken(String username);
	boolean validarToken(String token);
	String extraerUsername(String token);

}
