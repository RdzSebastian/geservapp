$(function () {
	setCartelError();

	function setCartelError(){
		if(eventoNoEncontrado){
			var cartelErrorDiv = document.getElementById("cartelError");
			cartelErrorDiv.classList.remove("d-none");
		}
	}

});