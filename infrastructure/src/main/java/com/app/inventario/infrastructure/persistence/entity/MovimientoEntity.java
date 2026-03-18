package com.app.inventario.infrastructure.persistence.entity;

import com.app.inventario.domain.model.TipoMovimiento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoMovimientoEntity tipo;

	@Column(nullable = false)
	private Integer cantidad;

	private String motivo;

	@Column(nullable = false)
	private LocalDateTime fecha;

	@Column(name = "creado_en")
	private LocalDateTime creadoEn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_id", nullable = false)
	private ProductoEntity producto;

}
