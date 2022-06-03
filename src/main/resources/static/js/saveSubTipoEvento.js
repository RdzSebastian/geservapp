$(function () {
	setHora();
	document.addEventListener("submit", setHoraDeInicioYFin);
	
	// Setear hora inicio y final elegida
	function setHoraDeInicioYFin(){
		var hora_inicio = $('#time_start_hour').val()
    	var minuto_inicio = $('#time_start_minute').val()

    	$('#duracion').val(hora_inicio + ":" + minuto_inicio)
	}
	
	
    function setHora() {
		var duracion_array = duracion.split(":");

		selectPreviusOption("time_start_hour", duracion_array[0]);
		selectPreviusOption("time_start_minute", duracion_array[1]);
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
	
	$(function(){
		for (var i = 0; i <= 11; i++) {
    		$('#' + i + 'desde').combodate({
          		customClass: 'form-control w-100'
			});
    		$('#' + i + 'hasta').combodate({
          		customClass: 'form-control w-100'
			});
	    }
	});
	
	// ----------------------------------------------------------------------------------	
	// Libreria combodate muestra el dia
	$('#date1').combodate({
          value: new Date(),
          minYear: moment().year(),
          maxYear: moment().year(),
          customClass: 'form-control w-100'
    });
    
    $('#date2').combodate({
          value: new Date(),
          minYear: moment().year() + 1,
          maxYear: moment().year() + 1,
          customClass: 'form-control w-100'
    });
    
    $('#date3').combodate({
          value: new Date(),
          minYear: moment().year() + 2,
          maxYear: moment().year() + 2,
          customClass: 'form-control w-100'
    });
	
});