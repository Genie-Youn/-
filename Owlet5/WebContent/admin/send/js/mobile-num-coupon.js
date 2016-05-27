var param =  location.search;

var urlObject = jQuery(location);
var protocol = urlObject.attr("protocol");
//var host = urlObject.attr("host").substring(0,
//		urlObject.attr("host").indexOf(':'));

var host = urlObject.attr("host");

//var port = urlObject.attr("port");

var apiName = '/mobile/getNumCoupon.do';

var index;

$(document).ready(function() {
		getCouponData();
		
		$('#coupon-btn').click(function(){
			
			var usePath='/mobile/numCouponCount.do';
			
			var url= protocol + '//' + host + '/' + usePath+'?num='+index;
			
			jQuery.post(url);
		});
});

function getCouponData(){
	var imagePath= 'tcpFile';
	
	var reqURL = protocol + '//' + host  + '/' + apiName+param;

	jQuery.post(reqURL).done(
			function(data) {
				console.log(data);
				
				var strArray=data.image.split('_');
				var codeArray=data.codePath.split('-');
				
				var array=param.split('&');
				
				index=data.codeIndex;
				
				$('.coupon-header').attr('href', 'mobile-coupon-list.html'+array[0]+'&'+array[1]);
				
				$('.coupon-title').text(data.name);
				$('#coupon1').val(codeArray[0]);
				$('#coupon2').val(codeArray[1]);
				$('#coupon3').val(codeArray[2]);
				$('#coupon4').val(codeArray[3]);
				$('.date').text(data.startDate + '-' + data.endDate);
				
	});
}
