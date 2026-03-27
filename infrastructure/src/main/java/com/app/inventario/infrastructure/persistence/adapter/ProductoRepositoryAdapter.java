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
	public List<Producto> buscarTodosActivos(){
		return jpaRepository.findByActivoTrueOrderByNombreAsc()
				.stream()
				.map(this::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public boolean existePorNombre(String nombre){
		return jpaRepository.existsByNombre(nombre);
	}

	@Override
	public List<Producto> buscarPorCategoriaActivos(String categoria){
		return jpaRepository.findByActivoTrueAndCategoriaContainingIgnoreCase(categoria)
				.stream()
				.map(this::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Producto> buscarPorNombreContieneActivos(String nombre){
		return jpaRepository.findByActivoTrueAndNombreContainingIgnoreCase(nombre)
				.stream()
				.map(this::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Producto actualizar(Producto producto){
		ProductoEntity entity = toEntity(producto);
		return toDomain(jpaRepository.save(entity));
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
				.activo(producto.getActivo())
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
				.activo(entity.getActivo())
				.build();
	}

}
