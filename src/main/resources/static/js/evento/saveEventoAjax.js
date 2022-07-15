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

 	$('#subTipoEvento').change(function () {
 		setTimeEndBySubTipoEvento();
		precioEventoBySubTipoEventoYFecha();
		setExtraAdultosYNinoCapacidad();
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

});