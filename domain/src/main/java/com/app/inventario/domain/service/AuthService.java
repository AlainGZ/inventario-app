package com.app.inventario.domain.service;

import com.app.inventario.domain.model.Usuario;
import com.app.inventario.domain.port.in.AuthUseCase;
import com.app.inventario.domain.port.out.TokenBlacklist;
import com.app.inventario.domain.port.out.TokenGenerator;
import com.app.inventario.domain.port.out.UsuarioRepository;

public class AuthService implements AuthUseCase {

	private final UsuarioRepository usuarioRepository;
	private final TokenGenerator tokenGenerator;
	private final TokenBlacklist tokenBlacklist;

	public AuthService(UsuarioRepository usuarioRepository, TokenGenerator tokenGenerator, TokenBlacklist tokenBlacklist){
		this.usuarioRepository = usuarioRepository;
		this.tokenGenerator = tokenGenerator;
		this.tokenBlacklist = tokenBlacklist;
	}

	@Override
	public String login(String username, String password){

		Usuario usuario = usuarioRepository.buscarPorUsername(username)
				.orElseThrow(() -> new IllegalArgumentException(
						"Credenciales invalidas."
				));

		if (!password.equals(usuario.getPassword())){
			throw new IllegalArgumentException("Credenciales Invalidas");
		}

		return tokenGenerator.generarToken(username);

	}

	@Override
	public void logout(String token){
		tokenBlacklist.agregar(token);
	}

}
