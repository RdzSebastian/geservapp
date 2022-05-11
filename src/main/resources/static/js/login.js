$(function () {	
	
	errorLogin();
	
	function errorLogin(){
		let searchParams = new URLSearchParams(window.location.search)
		if(searchParams.has('error')){
			var cartelErrorDiv = document.getElementById("cartelError");
			cartelErrorDiv.classList.remove("d-none");
		}
	}
	
});