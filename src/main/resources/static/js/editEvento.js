$(function () {
				document.addEventListener("submit", myFunction);

	function myFunction(){
		var a = document.getElementById("time_start").value;
	  	var b = document.getElementById("time_start_min").value;
	  	var c = document.getElementById("time_end").value;
	  	var d = document.getElementById("time_end_min").value;
	  	
	  	var e = document.getElementById("inicioF");
	  	var f = document.getElementById("finF");

		$("#inicioF").val(a + ":" + b)
		$("#finF").val(c + ":" + d)
		
	}
		
    $('#hastaElOtroDiaCheckbox').change(function(){
		if(this.checked)
		$('#horaFinal').fadeOut('slow');
		else
		$('#horaFinal').fadeIn('slow');
	});
});