package com.app.inventario.domain.port.in;

import com.app.inventario.domain.model.Movimiento;

public interface MovimientoUseCase {

	Movimiento registrarEntrada(Long productoId, Integer cantidad, String motivo);

	Movimiento registrarSalida(Long productoId, Integer cantidad, String motivo);
}
