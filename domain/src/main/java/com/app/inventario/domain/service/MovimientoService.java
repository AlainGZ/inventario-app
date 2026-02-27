package com.app.inventario.domain.service;

import com.app.inventario.domain.model.Movimiento;
import com.app.inventario.domain.model.TipoMovimiento;
import com.app.inventario.domain.port.in.MovimientoUseCase;
import com.app.inventario.domain.port.out.MovimientoRepository;
import com.app.inventario.domain.port.out.ProductoRepository;

import java.time.LocalDateTime;

public class MovimientoService implements MovimientoUseCase {

	private final MovimientoRepository movimientoRepository;
	private final ProductoRepository productoRepository;

	public MovimientoService(MovimientoRepository movimientoRepository,
							 ProductoRepository productoRepository){
		this.movimientoRepository = movimientoRepository;
		this.productoRepository = productoRepository;
	}

	@Override
	public Movimiento registrarEntrada(Long productoId,
									   Integer cantidad,
									   String motivo){

		productoRepository.buscarPorId(productoId)
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe un producto con id: " + productoId
				));

		if (cantidad <= 0){
			throw new IllegalArgumentException(
					"La cantidad debe ser mayor a 0"
			);
		}

		Movimiento movimiento = Movimiento.builder()
				.tipo(TipoMovimiento.ENTRADA)
				.cantidad(cantidad)
				.motivo(motivo)
				.fecha(LocalDateTime.now())
				.creadoEn(LocalDateTime.now())
				.productoId(productoId)
				.build();

		return movimientoRepository.guardar(movimiento);
	}

}
