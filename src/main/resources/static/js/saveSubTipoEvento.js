$(function () {
	setYears();
	setHora();
	setPrecioBasePorFecha();
	hideCapacidad();
	document.addEventListener("submit", setHoraDeInicioYFinYPrecioBasePorFecha);
	
	// Setear hora inicio y final elegida
	function setHoraDeInicioYFinYPrecioBasePorFecha(){
		var hora_inicio = $('#time_start_hour').val()
    	var minuto_inicio = $('#time_start_minute').val()

    	$('#duracion').val(hora_inicio + ":" + minuto_inicio)


		// Set PrecioConFecha
		for(i = 0; i <= 3; i++){
			$("#" + i + "desdeValor").val("01-" + addCero($("#" + i + "desde").val()) + "-"  + $("#year1").val())
    		$("#" + i + "hastaValor").val("01-" + addCero($("#" + i + "hasta").val()) + "-"  + $("#year1").val())
		}		
		
		for(i = 4; i <= 7; i++){
			$("#" + i + "desdeValor").val("01-" + addCero($("#" + i + "desde").val()) + "-"  + $("#year2").val())
    		$("#" + i + "hastaValor").val("01-" + addCero($("#" + i + "hasta").val()) + "-"  + $("#year2").val())
		}
		
		for(i = 8; i <= 11; i++){
			$("#" + i + "desdeValor").val("01-" + addCero($("#" + i + "desde").val()) + "-"  + $("#year3").val())
    		$("#" + i + "hastaValor").val("01-" + addCero($("#" + i + "hasta").val()) + "-"  + $("#year3").val())
		}

	}
	
	function addCero(valor){
		if(valor.length == 1){
			return "0" + valor.toString()
		}
		return valor.toString()
	}

	function setYears(){
		$("#year1").val(new Date().getFullYear())
		$("#year2").val(new Date().getFullYear() + 1)
		$("#year3").val(new Date().getFullYear() + 2)
	}

    function setHora() {
		if(duracion != null && duracion != ""){
			var duracion_array = duracion.split(":");

			selectPreviusOption("time_start_hour", duracion_array[0]);
			selectPreviusOption("time_start_minute", duracion_array[1]);
		}
	}
	
	function setPrecioBasePorFecha(){
		if(listaPrecioConFecha[0].desde != null && listaPrecioConFecha[0].hasta != null ){

			listaPrecioConFecha.forEach( function(precioConFecha, index) {
	
				document.getElementById(index + "precio").value = precioConFecha.precio;

				var desdeSplit = precioConFecha.desde.split("-");
				var hastaSplit = precioConFecha.hasta.split("-");
				
				document.getElementById(index + "desde").value = parseInt(desdeSplit[1]);
				document.getElementById(index + "hasta").value = parseInt(hastaSplit[1]);
	
			});
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

	// Año 1
	$('#0hasta').change(function () {
		changeNextDesde(0)
	});
	
	$('#1hasta').change(function () {
		changeNextDesde(1)
	});
	
	$('#2hasta').change(function () {
		changeNextDesde(2)
	});
	
	// Año 2
	$('#4hasta').change(function () {
		changeNextDesde(4)
	});
	
	$('#5hasta').change(function () {
		changeNextDesde(5)
	});

	$('#6hasta').change(function () {
		changeNextDesde(6)
	});
	
	// Año 3
	$('#8hasta').change(function () {
		changeNextDesde(8)
	});
	
	$('#9hasta').change(function () {
		changeNextDesde(9)
	});
	
	$('#10hasta').change(function () {
		changeNextDesde(10)
	});

	function changeNextDesde(id) {
		var valueHasta = $("#" + id + "hasta").val()
		id = id + 1
		document.getElementById(id + "desde").value = parseInt(valueHasta) + 1;
	}
	
});

	
	// Checkbox para ocultar hora final y que se setee hasta el otro dia 
    $('#capacidad_variable').change(function(){
		hideCapacidad();
	});
	
	function hideCapacidad(){
		if($('#capacidad_variable').is(':checked')){
			$('#capaciadVariableInputs').fadeOut('slow');
		}else{
			$('#capaciadVariableInputs').fadeIn('slow');
		}
	}

