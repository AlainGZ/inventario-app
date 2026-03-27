package com.app.inventario.domain.port.out;

import com.app.inventario.domain.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository {

	Producto guardar(Producto producto);
	Optional<Producto> buscarPorId(Long id);
	Optional<Producto> buscarPorNombre(String nombre);
	List<Producto> buscarTodosActivos();
	List<Producto> buscarPorCategoriaActivos(String categoria);
	List<Producto> buscarPorNombreContieneActivos(String nombre);
	Producto actualizar(Producto producto);

	boolean existePorNombre(String nombre);
}
