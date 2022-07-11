var resto24 = false
var presupuesto = 0;

$(document).ready(function() {
	window.onload = selectEventoEmpiezaVacio;
	
	hideCatering();

	//listen for window resize event
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
                setTimeEndBySubTipoEvento(subTipoEvento.duracion);
				setDisabledHoraFinal(subTipoEvento.horarioFinalAutomatico);
				setPlatoDisabled(subTipoEvento.capacidad.capacidadVariable);
				borrarErrorJquerrySteps();
            }
        });

    })
    // ----------------------------------------------------------------------------------



	// ----------------------------------------------------------------------------------
   	// Muestra los extras que correspondan en base a el subTipoEvento elegido
	function setExtras(subTipoEventoId, idDiv, listaExtras, nameExtra) {
		// Limpia los extra que se agregaron anteriormente
		$('#' + idDiv + ' div').remove();

		// Agrega los extras del subTipoEvento
		listaExtras.forEach(function(valorExtra) {
			valorExtra.listaSubTipoEvento.forEach(function(valorSubTipoEvento) {
				if(valorSubTipoEvento.id == subTipoEventoId){

					var listaExtraDiv = document.getElementById(idDiv);
					var checkbox = document.createElement('input');
					
					var extraDiv = document.createElement('div');
					extraDiv.setAttribute("id", "extraCheckbox");
					
					// Assigning the attributes
		            // to created checkbox
		            checkbox.type = "checkbox";
		            checkbox.name = nameExtra;
		            checkbox.value = valorExtra.id;
		            checkbox.id = nameExtra + "Id" + valorExtra.id;
		            checkbox.classList.add("form-check-input");
		            checkbox.classList.add("extraCheckbox");
		            checkbox.onchange = function () { 
						changeExtraCheckbox(valorExtra.precio , nameExtra + 'Id' + valorExtra.id);
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
	function setExtrasVariables(subTipoEventoId, idDiv, listaExtras, nameExtra, nameObjectExtra) {
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
					extraDiv.setAttribute("id", "extraCheckbox");
					extraDiv.className = "col-6";

					// Assigning the attributes to created checkbox
		            checkbox.type = "checkbox";

		            checkbox.name = nameExtra + "[" + index + "]." + nameObjectExtra;
		            checkbox.value = valorVariableExtra.id;
		            checkbox.id = nameExtra + "Id" + valorVariableExtra.id;
		            checkbox.classList.add("form-check-input");
		            checkbox.classList.add("extraVariableCheckbox");
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
					input.classList.add("form-control");
					input.id = nameExtra + "Id" + valorVariableExtra.id + "Cantidad";
					input.setAttribute('disabled', '');
					input.onchange = function () { 
						changeExtraVariableCheckbox(valorVariableExtra.precio , nameExtra + 'Id' + valorVariableExtra.id);
					}

		            // appending the checkbox and label to div
		            extraDiv.appendChild(checkbox);
		            extraDiv.appendChild(label);
		            
		            extraVariableDiv.appendChild(input);
		            
		            rowDiv.appendChild(extraDiv)
		            rowDiv.appendChild(extraVariableDiv)
		            
		           	listaExtraDiv.appendChild(rowDiv);

				}
			});
		});
	}
	// ----------------------------------------------------------------------------------



    // ----------------------------------------------------------------------------------
   	// Muestra los extras que correspondan en base a el subTipoEvento elegido
	function setExtrasBySubTipoEvento(subTipoEventoId) {
		setExtras(subTipoEventoId, "listaExtra", listaExtra, "extraSubTipoEvento");
	}

	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
   	// Muestra los extras variables que correspondan en base a el subTipoEvento elegido
	function setExtrasVariableBySubTipoEvento(subTipoEventoId) {
		setExtrasVariables(subTipoEventoId, "listaExtraVariable", listaExtraVariable, "eventoExtraVariableSubTipoEvento", "extraVariableSubTipoEvento");
	}
	// ----------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------
   	// Muestra los extras catering que correspondan en base a el subTipoEvento elegido
	function setExtraCateringBySubTipoEvento(subTipoEventoId) {
		setExtrasVariables(subTipoEventoId, "listaExtraCatering", listaExtraCatering, "cateringExtraVariableCatering", "extraVariableCatering");
	}
	// ----------------------------------------------------------------------------------

	// Muestra los tipo catering que correspondan en base a el subTipoEvento elegido
	function setTipoCateringBySubTipoEvento(subTipoEventoId) {
		setExtras(subTipoEventoId, "listaTipoCatering", listaTipoCatering, "tipoCatering");
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
			
			if($('#reservacion-wizard-t-3')[0].lastChild.data == " Datos de contacto"){
				$('#reservacion-wizard-t-3')[0].removeChild($('#reservacion-wizard-t-3')[0].lastChild);
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
				$('#reservacion-wizard-t-3')[0].appendChild(document.createTextNode(" Datos de contacto"));
			}
		}
			
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// Set la hora final en base a la hora del subTipoEvento
	$('#time_start_hour').change(function () {
		// Obtiene el valor de hora seteado
		var hora_inicio = $('#time_start_hour').val();
		
		// Obtiene el subTipoEvento
		var subTipoEventoId = $("#subTipoEvento").val();
		
		// Prepara variables
        var duracion_hora = null;
        var duracion_minuto = null;

		// Set los tiempos de duracion del subTipoEvento elegido
        listaSubTipoEvento.forEach( function(subTipoEvento) {
        	if(subTipoEventoId == subTipoEvento.id){
				var duracionSplit = subTipoEvento.duracion.split(":");
				duracion_hora = duracionSplit[0];
				duracion_minuto = duracionSplit[1];
			}
		});

		// Suma  la hora seteada mas la hora de duracion del subTipoEvento elegido
		var hora_fin = Number(hora_inicio) + Number(duracion_hora);
		var hora_fin_string = null;

		// Si el subTipoEvento tiene 30 min de duracion y el tiempo seteado es 30 suma 1 hora
		if(duracion_minuto == "30" && $('#time_start_minute').val() == "30"){
			hora_fin += 1;
		}

		// Si el resultado da un numero de hora con 1 solo espacio le agrega un 0 al principio
		if(hora_fin.toString().length == 1){
			hora_fin_string = "0" + hora_fin.toString();
		}else{
			// Si el resultado es mayor a 24 le resta para que quede en hora 00 
			if(hora_fin >= 24){
				hora_fin -= 24;
				resto24 = true;
				$('#hastaElOtroDiaCheckbox').prop("checked", true );
				$('#resto24').val(resto24);
				
				// Si el resultado da un numero de hora con 1 solo espacio le agrega un 0 al principio
				if(hora_fin.toString().length == 1){
					hora_fin_string = "0" + hora_fin.toString();
				}
			}else{
				// El resultado no resto 24 horas asique setea en false resto
				hora_fin_string = hora_fin.toString();
				resto24 = false;
				$('#hastaElOtroDiaCheckbox').prop("checked", false );
				$('#resto24').val(resto24);
			}
		}
		
		$('#time_end_hour').val(hora_fin_string);

	});
	// ----------------------------------------------------------------------------------
	
	// ----------------------------------------------------------------------------------
	// Set la hora basada en los minutos final cada vez que modifica el horario de unicio
	var yaSumoHora = false;
	$('#time_start_minute').change(function () {
		var minuto_inicio = $('#time_start_minute').val();
		
		var subTipoEventoId = document.getElementById("subTipoEvento").value;
        var duracion = null;
        
        listaSubTipoEvento.forEach( function(subTipoEvento) {
        	if(subTipoEventoId == subTipoEvento.id){
				var duracionSplit = subTipoEvento.duracion.split(":");
				duracion = duracionSplit[1];
			}
		});

		var minuto_fin = Number(minuto_inicio) + Number(duracion);
		var minuto_fin_string = null;
	
		if(minuto_fin.toString().length == 1){
			minuto_fin_string = "0" + minuto_fin.toString();
		}else{
			minuto_fin_string = minuto_fin.toString();
		}

		if(yaSumoHora){
			yaSumoHora = false;
			
			var hora_fin = $('#time_end_hour').val();
			
			if(resto24){
				hora_fin_number = Number(hora_fin) + 23;
				resto24 = false;
				$('#hastaElOtroDiaCheckbox').prop("checked", false );
				$('#resto24').val(resto24);
			}else{
				hora_fin_number = Number(hora_fin) - 1;
			}
			
			if(hora_fin_number.toString().length == 1){
				hora_fin_string = "0" + hora_fin_number.toString();
				}else{
					if(hora_fin_number >= 24){
						hora_fin_number -= 24;
						resto24 = true;
						$('#hastaElOtroDiaCheckbox').prop("checked", true );
						$('#resto24').val(resto24);
						if(hora_fin_number.toString().length == 1){
							hora_fin_string = "0" + hora_fin_number.toString();
						}
				}else{
					hora_fin_string = hora_fin_number.toString();
				}
			}
			
			$('#time_end_hour').val(hora_fin_string);
		}
		
		if(minuto_fin_string == "60"){
			minuto_fin_string = "00";
			yaSumoHora = true;
			
			var hora_fin = $('#time_end_hour').val();
			
			hora_fin_number = Number(hora_fin) + 1;
			
			if(hora_fin_number.toString().length == 1){
				hora_fin_string = "0" + hora_fin_number.toString();
			}else{
				if(hora_fin_number >= 24){
					hora_fin_number -= 24;
					resto24 = true;
					$('#hastaElOtroDiaCheckbox').prop("checked", true );

					$('#resto24').val(resto24);
					if(hora_fin_number.toString().length == 1){
						hora_fin_string = "0" + hora_fin_number.toString();
					}
				}else{
					hora_fin_string = hora_fin_number.toString();
				}
			}
			
			$('#time_end_hour').val(hora_fin_string);
		}

		$('#time_end_minute').val(minuto_fin_string);

	});
	
	// ----------------------------------------------------------------------------------
	
	// ----------------------------------------------------------------------------------
	// En base a la capacidad variable del subTipoEvento deja o no setear la cantidad de platos
	// y de este saldra el presupuesto
	function setPlatoDisabled(capacidadVariable){
		if(capacidadVariable){
			$("#plato").removeClass("d-none");
		}else{
			$("#plato").addClass("d-none");
		}
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// Borra error en subTipoEvento al elegirlo
	function borrarErrorJquerrySteps(){
		$('#tipoEvento-error').remove();
	}
	// ----------------------------------------------------------------------------------
	
	// Checkbox para ocultar hora final y que se setee hasta el otro dia 
	$('#catering_checkbox').change(function(){
		hideCatering();
	});
	
	function hideCatering(){
		if($('#catering_checkbox').is(':checked')){
			$('#catering').fadeIn('slow');
		}else{
			$('#catering').fadeOut('slow');
		}
	}
});

// ----------------------------------------------------------------------------------
// Suma o resta al precio del evento, el precio del extra que haya sido checkeado
function changeExtraCheckbox(extraValor, extraId) {
    var decider = document.getElementById(extraId);
    var precio = parseInt($("#presupuesto").val());

    if(decider.checked){
        precio += parseInt(extraValor);
    } else {
        precio -= parseInt(extraValor);         
    }

    $("#presupuesto").val(precio);
}
// ----------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Suma o resta al precio del evento, el precio del extra variable que haya sido checkeado y su cantidad 
function changeExtraVariableCheckbox(extraValor, extraVariableId) {
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
// Setea la hora final del evento en caso de no ser empresarial o subTipoEvento largo
function setTimeEndBySubTipoEvento(duracion) {
	var hora_inicio = $('#time_start_hour').val();
	var minuto_inicio = $('#time_start_minute').val();

	var duracionSplit = duracion.split(":");
	
	var hora_fin = Number(duracionSplit[0]) + Number(hora_inicio);
	var minuto_fin = Number(duracionSplit[1]) + Number(minuto_inicio);
	
	var hora_fin_string = null;
	var minuto_fin_string = null;

	
	if(hora_fin.toString().length == 1){
		hora_fin_string = "0" + hora_fin.toString();
	}else{
		hora_fin_string = hora_fin.toString();
	}
	
	if(minuto_fin.toString().length == 1){
		minuto_fin_string = "0" + minuto_fin.toString();
	}else{
		minuto_fin_string = minuto_fin.toString();
	}
	
	$('#time_end_hour').val(hora_fin_string);
	$('#time_end_minute').val(minuto_fin_string);
}
// ----------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Setea disabled la hora y minuto final en base al subTipoEvento
function setDisabledHoraFinal(horarioFinalAutomatico) {
	if(horarioFinalAutomatico){
		$('#time_end_hour').prop("disabled", true);
	 	$('#time_end_minute').prop("disabled", true);
	 	
		// Oculta CheckboxduraHastaElOtroDia
		$('#hastaElOtroDia').addClass("d-none");

	}else{
		$('#time_end_hour').removeAttr("disabled");
		$('#time_end_minute').removeAttr("disabled");
		
		// Muestra CheckboxduraHastaElOtroDia
		$('#hastaElOtroDia').removeClass("d-none");
	}
}
// ----------------------------------------------------------------------------------

// ----------------------------------------------------------------------------------
// Setea el precio final de todos los extras seleccionados
function precioExtras() {
	var checkboxes = $(".extraCheckbox" );
	var totalExtras = 0

	listaExtra.forEach(function(extra) {
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
// Setea el precio final de todos los extras variables seleccionados
function precioExtrasVariables() {
	var checkboxes = $(".extraVariableCheckbox" );
	var totalExtras = 0

	listaExtraVariable.forEach(function(extraVariable) {
		$.each(checkboxes, function(idArray, extraInput) {
			var extraVariableId = "eventoExtraVariableSubTipoEventoId" + extraVariable.id
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
	
	presupuesto_con_plato = parseInt(precio_adulto) + parseInt(precio_nino) + parseInt(presupuesto) + precioExtras() + precioExtraOtro() + precioExtrasVariables()

 	presupuesto_con_plato -= descuento(presupuesto_con_plato)

	$("#presupuesto").val(parseInt(presupuesto_con_plato));

}
// ---------------------------------------------------------------------------------

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


