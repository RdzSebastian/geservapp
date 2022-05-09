$(document).ready(function() {
	$("#buscarClientePorCuil").click(function(){

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
			url: "http://localhost:8080/buscarClientePorCuil",
			data : data,
			contentType: "application/json",
			success : function(response) {
				 $("#nombreCliente").val(response.nombre);
				 $("#apellido").val(response.apellido);
				 $("#email").val(response.email);
				 $("#celular").val(response.celular);
				 $("#empresa").val(response.empresa);
				 $("#sexo").val(response.sexo.id);
				 
				 var cliente = document.getElementById("clienteNoEncontrado");
				 cliente.classList.add("d-none")
				 
			},
			error : function() {
				var cliente = document.getElementById("clienteNoEncontrado");
				cliente.classList.remove("d-none")
			}
		});
	});
});