package com.app.inventario.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Estadisticas {

	private Integer totalEntradas;
	private Integer totalSalidas;
	private Integer totalProductosActivos;
	private Integer totalProductosConStockBajo;
	private String productoConMasMovimientos;

}
