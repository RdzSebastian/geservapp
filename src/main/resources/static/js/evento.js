document.addEventListener('DOMContentLoaded', function() {
  var calendarEl = document.getElementById('calendar');

  var calendar = new FullCalendar.Calendar(calendarEl, {
    customButtons: {
   	 agregarEvento: {
    	text: 'Nuevo Evento',
    	click: function() {
			window.location = '/saveEvento/0'
    		}
    	},
    Administracion: {
    	text: 'Panel de administracion',
    	click: function() {
			window.location = '/administracion'
    		}
    	},
    volverSalones: {
    	text: 'Volver Salones',
    	click: function() {
			window.location = '/'
    		}
    	}
  	},
  	locale: 'es',
    initialView: 'dayGridMonth',
    themeSystem: "bootstrap",
    headerToolbar: {
	 left: 'agregarEvento,volverSalones',
     center: 'title',
     right: 'prevYear,prev,next,nextYear today'
    },
    footerToolbar: {
		left: 'Administracion'
	}, events: {
        url : '/api/eventos/all'
      }
  });

  calendar.render();
	
});