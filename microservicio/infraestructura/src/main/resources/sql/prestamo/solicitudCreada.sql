select id, cedula,equipo_computo, fecha_creacion,fecha_entrega,estado
from tb_solicitud_prestamo where cedula = :cedula and estado != 0


