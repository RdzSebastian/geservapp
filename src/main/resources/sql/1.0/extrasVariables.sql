do $$ 
declare
	extra_animadora_ninos varchar := 'Animadora de niños';
    extra_camarero varchar := 'Camarero';
    extra_nino varchar := 'Niños';

begin
	
	INSERT INTO EXTRA_VARIABLE_SUB_TIPO_EVENTO (nombre,precio)
		VALUES (extra_animadora_ninos,1);

	INSERT INTO EXTRA_VARIABLE_SUB_TIPO_EVENTO (nombre,precio)
		VALUES (extra_camarero,1);

	INSERT INTO EXTRA_VARIABLE_SUB_TIPO_EVENTO (nombre,precio)
		VALUES (extra_nino,1);

end$$