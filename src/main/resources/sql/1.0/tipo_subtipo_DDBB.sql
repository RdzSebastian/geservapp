do $$ 
declare 

begin
	
	INSERT INTO tipo_evento (duracion,nombre)
		VALUES (3,'Corto');
	
	INSERT INTO tipo_evento (duracion,nombre)
		VALUES (5,'Medio');
	
	INSERT INTO tipo_evento (duracion,nombre)
		VALUES (10,'Largo');
end$$


do $$ 
declare 
		tipo_evento_corto varchar := 'Corto';
        tipo_evento_medio varchar := 'Medio';
        tipo_evento_largo varchar := 'Largo';
        tipo_evento_corto_id int := 0;
        tipo_evento_medio_id int := 0;
        tipo_evento_largo_id int := 0;
begin
	
	tipo_evento_corto_id :=(SELECT id FROM TIPO_EVENTO WHERE nombre = tipo_evento_corto);
   	tipo_evento_medio_id := (SELECT id FROM TIPO_EVENTO WHERE nombre = tipo_evento_medio);
    tipo_evento_largo_id := (SELECT id FROM TIPO_EVENTO WHERE nombre = tipo_evento_largo);
	
	INSERT INTO sub_tipo_evento (cant_personal,capacidad,nombre,precio_base,tipo_evento_id)
		VALUES (10,100,'Teens',10000,tipo_evento_corto_id);
	
	INSERT INTO sub_tipo_evento (cant_personal,capacidad,nombre,precio_base,tipo_evento_id)
		VALUES (10,100,'Barmitzvah',10000,tipo_evento_corto_id);
	
	INSERT INTO sub_tipo_evento (cant_personal,capacidad,nombre,precio_base,tipo_evento_id)
		VALUES (10,100,'Bautismo',10000,tipo_evento_corto_id);
	
	INSERT INTO sub_tipo_evento (cant_personal,capacidad,nombre,precio_base,tipo_evento_id)
		VALUES (10,100,'Comunion',10000,tipo_evento_corto_id);
	
	INSERT INTO sub_tipo_evento (cant_personal,capacidad,nombre,precio_base,tipo_evento_id)
		VALUES (10,100,'Infantiles',10000,tipo_evento_corto_id);

	INSERT INTO sub_tipo_evento (cant_personal,capacidad,nombre,precio_base,tipo_evento_id)
		VALUES (10,100,'Empresarial',10000,tipo_evento_medio_id);
	
	INSERT INTO sub_tipo_evento (cant_personal,capacidad,nombre,precio_base,tipo_evento_id)
		VALUES (10,100,'Casamiento',100000,tipo_evento_largo_id);

	INSERT INTO sub_tipo_evento (cant_personal,capacidad,nombre,precio_base,tipo_evento_id)
		VALUES (10,100,'Quinces',100000,tipo_evento_largo_id);
	

end$$

