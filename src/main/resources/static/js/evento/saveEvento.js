$(document).ready(function() {
	// Al iniciar la pantalla el select de sub tipo evento empieza vacio
	window.onload = selectEventoEmpiezaVacio;

	// Oculta el catering al iniciar la pantalla
	hideWithCheckbox("catering_checkbox", "catering");

	// Oculta el catering otro al iniciar la pantalla
	hideWithCheckbox("catering_checkbox", "catering_otro");

	// Oculta el precio de catering otro al iniciar la pantalla
	hideWithCheckbox("catering_otro_checkbox", "catering_otro_precio");

	// Setea la cantidad de Adultos al iniciar la pantalla
	setCantidadAdultos();

	// Al modificar el tamaño de pantalla llama a sizeScreen()
	window.addEventListener('resize', function(event){
		sizeScreen();
	});

	// ----------------------------------------------------------------------------------
	// Obtiene todas opciones de subTipoEvento en base al tipoEvento escogido
    var allTipoEventos = $('#subTipoEvento option')
    $('#tipoEvento').change(function () {
        $('#subTipoEvento option').remove()
        var classTipoEvento = $('#tipoEvento option:selected').prop('class');
        var opts = allTipoEventos.filter('.' + classTipoEvento);
        $.each(opts, function (i, j) {
            $(j).appendTo('#subTipoEvento');
        });
        
        addHiddenFirstOptionOnSelect("subTipoEvento", "Sub Tipo Evento");
		
		var optionSubTipoEvento = [];
		$('#subTipoEvento option').each(function(){
		   if($.inArray(this.value, optionSubTipoEvento) >-1){
		      $(this).remove()
		   }else{
		      optionSubTipoEvento.push(this.value);
		   }
		});
    });
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// Borra las opciones de subTipoEvento que no corresponden la tipoEvento escogido
	var optionValues =[];
	$('#tipoEvento option').each(function(){
	   if($.inArray(this.value, optionValues) >-1){
	      $(this).remove()
	   }else{
	      optionValues.push(this.value);
	   }
	});
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// Limpia opciones de subTipoEvento al cambiar de tipoEvento
	function selectEventoEmpiezaVacio() {
		$('#subTipoEvento option').remove()
		addHiddenFirstOptionOnSelect("subTipoEvento", "Sub Tipo Evento");
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// Presetea una opcion oculta de subTipoEvento al cambiar de tipoEvento
	function addHiddenFirstOptionOnSelect(elemento, valor) {
	  var x = document.getElementById(elemento);
	  var option = document.createElement("option");
	  option.value = valor;
	  option.text = valor;
	  option.defaultSelected = true
	  option.hidden = true
	  x.add(option);
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------	
	// Libreria combodate muestra el dia
	$('#date').combodate({
          value: new Date(),
          minYear: moment().year(),
          maxYear: moment().year() + 2,
          customClass: 'form-control d-inline',
          smartDays: true
    });
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// Busca el subTipoEvento seleccionado y setea el precio base, extras, hora inicio 
	// y final (disabled o enabled) de dicho subTipoEvento
    $('#subTipoEvento').change(function () {
        var subTipoEventoId = $("#subTipoEvento").val();
        listaSubTipoEvento.forEach( function(subTipoEvento) {
            if(subTipoEventoId == subTipoEvento.id){
                setServicioBySubTipoEvento(subTipoEvento.id);
				setDisabledHoraFinal(subTipoEvento.horarioFinalAutomatico);
				borrarErrorJquerrySteps();
            }
        });

    })
    // ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// Muestra los extras que correspondan en base a el subTipoEvento elegido
	function setServicioBySubTipoEvento(subTipoEventoId) {
		// Limpia los servicios que se agregaron anteriormente
		$('#ulServicio').remove();
		
		// Obtiene el div donde ira la lista de servicios
		var listaServicioDiv = document.getElementById("listaServicio");
		
		// Crea el ul que contendra a los li
		var ul = document.createElement('ul');
		ul.id = "ulServicio"
		// Agrega los extras del subTipoEvento
		listaServicio.forEach(function(valorServicio) {
			valorServicio.listaSubTipoEvento.forEach(function(valorSubTipoEvento) {
				if(valorSubTipoEvento.id == subTipoEventoId){
					var li = document.createElement("li");
					li.appendChild(document.createTextNode(valorServicio.nombre));
					ul.appendChild(li);
				}
			});
		});
		// Appendin the ul
		listaServicioDiv.appendChild(ul);
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// En base a la dimencion de la pantalla saca o pone el titulo del wizard
	function sizeScreen(){
		var newWidth = window.innerWidth;

		if(newWidth <= 800){
			if($('#reservacion-wizard-t-0')[0].lastChild.data == " Tipo de evento"){
				$('#reservacion-wizard-t-0')[0].removeChild($('#reservacion-wizard-t-0')[0].lastChild)
			}
			
			if($('#reservacion-wizard-t-1')[0].lastChild.data == " Datos del evento"){
				$('#reservacion-wizard-t-1')[0].removeChild($('#reservacion-wizard-t-1')[0].lastChild);
			}
			
			if($('#reservacion-wizard-t-2')[0].lastChild.data == " Cotizacion"){
				$('#reservacion-wizard-t-2')[0].removeChild($('#reservacion-wizard-t-2')[0].lastChild);
			}
			
			if($('#reservacion-wizard-t-3')[0].lastChild.data == " Catering"){
				$('#reservacion-wizard-t-3')[0].removeChild($('#reservacion-wizard-t-3')[0].lastChild);
			}

			if($('#reservacion-wizard-t-4')[0].lastChild.data == " Datos de contacto"){
				$('#reservacion-wizard-t-4')[0].removeChild($('#reservacion-wizard-t-4')[0].lastChild);
			}
		}else{
			if($('#reservacion-wizard-t-0')[0].lastChild.innerHTML == "1."){
				$('#reservacion-wizard-t-0')[0].appendChild(document.createTextNode(" Tipo de evento"));
			}
			
			if($('#reservacion-wizard-t-1')[0].lastChild.innerHTML == "2."){
				$('#reservacion-wizard-t-1')[0].appendChild(document.createTextNode(" Datos del evento"));
			}
			
			if($('#reservacion-wizard-t-2')[0].lastChild.innerHTML == "3."){
				$('#reservacion-wizard-t-2')[0].appendChild(document.createTextNode(" Cotizacion"));
			}
			
			if($('#reservacion-wizard-t-3')[0].lastChild.innerHTML == "4."){
				$('#reservacion-wizard-t-3')[0].appendChild(document.createTextNode(" Catering"));
			}

			if($('#reservacion-wizard-t-4')[0].lastChild.innerHTML == "5."){
				$('#reservacion-wizard-t-4')[0].appendChild(document.createTextNode(" Datos de contacto"));
			}
		}
			
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// Borra error en subTipoEvento al elegirlo
	function borrarErrorJquerrySteps(){
		$('#tipoEvento-error').remove();
	}
	// ----------------------------------------------------------------------------------
	
	// ----------------------------------------------------------------------------------
	// Checkbox para ocultar catering 
	$('#catering_checkbox').change(function(){
		hideWithCheckbox("catering_checkbox", "catering");
		hideWithCheckbox("catering_checkbox", "catering_otro");
	});
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// Checkbox para ocultar el precio de "Otro catering" 
	$('#catering_otro_checkbox').change(function(){
		hideWithCheckbox("catering_otro_checkbox", "catering_otro_precio");
	});
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// Oculta un componente por id en base a un checkbox
	function hideWithCheckbox(checkbox, componentToHide){
		if($('#' + checkbox).is(':checked')){
			$('#' + componentToHide).fadeIn('slow');
		}else{
			$('#' + componentToHide).fadeOut('slow');
		}
	}
	// ----------------------------------------------------------------------------------

});

// ----------------------------------------------------------------------------------
// Setea disabled la hora y minuto final en base al subTipoEvento
function setDisabledHoraFinal(horarioFinalAutomatico) {
	if(horarioFinalAutomatico){
		$('#time_end_hour').prop("disabled", true);
	 	$('#time_end_minute').prop("disabled", true);
	}else{
		$('#time_end_hour').removeAttr("disabled");
		$('#time_end_minute').removeAttr("disabled");
	}
}
// ----------------------------------------------------------------------------------

