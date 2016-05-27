jQuery(window).load(function(){

	var urlObject = jQuery(location);
	// var url = urlObject.attr("href");
	var protocol = urlObject.attr("protocol");
//	var host = urlObject.attr("host").substring(0,
//			urlObject.attr("host").indexOf(':'));
	
	var host = urlObject.attr("host");
	
	var apiName = 'getNoticeList.do';

	var param = location.search;

//	var port = urlObject.attr("port");
	var reqURL = protocol + '//' + host  + '/mobile/' + apiName+param;

	var array=param.split('&');
	
	
	jQuery.post(reqURL).done(
			function(data) {
				$.each(data, function(i, item) {
					$('#notice-list').append('<li class="ui-li-has-thumb"><a href="mobile-notice.html?'
							+'num='+item.nIndex+'&'+array[1]+'&'+'type='+item.type+'"'+'class="ui-btn ui-btn-icon-right ui-icon-carat-r">' 
							+ item.title +'</a></li>');
				});
			});
});