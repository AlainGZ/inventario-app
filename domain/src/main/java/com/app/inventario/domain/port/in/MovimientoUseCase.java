package com.app.inventario.domain.port.in;

import com.app.inventario.domain.model.Movimiento;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoUseCase {

	Movimiento registrarEntrada(Long productoId, Integer cantidad, String motivo);

	Movimiento registrarSalida(Long productoId, Integer cantidad, String motivo);

	List<Movimiento> consultarHistorial(Long productoId, LocalDate fecha);

}
