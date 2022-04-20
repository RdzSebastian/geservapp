do $$ 
declare 

begin
	INSERT INTO public.extra (nombre,precio)
		VALUES ('Animacion',200);
	INSERT INTO public.extra (nombre,precio)
		VALUES ('Capacidad',400);
	INSERT INTO public.extra (nombre,precio)
		VALUES ('Personal',2000);
	INSERT INTO public.extra (nombre,precio)
		VALUES ('Media Hora',100);
end$$