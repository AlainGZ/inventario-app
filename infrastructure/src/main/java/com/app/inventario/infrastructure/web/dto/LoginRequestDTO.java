package com.app.inventario.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

	@NotBlank(message = "El usuario es obligatorio")
	private String username;

	@NotBlank(message = "La contrasena es obligatoria")
	private String password;

}
