package com.app.inventario.infrastructure.config;

import com.app.inventario.application.usecase.RegistrarEntradaUseCase;
import com.app.inventario.application.usecase.RegistrarSalidaUseCase;
import com.app.inventario.domain.model.Movimiento;
import com.app.inventario.domain.port.in.AuthUseCase;
import com.app.inventario.domain.port.in.EstadisticasUseCase;
import com.app.inventario.domain.port.in.MovimientoUseCase;
import com.app.inventario.domain.port.in.ProductoUseCase;
import com.app.inventario.domain.port.out.*;
import com.app.inventario.domain.service.AuthService;
import com.app.inventario.domain.service.EstadisticasService;
import com.app.inventario.domain.service.MovimientoService;
import com.app.inventario.domain.service.ProductoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

	@Bean
	public ProductoUseCase productoUseCase(ProductoRepository productoRepository){
		return new ProductoService(productoRepository);
	}

	@Bean
	public MovimientoUseCase movimientoUseCase(MovimientoRepository movimientoRepository, ProductoRepository productoRepository){
		return new MovimientoService(movimientoRepository,productoRepository);
	}

	@Bean
	public RegistrarEntradaUseCase registrarEntradaUseCase(MovimientoUseCase movimientoUseCase){
		return new RegistrarEntradaUseCase(movimientoUseCase);
	}

	@Bean
	public RegistrarSalidaUseCase registrarSalidaUseCase(MovimientoUseCase movimientoUseCase){
		return new RegistrarSalidaUseCase(movimientoUseCase);
	}

	@Bean
	public AuthUseCase authUseCase(UsuarioRepository usuarioRepository, TokenGenerator tokenGenerator, TokenBlacklist tokenBlacklist){
		return new AuthService(usuarioRepository, tokenGenerator, tokenBlacklist);
	}

	@Bean
	public EstadisticasUseCase estadisticasUseCase(EstadisticasRepository estadisticasRepository){
		return new EstadisticasService(estadisticasRepository);
	}
}
