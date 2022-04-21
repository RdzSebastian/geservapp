$(document).ready(function() {

	$("#reservacion-wizard").steps({
	    headerTag: "h3",
	    bodyTag: "section",
	    transitionEffect: "slideLeft",
	    autoFocus: true,
	    onFinished: function(event, currentIndex) {
            $('button[type="submit"]').trigger('click');
        }
	});
	
	$("#reservacion-form").validate();

	
});