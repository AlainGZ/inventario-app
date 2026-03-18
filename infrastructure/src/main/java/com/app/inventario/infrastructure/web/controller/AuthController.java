package com.app.inventario.infrastructure.web.controller;

import com.app.inventario.domain.port.in.AuthUseCase;
import com.app.inventario.infrastructure.web.dto.LoginRequestDTO;
import com.app.inventario.infrastructure.web.dto.LoginResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthUseCase authUseCase;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request){

		String token = authUseCase.login(
				request.getUsername(),
				request.getPassword()
		);
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader){

		String token = authHeader.substring(7);
		authUseCase.logout(token);
		return ResponseEntity.noContent().build();

	}

}