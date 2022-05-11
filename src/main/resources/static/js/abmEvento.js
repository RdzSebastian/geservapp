$(function () {

	var agregarEvento = document.querySelector('button[aria-label="agregarEvento"]');
	var iconoAgregarEvento = document.createElement('span');
	iconoAgregarEvento.classList.add("fa");
 	iconoAgregarEvento.classList.add("fa-plus");
 	var nuevoEnventoTexto = document.createTextNode("Nuevo Evento");

	var volverSalones = document.querySelector('button[aria-label="volverSalones"]');
	var iconoVolverSalones = document.createElement('span');
	iconoVolverSalones.classList.add("fa");
 	iconoVolverSalones.classList.add("fa-sign-out-alt");
	var volverSalonesTexto = document.createTextNode("Volver Salones");

	var mes = $(".fc-dayGridMonth-button");
	var semana = $(".fc-timeGridWeek-button");
	var agenda = $(".fc-listWeek-button");

	sizeScreen();	

	//listen for window resize event
	window.addEventListener('resize', function(event){
		sizeScreen();
	});
	
	function sizeScreen(){
		var newWidth = window.innerWidth;

	    if(newWidth <= 950){
			cambiarTextoPorIconos();
			hacerTituloChico();
		}else{
			cambiarIconosPorTexto();
			hacerTituloGrande()
		}
	}
	
	function cambiarIconosPorTexto(){

		// Elimina el icono
		agregarEvento.replaceChildren();
		volverSalones.replaceChildren();

		// Agrega el text
		agregarEvento.appendChild(nuevoEnventoTexto);
		volverSalones.appendChild(volverSalonesTexto);

		mes[0].classList.remove("d-none");
		semana[0].classList.remove("d-none");
		agenda[0].classList.remove("d-none");

	}
	
	function cambiarTextoPorIconos(){

		// Elimina el texto
		agregarEvento.replaceChildren();
		volverSalones.replaceChildren();

		// Agrega el icono
		agregarEvento.appendChild(iconoAgregarEvento);
		volverSalones.appendChild(iconoVolverSalones);
		
		mes[0].classList.add("d-none");
		semana[0].classList.add("d-none");
		agenda[0].classList.add("d-none");

	}
	
	function hacerTituloGrande(){
		var title = document.getElementsByTagName('h5');
		if(title[0] != null){
			title[0].outerHTML = '<h2>' + title[0].innerHTML + '</h2>';
		}
	}

	function hacerTituloChico(){
		var title = document.getElementsByTagName('h2');
		if(title[0] != null){
			title[0].outerHTML = '<h5>' + title[0].innerHTML + '</h5>';
		}
	}
});