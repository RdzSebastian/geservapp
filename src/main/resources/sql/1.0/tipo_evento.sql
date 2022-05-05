do $$ 
declare 

begin
	
	INSERT INTO tipo_evento (duracion,nombre)
		VALUES ('Corto');
	
	INSERT INTO tipo_evento (duracion,nombre)
		VALUES ('Medio');
	
	INSERT INTO tipo_evento (duracion,nombre)
		VALUES ('Largo');
end$$
