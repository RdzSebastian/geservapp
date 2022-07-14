$(document).ready(function() {

	setTituloPrecioBase();

	setExtrasBySubTipoEvento($("#subTipoEvento").val());
	
	setExtrasVariableBySubTipoEvento($("#subTipoEvento").val());

	setExtrasSeleccionadas("extraSubTipoEvento", listaExtraSeleccionadas);
	
	setExtrasVariablesSeleccionadas("eventoExtraVariableSubTipoEvento", listaExtraVariableSeleccionadas);
	

			
	function setExtrasSeleccionadas(nameExtra, listaExtras){
		listaExtras.forEach( function(valor) {
			document.querySelector("#" + nameExtra + "Id" + valor.id).checked = true
		});
	}
	
	function setExtrasVariablesSeleccionadas(nameExtra, listaExtras){
		listaExtras.forEach( function(valor) {
			document.querySelector("#" + nameExtra + "Id" + valor.extraVariableSubTipoEvento.id).checked = true
			$("#" + nameExtra + "Id" + valor.extraVariableSubTipoEvento.id + "Cantidad").val(valor.cantidad)
			$("#" + nameExtra + "Id" + valor.extraVariableSubTipoEvento.id + "Cantidad").removeAttr("disabled");
		});
	}

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
});

// ----------------------------------------------------------------------------------
// Suma o resta al precio del evento, el precio del extra que haya sido checkeado
function changeExtraCheckbox() {
    sumarPresupuesto();
}
// ----------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Suma o resta al precio del evento, el precio del extra variable que haya sido checkeado y su cantidad 
function changeExtraVariableCheckbox() {
    sumarPresupuesto();
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
// Setea un titulo con el precio base del evento a la fecha de la reserva segun su subTipoEvento
function setTituloPrecioBase() { 
	$('#titulo_precio_base_sub_tipo_evento').text("El precio base del evento en la fecha reservada es: " + presupuesto);
}
// ----------------------------------------------------------------------------------