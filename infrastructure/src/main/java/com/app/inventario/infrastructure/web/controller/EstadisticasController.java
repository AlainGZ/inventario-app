package com.app.inventario.infrastructure.web.controller;

import com.app.inventario.domain.model.Estadisticas;
import com.app.inventario.domain.port.in.EstadisticasUseCase;
import com.app.inventario.infrastructure.web.dto.EstadisticasResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estadisticas")
@RequiredArgsConstructor
public class EstadisticasController {

	private final EstadisticasUseCase estadisticasUseCase;

	@GetMapping
	public ResponseEntity<EstadisticasResponseDTO> obtenerEstadisticas(){
		Estadisticas estadisticas = estadisticasUseCase.obtenerEstadisticas();

		return ResponseEntity.ok(
				EstadisticasResponseDTO.builder()
						.totalEntradas(estadisticas.getTotalEntradas())
						.totalSalidas(estadisticas.getTotalSalidas())
						.totalProductosActivos(estadisticas.getTotalProductosActivos())
						.totalProductosConStockBajo(estadisticas.getTotalProductosConStockBajo())
						.productoConMasMovimientos(estadisticas.getProductoConMasMovimientos())
						.build()
		);
	}

}
