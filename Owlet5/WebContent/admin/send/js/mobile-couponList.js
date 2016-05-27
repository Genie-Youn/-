jQuery(window).load(function(){

	var urlObject = jQuery(location);
	// var url = urlObject.attr("href");
	var protocol = urlObject.attr("protocol");
//	var host = urlObject.attr("host").substring(0,
//			urlObject.attr("host").indexOf(':'));
	
	var host = urlObject.attr("host");
	
	var apiName = 'getCouponList.do';

	var param = location.search;

//	var port = urlObject.attr("port");
	var reqURL = protocol + '//' + host + '/mobile/' + apiName+param;

	
	jQuery.post(reqURL).done(
			function(data) {
				$.each(data, function(i, item) {
					if(item.type==0){
						$('#coupon-list').append('<li class="ui-li-has-thumb"><a href="mobile-img-coupon.html?'
								+'num='+item.genIndex+'&code='+item.storeCode+'"'+'class="ui-btn ui-btn-icon-right ui-icon-carat-r">' 
								+ item.name +'</a></li>');
					}else{
						$('#coupon-list').append('<li><a href="mobile-num-coupon.html?'
								+'num='+item.genIndex+'&code='+item.storeCode+'"'+'class="ui-btn ui-btn-icon-right ui-icon-carat-r">' 
								+ item.name +'</a></li>');
					}
				});
			});
});