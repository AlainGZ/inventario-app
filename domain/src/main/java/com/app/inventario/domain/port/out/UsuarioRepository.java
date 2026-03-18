package com.app.inventario.domain.port.out;

import com.app.inventario.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository {

	Optional<Usuario> buscarPorUsername(String username);

	Usuario guardar(Usuario usuario);

}
