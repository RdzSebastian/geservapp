do $$ 
declare
    -- En todos
    servicio_invitacion varchar := 'Invitacion digital';
    servicio_ayudante_cocina varchar := 'Ayudante de cocina';
    servicio_musicalizacion varchar := 'Musicalizacion durante todo el evento';
    servicio_zona_aire_libre varchar := 'Zona al aire libre';
    servicio_vajilla_lunch varchar := 'Servicio de vajilla para lunch';
    servicio_servicio_te varchar := 'Servicio de te y cafe (durante la mesa dulce)';
    servicio_espacio_candybar varchar := 'Espacio para candy bar';
    servicio_proyector varchar := 'Proyector';
    servicio_wifi varchar := 'Wifi';
    servicio_asistencia_medica varchar := 'Asistencia médica';
    servicio_seguridad varchar := 'Seguridad';

    -- En algunos
    servicio_coordinadora_evento varchar := 'Coordinadora del evento';
    servicio_animadora varchar := 'Animadora';
    servicio_pelos_locos varchar := 'Pelos locos';
    servicio_pulceras_luminosas varchar := 'Pulceras luminosas';
    servicio_pista_baile varchar := 'Pista de baile';
    servicio_snacks varchar := 'Snacks, pizza libre y barra de jugos y gaseosas';
    servicio_pelotero varchar := 'Pelotero';
    servicio_plaza_blanda varchar := 'Plaza blanda';
    servicio_dj varchar := 'DJ';
    servicio_mesas_sillas_vestidas varchar := 'Mesas y sillas vestidas';
    servicio_camarero varchar := 'Camarero';
    servicio_mozo varchar := 'Mozo';
    servicio_camino_mesas varchar := 'Camino para las mesas';


    servicio_invitacion int := 0;
    servicio_ayudante_cocina int := 0;
    servicio_musicalizacion int := 0;
    servicio_zona_aire_libre int := 0;
    servicio_vajilla_lunch int := 0;
    servicio_servicio_te int := 0;
    servicio_espacio_candybar int := 0;
    servicio_proyector int := 0;
    servicio_wifi int := 0;
    servicio_asistencia_medica int := 0;
    servicio_seguridad int := 0;

    servicio_coordinadora_evento int := 0;
    servicio_animadora int := 0;
    servicio_pelos_locos int := 0;
    servicio_pulceras_luminosas int := 0;
    servicio_pista_baile int := 0;
    servicio_snacks int := 0;
    servicio_pelotero int := 0;
    servicio_plaza_blanda int := 0;
    servicio_dj int := 0;
    servicio_mesas_sillas_vestidas int := 0;
    servicio_camarero int := 0;
    servicio_mozo int := 0;
    servicio_camino_mesas int := 0;

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
    sub_tipo_evento_cumpleanos adulto_id int := 0;

begin

    servicio_invitacion_id :=(SELECT id FROM SERVICIO WHERE nombre = servicio_invitacion);
    servicio_ayudante_cocina_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_ayudante_cocina);
    servicio_musicalizacion_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_musicalizacion);
    servicio_zona_aire_libre_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_zona_aire_libre);
    servicio_vajilla_lunch_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_vajilla_lunch);
    servicio_servicio_te_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_servicio_te);
    servicio_espacio_candybar_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_espacio_candybar);
    servicio_proyector_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_proyector);
    servicio_wifi_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_wifi);
    servicio_asistencia_medica_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_asistencia_medica);
    servicio_seguridad_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_seguridad);

    servicio_coordinadora_evento_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_coordinadora_evento);
    servicio_animadora_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_animadora);
    servicio_pelos_locos_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_pelos_locos);
    servicio_pulceras_luminosas_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_pulceras_luminosas);
    servicio_pista_baile_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_pista_baile);
    servicio_snacks_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_snacks);
    servicio_pelotero_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_pelotero);
    servicio_plaza_blanda_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_plaza_blanda);
    servicio_dj_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_dj);
    servicio_mesas_sillas_vestidas_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_mesas_sillas_vestidas);
    servicio_camarero_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_camarero);
    servicio_mozo_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_mozo);
    servicio_camino_mesas_id := (SELECT id FROM SERVICIO WHERE nombre = servicio_camino_mesas);

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

    -- Bautismo
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_invitacion_id,sub_tipo_evento_bautismo_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_ayudante_cocina_id,sub_tipo_evento_bautismo_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_musicalizacion_id,sub_tipo_evento_bautismo_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_zona_aire_libre_id,sub_tipo_evento_bautismo_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_vajilla_lunch_id,sub_tipo_evento_bautismo_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_servicio_te_id,sub_tipo_evento_bautismo_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_espacio_candybar_id,sub_tipo_evento_bautismo_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_proyector_id,sub_tipo_evento_bautismo_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_wifi_id,sub_tipo_evento_bautismo_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_asistencia_medica_id,sub_tipo_evento_bautismo_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_seguridad_id,sub_tipo_evento_bautismo_id);

    -- Bautismo Servicio Extendido
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (,sub_tipo_evento_bautismo_id);


    -- Baby Shower
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_invitacion_id,sub_tipo_evento_baby_shower_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_ayudante_cocina_id,sub_tipo_evento_baby_shower_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_musicalizacion_id,sub_tipo_evento_baby_shower_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_zona_aire_libre_id,sub_tipo_evento_baby_shower_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_vajilla_lunch_id,sub_tipo_evento_baby_shower_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_servicio_te_id,sub_tipo_evento_baby_shower_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_espacio_candybar_id,sub_tipo_evento_baby_shower_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_proyector_id,sub_tipo_evento_baby_shower_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_wifi_id,sub_tipo_evento_baby_shower_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_asistencia_medica_id,sub_tipo_evento_baby_shower_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_seguridad_id,sub_tipo_evento_baby_shower_id);
        
    -- Baby Shower Servicio Extendido  
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (,sub_tipo_evento_bautismo_id);


    -- Cumpleaños infantil
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_invitacion_id,sub_tipo_evento_cumpleanos_infantil);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_ayudante_cocina_id,sub_tipo_evento_cumpleanos_infantil);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_musicalizacion_id,sub_tipo_evento_cumpleanos_infantil);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_zona_aire_libre_id,sub_tipo_evento_cumpleanos_infantil);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_vajilla_lunch_id,sub_tipo_evento_cumpleanos_infantil);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_servicio_te_id,sub_tipo_evento_cumpleanos_infantil);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_espacio_candybar_id,sub_tipo_evento_cumpleanos_infantil);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_proyector_id,sub_tipo_evento_cumpleanos_infantil);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_wifi_id,sub_tipo_evento_cumpleanos_infantil);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_asistencia_medica_id,sub_tipo_evento_cumpleanos_infantil);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_seguridad_id,sub_tipo_evento_cumpleanos_infantil);
    
    -- Cumpleaños infantil Servicio Extendido  
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (,sub_tipo_evento_bautismo_id);


    -- Cumpleaños adulto 
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_invitacion_id,sub_tipo_evento_cumpleanos_infantil);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_ayudante_cocina_id,sub_tipo_evento_cumpleanos_infantil);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_musicalizacion_id,sub_tipo_evento_cumpleanos_infantil);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_zona_aire_libre_id,sub_tipo_evento_cumpleanos_infantil);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_vajilla_lunch_id,sub_tipo_evento_cumpleanos_infantil);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_servicio_te_id,sub_tipo_evento_cumpleanos_infantil);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_espacio_candybar_id,sub_tipo_evento_cumpleanos_infantil);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_proyector_id,sub_tipo_evento_cumpleanos_infantil);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_wifi_id,sub_tipo_evento_cumpleanos_infantil);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_asistencia_medica_id,sub_tipo_evento_cumpleanos_infantil);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_seguridad_id,sub_tipo_evento_cumpleanos_infantil);

    -- Cumpleaños adulto Servicio Extendido  
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (,sub_tipo_evento_bautismo_id);


    -- Casamiento
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_invitacion_id,sub_tipo_evento_casamiento_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_ayudante_cocina_id,sub_tipo_evento_casamiento_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_musicalizacion_id,sub_tipo_evento_casamiento_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_zona_aire_libre_id,sub_tipo_evento_casamiento_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_vajilla_lunch_id,sub_tipo_evento_casamiento_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_servicio_te_id,sub_tipo_evento_casamiento_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_espacio_candybar_id,sub_tipo_evento_casamiento_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_proyector_id,sub_tipo_evento_casamiento_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_wifi_id,sub_tipo_evento_casamiento_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_asistencia_medica_id,sub_tipo_evento_casamiento_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_seguridad_id,sub_tipo_evento_casamiento_id);

    -- Casamiento Servicio Extendido  
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (,sub_tipo_evento_bautismo_id);


    -- Quinces
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_invitacion_id,sub_tipo_evento_quinces_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_ayudante_cocina_id,sub_tipo_evento_quinces_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_musicalizacion_id,sub_tipo_evento_quinces_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_zona_aire_libre_id,sub_tipo_evento_quinces_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_vajilla_lunch_id,sub_tipo_evento_quinces_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_servicio_te_id,sub_tipo_evento_quinces_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_espacio_candybar_id,sub_tipo_evento_quinces_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_proyector_id,sub_tipo_evento_quinces_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_wifi_id,sub_tipo_evento_quinces_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_asistencia_medica_id,sub_tipo_evento_quinces_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_seguridad_id,sub_tipo_evento_quinces_id);

    -- Quinces Servicio Extendido  
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (,sub_tipo_evento_bautismo_id);


    -- Quinces economico
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_invitacion_id,sub_tipo_evento_quinces_corto_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_ayudante_cocina_id,sub_tipo_evento_quinces_corto_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_musicalizacion_id,sub_tipo_evento_quinces_corto_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_zona_aire_libre_id,sub_tipo_evento_quinces_corto_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_vajilla_lunch_id,sub_tipo_evento_quinces_corto_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_servicio_te_id,sub_tipo_evento_quinces_corto_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_espacio_candybar_id,sub_tipo_evento_quinces_corto_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_proyector_id,sub_tipo_evento_quinces_corto_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_wifi_id,sub_tipo_evento_quinces_corto_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_asistencia_medica_id,sub_tipo_evento_quinces_corto_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_seguridad_id,sub_tipo_evento_quinces_corto_id);

    -- Quinces economico Servicio Extendido  
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (,sub_tipo_evento_bautismo_id);


    -- Comunion
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_invitacion_id,sub_tipo_evento_comunion_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_ayudante_cocina_id,sub_tipo_evento_comunion_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_musicalizacion_id,sub_tipo_evento_comunion_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_zona_aire_libre_id,sub_tipo_evento_comunion_id);
    
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_vajilla_lunch_id,sub_tipo_evento_comunion_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_servicio_te_id,sub_tipo_evento_comunion_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_espacio_candybar_id,sub_tipo_evento_comunion_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_proyector_id,sub_tipo_evento_comunion_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_wifi_id,sub_tipo_evento_comunion_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_asistencia_medica_id,sub_tipo_evento_comunion_id);

    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (servicio_seguridad_id,sub_tipo_evento_comunion_id);
    
    -- Comunion Servicio Extendido  
    INSERT into sub_tipo_evento_servicio (servicio_id,sub_tipo_evento_id)
        VALUES (,sub_tipo_evento_bautismo_id);

end$$