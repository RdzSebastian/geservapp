$(function () {
	
	setAllComponents();



	// ----------------------------------------------------------------------------------
	// Setea todos los campos
	function setAllComponents() {
		$("#evento").val(evento.nombre);
		$("#subTipoEvento").val(evento.subTipoEvento.nombre);
		$("#date").val(fecha);
		$("#capacidad_adultos").val(evento.capacidad.capacidadAdultos);
		$("#capacidad_ninos").val(evento.capacidad.capacidadNinos);
		$("#salon").val(evento.salon.nombre);
		$("#presupuesto").val(presupuesto);
		$("#cliente").val(evento.cliente.apellido + ", " + evento.cliente.nombre);
		setExtras();
		setCatering();
		setHora();			
	}
	// ----------------------------------------------------------------------------------
	
	// ----------------------------------------------------------------------------------
	// Setea el horario guardado en la base de datos
	function setHora() {
		var inicio_array = inicio.split(":");
		var fin_array = fin.split(":");
	
		selectPreviusOption("time_start_hour", inicio_array[0]);
		selectPreviusOption("time_start_minute", inicio_array[1]);
		selectPreviusOption("time_end_hour", fin_array[0]);
		selectPreviusOption("time_end_minute", fin_array[1]);
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// Setea el elemento que le envies en el select component
	function selectPreviusOption(elemento, horario){
		var element = document.getElementById(elemento);
		for(var i=0; i < element.options.length; i++){
		    if(element.options[i].value === horario) {
		      element.selectedIndex = i;
		      break;
		    }
		}
	}
	// ----------------------------------------------------------------------------------
	
		// ----------------------------------------------------------------------------------
	// Setea el horario guardado en la base de datos
	function setExtras() {
		if(listaExtraSeleccionadas.length != 0 || listaExtraVariableSeleccionadas.length != 0){
			$("#extras").val("Si");
		}else{
			$("#extras").val("No");
		}
	}
	// ----------------------------------------------------------------------------------
	
		// ----------------------------------------------------------------------------------
	// Setea el horario guardado en la base de datos
	function setCatering() {
		if(evento.catering.presupuesto != 0){
			$("#catering").val("Si");
		}else{
			$("#catering").val("No");
		}
	}
	// ----------------------------------------------------------------------------------

});