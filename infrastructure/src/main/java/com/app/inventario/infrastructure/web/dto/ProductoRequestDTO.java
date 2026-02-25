package com.app.inventario.infrastructure.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequestDTO {

	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;

	@NotBlank(message = "La categoria es obligatoria")
	private String categoria;

	@NotNull(message = "El precio es obligatorio")
	@DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
	private BigDecimal precio;

	private Integer stockMinimo;

}
