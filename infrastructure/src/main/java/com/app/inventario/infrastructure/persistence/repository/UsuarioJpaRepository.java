package com.app.inventario.infrastructure.persistence.repository;

import com.app.inventario.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {

	Optional<UsuarioEntity> findByUsername(String username);
}
