do $$ 
declare

	servicio_invitacion varchar := 'Invitacion digital';
    servicio_ayudante_cocina varchar := 'Ayudante de cocina: 1';
    servicio_musicalizacion varchar := 'Musicalizacion durante todo el evento';
   	servicio_zona_aire_libre varchar := 'Zona al aire libre';
    servicio_vajilla_lunch varchar := 'Servicio de vajilla para lunch';
    servicio_servicio_te varchar := 'Servicio de te y cafe (durante la mesa dulce)';
    servicio_espacio_candybar varchar := 'Espacio para candy bar';
    servicio_proyector varchar := 'Proyector';
    servicio_wifi varchar := 'Wifi';
    servicio_asistencia_medica varchar := 'Asistencia médica';
    servicio_seguridad varchar := 'Seguridad';


	servicio_coordinadora_evento varchar := 'Coordinadora del evento: 1';
	servicio_coordinadora_evento_2 varchar := 'Coordinadora del evento: 2';
	servicio_animadora varchar := 'Animadora: 1';
	servicio_animadora_2 varchar := 'Animadora: 2';
    servicio_pelos_locos varchar := 'Pelos locos';
    servicio_pulceras_luminosas varchar := 'Pulceras luminosas';
    servicio_pista_baile varchar := 'Pista de baile';
    servicio_snacks varchar := 'Snacks, pizza libre y barra de jugos y gaseosas';
    servicio_pelotero varchar := 'Pelotero';
    servicio_plaza_blanda varchar := 'Plaza blanda';
    servicio_dj varchar := 'DJ';
    servicio_mesas_sillas_vestidas varchar := 'Mesas y sillas vestidas';
    servicio_camarero varchar := 'Camarero: 1';
    servicio_mozo varchar := 'Mozo: 1';
	servicio_camino_mesas varchar := 'Camino para las mesas';

begin
	
	INSERT INTO servicio (nombre)
		VALUES (servicio_invitacion);
	
	INSERT INTO servicio (nombre)
		VALUES (servicio_ayudante_cocina);
	
	INSERT INTO servicio (nombre)
		VALUES (servicio_musicalizacion);
	
	INSERT INTO servicio (nombre)
		VALUES (servicio_zona_aire_libre);
	
	INSERT INTO servicio (nombre)
		VALUES (servicio_vajilla_lunch);
	
	INSERT INTO servicio (nombre)
		VALUES (servicio_servicio_te);
	
	INSERT INTO servicio (nombre)
		VALUES (servicio_espacio_candybar);
	
	INSERT INTO servicio (nombre)
		VALUES (servicio_proyector);
	
	INSERT INTO servicio (nombre)
		VALUES (servicio_wifi);
	
	INSERT INTO servicio (nombre)
		VALUES (servicio_asistencia_medica);
	
	INSERT INTO servicio (nombre)
		VALUES (servicio_seguridad);
	
	INSERT INTO servicio (nombre)
		VALUES (servicio_coordinadora_evento);

	INSERT INTO servicio (nombre)
		VALUES (servicio_coordinadora_evento_2);
	
	INSERT INTO servicio (nombre)
		VALUES (servicio_animadora);

	INSERT INTO servicio (nombre)
		VALUES (servicio_animadora_2);
	
	INSERT INTO servicio (nombre)
		VALUES (servicio_pelos_locos);
	
	INSERT INTO servicio (nombre)
		VALUES (servicio_pulceras_luminosas);

	INSERT INTO servicio (nombre)
		VALUES (servicio_pista_baile);

	INSERT INTO servicio (nombre)
		VALUES (servicio_snacks);

	INSERT INTO servicio (nombre)
		VALUES (servicio_pelotero);

	INSERT INTO servicio (nombre)
		VALUES (servicio_plaza_blanda);

	INSERT INTO servicio (nombre)
		VALUES (servicio_dj);

	INSERT INTO servicio (nombre)
		VALUES (servicio_mesas_sillas_vestidas);

	INSERT INTO servicio (nombre)
		VALUES (servicio_camarero);

	INSERT INTO servicio (nombre)
		VALUES (servicio_mozo);

	INSERT INTO servicio (nombre)
		VALUES (servicio_camino_mesas);

end$$