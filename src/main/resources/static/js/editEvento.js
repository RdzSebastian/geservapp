$(function () {
	var buttonA = $('#hastaElOtroDiaCheckbox');
	hideHoraFinal(buttonA[0]);

	document.addEventListener("submit", setHoraDeInicioYFin);
	
	// Setear hora inicio y final elegida
	function setHoraDeInicioYFin(){
		var hora_inicio = $('#time_start_hour').val()
    	var minuto_inicio = $('#time_start_minute').val()

    	$('#time_start').val(hora_inicio + ":" + minuto_inicio)

		if(!$('#hastaElOtroDiaCheckbox').checked){
		    var hora_fin = $('#time_end_hour').val()
    		var minuto_fin = $('#time_end_minute').val()
    		$('#time_end').val(hora_fin + ":" + minuto_fin)
		}
	}

    // Checkbox para ocultar hora final y que se setee hasta el otro dia 
    $('#hastaElOtroDiaCheckbox').change(function(){
		hideHoraFinal(this);
	});
	
	function hideHoraFinal(button){
		if(button.checked)
			$('#time_end_div').fadeOut('slow');
		else
			$('#time_end_div').fadeIn('slow');
	}
});