$(document).ready(function () {
    var dataTable = $('#pagination').DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json"
		},
		"ordering": false,
        "info":     false,
        "lengthChange": false,
        "dom": 'lrtip',
	});
	
    $("#filterbox").keyup(function() {
        dataTable.search(this.value).draw();
    });
    
});