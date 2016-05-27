$(document).ready(function() {
	$('#noticeTable').dataTable({
//		"bPaginate": false,
		"bLengthChange": false,
		"bFilter": true,
		"bInfo": false,
//		"bAutoWidth": false
	} );

	$('.addNotice').bind('click' , function(){location.href='/adder/enrollPage.do?check=0'});
	$('.dataTables_filter input').attr("placeholder", "제목을 입력하세요.");

});