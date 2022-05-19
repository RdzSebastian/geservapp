do $$ 
declare
	extra_animadora_ninos varchar := 'Animadora de niños';
    extra_animacion_adultos varchar := 'Animacion de adultos';
    extra_capacidad varchar := 'Capacidad';
    extra_camarero varchar := 'Camarero';
   	extra_media_hora varchar := 'Media Hora';
    extra_hora varchar := 'Hora';
    extra_mesas_sillas_vestida varchar := 'Mesas y sillas vestidas';
    extra_mozo varchar := 'Mozo';
    extra_cuidadora_ninos varchar := 'Cuidadora de niños';
    extra_show_varios varchar := 'Show varios';
    extra_barra_tragos varchar := 'Barra de tragos';
    extra_torta_mesa_dulce_candy_bar varchar := 'Torta, mesa dulce y/o candy bar';
    extra_souvenirs varchar := 'Souvenirs';
    extra_video varchar := 'Video';
	extra_foto_fiesta varchar := 'Fotos de fiesta';
	
	extra_animadora_ninos_id int := 0;
    extra_animacion_adultos_id int := 0;
    extra_capacidad_id int := 0;
	extra_camarero_id int := 0;
	extra_media_hora_id int := 0;
	extra_hora_id int := 0;
	extra_mesas_sillas_vestida_id int := 0;
	extra_mozo_id int := 0;
	extra_cuidadora_ninos_id int := 0;
	extra_show_varios_id int := 0;
	extra_barra_tragos_id int := 0;
	extra_torta_mesa_dulce_candy_bar_id int := 0;
	extra_souvenirs_id int := 0;
	extra_video_id int := 0;
	extra_foto_fiesta_id int := 0;
	
   	sub_tipo_evento_bautismo varchar := 'Bautismo / 1 Añito';
	sub_tipo_evento_baby_shower varchar := 'Baby Shower';
	sub_tipo_evento_comunion varchar := 'Comunion';
	sub_tipo_evento_empresarial varchar := 'Empresarial';
	sub_tipo_evento_casamiento varchar := 'Casamiento';
	sub_tipo_evento_quinces_corto varchar := 'Quinces Corto';
	sub_tipo_evento_quinces varchar := 'Quinces';
	sub_tipo_evento_cumpleanos_infantil varchar := 'Cumpleaños Infantil';
	sub_tipo_evento_cumpleanos_teens varchar := 'Cumpleaños Teens';
	sub_tipo_evento_cumpleanos_adulto varchar := 'Cumpleaños Adulto';

	sub_tipo_evento_bautismo_id int := 0;
	sub_tipo_evento_baby_shower_id int := 0;
	sub_tipo_evento_comunion_id int := 0;
	sub_tipo_evento_infantiles_id int := 0;
	sub_tipo_evento_empresarial_id int := 0;
	sub_tipo_evento_casamiento_id int := 0;
	sub_tipo_evento_quinces_corto_id int := 0;
	sub_tipo_evento_quinces_id int := 0;
	sub_tipo_evento_cumpleanos_infantil_id int := 0;
	sub_tipo_evento_cumpleanos_teens_id int := 0;
	sub_tipo_evento_cumpleanos_adulto_id int := 0;

begin

	extra_animadora_ninos_id :=(SELECT id FROM EXTRA WHERE nombre = extra_animadora_ninos);
	extra_animacion_adultos_id := (SELECT id FROM EXTRA WHERE nombre = extra_animacion_adultos);
	extra_capacidad_id := (SELECT id FROM EXTRA WHERE nombre = extra_capacidad);
	extra_camarero_id := (SELECT id FROM EXTRA WHERE nombre = extra_camarero);
	extra_media_hora_id := (SELECT id FROM EXTRA WHERE nombre = extra_media_hora);
	extra_hora_id := (SELECT id FROM EXTRA WHERE nombre = extra_hora);
	extra_mesas_sillas_vestida_id := (SELECT id FROM EXTRA WHERE nombre = extra_mesas_sillas_vestida);
	extra_mozo_id := (SELECT id FROM EXTRA WHERE nombre = extra_mozo);
	extra_cuidadora_ninos_id := (SELECT id FROM EXTRA WHERE nombre = extra_cuidadora_ninos);
	extra_show_varios_id := (SELECT id FROM EXTRA WHERE nombre = extra_show_varios);
	extra_barra_tragos_id := (SELECT id FROM EXTRA WHERE nombre = extra_barra_tragos);
	extra_torta_mesa_dulce_candy_bar_id := (SELECT id FROM EXTRA WHERE nombre = extra_torta_mesa_dulce_candy_bar);
	extra_souvenirs_id := (SELECT id FROM EXTRA WHERE nombre = extra_souvenirs);
	extra_video_id := (SELECT id FROM EXTRA WHERE nombre = extra_video);
	extra_foto_fiesta_id := (SELECT id FROM EXTRA WHERE nombre = extra_foto_fiesta);

	sub_tipo_evento_bautismo_id = (SELECT id FROM SUB_TIPO_EVENTO WHERE nombre = sub_tipo_evento_bautismo); 
	sub_tipo_evento_baby_shower_id = (SELECT id FROM SUB_TIPO_EVENTO WHERE nombre = sub_tipo_evento_baby_shower); 
	sub_tipo_evento_comunion_id = (SELECT id FROM SUB_TIPO_EVENTO WHERE nombre = sub_tipo_evento_comunion); 
	sub_tipo_evento_empresarial_id = (SELECT id FROM SUB_TIPO_EVENTO WHERE nombre = sub_tipo_evento_empresarial); 
	sub_tipo_evento_casamiento_id = (SELECT id FROM SUB_TIPO_EVENTO WHERE nombre = sub_tipo_evento_casamiento); 
	sub_tipo_evento_quinces_corto_id = (SELECT id FROM SUB_TIPO_EVENTO WHERE nombre = sub_tipo_evento_quinces_corto); 
	sub_tipo_evento_quinces_id = (SELECT id FROM SUB_TIPO_EVENTO WHERE nombre = sub_tipo_evento_quinces); 
	sub_tipo_evento_cumpleanos_infantil_id = (SELECT id FROM SUB_TIPO_EVENTO WHERE nombre = sub_tipo_evento_cumpleanos_infantil); 
	sub_tipo_evento_cumpleanos_teens_id = (SELECT id FROM SUB_TIPO_EVENTO WHERE nombre = sub_tipo_evento_cumpleanos_teens); 
	sub_tipo_evento_cumpleanos_adulto_id = (SELECT id FROM SUB_TIPO_EVENTO WHERE nombre = sub_tipo_evento_cumpleanos_adulto); 

	-- ======================= Extras 1 ======================= 

	-- Bautismo
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_capacidad_id,sub_tipo_evento_bautismo_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_animadora_ninos_id,sub_tipo_evento_bautismo_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_camarero_id,sub_tipo_evento_bautismo_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_media_hora_id,sub_tipo_evento_bautismo_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_hora_id,sub_tipo_evento_bautismo_id);

	-- Baby Shower
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_capacidad_id,sub_tipo_evento_baby_shower_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_animadora_ninos_id,sub_tipo_evento_baby_shower_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_camarero_id,sub_tipo_evento_baby_shower_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_media_hora_id,sub_tipo_evento_baby_shower_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_hora_id,sub_tipo_evento_baby_shower_id);
	
		-- Cumpleaños infantil
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_capacidad_id,sub_tipo_evento_cumpleanos_infantil_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_animadora_ninos_id,sub_tipo_evento_cumpleanos_infantil_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_camarero_id,sub_tipo_evento_cumpleanos_infantil_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_media_hora_id,sub_tipo_evento_cumpleanos_infantil_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_hora_id,sub_tipo_evento_cumpleanos_infantil_id);
	
	-- Cumpleaños Teens
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_capacidad_id,sub_tipo_evento_cumpleanos_teens_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_animadora_ninos_id,sub_tipo_evento_cumpleanos_teens_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_camarero_id,sub_tipo_evento_cumpleanos_teens_id);
	
	-- Cumpleaños adulto
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_capacidad_id,sub_tipo_evento_cumpleanos_adulto_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_animadora_ninos_id,sub_tipo_evento_cumpleanos_adulto_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_camarero_id,sub_tipo_evento_cumpleanos_adulto_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_media_hora_id,sub_tipo_evento_cumpleanos_adulto_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_hora_id,sub_tipo_evento_cumpleanos_adulto_id);

	-- ======================= Extras 2 ======================= 	

	-- Casamiento
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_mozo_id,sub_tipo_evento_casamiento_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_cuidadora_ninos_id,sub_tipo_evento_casamiento_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_animacion_adultos_id,sub_tipo_evento_casamiento_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_show_varios_id,sub_tipo_evento_casamiento_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_barra_tragos_id,sub_tipo_evento_casamiento_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_torta_mesa_dulce_candy_bar_id,sub_tipo_evento_casamiento_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_souvenirs_id,sub_tipo_evento_casamiento_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_video_id,sub_tipo_evento_casamiento_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_foto_fiesta_id,sub_tipo_evento_casamiento_id);

	-- Quinces
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_mozo_id,sub_tipo_evento_quinces_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_cuidadora_ninos_id,sub_tipo_evento_quinces_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_animacion_adultos_id,sub_tipo_evento_quinces_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_show_varios_id,sub_tipo_evento_quinces_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_barra_tragos_id,sub_tipo_evento_quinces_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_torta_mesa_dulce_candy_bar_id,sub_tipo_evento_quinces_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_souvenirs_id,sub_tipo_evento_quinces_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_video_id,sub_tipo_evento_quinces_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_foto_fiesta_id,sub_tipo_evento_quinces_id);

	-- Quinces economico
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_mesas_sillas_vestida_id,sub_tipo_evento_quinces_corto_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_mozo_id,sub_tipo_evento_quinces_corto_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_cuidadora_ninos_id,sub_tipo_evento_quinces_corto_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_animacion_adultos_id,sub_tipo_evento_quinces_corto_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_show_varios_id,sub_tipo_evento_quinces_corto_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_barra_tragos_id,sub_tipo_evento_quinces_corto_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_torta_mesa_dulce_candy_bar_id,sub_tipo_evento_quinces_corto_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_souvenirs_id,sub_tipo_evento_quinces_corto_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_video_id,sub_tipo_evento_quinces_corto_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_foto_fiesta_id,sub_tipo_evento_quinces_corto_id);

	-- Comunion
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_mesas_sillas_vestida_id,sub_tipo_evento_comunion_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_mozo_id,sub_tipo_evento_comunion_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_cuidadora_ninos_id,sub_tipo_evento_comunion_id);
	
	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_animacion_adultos_id,sub_tipo_evento_comunion_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_show_varios_id,sub_tipo_evento_comunion_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_barra_tragos_id,sub_tipo_evento_comunion_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_torta_mesa_dulce_candy_bar_id,sub_tipo_evento_comunion_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_souvenirs_id,sub_tipo_evento_comunion_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_video_id,sub_tipo_evento_comunion_id);

	INSERT into sub_tipo_evento_extra (extra_id,sub_tipo_evento_id)
		VALUES (extra_foto_fiesta_id,sub_tipo_evento_comunion_id);

end$$