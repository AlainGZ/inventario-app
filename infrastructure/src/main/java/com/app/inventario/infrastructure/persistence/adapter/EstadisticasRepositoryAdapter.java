package com.app.inventario.infrastructure.persistence.adapter;

import com.app.inventario.domain.port.out.EstadisticasRepository;
import com.app.inventario.infrastructure.persistence.entity.ProductoEntity;
import com.app.inventario.infrastructure.persistence.repository.MovimientoJpaRepository;
import com.app.inventario.infrastructure.persistence.repository.ProductoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EstadisticasRepositoryAdapter implements EstadisticasRepository {

	private final MovimientoJpaRepository movimientoJpaRepository;
	private final ProductoJpaRepository productoJpaRepository;

	@Override
	public Integer totalEntradas(){
		return movimientoJpaRepository.totalEntradas();
	}

	@Override
	public Integer totalSalidas(){
		return movimientoJpaRepository.totalSalidas();
	}

	@Override
	public Integer totalProductosActivos(){
		return productoJpaRepository.countByActivoTrue();
	}

	@Override
	public Integer totalProductosConStockBajo(){
		List<ProductoEntity> productosActivos = productoJpaRepository.findByActivoTrueOrderByNombreAsc();

		return (int) productosActivos.stream()
				.filter(p -> {
					Integer stockActual = movimientoJpaRepository.calcularStockActual(p.getId());
					return stockActual <= p.getStockMinimo();
				})
				.count();
	}

	@Override
	public String productoConMasMovimientos(){
		Long productoId = movimientoJpaRepository.idProductoConMasMovimientos();

		if (productoId == null){
			return "Sin movimientos";
		}

		return productoJpaRepository.findById(productoId)
				.map(ProductoEntity::getNombre)
				.orElse("Producto no encontrado");
	}

}
