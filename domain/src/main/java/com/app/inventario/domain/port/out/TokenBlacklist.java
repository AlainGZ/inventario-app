package com.app.inventario.domain.port.out;

public interface TokenBlacklist {

	void agregar(String token);

	boolean contiene(String token);

}
