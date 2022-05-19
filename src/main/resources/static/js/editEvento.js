$(function () {
	setPlatoDisabled();
	document.addEventListener("submit", setHoraDeInicioYFin);
	
	// Setear hora inicio y final elegida
	function setHoraDeInicioYFin(){
		var hora_inicio = $('#time_start_hour').val()
    	var minuto_inicio = $('#time_start_minute').val()

    	$('#time_start').val(hora_inicio + ":" + minuto_inicio)

	    var hora_fin = $('#time_end_hour').val()
		var minuto_fin = $('#time_end_minute').val()
		$('#time_end').val(hora_fin + ":" + minuto_fin)
	}

    // ----------------------------------------------------------------------------------
	// platos
	function setPlatoDisabled(){
		if(capacidadVariable){
			$("#plato").removeClass("d-none");
			capacidadYPrecio.cantidad
			
		}else{
			$("#plato").addClass("d-none");
		}
	}
	// ----------------------------------------------------------------------------------
    
});