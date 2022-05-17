do $$ 
declare
	extra_animadora_ninos varchar := 'Animadora de niños';
    extra_animacion_adultos varchar := 'Animacion de adultos';
    extra_capacidad varchar := 'Capacidad';
    extra_camarero varchar := 'Camarero';
    extra_mozo varchar := 'Mozo';
   	extra_media_hora varchar := 'Media Hora';
    extra_hora varchar := 'Hora';
    extra_mesas_sillas_vestida varchar := 'Mesas y sillas vestidas';
    extra_cuidadora_ninos varchar := 'Cuidadora de niños';
    extra_show_varios varchar := 'Show varios';
    extra_barra_tragos varchar := 'Barra de tragos';
    extra_torta_mesa_dulce_candy_bar varchar := 'Torta, mesa dulce y/o candy bar';
    extra_souvenirs varchar := 'Souvenirs';
    extra_video varchar := 'Video';
	extra_foto varchar := 'Fotos de fiesta';
       
begin
	
	INSERT INTO extra (nombre,precio)
		VALUES (extra_animadora_ninos,1);
	
	INSERT INTO extra (nombre,precio)
		VALUES (extra_animacion_adultos,1);
	
	INSERT INTO extra (nombre,precio)
		VALUES (extra_capacidad,1);
	
	INSERT INTO extra (nombre,precio)
		VALUES (extra_camarero,1);
	
	INSERT INTO extra (nombre,precio)
		VALUES (extra_mozo,1);
	
	INSERT INTO extra (nombre,precio)
		VALUES (extra_media_hora,1);
	
	INSERT INTO extra (nombre,precio)
		VALUES (extra_hora,1);
	
	INSERT INTO extra (nombre,precio)
		VALUES (extra_mesas_sillas_vestida,1);
	
	INSERT INTO extra (nombre,precio)
		VALUES (extra_cuidadora_ninos,1);
	
	INSERT INTO extra (nombre,precio)
		VALUES (extra_show_varios,1);
	
	INSERT INTO extra (nombre,precio)
		VALUES (extra_barra_tragos,1);
	
	INSERT INTO extra (nombre,precio)
		VALUES (extra_torta_mesa_dulce_candy_bar,1);
	
	INSERT INTO extra (nombre,precio)
		VALUES (extra_souvenirs,1);
	
	INSERT INTO extra (nombre,precio)
		VALUES (extra_video,1);
	
	INSERT INTO extra (nombre,precio)
		VALUES (extra_foto,1);

end$$