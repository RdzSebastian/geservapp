document.addEventListener('DOMContentLoaded', function() {
  var calendarEl = document.getElementById('calendar');

  var calendar = new FullCalendar.Calendar(calendarEl, {
    customButtons: {
   	 eventos: {
    	text: 'Nuevo Evento',
    	click: function() {
			window.location = 'saveEventos/0'
    		}
    	},
    usuarios: {
    	text: 'Usuarios',
    	click: function() {
			window.location = 'abmUsuario'
    		}
    	}
  	},
  	locale: 'es',
    initialView: 'dayGridMonth',
    themeSystem: "bootstrap",
    headerToolbar: {
	 left: 'eventos',
     center: 'title',
     right: 'prevYear,prev,next,nextYear today'
    },
    footerToolbar: {
		left: 'usuarios'
	}, events: {
        url : '/api/eventos/all'
      }
  });

  calendar.render();
	
});