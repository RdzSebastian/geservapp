$(function () {
	document.addEventListener("submit", setStartAndEndTime);

	function setStartAndEndTime(){
		var time_start = document.getElementById("time_start").value;
	  	var time_start_min = document.getElementById("time_start_min").value;
	  	var time_end = document.getElementById("time_end").value;
	  	var time_end_min = document.getElementById("time_end_min").value;

		$("#inicioF").val(time_start + ":" + time_start_min)
		$("#finF").val(time_end + ":" + time_end_min)
		
	}

    $('#hastaElOtroDiaCheckbox').change(function(){
		if(this.checked)
			$('#horaFinal').fadeOut('slow');
		else
			$('#horaFinal').fadeIn('slow');
	});
});