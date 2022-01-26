update tb_solicitud_prestamo
set cedula = :cedula,
	equipo_computo = :equipoComputo,
	fecha_creacion = :fechaCreacion,
	fecha_entrega = :fechaEntrega,
	estado = :estado
where id = :id
