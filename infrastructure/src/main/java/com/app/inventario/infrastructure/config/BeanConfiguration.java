package com.app.inventario.infrastructure.config;

import com.app.inventario.domain.port.in.ProductoUseCase;
import com.app.inventario.domain.port.out.ProductoRepository;
import com.app.inventario.domain.service.ProductoService;
import com.app.inventario.infrastructure.web.dto.ProductoResponseDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

	@Bean
	public ProductoUseCase productoUseCase(ProductoRepository productoRepository){
		return new ProductoService(productoRepository);
	}

}
