package com.app.inventario.infrastructure.persistence.adapter;

import com.app.inventario.domain.model.Usuario;
import com.app.inventario.domain.port.out.UsuarioRepository;
import com.app.inventario.infrastructure.persistence.entity.UsuarioEntity;
import com.app.inventario.infrastructure.persistence.repository.UsuarioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepository {

	private final UsuarioJpaRepository jpaRepository;

	@Override
	public Optional<Usuario> buscarPorUsername(String username){
		return jpaRepository.findByUsername(username)
				.map(this::toDomain);
	}

	@Override
	public Usuario guardar(Usuario usuario){
		UsuarioEntity entity = toEntity(usuario);
		return toDomain(jpaRepository.save(entity));
	}

	private Usuario toDomain(UsuarioEntity entity){
		return Usuario.builder()
				.id(entity.getId())
				.username(entity.getUsername())
				.password(entity.getPassword())
				.build();
	}

	private UsuarioEntity toEntity(Usuario usuario){
		return UsuarioEntity.builder()
				.id(usuario.getId())
				.username(usuario.getUsername())
				.password(usuario.getPassword())
				.build();
	}

}
