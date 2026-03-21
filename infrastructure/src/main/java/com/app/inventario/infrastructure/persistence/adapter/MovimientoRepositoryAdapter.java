package com.app.inventario.infrastructure.persistence.adapter;

import com.app.inventario.domain.model.Movimiento;
import com.app.inventario.domain.model.TipoMovimiento;
import com.app.inventario.domain.port.out.MovimientoRepository;
import com.app.inventario.infrastructure.persistence.entity.MovimientoEntity;
import com.app.inventario.infrastructure.persistence.entity.ProductoEntity;
import com.app.inventario.infrastructure.persistence.entity.TipoMovimientoEntity;
import com.app.inventario.infrastructure.persistence.repository.MovimientoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MovimientoRepositoryAdapter implements MovimientoRepository {

	public final MovimientoJpaRepository jpaRepository;

	@Override
	public Movimiento guardar(Movimiento movimiento){
		MovimientoEntity entity = toEntity(movimiento);
		MovimientoEntity guardado = jpaRepository.save(entity);
		return toDomain(guardado);
	}

	@Override
	public List<Movimiento> buscarPorProductoId(Long productoId){
		return jpaRepository.findByProductoIdOrderByFechaDesc(productoId)
				.stream()
				.map(this::toDomain)
				.collect(Collectors.toList());
	}


	@Override
	public Integer calcularStockActual(Long productoId){
		return jpaRepository.calcularStockActual(productoId);
	}

	@Override
	public List<Movimiento> buscarTodos(){
		return jpaRepository.findAllByOrderByFechaDesc()
				.stream()
				.map(this::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Movimiento> buscarPorFecha(LocalDate fecha){
		return jpaRepository.findByFecha(fecha)
				.stream()
				.map(this::toDomain)
				.collect(Collectors.toList());
	}

	private MovimientoEntity toEntity(Movimiento movimiento){
		ProductoEntity productoEntity = new ProductoEntity();
		productoEntity.setId(movimiento.getProductoId());

		return MovimientoEntity.builder()
				.tipo(TipoMovimientoEntity.valueOf(movimiento.getTipo().name()))
				.cantidad(movimiento.getCantidad())
				.motivo(movimiento.getMotivo())
				.fecha(movimiento.getFecha())
				.creadoEn(movimiento.getCreadoEn())
				.producto(productoEntity)
				.build();
	}

	private Movimiento toDomain(MovimientoEntity entity){
		return Movimiento.builder()
				.id(entity.getId())
				.tipo(TipoMovimiento.valueOf(entity.getTipo().name()))
				.cantidad(entity.getCantidad())
				.motivo(entity.getMotivo())
				.fecha(entity.getFecha())
				.creadoEn(entity.getCreadoEn())
				.productoId(entity.getProducto().getId())
				.build();

	}

}
