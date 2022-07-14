$(document).ready(function() {
	
	setCateringOtroPrecio();
	
	setPresupuestoInicial();

	if($("#presupuesto_catering").val() == 0){		
		hideWithCheckbox("catering_checkbox", "catering");
	
		hideWithCheckbox("catering_checkbox", "catering_extras");
	
		hideWithCheckbox("catering_checkbox", "catering_otro");
	}else if($("#catering_otro_precio_input").val() == 0){
		setCateringChecked();
		document.getElementById("catering_otro_precio_input").setAttribute("readonly", true);
	}else{
		setCateringChecked();
		setCheckboxPrecioOtro();
	}

	setExtraCateringBySubTipoEvento($("#subTipoEvento").val());

	setTipoCateringBySubTipoEvento($("#subTipoEvento").val());

	setExtrasVariablesSeleccionadas("cateringExtraVariableCatering", listaExtraCateringSeleccionadas);

	setExtrasSeleccionadas("tipoCatering", listaTipoCateringSeleccionadas);
	
	setCantidadAdultos();
	
			
	function setExtrasSeleccionadas(nameExtra, listaExtras){
		listaExtras.forEach( function(valor) {
			document.querySelector("#" + nameExtra + "Id" + valor.id).checked = true
		});
	}

	function setExtrasVariablesSeleccionadas(nameExtra, listaExtras){
		listaExtras.forEach( function(valor) {
			document.querySelector("#" + nameExtra + "Id" + valor.extraVariableCatering.id).checked = true
			$("#" + nameExtra + "Id" + valor.extraVariableCatering.id + "Cantidad").val(valor.cantidad)
			$("#" + nameExtra + "Id" + valor.extraVariableCatering.id + "Cantidad").removeAttr("disabled");
		});
	}

    // ----------------------------------------------------------------------------------
   	// Muestra los extras catering que correspondan en base a el subTipoEvento elegido
	function setExtraCateringBySubTipoEvento(subTipoEventoId) {
		setExtrasVariables(subTipoEventoId, "listaExtraCatering", listaExtraCatering, "cateringExtraVariableCatering", "extraVariableCatering", "extraVariableCheckboxCatering");
	}
	// ----------------------------------------------------------------------------------

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
			var listaExtraDiv = document.getElementById(idDiv);
			var checkbox = document.createElement('input');
			
			var rowDiv = document.createElement('div');
            rowDiv.className = "row";
            
			var extraDiv = document.createElement('div');
			extraDiv.setAttribute("id", nameExtraCheckbox);
			extraDiv.className = "col-9";

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
		});
	}
	// ----------------------------------------------------------------------------------
	
	// ----------------------------------------------------------------------------------
	// Checkbox para ocultar catering 
	$('#catering_checkbox').change(function(){
		hideWithCheckbox("catering_checkbox", "catering");
		hideWithCheckbox("catering_checkbox", "catering_extras");
		hideWithCheckbox("catering_checkbox", "catering_otro");
	});
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// Checkbox para ocultar el precio de "Otro catering" 
	$('#catering_otro_checkbox').change(function(){
		if($('#catering_otro_checkbox').is(':checked')){
			document.getElementById("catering_otro_precio_input").removeAttribute("readonly");
			desCheckedAllTipoCatering();
			sumarPresupuestoCatering();
		}else{
			resetValueOfCatering();
		}
	});
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// Oculta un componente por id en base a un checkbox
	function hideWithCheckbox(checkbox, componentToHide){
		if($('#' + checkbox).is(':checked')){
			$('#' + componentToHide).fadeIn('slow');
		}else{
			$('#' + componentToHide).hide();
		}
	}
	// ----------------------------------------------------------------------------------

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
// Suma o resta al precio del evento, el precio del extra que haya sido checkeado
function changeExtraCheckbox() {
	document.querySelector("#catering_otro_checkbox").checked = false
	resetValueOfCatering();
    sumarPresupuestoCatering();
}
// ----------------------------------------------------------------------------------


function resetValueOfCatering(){
	$('#catering_otro_precio_input').val(0);
	document.getElementById("catering_otro_precio_input").setAttribute("readonly", true);
	sumarPresupuestoCatering();
}
	

// ----------------------------------------------------------------------------------
// Suma o resta al precio del evento, el precio del extra variable que haya sido checkeado y su cantidad 
function changeExtraVariableCheckbox() {
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
		sumarPresupuestoCatering();
		inputCantidad.prop("disabled", true);
    }
}
// ----------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Setea el presupuesto del evento en base a la cantidad de platos por adulto y niño
function sumarPresupuestoCatering() {
	var presupuesto_catering_calculo = 0;
	var cantidad_adultos = $('#cantidad_plato_adulto').val();

	// Si el checkbox de catering otro esta checkeado calcula con ese precio
	if($('#catering_otro_checkbox').is(':checked')){
		presupuesto_catering_calculo = cantidad_adultos * cateringOtro()
	}else{
		presupuesto_catering_calculo = cantidad_adultos * precioExtras(listaTipoCatering, "tipoCatering", "extraCheckboxCatering");
	}

	presupuesto_catering_calculo += precioExtrasVariables(listaExtraCatering, "cateringExtraVariableCatering", "extraVariableCheckboxCatering");

	$("#presupuesto_catering").val(parseInt(presupuesto_catering_calculo));

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
function setCateringOtroPrecio() {
	return $("#catering_otro_precio_input").val(presupuesto_otro);
}
// ---------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Suma el Extra Otro al presupuesto
function cateringOtro() {
	return parseInt($("#catering_otro_precio_input").val());
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
// Setea la cantidad de adultos en la seccion de catering
function setCheckboxPrecioOtro() {
	document.querySelector("#catering_otro_checkbox").checked = true;
}
// ----------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Setea la cantidad de adultos en la seccion de catering
function setPresupuestoInicial() {
	$("#presupuesto_catering").val(presupuesto_catering);
}
// ----------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Setea la cantidad de adultos en la seccion de catering
function setCateringChecked() {
	document.querySelector("#catering_checkbox").checked = true;
}
// ----------------------------------------------------------------------------------
