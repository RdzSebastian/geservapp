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
		}else{
			$("#plato").addClass("d-none");
		}
	}
	// ----------------------------------------------------------------------------------

});

// ----------------------------------------------------------------------------------
// Setea el precio final de todos los extras seleccionados
function precioExtras() {
	var checkboxes = $(".extraCheckbox" );
	var totalExtras = 0

	listaExtraJS.forEach(function(extra) {
		$.each(checkboxes, function(idArray, extraInput) {
			var id = "extraId" + extra.id
			 if(extraInput.checked){
				if(extraInput.id == id){
					totalExtras += extra.precio
				}
			}
		});
	});
	return totalExtras
}
// ----------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Setea el presupuesto del evento en base a la cantidad de platos por adulto y niño
function sumarPresupuesto() {
	var presupuesto_con_plato = 0;
	var cantidad_plato_adulto = parseInt($("#cantidad_plato_adulto").val());
	var precio_plato_adulto = parseInt($("#precio_plato_adulto").val());
	var cantidad_plato_nino = parseInt($("#cantidad_plato_nino").val());
	var precio_plato_nino = parseInt($("#precio_plato_nino").val());
	var precio_adulto = 0;
	var precio_nino = 0;

	
	if(!isNaN(cantidad_plato_adulto) && !isNaN(precio_plato_adulto)){
		precio_adulto = cantidad_plato_adulto * precio_plato_adulto
	}
	
	if(!isNaN(cantidad_plato_nino) && !isNaN(precio_plato_nino)){
		precio_nino = cantidad_plato_nino * precio_plato_nino
	}
	
	if(!isNaN(cantidad_plato_adulto) || !isNaN(precio_plato_nino)){
		presupuesto_con_plato = parseInt(precio_adulto) + parseInt(precio_nino) + parseInt(presupuesto) + precioExtras()
	}else{
		presupuesto_con_plato = parseInt(presupuesto) + precioExtras()
	}

	$("#presupuesto").val(presupuesto_con_plato);

}
// ---------------------------------------------------------------------------------