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
				.activo(true)
				.build();
		return productoRepository.guardar(productoNuevo);

	}

	@Override
	public List<Producto> obtenerTodos(){
		return productoRepository.buscarTodosActivos();
	}

	@Override
	public List<Producto> buscarPorCategoria(String categoria){
		return productoRepository.buscarPorCategoriaActivos(categoria);
	}

	@Override
	public List<Producto> buscarPorNombre(String nombre){
		return productoRepository.buscarPorNombreContieneActivos(nombre);
	}

	@Override
	public Producto actualizarProducto(Long id, String nombre, String categoria, BigDecimal precio, Integer stockMinimo){

		Producto productoExistente = productoRepository.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("No existe un producto con id: "+id));

		Producto productoActualizado = Producto.builder()
				.id(productoExistente.getId())
				.nombre(nombre)
				.categoria(categoria)
				.precio(precio)
				.stockMinimo(stockMinimo)
				.creadoEn(productoExistente.getCreadoEn())
				.actualizadoEn(LocalDateTime.now())
				.activo(productoExistente.getActivo())
				.build();

		return productoRepository.actualizar(productoActualizado);


	}

	@Override
	public void desactivarProducto(Long id){
		Producto producto = productoRepository.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("No existe un producto con id: "+id));

		Producto productoDesactivado = Producto.builder()
				.id(producto.getId())
				.nombre(producto.getNombre())
				.categoria(producto.getCategoria())
				.precio(producto.getPrecio())
				.stockMinimo(producto.getStockMinimo())
				.creadoEn(producto.getCreadoEn())
				.actualizadoEn(LocalDateTime.now())
				.activo(false)
				.build();
		productoRepository.actualizar(productoDesactivado);
	}

}
