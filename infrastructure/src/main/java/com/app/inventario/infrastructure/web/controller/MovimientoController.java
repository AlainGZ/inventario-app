package com.app.inventario.infrastructure.web.controller;

import com.app.inventario.application.usecase.RegistrarEntradaUseCase;
import com.app.inventario.application.usecase.RegistrarSalidaUseCase;
import com.app.inventario.domain.model.Movimiento;
import com.app.inventario.domain.port.in.MovimientoUseCase;
import com.app.inventario.domain.port.out.MovimientoRepository;
import com.app.inventario.infrastructure.web.dto.MovimientoRequestDTO;
import com.app.inventario.infrastructure.web.dto.MovimientoResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

	private final RegistrarEntradaUseCase registrarEntradaUseCase;
	private final RegistrarSalidaUseCase registrarSalidaUseCase;
	private final MovimientoRepository movimientoRepository;
	private final MovimientoUseCase movimientoUseCase;

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

	@PostMapping("/salida")
	public ResponseEntity<MovimientoResponseDTO> registrarSalida(
			@Valid @RequestBody MovimientoRequestDTO request){

		Movimiento movimiento = registrarSalidaUseCase.ejecutar(
				request.getProductoId(),
				request.getCantidad(),
				request.getMotivo()
		);

		Integer stockActual = movimientoRepository.calcularStockActual(request.getProductoId());

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(toResponse(movimiento, stockActual));
	}

	@GetMapping
	public ResponseEntity<List<MovimientoResponseDTO>> consultarHistorial(
			@RequestParam(required = false) Long productoId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fecha){

		List<Movimiento> movimientos = movimientoUseCase.consultarHistorial(productoId, fecha);

		List<MovimientoResponseDTO> response = movimientos.stream()
				.map(m -> toResponse(m, movimientoRepository.calcularStockActual(m.getProductoId())))
				.collect(Collectors.toList());

		return ResponseEntity.ok(response);
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
