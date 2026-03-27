package com.app.inventario.infrastructure.web.controller;

import com.app.inventario.domain.model.Producto;
import com.app.inventario.domain.port.in.ProductoUseCase;
import com.app.inventario.domain.port.out.MovimientoRepository;
import com.app.inventario.infrastructure.web.dto.ProductoRequestDTO;
import com.app.inventario.infrastructure.web.dto.ProductoResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

	private final ProductoUseCase productoUseCase;
	private final MovimientoRepository movimientoRepository;

	@PostMapping
	public ResponseEntity<ProductoResponseDTO> agregar(@Valid @RequestBody ProductoRequestDTO request){
		Producto producto = Producto.builder()
				.nombre(request.getNombre())
				.categoria(request.getCategoria())
				.precio(request.getPrecio())
				.stockMinimo(request.getStockMinimo())
				.build();
		Producto guardado = productoUseCase.agregarProducto(producto);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(toResponse(guardado));
	}

	@GetMapping
	public ResponseEntity<List<ProductoResponseDTO>> obtenerTodos(
			@RequestParam(required = false) String nombre,
			@RequestParam(required = false) String categoria){

		List<Producto> productos;

		if (nombre != null && !nombre.isBlank()){
			productos = productoUseCase.buscarPorNombre(nombre);
		} else if (categoria != null && !categoria.isBlank()) {
			productos = productoUseCase.buscarPorCategoria(categoria);
		}else {
			productos = productoUseCase.obtenerTodos();
		}

		return ResponseEntity.ok(
				productos.stream()
						.map(this::toResponse)
						.collect(Collectors.toList())
		);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductoResponseDTO> actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO request){

		Producto producto = productoUseCase.actualizarProducto(
				id,
				request.getNombre(),
				request.getCategoria(),
				request.getPrecio(),
				request.getStockMinimo()
		);

		return ResponseEntity.ok(toResponse(producto));

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarProducto(@PathVariable Long id){
		productoUseCase.desactivarProducto(id);
		return ResponseEntity.noContent().build();
	}


	private ProductoResponseDTO toResponse(Producto producto){

		Integer stockActual = movimientoRepository.calcularStockActual(producto.getId());

		return ProductoResponseDTO.builder()
				.id(producto.getId())
				.nombre(producto.getNombre())
				.categoria(producto.getCategoria())
				.precio(producto.getPrecio())
				.stockMinimo(producto.getStockMinimo())
				.creadoEn(producto.getCreadoEn())
				.stockBajo(stockActual <= producto.getStockMinimo())
				.stockActual(stockActual)
				.build();
	}

}
