document.addEventListener('DOMContentLoaded', function() {
  var calendarEl = document.getElementById('calendar');

   var calendar = new FullCalendar.Calendar(calendarEl, {
    customButtons: {
     agregarEvento: {
	   bootstrapFontAwesome: 'fa-plus',
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
       bootstrapFontAwesome: 'fa-sign-out-alt',
       click: function() {
         window.location = '/'
       }
     }
   },
   locale: 'es',
   initialView: 'dayGridMonth',
   themeSystem: "bootstrap",
   selectable: true,
   headerToolbar: {
    left: 'agregarEvento,volverSalones today',
    center: 'title',
    right: 'prevYear,prev,next,nextYear dayGridMonth,timeGridWeek,listWeek'
   },
   dateClick: function(info) {
     window.location = '/seleccionarFecha/?arr=' + info.dateStr;
   },
   footerToolbar: {
    left: 'Administracion'
   }, 
   events: {
    url : '/api/eventos/all'
   }
  });

 calendar.render();

});