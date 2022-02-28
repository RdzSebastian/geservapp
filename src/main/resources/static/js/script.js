document.addEventListener('DOMContentLoaded', function() {
  var calendarEl = document.getElementById('calendar');

  var calendar = new FullCalendar.Calendar(calendarEl, {
    customButtons: {
   	 myCustomButton: {
    	text: 'Nuevo Evento',
    	click: function() {
			window.location = 'saveEventos/0'
    		}
    	}
  	},
  	locale: 'es',
    initialView: 'dayGridMonth',
    themeSystem: "bootstrap",
    headerToolbar: {
	 left: 'myCustomButton',
     center: 'title',
     right: 'prevYear,prev,next,nextYear today'
    }, events: {
        url : '/api/eventos/all'
      }
  });

  calendar.render();
	
});