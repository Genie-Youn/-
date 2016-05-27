jQuery(window).load(function(){

	var urlObject = jQuery(location);
	// var url = urlObject.attr("href");
	var protocol = urlObject.attr("protocol");
//	var host = urlObject.attr("host").substring(0,
//			urlObject.attr("host").indexOf(':'));
	
	var host = urlObject.attr("host");
	
	var apiName = 'groupInfo.do';

	var param = location.search;
	
	console.log(param);

	var port = urlObject.attr("port");
	var reqURL = protocol + '//' + host  + '/mobile/' + apiName+param;

	var array=param.split('&');
	
	
	jQuery.post(reqURL).done(
			function(data) {
				$.each(data, function(i, item) {
					var allHtml=item.content;
					
					var img=$(allHtml).find('img').attr('src');
					
					var group=array[0].substr(1);
					
					$('#list-name').text(item.groupName);
					
					$('#notice-list').append('<li class="ui-li-has-thumb">'
							+ '<img src="'+ protocol + '//' + host + img +'"' +'style="width:80px; height:80px;"' +'/>'
							+ '<a href="img-frame.html?'
							+'num='+item.nIndex+'&'+array[1]+'&'+group+'"'+'class="ui-btn ui-btn-icon-right ui-icon-carat-r">' 
							+ item.title + '&nbsp;&nbsp;&nbsp;&nbsp;'+'</a>' +'</li>');
				});
			});
});