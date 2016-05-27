var param =  location.search;

var urlObject = jQuery(location);
var protocol = urlObject.attr("protocol");
//var host = urlObject.attr("host").substring(0,
//		urlObject.attr("host").indexOf(':'));

var host = urlObject.attr("host");

//var port = urlObject.attr("port");

var apiName = '/mobile/getImgCoupon.do';

var index;

$(document).ready(function() {
		getCouponData();
		
		$('#coupon-btn').click(function(){
			
			var usePath='/mobile/imgCouponCount.do';
			
			var url= protocol + '//' + host + '/' + usePath+'?num='+index;
			console.log(url);
			jQuery.post(url);
		});
});

function getCouponData(){
	var imagePath= 'tcpFile';
	
	var reqURL = protocol + '//' + host + '/' + apiName+param;

	jQuery.post(reqURL).done(
			function(data) {
			
				console.log(data);
				
				var array=param.split('&');
				
				var strArray=data.image.split('_');
				
				index=data.codeIndex;
				
				$('.coupon-header').attr('href', 'mobile-coupon-list.html'+array[0]+'&'+array[1]);
				
				$('.coupon-title').text(data.name);
				$('.coupon-img').attr('src' , ''+ protocol + '//' + host
						+ '/' + imagePath + '/' + 'res_' + strArray[0] +'/coupons/' + data.image+'');
				$('.date').text(data.startDate + '-' + data.endDate);
				
				$('.coupon-img').css('width' , '100%');
			});
};

