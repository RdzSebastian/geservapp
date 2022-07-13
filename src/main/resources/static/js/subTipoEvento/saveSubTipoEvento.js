$(function () {
	setHora();
	document.addEventListener("submit", setHoraDeInicioYFin);

    function setHora() {
		if(duracion != null && duracion != ""){
			var duracion_array = duracion.split(":");

			selectPreviusOption("time_start_hour", duracion_array[0]);
			selectPreviusOption("time_start_minute", duracion_array[1]);
		}
	}

	function selectPreviusOption(elemento, horario){
		var element = document.getElementById(elemento);
		for(var i=0; i < element.options.length; i++){
		    if(element.options[i].value === horario) {
		      element.selectedIndex = i;
		      break;
		    }
		}
	}
		
	function setHoraDeInicioYFin(){
		var hora_inicio = $('#time_start_hour').val()
    	var minuto_inicio = $('#time_start_minute').val()

    	$('#duracion').val(hora_inicio + ":" + minuto_inicio)
    }

});

