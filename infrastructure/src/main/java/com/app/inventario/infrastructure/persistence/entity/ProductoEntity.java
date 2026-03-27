package com.app.inventario.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String nombre;

	@Column(nullable = false)
	private String categoria;

	@Column(nullable = false)
	private BigDecimal precio;

	@Column(name = "stock_minimo")
	private Integer stockMinimo;

	@Column(name = "creado_en")
	private LocalDateTime creadoEn;

	@Column(name = "actualizado_en")
	private LocalDateTime actualizadoEn;

	@Column(nullable = false)
	private Boolean activo = true;
}
