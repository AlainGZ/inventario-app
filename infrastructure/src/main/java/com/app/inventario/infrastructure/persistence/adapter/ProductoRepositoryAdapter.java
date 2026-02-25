package com.app.inventario.infrastructure.persistence.adapter;


import com.app.inventario.domain.model.Producto;
import com.app.inventario.domain.port.out.ProductoRepository;
import com.app.inventario.infrastructure.persistence.entity.ProductoEntity;
import com.app.inventario.infrastructure.persistence.repository.ProductoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductoRepositoryAdapter implements ProductoRepository {

	private final ProductoJpaRepository jpaRepository;

	@Override
	public Producto guardar(Producto producto){
		ProductoEntity entity = toEntity(producto);
		ProductoEntity guardado = jpaRepository.save(entity);
		return toDomain(guardado);
	}

	@Override
	public Optional<Producto> buscarPorId(Long id){
		return jpaRepository.findById(id).map(this::toDomain);
	}

	@Override
	public Optional<Producto> buscarPorNombre(String nombre){
		return jpaRepository.findByNombre(nombre).map(this::toDomain);
	}

	@Override
	public List<Producto> buscarTodos(){
		return jpaRepository.findAll()
				.stream()
				.map(this::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public boolean existePorNombre(String nombre){
		return jpaRepository.existsByNombre(nombre);
	}


	private ProductoEntity toEntity(Producto producto){
		return ProductoEntity.builder()
				.id(producto.getId())
				.nombre(producto.getNombre())
				.categoria(producto.getCategoria())
				.precio(producto.getPrecio())
				.stockMinimo(producto.getStockMinimo())
				.creadoEn(producto.getCreadoEn())
				.actualizadoEn(producto.getActualizadoEn())
				.build();
	}

	private Producto toDomain(ProductoEntity entity){
		return Producto.builder()
				.id(entity.getId())
				.nombre(entity.getNombre())
				.categoria(entity.getCategoria())
				.precio(entity.getPrecio())
				.stockMinimo(entity.getStockMinimo())
				.creadoEn(entity.getCreadoEn())
				.actualizadoEn(entity.getActualizadoEn())
				.build();
	}

}
