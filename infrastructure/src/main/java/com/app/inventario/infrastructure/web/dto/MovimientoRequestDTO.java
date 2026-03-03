package com.app.inventario.infrastructure.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoRequestDTO {

	@NotNull(message = "El producto es obligatorio")
	private Long productoId;

	@NotNull(message = "La cantidad es obligatoria")
	@Min(value = 1, message = "La cantidad debe ser mayor a cero")
	private Integer cantidad;

	private String motivo;

}
