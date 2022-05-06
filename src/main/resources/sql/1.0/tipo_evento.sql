do $$ 
declare 

begin
	
	INSERT INTO tipo_evento (nombre)
		VALUES ('Corto');
	
	INSERT INTO tipo_evento (nombre)
		VALUES ('Medio');
	
	INSERT INTO tipo_evento (nombre)
		VALUES ('Largo');
end$$
