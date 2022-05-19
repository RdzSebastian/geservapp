do $$
declare

begin

   	INSERT INTO capacidad (capacidad_variable,capacidad_adultos,capacidad_ninos)
		VALUES (true, 0, 0);
	
   	INSERT INTO capacidad (capacidad_variable,capacidad_adultos,capacidad_ninos)
		VALUES (false, 30, 30);

   	INSERT INTO capacidad (capacidad_variable,capacidad_adultos,capacidad_ninos)
		VALUES (false, 30, 20);
	
   	INSERT INTO capacidad (capacidad_variable,capacidad_adultos,capacidad_ninos)
		VALUES (false, 20, 30);

   	INSERT INTO capacidad (capacidad_variable,capacidad_adultos,capacidad_ninos)
		VALUES (false, 15, 30);

end$$
