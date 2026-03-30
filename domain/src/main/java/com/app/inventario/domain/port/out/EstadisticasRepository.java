package com.app.inventario.domain.port.out;

public interface EstadisticasRepository {

	Integer totalEntradas();

	Integer totalSalidas();

	Integer totalProductosActivos();

	Integer totalProductosConStockBajo();

	String productoConMasMovimientos();

}
