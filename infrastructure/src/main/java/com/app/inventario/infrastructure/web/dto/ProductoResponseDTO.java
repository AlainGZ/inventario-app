package com.app.inventario.infrastructure.web.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponseDTO {

	private Long id;
	private String nombre;
	private String categoria;
	private BigDecimal precio;
	private Integer stockMinimo;
	private LocalDateTime creadoEn;
	private boolean stockBajo;
    private Integer stockActual;
}
