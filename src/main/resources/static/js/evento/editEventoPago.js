$(document).ready( function () {

	setAllComponents();
	setPagosBySubTipoEvento();

	// ----------------------------------------------------------------------------------
	// Setea todos los campos
	function setAllComponents() {
		$("#evento").val(reservaContainer.evento.nombre);
		$("#presupuesto").val(reservaContainer.evento.presupuesto);
		
		var total_abonado = 0;
		listaPagos.forEach(function(pago) {
			total_abonado += pago.pago;
		});
		
		$("#monto_abonado_total").val(total_abonado);
		$("#monto_faltante").val(reservaContainer.evento.presupuesto - total_abonado);
	}
	// ----------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------
	// Muestra los pagos del evento elegido hasta la fecha
	function setPagosBySubTipoEvento() {

		// Obtiene el div donde ira la lista de pagos
		var listaPagosDiv = document.getElementById("listaPagos");
		
		// Crea el div que contendra los pagos
		var div = document.createElement('div');
		div.id = "pagos"
		
		var index = 1;
		var fechaSplit = "";
		var AnoMesDiaSplit = "";
		
		if(listaPagos == ""){
			var p = document.createElement("p");;

			p.appendChild(document.createTextNode("No existen pagos realizados de este evento"));
			div.appendChild(p);
		}else{
			// Agrega los pagos
			listaPagos.forEach(function(pago) {
				
				var divRow = document.createElement('div');
				divRow.classList.add("row");
				divRow.classList.add("mb-3");

				var divPrecio = document.createElement('div');
				divPrecio.classList.add("col-3");
				
				var precio = document.createElement("p");
				precio.appendChild(document.createTextNode("Pago " + index + ":"));
				
				var precioInputGroupCol = document.createElement('div');
				precioInputGroupCol.classList.add("col-5");

				var precioInputGroup = document.createElement('div');
				precioInputGroup.classList.add("input-group");
				
				var precioInputGroupPrepend = document.createElement("div");
				precioInputGroupPrepend.classList.add("input-group-prepend");

				var precioInputGroupSignoPeso = document.createElement("div");
				precioInputGroupSignoPeso.classList.add("input-group-text");
				precioInputGroupSignoPeso.innerText = "$";
				
				precioInputGroupPrepend.appendChild(precioInputGroupSignoPeso);

				var precioInput = document.createElement("input");
				precioInput.value = pago.pago;
				precioInput.classList.add("form-control");
				precioInput.disabled = true;

				var divFechaEntera = document.createElement('div');
				divFechaEntera.classList.add("col-4");
				
				var divFecha = document.createElement('div');
				
				var fecha = document.createElement("small");
				fecha.appendChild(document.createTextNode("Fecha:"));
				
				divFecha.appendChild(fecha);
				
				var divFechaNumero = document.createElement('div');
				
				var fechaNumero = document.createElement("small");
				fechaSplit = pago.fecha.split("T");
				AnoMesDiaSplit = fechaSplit[0].split("-")
				
				fechaNumero.appendChild(document.createTextNode(AnoMesDiaSplit[2] + "-" +  AnoMesDiaSplit[1] + "-" +  AnoMesDiaSplit[0]));
				
				divFechaNumero.appendChild(fechaNumero);
				
				
				divPrecio.appendChild(precio);
				precioInputGroup.appendChild(precioInputGroupPrepend);
				precioInputGroup.appendChild(precioInput);
				precioInputGroupCol.appendChild(precioInputGroup);
//				precioInputGroupCol.appendChild(precioInput);
				divFechaEntera.appendChild(divFecha);
				divFechaEntera.appendChild(divFechaNumero);

				divRow.appendChild(divPrecio);
				divRow.appendChild(precioInputGroupCol);
				divRow.appendChild(divFechaEntera);
				
				div.appendChild(divRow);
	
				index++;
			});
		}
		// Agrega todos los pagos al div ListaPagos
		listaPagosDiv.appendChild(div);
	}
	// ----------------------------------------------------------------------------------
});