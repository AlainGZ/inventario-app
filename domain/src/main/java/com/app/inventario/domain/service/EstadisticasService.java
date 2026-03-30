package com.app.inventario.domain.service;

import com.app.inventario.domain.model.Estadisticas;
import com.app.inventario.domain.port.in.EstadisticasUseCase;
import com.app.inventario.domain.port.out.EstadisticasRepository;

public class EstadisticasService implements EstadisticasUseCase {

	private final EstadisticasRepository estadisticasRepository;

	public EstadisticasService(EstadisticasRepository estadisticasRepository){
		this.estadisticasRepository = estadisticasRepository;
	}

	@Override
	public Estadisticas obtenerEstadisticas(){
		return Estadisticas.builder()
				.totalEntradas(estadisticasRepository.totalEntradas())
				.totalSalidas(estadisticasRepository.totalSalidas())
				.totalProductosActivos(estadisticasRepository.totalProductosActivos())
				.totalProductosConStockBajo(estadisticasRepository.totalProductosConStockBajo())
				.productoConMasMovimientos(estadisticasRepository.productoConMasMovimientos())
				.build();
	}

}
