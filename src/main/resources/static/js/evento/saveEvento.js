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
                setExtrasBySubTipoEvento(subTipoEvento.id);
                setExtrasVariableBySubTipoEvento(subTipoEvento.id);
                setExtraCateringBySubTipoEvento(subTipoEvento.id);
                setTipoCateringBySubTipoEvento(subTipoEvento.id);
				setDisabledHoraFinal(subTipoEvento.horarioFinalAutomatico);
				borrarErrorJquerrySteps();
            }
        });

    })
    // ----------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------
   	// Muestra los extras que correspondan en base a el subTipoEvento elegido
	function setExtrasBySubTipoEvento(subTipoEventoId) {
		setExtras(subTipoEventoId, "listaExtra", listaExtra, "extraSubTipoEvento", "extraCheckboxSubTipoEvento");
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
   	// Muestra los extras variables que correspondan en base a el subTipoEvento elegido
	function setExtrasVariableBySubTipoEvento(subTipoEventoId) {
		setExtrasVariables(subTipoEventoId, "listaExtraVariable", listaExtraVariable, "eventoExtraVariableSubTipoEvento", "extraVariableSubTipoEvento", "extraVariableCheckboxSubTipoEvento");
	}
	// ----------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------
   	// Muestra los extras catering que correspondan en base a el subTipoEvento elegido
	function setExtraCateringBySubTipoEvento(subTipoEventoId) {
		setExtrasVariables(subTipoEventoId, "listaExtraCatering", listaExtraCatering, "cateringExtraVariableCatering", "extraVariableCatering", "extraVariableCheckboxCatering");
	}
	// ----------------------------------------------------------------------------------

	// Muestra los tipo catering que correspondan en base a el subTipoEvento elegido
	function setTipoCateringBySubTipoEvento(subTipoEventoId) {
		setExtras(subTipoEventoId, "listaTipoCatering", listaTipoCatering, "tipoCatering", "extraCheckboxCatering");
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
   	// Muestra los extras que correspondan en base a el subTipoEvento elegido
	function setExtras(subTipoEventoId, idDiv, listaExtras, nameExtra, nameExtraCheckbox) {
		// Limpia los extra que se agregaron anteriormente
		$('#' + idDiv + ' div').remove();

		// Agrega los extras del subTipoEvento
		listaExtras.forEach(function(valorExtra) {
			valorExtra.listaSubTipoEvento.forEach(function(valorSubTipoEvento) {
				if(valorSubTipoEvento.id == subTipoEventoId){

					var listaExtraDiv = document.getElementById(idDiv);
					var checkbox = document.createElement('input');
					
					var extraDiv = document.createElement('div');
					extraDiv.setAttribute("id", nameExtraCheckbox);
					
					// Assigning the attributes
		            // to created checkbox
		            checkbox.type = "checkbox";
		            checkbox.name = nameExtra;
		            checkbox.value = valorExtra.id;
		            checkbox.id = nameExtra + "Id" + valorExtra.id;
		            checkbox.classList.add("form-check-input");
		            checkbox.classList.add(nameExtraCheckbox);
		            checkbox.onchange = function () { 
						changeExtraCheckbox();
					}
		            
		            // creating label for checkbox
		            var label = document.createElement('label');
		              
		            // assigning attributes for the created label tag 
		            label.htmlFor = "id";

		            // appending the created text to the created label tag 
		            label.appendChild(document.createTextNode("\u00A0" + valorExtra.nombre + ' $' + valorExtra.precio));
		              
		            // appending the checkbox and label to div
		            extraDiv.appendChild(checkbox);
		            extraDiv.appendChild(label);
		           	listaExtraDiv.appendChild(extraDiv);

				}
			});
		});
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
   	// Muestra los extras variables que correspondan en base a el subTipoEvento elegido
	function setExtrasVariables(subTipoEventoId, idDiv, listaExtras, nameExtra, nameObjectExtra, nameExtraCheckbox) {
		// Limpia los extra que se agregaron anteriormente
		$('#' + idDiv +' div').remove();
		var index = 0
		
		// Agrega los extras del subTipoEvento
		listaExtras.forEach(function(valorVariableExtra) {
			valorVariableExtra.listaSubTipoEvento.forEach(function(valorSubTipoEvento) {
				if(valorSubTipoEvento.id == subTipoEventoId){

					var listaExtraDiv = document.getElementById(idDiv);
					var checkbox = document.createElement('input');
					
					var rowDiv = document.createElement('div');
		            rowDiv.className = "row";
		            
					var extraDiv = document.createElement('div');
					extraDiv.setAttribute("id", nameExtraCheckbox);
					extraDiv.className = "col-8";

					// Assigning the attributes to created checkbox
		            checkbox.type = "checkbox";

		            checkbox.name = nameExtra + "[" + index + "]." + nameObjectExtra;
		            checkbox.value = valorVariableExtra.id;
		            checkbox.id = nameExtra + "Id" + valorVariableExtra.id;
		            checkbox.classList.add("form-check-input");
		            checkbox.classList.add(nameExtraCheckbox);
		            checkbox.onchange = function () { 
						changeExtraVariableCantidadDisabled(nameExtra + 'Id' + valorVariableExtra.id);
					}
		            
		            // creating label for checkbox
		            var label = document.createElement('label');
		              
		            // assigning attributes for the created label tag 
		            label.htmlFor = "id";
		            
		            // appending the created text to the created label tag 
		            label.appendChild(document.createTextNode("\u00A0" + valorVariableExtra.nombre + ' $' + valorVariableExtra.precio));
		            
		            var extraVariableDiv = document.createElement('div');
		            extraVariableDiv.className = "col-3";
					
		            var input = document.createElement('input');
				    input.type = "number";
				    input.name = nameExtra + "[" + index + "].cantidad";
				    input.value = 0;
					input.classList.add("form-control");
					input.id = nameExtra + "Id" + valorVariableExtra.id + "Cantidad";
					input.setAttribute('disabled', '');
					input.onchange = function () { 
						changeExtraVariableCheckbox();
					}

		            // appending the checkbox and label to div
		            extraDiv.appendChild(checkbox);
		            extraDiv.appendChild(label);
		            
		            extraVariableDiv.appendChild(input);
		            
		            rowDiv.appendChild(extraDiv)
		            rowDiv.appendChild(extraVariableDiv)
		            
		           	listaExtraDiv.appendChild(rowDiv);
					
					index++;
				}
			});
		});
	}
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
		resetValueOfCatering();
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

	function resetValueOfCatering(){
		$('#precio_plato_adulto').val(0);
		desCheckedAllTipoCatering();
		sumarPresupuestoCatering();
	}

	function desCheckedAllTipoCatering() {
		var checkboxes = $(".extraCheckboxCatering");

		$.each(checkboxes, function(idArray, extraInput) {
			if(extraInput.checked){
				document.querySelector("#" + extraInput.id).checked = false
			}
		});
	}

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

// ----------------------------------------------------------------------------------
// Suma o resta al precio del evento, el precio del extra que haya sido checkeado
function changeExtraCheckbox() {
    sumarPresupuesto();
    sumarPresupuestoCatering();
}
// ----------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Suma o resta al precio del evento, el precio del extra variable que haya sido checkeado y su cantidad 
function changeExtraVariableCheckbox() {
    sumarPresupuesto();
    sumarPresupuestoCatering();
}
// ----------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Suma o resta al precio del evento, el precio del extra variable que haya sido checkeado y su cantidad 
function changeExtraVariableCantidadDisabled(extraVariableId) {
    var decider = document.getElementById(extraVariableId);
    var inputCantidad = $("#" + extraVariableId + "Cantidad");

    if(decider.checked){
		inputCantidad.removeAttr("disabled");
    } else {
		inputCantidad.val(0);
		sumarPresupuesto();
		sumarPresupuestoCatering();
		inputCantidad.prop("disabled", true);
    }
}
// ----------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Setea el presupuesto del evento en base a la cantidad de platos por adulto y niño
function sumarPresupuesto() {
	var presupuesto_con_extras_descuento = 0;

	presupuesto_con_extras_descuento = parseInt(presupuesto) + precioExtras(listaExtra, "extraSubTipoEvento", "extraCheckboxSubTipoEvento") + precioExtrasVariables(listaExtraVariable, "eventoExtraVariableSubTipoEvento", "extraVariableCheckboxSubTipoEvento") + precioExtraOtro()

	presupuesto_con_extras_descuento -= descuento(presupuesto_con_extras_descuento);

	$("#presupuesto").val(parseInt(presupuesto_con_extras_descuento));
}
// ---------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Setea el presupuesto del evento en base a la cantidad de platos por adulto y niño
function sumarPresupuestoCatering() {
	var presupuesto_catering = 0;
	var cantidad_adultos = $('#cantidad_plato_adulto').val();

	// Si el checkbox de catering otro esta checkeado calcula con ese precio
	if($('#catering_otro_checkbox').is(':checked')){
		presupuesto_catering = cantidad_adultos * cateringOtro()
	}else{
		presupuesto_catering = cantidad_adultos * precioExtras(listaTipoCatering, "tipoCatering", "extraCheckboxCatering");
	}

	presupuesto_catering += precioExtrasVariables(listaExtraCatering, "cateringExtraVariableCatering", "extraVariableCheckboxCatering");

	$("#presupuesto_catering").val(parseInt(presupuesto_catering));

}
// ---------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Setea el precio final de todos los extras seleccionados
function precioExtras(listaExtras, nameExtra, nameExtraCheckbox) {
	var checkboxes = $("." + nameExtraCheckbox);
	var totalExtras = 0

	listaExtras.forEach(function(extra) {
		$.each(checkboxes, function(idArray, extraInput) {
			var id = nameExtra + "Id" + extra.id
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
// Setea el precio final de todos los extras variables seleccionados
function precioExtrasVariables(listaExtras, nameExtra, nameExtraVariableCheckbox) {
	var checkboxes = $("." + nameExtraVariableCheckbox);
	var totalExtras = 0

	listaExtras.forEach(function(extraVariable) {
		$.each(checkboxes, function(idArray, extraInput) {
			var extraVariableId = nameExtra + "Id" + extraVariable.id;
			 if(extraInput.checked){
				if(extraInput.id == extraVariableId){
					totalExtras += parseInt(extraVariable.precio) * parseInt($("#" + extraVariableId + "Cantidad").val());
				}
			}
		});
	});
	return totalExtras
}
// ----------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Suma el Extra Otro al presupuesto
function precioExtraOtro() {
	return parseInt($("#extraOtro").val());
}
// ---------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Aplica el descuento al presupuestoTotal con extras y todo
function descuento(presupuestoTotal) {
	return presupuestoTotal * (parseInt($("#descuento").val()) / 100);
}
// ---------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Suma el Extra Otro al presupuesto
function cateringOtro() {
	return parseInt($("#precio_plato_adulto").val());;
}
// ---------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Setea la cantidad de adultos en la seccion de catering
function setCantidadAdultos() {
	var cantidad_adultos = $('#cantidad_plato_adulto').val();
	$('#titulo_cantidad_adultos').text("La cantidad de adultos del evento es: " + cantidad_adultos);
}
// ----------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Setea el presupuesto que viene en el AJAX
function presupuesto() {
	presupuesto = parseInt(document.getElementById("presupuesto").value);
}
// ---------------------------------------------------------------------------------
