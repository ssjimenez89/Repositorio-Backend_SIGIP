UPDATE destablecimiento SET estado = 18
WHERE destablecimiento.id_destablecimiento IN
(
SELECT
	destablecimiento_planificacion.id_destablecimiento
FROM
	 dplanificacion_fecha
	JOIN
	 nplanificacion ON nplanificacion.id_nplanificacion = dplanificacion_fecha.id_nplanificacion

	JOIN
	 destablecimiento_planificacion ON nplanificacion.id_nplanificacion = destablecimiento_planificacion.id_nplanificacion
WHERE
	dplanificacion_fecha.fecha = $1
)