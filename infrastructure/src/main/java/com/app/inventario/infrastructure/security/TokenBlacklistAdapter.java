package com.app.inventario.infrastructure.security;

import com.app.inventario.domain.port.out.TokenBlacklist;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TokenBlacklistAdapter implements TokenBlacklist {

	private final Set<String> tokensInvalidos = new HashSet<>();

	@Override
	public void agregar(String token){
		tokensInvalidos.add(token);
	}

	@Override
	public boolean contiene(String token){
		return tokensInvalidos.contains(token);
	}

}
