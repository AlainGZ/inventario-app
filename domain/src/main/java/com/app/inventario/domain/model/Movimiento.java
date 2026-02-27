package com.app.inventario.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movimiento {

	private Long id;
	private TipoMovimiento tipo;
	private Integer cantidad;
	private String motivo;
	private LocalDateTime fecha;
	private LocalDateTime creadoEn;
	private Long productoId;

}
