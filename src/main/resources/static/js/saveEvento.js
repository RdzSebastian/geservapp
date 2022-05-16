var resto24 = false

$(document).ready(function() {
	window.onload = selectEventoEmpiezaVacio;
	
	//listen for window resize event
	window.addEventListener('resize', function(event){
		sizeScreen();
	});
	
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

	// Borra las opciones de subTipoEvento que no corresponden la tipoEvento escogido
	var optionValues =[];
	$('#tipoEvento option').each(function(){
	   if($.inArray(this.value, optionValues) >-1){
	      $(this).remove()
	   }else{
	      optionValues.push(this.value);
	   }
	});

	// Limpia opciones de subTipoEvento al cambiar de tipoEvento
	function selectEventoEmpiezaVacio() {
		$('#subTipoEvento option').remove()
		addHiddenFirstOptionOnSelect("subTipoEvento", "Sub Tipo Evento");
	}

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
	
	
	// Libreria combodate muestra el dia
	$('#date').combodate({
          value: new Date(),
          minYear: moment().year(),
          maxYear: moment().year() + 2,
          customClass: 'form-control d-inline',
          smartDays: true
    });

    // Checkbox para ocultar hora final y que se setee hasta el otro dia 
    $('#hastaElOtroDiaCheckbox').change(function(){
		hideHoraFinal(this);
	});
	
	function hideHoraFinal(button){
		if(button.checked)
			$('#time_end_div').fadeOut('slow');
		else
			$('#time_end_div').fadeIn('slow');
	}
	

	// Busca el subTipoEvento seleccionado y setea el precio base de dicho subTipoEvento
    $('#subTipoEvento').change(function () {
        var subTipoEventoId = document.getElementById("subTipoEvento").value;
        var precio = null;
        listaSubTipoEvento.forEach( function(subTipoEvento) {
            if(subTipoEventoId == subTipoEvento.id){
                precio = subTipoEvento.precioBase;

                setServicioBySubTipoEvento(subTipoEvento.id);
                setExtrasBySubTipoEvento(subTipoEvento.id);
                setTimeEndBySubTipoEvento(subTipoEvento.duracion);
				setDisabledHoraFinal(subTipoEvento.id);
            }
        });

        $("#presupuesto").val(precio);
    })
    
   	// Muestra los extras que correspondan en base a el subTipoEvento elegido
	function setExtrasBySubTipoEvento(subTipoEventoId) {
		// Limpia los extra que se agregaron anteriormente
		$('#listaExtra div').remove();

		// Agrega los extras del subTipoEvento
		listaExtra.forEach(function(valorExtra) {
			valorExtra.listaSubTipoEvento.forEach(function(valorSubTipoEvento) {
				if(valorSubTipoEvento.id == subTipoEventoId){

					var listaExtraDiv = document.getElementById("listaExtra");
					var checkbox = document.createElement('input');
					
					var extraDiv = document.createElement('div');
					extraDiv.setAttribute("id", "extraCheckbox");
					// Assigning the attributes
		            // to created checkbox
		            checkbox.type = "checkbox";
		            checkbox.name = "extra";
		            checkbox.value = valorExtra.id;
		            checkbox.id = "extraId" + valorExtra.id;
		            checkbox.classList.add("form-check-input");
		            checkbox.onchange = function () { 
						changeCheckbox(valorExtra.precio , 'extraId' + valorExtra.id);
					}
		            
		            // creating label for checkbox
		            var label = document.createElement('label');
		              
		            // assigning attributes for 
		            // the created label tag 
		            label.htmlFor = "id";
//		            label.classList.add("form-check-label");
//		            label.classList.add("ml-2");
		            
		            // appending the created text to 
		            // the created label tag 
		            label.appendChild(document.createTextNode(valorExtra.nombre));
		              
		            // appending the checkbox
		            // and label to div
		            extraDiv.appendChild(checkbox);
		            extraDiv.appendChild(label);
		           	listaExtraDiv.appendChild(extraDiv);

				}
			});
		});
	}
	
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

	$('#time_start_hour').change(function () {
		var hora_inicio = $('#time_start_hour').val();
		
		var subTipoEventoId = document.getElementById("subTipoEvento").value;
        var duracion = null;
        
        listaSubTipoEvento.forEach( function(subTipoEvento) {
        	if(subTipoEventoId == subTipoEvento.id){
				var duracionSplit = subTipoEvento.duracion.split(":");
				duracion = duracionSplit[0];
			}
		});

		var hora_fin = Number(hora_inicio) + Number(duracion);
		var hora_fin_string = null;

		if(hora_fin.toString().length == 1){
			hora_fin_string = "0" + hora_fin.toString();
			}else{
				if(hora_fin >= 24){
					hora_fin -= 24;
					resto24 = true;
					if(hora_fin.toString().length == 1){
						hora_fin_string = "0" + hora_fin.toString();
					}
			}else{
				hora_fin_string = hora_fin.toString();
			}
		}
		
		$('#time_end_hour').val(hora_fin_string);

	});
	
	// Set la hora final cada vez que modifica el horario de unicio
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
				resto24 = false
			}else{
				hora_fin_number = Number(hora_fin) - 1;
			}
			
			if(hora_fin_number.toString().length == 1){
				hora_fin_string = "0" + hora_fin_number.toString();
				}else{
					if(hora_fin_number >= 24){
						hora_fin_number -= 24;
						resto24 = true;
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

});


// Suma o resta al precio del evento, el precio del extra que haya sido checkeado
function changeCheckbox(extraValor, extraId) {
    var decider = document.getElementById(extraId);
    var precio = parseInt($("#presupuesto").val());

    if(decider.checked){
        precio += parseInt(extraValor);
    } else {
        precio -= parseInt(extraValor);         
    }

    $("#presupuesto").val(precio);
}

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


// Setea disabled la hora y minuto final en base al subTipoEvento
// TODO agregar una lista de subTipoEvento con disabled horario 
function setDisabledHoraFinal(id) {
	if(id == 4){
		$('#time_end_hour').removeAttr("disabled");
		$('#time_end_minute').removeAttr("disabled");
	}else{
		if (!$('#time_end_hour').attr("disabled")) {
			$('#time_end_hour').prop("disabled", true);
		 	$('#time_end_minute').prop("disabled", true);
		 }
	}
}
