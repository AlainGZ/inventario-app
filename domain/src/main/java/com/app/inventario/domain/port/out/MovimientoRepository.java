package com.app.inventario.domain.port.out;

import com.app.inventario.domain.model.Movimiento;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoRepository {

	Movimiento guardar(Movimiento movimiento);

	List<Movimiento> buscarPorProductoId(Long productoId);

	Integer calcularStockActual(Long productoId);

	List<Movimiento> buscarTodos();

	List<Movimiento> buscarPorFecha(LocalDate fecha);

}
