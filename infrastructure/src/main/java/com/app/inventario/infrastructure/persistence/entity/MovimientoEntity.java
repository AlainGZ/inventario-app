package com.app.inventario.infrastructure.persistence.entity;

import com.app.inventario.domain.model.TipoMovimiento;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class MovimientoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private TipoMovimiento tipo;

	@Column(nullable = false)
	private Integer cantidad;

	private String motivo;

	private LocalDateTime fecha;

	@Column(name = "creado_en")
	private LocalDateTime creadoEn;

	@Column(name = "producto_id")
	private Long productoId;

}
