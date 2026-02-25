package com.app.inventario.domain.service;

import com.app.inventario.domain.model.Producto;
import com.app.inventario.domain.port.in.ProductoUseCase;
import com.app.inventario.domain.port.out.ProductoRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProductoService implements ProductoUseCase {

	private final ProductoRepository productoRepository;

	public ProductoService(ProductoRepository productoRepository){
		this.productoRepository = productoRepository;
	}

	@Override
	public Producto agregarProducto(Producto producto){

		if (producto.getNombre() == null || producto.getNombre().isBlank()){
			throw new IllegalArgumentException(
					"El nombre del producto es obligatorio"
			);
		}


		if (productoRepository.existePorNombre((producto.getNombre()))){
			throw new IllegalArgumentException(
					"Ya existe un producto con el nombre: " + producto.getNombre()
			);
		}


		if (producto.getPrecio().compareTo(BigDecimal.ZERO) < 0){
			throw new IllegalArgumentException(
					"El precio no puede ser negativo"
			);
		}


		Producto productoNuevo = Producto.builder()
				.nombre(producto.getNombre())
				.categoria(producto.getCategoria())
				.precio(producto.getPrecio())
				.stockMinimo(producto.getStockMinimo())
				.creadoEn(LocalDateTime.now())
				.actualizadoEn(LocalDateTime.now())
				.build();
		return productoRepository.guardar(productoNuevo);

	}

	@Override
	public List<Producto> obtenerTodos(){
		return productoRepository.buscarTodos();
	}


}
