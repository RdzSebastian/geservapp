do $$ 
declare

   	extra_media_hora varchar := 'Media Hora';
    extra_hora varchar := 'Hora';
    extra_mesas_sillas_vestida varchar := 'Mesas y sillas vestidas';
    extra_souvenirs varchar := 'Souvenirs';
    extra_video varchar := 'Video';
	extra_foto varchar := 'Fotos de fiesta';
       
begin

	INSERT INTO EXTRA_SUB_TIPO_EVENTO (nombre,precio)
		VALUES (extra_media_hora,1);
	
	INSERT INTO EXTRA_SUB_TIPO_EVENTO (nombre,precio)
		VALUES (extra_hora,1);
	
	INSERT INTO EXTRA_SUB_TIPO_EVENTO (nombre,precio)
		VALUES (extra_mesas_sillas_vestida,1);

	INSERT INTO EXTRA_SUB_TIPO_EVENTO (nombre,precio)
		VALUES (extra_souvenirs,1);
	
	INSERT INTO EXTRA_SUB_TIPO_EVENTO (nombre,precio)
		VALUES (extra_video,1);
	
	INSERT INTO EXTRA_SUB_TIPO_EVENTO (nombre,precio)
		VALUES (extra_foto,1);

end$$