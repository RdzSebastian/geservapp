$(document).ready(function() {
	
	$.validator.addMethod("selectTipoEventoValido", function(value, element) {
		if ($("#tipoEvento")[0].selectedIndex === 0 ) {
			return false;
		} 
			return true;
		}, 
		"Por favor seleccione un tipo evento válido");
		
	$.validator.addMethod("selectSubTipoEventoValido", function(value, element) {
		if ($("#subTipoEvento")[0].selectedIndex === ($("#subTipoEvento")[0].options.length -1)) {
			return false;
		} 
			return true;
		}, 
		"Por favor seleccione un Sub Tipo Evento válido");

//
//	$.validator.addMethod("cuilValido", function(value, element) {
//		if ($("#cuil").value === 0) {
//			return false;
//		} 
//			return true;
//		}, 
//		"Por favor ingrese un cuil válido");
//		
//	$.validator.addMethod("celularValido", function(value, element) {
//		if ($("#celular").value === 0) {
//			return false;
//		} 
//			return true;
//		}, 
//		"Por favor ingrese un celular válido");

	$("#reservacion-wizard").steps({
	    headerTag: "h3",
	    bodyTag: "section",
	    transitionEffect: "slideLeft",
	    autoFocus: true,
	    labels: {
            previous: 'Atras',
            next: 'Siguiente',
            finish: 'Terminar',
            current: ''
        },
        onStepChanging: function (event, currentIndex, newIndex){
	        form.validate().settings.ignore = ":disabled,:hidden";
	        return form.valid();
	    },
	    onFinishing: function (event, currentIndex){
	        form.validate().settings.ignore = ":disabled";
	        return form.valid();
	    },
	    onFinished: function(event, currentIndex) {
			$("#spinner-finish").removeClass("d-none");
            $('button[type="submit"]').trigger('click');
        },
	}).find('a')
        .last().parent().parent()
        .prepend("<li><a href='../abmEvento/" + salonId + "'>Volver Calendario</a></li>");
        
	var form = $("#reservacion-form");
	form.validate({
	lang: 'es',
    errorPlacement: function errorPlacement(error, element) { element.before(error); },
    	rules: {
		    tipoEvento: {
		      selectTipoEventoValido: true,
		      selectSubTipoEventoValido: true
		    },
		    field: {
		      required: true,
		      email: true
		    }
    	}
	});
});