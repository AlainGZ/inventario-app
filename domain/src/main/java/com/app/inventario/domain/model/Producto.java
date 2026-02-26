package com.app.inventario.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

	private Long id;
	private String nombre;
	private String categoria;
	private BigDecimal precio;
	private Integer stockMinimo;
	private LocalDateTime creadoEn;
	private LocalDateTime actualizadoEn;

}
