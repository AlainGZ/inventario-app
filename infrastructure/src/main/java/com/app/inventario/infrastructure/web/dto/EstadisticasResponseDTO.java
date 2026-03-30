package com.app.inventario.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class EstadisticasResponseDTO {

	private Integer totalEntradas;
	private Integer totalSalidas;
	private Integer totalProductosActivos;
	private Integer totalProductosConStockBajo;
	private String productoConMasMovimientos;

}
