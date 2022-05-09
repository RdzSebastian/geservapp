$(function () {
	setCartelError();

	function setCartelError(){
		if(!admin){
			$('#usuario').addClass("d-none");
			$('#salon').addClass("d-none");
			$('#tipoEvento').addClass("d-none");
			$('#subTipoEvento').addClass("d-none");
			$('#extra').addClass("d-none");
			$('#servicio').addClass("d-none");
		}
	}

});