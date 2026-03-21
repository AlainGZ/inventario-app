package com.app.inventario.infrastructure.persistence.repository;

import com.app.inventario.infrastructure.persistence.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoJpaRepository extends JpaRepository<MovimientoEntity, Long> {

	@Query("SELECT COALESCE(SUM(CASE WHEN m.tipo = 'ENTRADA' THEN m.cantidad ELSE -m.cantidad END), 0) " +
			"FROM MovimientoEntity m WHERE m.producto.id = :productoId")
	Integer calcularStockActual(@Param("productoId") Long prductoId);

	List<MovimientoEntity> findByProductoIdOrderByFechaDesc(Long productoId);

	@Query("SELECT m FROM MovimientoEntity m WHERE CAST(m.fecha AS date) = :fecha ORDER BY m.fecha DESC")
	List<MovimientoEntity> findByFecha(@Param("fecha") LocalDate fecha);

	List<MovimientoEntity> findAllByOrderByFechaDesc();

}
