package com.app.inventario.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoResponseDTO {

	private Long id;
	private String tipo;
	private Integer cantidad;
	private String motivo;
	private LocalDateTime fecha;
	private Long productoId;
	private Integer stockActual;

}
