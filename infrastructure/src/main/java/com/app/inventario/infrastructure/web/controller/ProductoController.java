package com.app.inventario.infrastructure.web.controller;

import com.app.inventario.domain.model.Producto;
import com.app.inventario.domain.port.in.ProductoUseCase;
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
	public ResponseEntity<List<ProductoResponseDTO>> obtenerTodos(){
		List<ProductoResponseDTO> productos = productoUseCase.obtenerTodos()
				.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
		return ResponseEntity.ok(productos);
	}


	private ProductoResponseDTO toResponse(Producto producto){
		return ProductoResponseDTO.builder()
				.id(producto.getId())
				.nombre(producto.getNombre())
				.categoria(producto.getCategoria())
				.precio(producto.getPrecio())
				.stockMinimo(producto.getStockMinimo())
				.creadoEn(producto.getCreadoEn())
				.build();
	}

}
