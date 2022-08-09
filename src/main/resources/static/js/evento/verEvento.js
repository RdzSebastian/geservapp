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
		setExtrasVariables();
		setExtraCatering();
		setTipoCatering();
		verExtrasSubTipoEvento();
		verExtrasVariableSubTipoEvento();
		verTipoCatering();
		verExtrasCatering();
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
		if(listaExtraSeleccionadas.length != 0){
			$("#extras").val("Si");
		}else{
			$("#extras").val("No");
		}
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// Setea el horario guardado en la base de datos
	function setExtrasVariables() {
		if(listaExtraVariableSeleccionadas.length != 0){
			$("#extrasVariables").val("Si");
		}else{
			$("#extrasVariables").val("No");
		}
	}
	// ----------------------------------------------------------------------------------	
	
	// ------------------------------------------------------------------------------
	// Setea el horario guardado en la base de datos
	function setExtraCatering() {
		if(listaExtraCateringSeleccionadas.length != 0){
			$("#extraCatering").val("Si");
		}else{
			$("#extraCatering").val("No");
		}
	}
	// ----------------------------------------------------------------------------------
	
		// ------------------------------------------------------------------------------
	// Setea el horario guardado en la base de datos
	function setTipoCatering() {
		if(listaTipoCateringSeleccionadas.length != 0){
			$("#tipoCatering").val("Si");
		}else{
			$("#tipoCatering").val("No");
		}
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
   	// Muestra los extras que correspondan en base a la reserva realizada
	function verExtrasSubTipoEvento() {
		verExtras("listaExtra", listaExtraSeleccionadas);
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
   	// Muestra los extras variables que correspondan en base a el subTipoEvento elegido
	function verExtrasVariableSubTipoEvento() {
		verExtrasVariables("listaExtraVariable", listaExtraVariableSeleccionadas);
	}
	// ----------------------------------------------------------------------------------
	
	// ----------------------------------------------------------------------------------
   	// Muestra los extras variables que correspondan en base a el subTipoEvento elegido
	function verExtrasCatering() {
		verExtrasVariables("listaExtraCatering", listaExtraCateringSeleccionadas);
	}
	// ----------------------------------------------------------------------------------
	
	// ----------------------------------------------------------------------------------
   	// Muestra los extras variables que correspondan en base a el subTipoEvento elegido
	function verTipoCatering() {
		verExtras("listaTipoCatering", listaTipoCateringSeleccionadas);
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
   	// Muestra los extras que correspondan en base a el subTipoEvento elegido
	function verExtras(idDiv, listaExtras) {
		// Limpia los extra que se agregaron anteriormente
		$('#' + idDiv + ' div').remove();

		// Agrega los extras del subTipoEvento
		listaExtras.forEach(function(valorExtra) {
			var listaExtraDiv = document.getElementById(idDiv);
			var extraDiv = document.createElement('div');
            var label = document.createElement('small');

            // appending the created text to the created label tag 
            label.appendChild(document.createTextNode("\u00A0" + valorExtra.nombre + ' $' + valorExtra.precio));
              
            // appending the checkbox and label to div
            extraDiv.appendChild(label);
           	listaExtraDiv.appendChild(extraDiv);

		});
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
   	// Muestra los extras variables que correspondan en base a el subTipoEvento elegido
	function verExtrasVariables(idDiv, listaExtras) {
		// Limpia los extra que se agregaron anteriormente
		$('#' + idDiv +' div').remove();
		
		listaExtras.forEach(function(valorVariableExtra) {
			var listaExtraDiv = document.getElementById(idDiv);
			
			var rowDiv = document.createElement('div');
            rowDiv.className = "row";
            
			var extraDiv = document.createElement('div');
			extraDiv.className = "col-9";

            // creating label for checkbox
            var label = document.createElement('small');
            
            // appending the created text to the created label tag 
            label.appendChild(document.createTextNode("\u00A0" + valorVariableExtra.nombre + ' $' + valorVariableExtra.precio + " x" + valorVariableExtra.cantidad));
            
            var extraVariableDiv = document.createElement('div');
            extraVariableDiv.className = "col-3";

            // appending the checkbox and label to div
            extraDiv.appendChild(label);

            rowDiv.appendChild(extraDiv)
            rowDiv.appendChild(extraVariableDiv)
            
           	listaExtraDiv.appendChild(rowDiv);
		});
	}
	// ----------------------------------------------------------------------------------

});