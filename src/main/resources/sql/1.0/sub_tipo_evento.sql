do $$ 
declare
		tipo_evento_corto varchar := 'Corto';
        tipo_evento_medio varchar := 'Medio';
        tipo_evento_largo varchar := 'Largo';

        tipo_evento_corto_id int := 0;
        tipo_evento_medio_id int := 0;
        tipo_evento_largo_id int := 0;

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
	
		duracion_2_30 TIME := '02:30:00';
		duracion_3_00 TIME := '03:00:00';
		duracion_4_00 TIME := '04:00:00';
		duracion_5_00 TIME := '05:00:00';
		duracion_7_00 TIME := '07:00:00';

		capacidad_variable_id int := 0;
		capacidad_fija_30_30_id int := 0;
		capacidad_fija_30_20_id int := 0;
		capacidad_fija_20_30_id int := 0;
		capacidad_fija_15_30_id int := 0;

begin
	
	tipo_evento_corto_id :=(SELECT id FROM TIPO_EVENTO WHERE nombre = tipo_evento_corto);
   	tipo_evento_medio_id := (SELECT id FROM TIPO_EVENTO WHERE nombre = tipo_evento_medio);
    tipo_evento_largo_id := (SELECT id FROM TIPO_EVENTO WHERE nombre = tipo_evento_largo);

    capacidad_variable_id := (SELECT id FROM CAPACIDAD WHERE capacidad_variable = true);
    capacidad_fija_30_30_id := (SELECT id FROM CAPACIDAD WHERE capacidad_variable = false AND capacidad_adultos = 30 AND capacidad_ninos = 30);
    capacidad_fija_30_20_id := (SELECT id FROM CAPACIDAD WHERE capacidad_variable = false AND capacidad_adultos = 30 AND capacidad_ninos = 20);  
    capacidad_fija_20_30_id := (SELECT id FROM CAPACIDAD WHERE capacidad_variable = false AND capacidad_adultos = 20 AND capacidad_ninos = 30);  
    capacidad_fija_15_30_id := (SELECT id FROM CAPACIDAD WHERE capacidad_variable = false AND capacidad_adultos = 15 AND capacidad_ninos = 30);  


   	INSERT INTO sub_tipo_evento (cant_personal,capacidad_id,nombre,precio_base,tipo_evento_id,duracion,horario_final_automatico)
		VALUES (1,capacidad_fija_30_30_id,sub_tipo_evento_cumpleanos_infantil,1,tipo_evento_corto_id,duracion_2_30,true);
	
   	INSERT INTO sub_tipo_evento (cant_personal,capacidad_id,nombre,precio_base,tipo_evento_id,duracion,horario_final_automatico)
		VALUES (1,capacidad_fija_30_20_id,sub_tipo_evento_cumpleanos_teens,1,tipo_evento_corto_id,duracion_2_30,true);
	
   	INSERT INTO sub_tipo_evento (cant_personal,capacidad_id,nombre,precio_base,tipo_evento_id,duracion,horario_final_automatico)
		VALUES (1,capacidad_fija_20_30_id,sub_tipo_evento_cumpleanos_adulto,1,tipo_evento_corto_id, duracion_4_00,true);
	
   	INSERT INTO sub_tipo_evento (cant_personal,capacidad_id,nombre,precio_base,tipo_evento_id,duracion,horario_final_automatico)
		VALUES (1,capacidad_fija_15_30_id,sub_tipo_evento_baby_shower,1,tipo_evento_corto_id,duracion_3_00,true);

   	INSERT INTO sub_tipo_evento (cant_personal,capacidad_id,nombre,precio_base,tipo_evento_id,duracion,horario_final_automatico)
		VALUES (1,capacidad_fija_15_30_id,sub_tipo_evento_bautismo,1,tipo_evento_corto_id,duracion_4_00,true);

   	INSERT INTO sub_tipo_evento (cant_personal,capacidad_id,nombre,precio_base,tipo_evento_id,duracion,horario_final_automatico)
		VALUES (1,capacidad_fija_20_30_id,sub_tipo_evento_comunion,1,tipo_evento_corto_id,duracion_4_00,true);

   	INSERT INTO sub_tipo_evento (cant_personal,capacidad_id,nombre,precio_base,tipo_evento_id,duracion,horario_final_automatico)
		VALUES (1,capacidad_fija_30_30_id,sub_tipo_evento_quinces_corto,1,tipo_evento_corto_id,duracion_4_00,false);

   	INSERT INTO sub_tipo_evento (cant_personal,capacidad_id,nombre,precio_base,tipo_evento_id,duracion,horario_final_automatico)
		VALUES (1,capacidad_variable_id,sub_tipo_evento_empresarial,1,tipo_evento_medio_id,duracion_4_00,false);

   	INSERT INTO sub_tipo_evento (cant_personal,capacidad_id,nombre,precio_base,tipo_evento_id,duracion,horario_final_automatico)
		VALUES (1,capacidad_variable_id,sub_tipo_evento_casamiento,1,tipo_evento_largo_id,duracion_7_00,false);

   	INSERT INTO sub_tipo_evento (cant_personal,capacidad_id,nombre,precio_base,tipo_evento_id,duracion,horario_final_automatico)
		VALUES (1,capacidad_variable_id,sub_tipo_evento_quinces,1,tipo_evento_largo_id,duracion_7_00,false);

end$$
