package com.app.inventario.domain.service;

import com.app.inventario.domain.model.Movimiento;
import com.app.inventario.domain.model.TipoMovimiento;
import com.app.inventario.domain.port.in.MovimientoUseCase;
import com.app.inventario.domain.port.out.MovimientoRepository;
import com.app.inventario.domain.port.out.ProductoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

	@Override
	public Movimiento registrarSalida(Long productoId, Integer cantidad, String motivo){

		productoRepository.buscarPorId(productoId)
				.orElseThrow(() -> new IllegalArgumentException(
						"No existe un producto con id: " + productoId
				));

		if (cantidad <= 0){
			throw new IllegalArgumentException(
					"La cantidad debe ser mayor a cero"
			);
		}

		Integer stockActual = movimientoRepository.calcularStockActual(productoId);
		if (cantidad > stockActual){
			throw new IllegalArgumentException(
					"Stock insuficiente. El stock actual es: " + stockActual + " unidades."
			);
		}

		Movimiento movimiento = Movimiento.builder()
				.tipo(TipoMovimiento.SALIDA)
				.cantidad(cantidad)
				.motivo(motivo)
				.fecha(LocalDateTime.now())
				.creadoEn(LocalDateTime.now())
				.productoId(productoId)
				.build();

		return movimientoRepository.guardar(movimiento);

	}

	@Override
	public List<Movimiento> consultarHistorial(Long productoId, LocalDate fecha){

		if (productoId != null && fecha != null){
			return movimientoRepository.buscarPorProductoId(productoId)
					.stream()
					.filter(m -> m.getFecha().toLocalDate().equals(fecha))
					.collect(Collectors.toList());
		}

		if (productoId != null){
			return movimientoRepository.buscarPorProductoId(productoId);
		}

		if (fecha != null){
			return movimientoRepository.buscarPorFecha(fecha);
		}

		return movimientoRepository.buscarTodos();

	}

}
