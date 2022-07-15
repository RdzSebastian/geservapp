$(function () {

	setCartel();

	function setCartel(){
		if(eventoEncontrado != null){
			if(eventoEncontrado){
				$("#comprobanteDescargado").removeClass("d-none");
				$("#comprobanteNoDescargado").addClass("d-none");
			}else{
				$("#comprobanteNoDescargado").removeClass("d-none");
				$("#comprobanteDescargado").addClass("d-none");
			}
		}
	}

});