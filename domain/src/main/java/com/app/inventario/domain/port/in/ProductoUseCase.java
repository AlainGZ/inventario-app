package com.app.inventario.domain.port.in;

import com.app.inventario.domain.model.Producto;

import java.util.List;

public interface ProductoUseCase {

	Producto agregarProducto(Producto producto);
	List<Producto> obtenerTodos();

}
