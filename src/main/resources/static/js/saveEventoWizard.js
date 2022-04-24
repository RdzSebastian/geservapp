$(document).ready(function() {

	$("#reservacion-wizard").steps({
	    headerTag: "h3",
	    bodyTag: "section",
	    transitionEffect: "slideLeft",
	    autoFocus: true,
	    onFinished: function(event, currentIndex) {
            $('button[type="submit"]').trigger('click');
        },
	}).find('a')
        .last().parent().parent()
        .prepend("<li><a href='../abmEvento/1'>Volver Calendario</a></li>");
        
    $("#reservacion-form").validate();
});