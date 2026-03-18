package com.app.inventario.domain.port.in;

public interface AuthUseCase {

	String login(String username, String password);

	void logout(String token);

}
