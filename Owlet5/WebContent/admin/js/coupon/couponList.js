$(document).ready(function() {
	$('#couponTable').dataTable({
//		"bPaginate": false,
		"bLengthChange": false,
		"bFilter": true,
		"bInfo": false,
//		"bAutoWidth": false
	});

	$('.addCoupon').bind('click' , function(){location.href='/event/coupon.do'});
	$('.dataTables_filter input').attr("placeholder", "제목을 입력하세요.");
});