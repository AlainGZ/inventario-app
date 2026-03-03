package com.app.inventario.infrastructure.web.controller;

import com.app.inventario.application.usecase.RegistrarEntradaUseCase;
import com.app.inventario.domain.model.Movimiento;
import com.app.inventario.domain.port.out.MovimientoRepository;
import com.app.inventario.infrastructure.web.dto.MovimientoRequestDTO;
import com.app.inventario.infrastructure.web.dto.MovimientoResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

	private final RegistrarEntradaUseCase registrarEntradaUseCase;
	private final MovimientoRepository movimientoRepository;

	@PostMapping("/entrada")
	public ResponseEntity<MovimientoResponseDTO> registrarEntrada(
			@Valid @RequestBody MovimientoRequestDTO request){

		Movimiento movimiento = registrarEntradaUseCase.ejecutar(
				request.getProductoId(),
				request.getCantidad(),
				request.getMotivo()
		);

		Integer stockActual = movimientoRepository
				.calcularStockActual(request.getProductoId());
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(toResponse(movimiento, stockActual));

	}

	private MovimientoResponseDTO toResponse(Movimiento movimiento, Integer stockActual){

		return MovimientoResponseDTO.builder()
				.id(movimiento.getId())
				.tipo(movimiento.getTipo().name())
				.cantidad(movimiento.getCantidad())
				.motivo(movimiento.getMotivo())
				.fecha(movimiento.getFecha())
				.productoId(movimiento.getProductoId())
				.stockActual(stockActual)
				.build();

	}


}
