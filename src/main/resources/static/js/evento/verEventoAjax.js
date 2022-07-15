$(function () {

	$("#reenviarMail").click(function() {
		reenviarMail();
	});

	function reenviarMail(){
		data = {
			codigo: $("#codigo").val(),
		};
		
		$.ajax({
			type: 'GET',
			url: "http://localhost:8080/api/eventos/reenviarMail",
			data : data,
			contentType: "application/json",
			success : function(response) {
				if(response){
					$("#MailEnviado").removeClass("d-none");
					$("#MailNoEnviado").addClass("d-none");
				}else{
					$("#MailNoEnviado").removeClass("d-none");
					$("#MailEnviado").addClass("d-none");
				}

			}
		});
	}

});