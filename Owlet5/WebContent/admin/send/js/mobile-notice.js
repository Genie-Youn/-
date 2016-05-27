var urlObject = jQuery(location);
var protocol = urlObject.attr("protocol");
//var host = urlObject.attr("host").substring(0,
//		urlObject.attr("host").indexOf(':'));

var host = urlObject.attr("host");

//var port = urlObject.attr("port");

var apiName = 'mobile/getAlarmDataInfo.do';

$(document).ready(function() {
	getNoticeData();
});

function getNoticeData(){
	var param =  location.search;
	
	var reqURL = protocol + '//' + host + '/' + apiName + param;

	console.log(reqURL);
	
	jQuery.post(reqURL).done(
			function(data) {
				var allHtml=data.context;
				
				var array=param.split('&');
				
				$('.notice-header').attr('href', 'mobile-list.html'+array[0]+'&'+array[1]);
				
				$('.frame-title').text(data.name);

				$('.frame-content').html(allHtml);

				$('.pic-slot-box-wrapper').css('width' , '100%');
			});
};

