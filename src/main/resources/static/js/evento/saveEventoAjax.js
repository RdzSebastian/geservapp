$(document).ready(function() {

	$("#cuil").change(function(){

	  // Setea todos los campos vacios menos sexo
	  $("#nombreCliente").val(null);
	  $("#apellido").val(null);
	  $("#email").val(null);
	  $("#celular").val(null);
	  $("#empresa").val(null);

		data = {
			cuil: $("#cuil").val()
		};
		
		$.ajax({
			type: 'GET',
			url: "http://localhost:8080/api/eventos/buscarClientePorCuil",
			data : data,
			contentType: "application/json",
			success : function(cliente) {
				
				if(cliente != ""){
					// Todos los campos en disabled para que no edite al cliente encontrado
					$('#nombreCliente').prop("readonly", true);
					$('#apellido').prop("readonly", true);
					$('#email').prop("readonly", true);
					$('#celular').prop("readonly", true);
					$('#empresa').prop("readonly", true);
					$('#sexo').prop("disabled", true);
					
					// Setea todos los valores del cliente encontrado
					$("#nombreCliente").val(cliente.nombre);
					$("#apellido").val(cliente.apellido);
					$("#email").val(cliente.email);
					$("#celular").val(cliente.celular);
					$("#empresa").val(cliente.empresa);
					$("#sexo").val(cliente.sexo.id);
					 
					// Esconde cartel de cliente NO encontrado y muestra cartel de cliente encontrado
					$("#clienteEncontrado").removeClass("d-none");
					$("#clienteNoEncontrado").addClass("d-none");
				}else{
					// Remueve los disabled para que ingrese un nuevo cliente al no haberlo encontrado
					$('#nombreCliente').removeAttr("readonly");
					$('#apellido').removeAttr("readonly");
					$('#email').removeAttr("readonly");
					$('#celular').removeAttr("readonly");
					$('#empresa').removeAttr("readonly");
					$('#sexo').removeAttr("disabled");

					// Esconde cartel de cliente encontrado y muestra cartel de cliente NO encontrado
					$("#clienteEncontrado").addClass("d-none");
					$("#clienteNoEncontrado").removeClass("d-none");
				}
			}
		});
	});
	
	// Al modificar la fecha que chequee los horarios disponibles, traiga la lista de eventos de ese dia 
	// y el precio de ese dia
	$("#date").change(function(){
		setTimeEndBySubTipoEvento()
		listaEventosByDia();
		precioEventoBySubTipoEventoYFecha();
	});

	// Al modificar la hora de inicio que chequee los horarios disponibles y traiga la lista de eventos de ese dia
	$("#time_start_hour").change(function(){
		setTimeEndBySubTipoEvento()
		listaEventosByDia();
	});

	// Al modificar los minutos de inicio que chequee los horarios disponibles y traiga la lista de eventos de ese dia
	$("#time_start_minute").change(function(){
		setTimeEndBySubTipoEvento()
		listaEventosByDia();
	});
	

	// Al modificar la horade inicio que chequee los horarios disponibles
	$("#time_end_hour").change(function(){
		horarioDisponible();
	});

	// Al modificar la horade inicio que chequee los horarios disponibles
	$("#time_end_minute").change(function(){
		horarioDisponible();
	});

	// Busca si el horario esta disponible, al finalizar muestra el cartel correspondiente 
	function horarioDisponible(){
		data = {
			fecha: $("#date").val(),
			inicio: $("#time_start_hour").val() + ":" + $("#time_start_minute").val(),
			fin: $("#time_end_hour").val() + ":" + $("#time_end_minute").val(),
			resto24: $("#hastaElOtroDiaCheckbox").is(':checked')
		};
		
		$.ajax({
			type: 'GET',
			url: "http://localhost:8080/api/eventos/horarioDisponible",
			data : data,
			contentType: "application/json",
			success : function(response) {
				if(response){
					$("#fechaNoDisponible").addClass("d-none");
					$("#fechaDisponible").removeClass("d-none");
				}else{
					$("#fechaNoDisponible").removeClass("d-none");
					$("#fechaDisponible").addClass("d-none");
				}

			}
		});
	}

	// Trae la lista de eventos por dia con su respectivo subTipoEvento y horario del evento
	function listaEventosByDia(){
		data = {
			fecha: $("#date").val()
		};

		$.ajax({
			type: 'GET',
			url: "http://localhost:8080/api/eventos/listaEventosByDia",
			data : data,
			contentType: "application/json",
			success : function(listaEvento) {

				// Limpia las fechas que se agregaron anteriormente
				$('#ulEvento').remove();

				if(listaEvento != ""){
					// Obtiene el div donde ira la lista de servicios
					var listaEventoDiv = document.getElementById("listaEvento");
					
					// Crea el ul que contendra a los li
					var ul = document.createElement('ul');
					ul.id = "ulEvento"
			
					listaEvento.forEach(function(fecha) {
						var li = document.createElement("li");
						li.appendChild(document.createTextNode(fecha));
						ul.appendChild(li);
					});
					listaEventoDiv.appendChild(ul);
				}
			}
		});
	}

	// Al cambiar el subTipoEvento setea la hora y busca los extras
 	$('#subTipoEvento').change(function () {
 		setTimeEndBySubTipoEvento();
		setListasDeExtras();
	});

	// Trae el precio del subTipoEvento, en caso de ser fin de semana le agrega un extra
	function precioEventoBySubTipoEventoYFecha(){
		data = {
			fecha: $("#date").val(),
			subTipoEventoId: $("#subTipoEvento").val()
		};

		$.ajax({
			type: 'GET',
			url: "http://localhost:8080/api/eventos/precioEventoBySubTipoEventoYFecha",
			data : data,
			contentType: "application/json",
			success : function(precio) {
				$("#presupuesto").val(parseInt(precio))
				presupuesto = parseInt(document.getElementById("presupuesto").value);
				sumarPresupuesto();
			}
		});
	}

	// Setea hora de fin
	function setTimeEndBySubTipoEvento(){

		data = {
			fecha: $("#date").val(),
			inicio: $("#time_start_hour").val() + ":" + $("#time_start_minute").val(),
			subTipoEventoId: $("#subTipoEvento").val()
		};

		$.ajax({
			type: 'GET',
			url: "http://localhost:8080/api/eventos/setTimeEndBySubTipoEvento",
			data : data,
			contentType: "application/json",
			success : function(OtroDiaHoraFinContainer) {

				$('#time_end_hour').val(OtroDiaHoraFinContainer.fin_hora);
				$('#time_end_minute').val(OtroDiaHoraFinContainer.fin_minutos);

				document.querySelector("#hastaElOtroDiaCheckbox").checked = OtroDiaHoraFinContainer.otroDia;

				horarioDisponible();
			}
		});
	}

 	$('#cantidad_plato_adulto').change(function () {
		setExtraAdultosYNinoCapacidad();
	});

	$('#cantidad_plato_nino').change(function () {
		setExtraAdultosYNinoCapacidad();
	});

	// Setea extra camarera o nino en base a la capacidad base del evento
	function setExtraAdultosYNinoCapacidad(){

		data = {
			adultos: $("#cantidad_plato_adulto").val(),
			ninos: $("#cantidad_plato_nino").val(),
			subTipoEventoId: $("#subTipoEvento").val()
		};

		$.ajax({
			type: 'GET',
			url: "http://localhost:8080/api/eventos/setExtraAdultosYNinoCapacidad",
			data : data,
			contentType: "application/json",
			success : function(extraCapacidadContainer) {

				var nameExtra = "eventoExtraVariableSubTipoEvento";

				// -------------------------- Nino ----------------------------------
				var extraNinoId = extraCapacidadContainer.idExtraNinos

				var checkboxIdNino = nameExtra + "Id" + extraNinoId
				var inputIdNino = nameExtra + "Id" + extraNinoId + "Cantidad";
					
				if(extraCapacidadContainer.cantidadNinos > 0){
					document.querySelector("#" + checkboxIdNino).checked = true
					$('#' + inputIdNino).val(extraCapacidadContainer.cantidadNinos);
					$('#' + inputIdNino).removeAttr("disabled");
				}else{
					document.querySelector("#" + checkboxIdNino).checked = false
					$('#' + inputIdNino).val(0);
					$('#' + inputIdNino).prop("disabled", true)
				}
				
				// -------------------------- Camarera ----------------------------------
				var extraCamareraId = extraCapacidadContainer.idExtraCamarera

				var checkboxIdCamarera = nameExtra + "Id" + extraCamareraId
				var inputIdCamarera = nameExtra + "Id" + extraCamareraId + "Cantidad";
				
				if(extraCapacidadContainer.cantidadCamarera > 0){
					document.querySelector("#" + checkboxIdCamarera).checked = true
					$('#' + inputIdCamarera).val(extraCapacidadContainer.cantidadCamarera);
					$('#' + inputIdCamarera).removeAttr("disabled");
				}else{
					document.querySelector("#" + checkboxIdCamarera).checked = false
					$('#' + inputIdCamarera).val(0);
					$('#' + inputIdCamarera).prop("disabled", true)
				}
				
				sumarPresupuesto();
			}
		});
	}

	// ----------------------------------------------------------------------------------
   	// Muestra los extras que correspondan en base a el subTipoEvento elegido
	function setListasDeExtras() {

		data = {
			fecha: $("#date").val(),
			subTipoEventoId: $("#subTipoEvento").val()
		};

		$.ajax({
			type: 'GET',
			url: "http://localhost:8080/api/eventos/getExtrasConPrecio",
			data : data,
			contentType: "application/json",
			success : function(listasDeExtras) {
				
				listaExtra = listasDeExtras.listaExtra;
				listaExtraVariable = listasDeExtras.listaExtraVariable;
				listaExtraCatering = listasDeExtras.listaExtraCatering;
				listaTipoCatering = listasDeExtras.listaTipoCatering;
				
				setExtrasBySubTipoEvento();
		        setExtrasVariableBySubTipoEvento();
		        setExtrasCateringBySubTipoEvento();
		        setTipoCateringBySubTipoEvento();

       			setExtraAdultosYNinoCapacidad();
       			precioEventoBySubTipoEventoYFecha();
				
			}
		});

	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
   	// Muestra los extras que correspondan en base a el subTipoEvento elegido
	function setExtrasBySubTipoEvento() {
		setExtras("listaExtra", listaExtra, "extraSubTipoEvento", "extraCheckboxSubTipoEvento");
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
   	// Muestra los extras variables que correspondan en base a el subTipoEvento elegido
	function setExtrasVariableBySubTipoEvento() {
		setExtrasVariables("listaExtraVariable", listaExtraVariable, "eventoExtraVariableSubTipoEvento", "extraVariableSubTipoEvento", "extraVariableCheckboxSubTipoEvento");
	}
	// ----------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------
   	// Muestra los extras catering que correspondan en base a el subTipoEvento elegido
	function setExtrasCateringBySubTipoEvento() {
		setExtrasVariables("listaExtraCatering", listaExtraCatering, "cateringExtraVariableCatering", "extraVariableCatering", "extraVariableCheckboxCatering");
	}
	// ----------------------------------------------------------------------------------

	// Muestra los tipo catering que correspondan en base a el subTipoEvento elegido
	function setTipoCateringBySubTipoEvento() {
		setExtras("listaTipoCatering", listaTipoCatering, "tipoCatering", "extraCheckboxCatering");
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
   	// Muestra los extras que correspondan en base a el subTipoEvento elegido
	function setExtras(idDiv, listaExtras, nameExtra, nameExtraCheckbox) {
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
	function setExtrasVariables(idDiv, listaExtras, nameExtra, nameObjectExtra, nameExtraCheckbox) {
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
		});
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// Checkbox para ocultar el precio de "Otro catering" 
	$('#catering_otro_checkbox').change(function(){
		resetValueOfCatering();
	});
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
