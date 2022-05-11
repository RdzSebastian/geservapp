$(document).ready(function() {
	window.onload = selectEventoEmpiezaVacio;

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
	// TODO modificar index por valor id 
    $('#subTipoEvento').change(function () {
        var index = document.getElementById("subTipoEvento").value;
        var precio = null;
        listaSubTipoEvento.forEach( function(valor, indice, array) {
            if(index == valor.id){
                precio = valor.precioBase;
                
                setServicioBySubTipoEvento(valor.id);
                setExtrasBySubTipoEvento(valor.id);

            }
        });

        $("#presupuesto").val(precio);
    })
    
   	// Muestra los extras que correspondan en base a el subTipoEvento elegido
	function setExtrasBySubTipoEvento(subTipoEventoId) {
		// Limpia los extra que se agregaron anteriormente
		$('#listaExtra div').remove();

		// Agrega los extras del subTipoEvento
		listaExtra.forEach(function(valorExtra, indiceExtra, arrayExtra) {
			valorExtra.listaSubTipoEvento.forEach(function(valorSubTipoEvento, indiceSubTipoEvento, arraySubTipoEvento) {
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
		listaServicio.forEach(function(valorServicio, indiceServicio, arrayServicio) {
			valorServicio.listaSubTipoEvento.forEach(function(valorSubTipoEvento, indiceSubTipoEvento, arraySubTipoEvento) {
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