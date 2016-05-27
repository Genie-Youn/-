var appIndex;
var code;

jQuery(function() {
	
	console.log("Ready");
	
	var urlObject = jQuery(location);
	var protocol = urlObject.attr("protocol");
	var host = urlObject.attr("host");	
	var apiName = 'mobile/ConfirmInfo.do';
	var reqURL = protocol + '//' + host + '/' + apiName;
	
	var urlValue;

	console.log(reqURL);
	jQuery.post(reqURL).done(
			function(data) {
				console.log(data);
				
				appIndex=data.appIndex;
				code=data.storeCode;
				
				$('#confirm-frame').attr('src' , protocol+'//'+host+'/admin/mobile/mobile-index.html'
						+'?num='+appIndex+'&code='+code);
				
				$('.code-img').attr('src' , 'http://chart.apis.google.com/chart?cht=qr&chs=150x150&chl='
						+ protocol+'//'+host+'/admin/mobile/mobile-index.html'
						+'?code='+code);
				
//				urlValue = protocol+'//'+host+'/admin/mobile/mobile-index.html'
//				+'?num='+appIndex+'&code='+code;
				
				urlValue = 'http://chart.apis.google.com/chart?cht%3Dqr%26chs%3D150x150%26chl%3D'
					+ protocol+'//'+host+'/admin/mobile/mobile-index.html'
					+'?code='+code;
				
//				console.log($('code-img'));
				
//				console.log('UrlValue : ' + urlValue);
			});
	
	$('#sns-twitter').on('click', function(){(window.open('http://twitter.com/home?status='+urlValue ,'test','width=900,height=800,toolbar=0,scrollbars=0,resizable=0' )).focus();});
	
	$('#sns-facebook').on('click', function(){(window.open('http://www.facebook.com/share.php?u='+urlValue,'test','width=900,height=800,toolbar=0,scrollbars=0,resizable=0' )).focus();});
	
});
