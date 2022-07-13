$(document).ready( function () {
	
	 $('#time_start_hour').change(function () {
 		setTimeEndBySubTipoEvento();
 		listaEventosByDia();
	});
	
	$('#time_start_minute').change(function () {
 		setTimeEndBySubTipoEvento();
 		listaEventosByDia();
	});
	
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

});