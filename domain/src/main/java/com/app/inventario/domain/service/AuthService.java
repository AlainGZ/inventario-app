package com.app.inventario.domain.service;

import com.app.inventario.domain.model.Usuario;
import com.app.inventario.domain.port.in.AuthUseCase;
import com.app.inventario.domain.port.out.TokenGenerator;
import com.app.inventario.domain.port.out.UsuarioRepository;

public class AuthService implements AuthUseCase {

	private final UsuarioRepository usuarioRepository;
	private final TokenGenerator tokenGenerator;

	public AuthService(UsuarioRepository usuarioRepository, TokenGenerator tokenGenerator){
		this.usuarioRepository = usuarioRepository;
		this.tokenGenerator = tokenGenerator;
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
}
