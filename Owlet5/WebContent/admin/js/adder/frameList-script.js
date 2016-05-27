$(document).ready(function() {
    $('#frameTable').dataTable({
//		"bPaginate": false,
		"bLengthChange": false,
		"bFilter": true,
		"bInfo": false,
//		"bAutoWidth": false
    });
    $('.dataTables_filter input').attr("Search", "검색");
    $('.dataTables_filter input').attr("placeholder", "제목을 입력하세요.");
} );