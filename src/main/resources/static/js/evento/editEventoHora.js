$(document).ready( function () {
	setHora();
	document.addEventListener("submit", setHoraDeInicioYFin);

	// Setea el horario guardado en la base de datos
	function setHora() {
		var inicio_array = inicio.split(":");
		var fin_array = fin.split(":");
	
		selectPreviusOption("time_start_hour", inicio_array[0]);
		selectPreviusOption("time_start_minute", inicio_array[1]);
		selectPreviusOption("time_end_hour", fin_array[0]);
		selectPreviusOption("time_end_minute", fin_array[1]);
	}

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
	
	// Setear hora inicio y final elegida para guardar
	function setHoraDeInicioYFin(){
		var hora_inicio = $('#time_start_hour').val()
    	var minuto_inicio = $('#time_start_minute').val()

    	$('#time_start').val(hora_inicio + ":" + minuto_inicio)

	    var hora_fin = $('#time_end_hour').val()
		var minuto_fin = $('#time_end_minute').val()
		$('#time_end').val(hora_fin + ":" + minuto_fin)
	}
});