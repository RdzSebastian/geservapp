$(document).ready(function() {

	$("#cuil").change(function(){

	  $("#nombreCliente").val(null);
	  $("#apellido").val(null);
	  $("#email").val(null);
	  $("#celular").val(null);
	  $("#empresa").val(null);
	  $("#sexo").val(null);

		data = {
			cuil: $("#cuil").val()
		};
		
		$.ajax({
			type: 'GET',
			url: "http://138.219.42.251/api/eventos/buscarClientePorCuil",
			data : data,
			contentType: "application/json",
			success : function(response) {
				 $("#nombreCliente").val(response.nombre);
				 $("#apellido").val(response.apellido);
				 $("#email").val(response.email);
				 $("#celular").val(response.celular);
				 $("#empresa").val(response.empresa);
				 $("#sexo").val(response.sexo.id);
				 
				 $("#clienteEncontrado").removeClass("d-none");
				 $("#clienteNoEncontrado").addClass("d-none");

			},
			error : function() {
				$("#clienteEncontrado").addClass("d-none");
				$("#clienteNoEncontrado").removeClass("d-none");
			}
		});
	});
	
	$("#date").change(function(){
		horarioDisponible();
		listaEventosByDia();
	});

	$("#time_start_hour").change(function(){
		horarioDisponible();
	});
	
	$("#time_start_minute").change(function(){
		horarioDisponible();
	});
	
	
	$("#time_end_hour").change(function(){
		horarioDisponible();
	});
	
	
	$("#time_end_minute").change(function(){
		horarioDisponible();
	});

	function horarioDisponible(){
		data = {
			fecha: $("#date").val(),
			inicio: $("#time_start_hour").val() + ":" + $("#time_start_minute").val(),
			fin: $("#time_end_hour").val() + ":" + $("#time_end_minute").val(),
			resto24: resto24
		};
		
		$.ajax({
			type: 'GET',
			url: "http://138.219.42.251/api/eventos/horarioDisponible",
			data : data,
			contentType: "application/json",
			success : function() {
				$("#fechaNoDisponible").addClass("d-none");
				$("#fechaDisponible").removeClass("d-none");
			},
			error : function() {
				$("#fechaNoDisponible").removeClass("d-none");
				$("#fechaDisponible").addClass("d-none");
			}
		});
	}
	
	function listaEventosByDia(){
		data = {
			fecha: $("#date").val()
		};
		
		$.ajax({
			type: 'GET',
			url: "http://138.219.42.251/api/eventos/listaEventosByDia",
			data : data,
			contentType: "application/json",
			success : function(response) {
				// Limpia las fechas que se agregaron anteriormente
				$('#ulEvento').remove();
				
				// Obtiene el div donde ira la lista de servicios
				var listaEventoDiv = document.getElementById("listaEvento");
				
				// Crea el ul que contendra a los li
				var ul = document.createElement('ul');
				ul.id = "ulEvento"
		
				response.forEach(function(fecha) {
					var li = document.createElement("li");
					li.appendChild(document.createTextNode(fecha));
					ul.appendChild(li);
				});
				listaEventoDiv.appendChild(ul);
			},
			error : function() {
				// Limpia las fechas que se agregaron anteriormente
				$('#ulEvento').remove();
			}
		});
	}
	
});