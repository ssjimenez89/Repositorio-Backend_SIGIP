UPDATE dvar_estab
SET id_nestado = 5
WHERE
	dvar_estab.id_dvar_estab IN (
	SELECT
		dvar_estab.id_dvar_estab
	FROM
		dvar_estab
		JOIN nplanificacion ON dvar_estab.id_planificacion = nplanificacion.id_nplanificacion
		JOIN dplanificacion_fecha ON nplanificacion.id_nplanificacion = dplanificacion_fecha.id_nplanificacion
	WHERE
	dplanificacion_fecha.fecha = $1
	)