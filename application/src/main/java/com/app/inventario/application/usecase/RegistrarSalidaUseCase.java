package com.app.inventario.application.usecase;

import com.app.inventario.domain.model.Movimiento;
import com.app.inventario.domain.port.in.MovimientoUseCase;

public class RegistrarSalidaUseCase {

	private final MovimientoUseCase movimientoUseCase;

	public RegistrarSalidaUseCase(MovimientoUseCase movimientoUseCase){
		this.movimientoUseCase = movimientoUseCase;
	}

	public Movimiento ejecutar(Long productoId, Integer cantidad, String motivo){
		return movimientoUseCase.registrarSalida(productoId, cantidad, motivo);
	}

}
