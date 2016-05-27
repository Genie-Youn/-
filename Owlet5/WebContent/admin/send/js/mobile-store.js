jQuery(window).load(function(){
	
	var urlObject = jQuery(location);
	// var url = urlObject.attr("href");
	var protocol = urlObject.attr("protocol");
//	var host = urlObject.attr("host").substring(0,
//			urlObject.attr("host").indexOf(':'));
	
	var host = urlObject.attr("host");
	
	var apiName = 'storeInfo.do';
	
	var imagePath= 'tcpFile';
	
	var param = location.search;
	
//	var port = urlObject.attr("port");
	var reqURL = protocol + '//' + host + '/' + apiName+param;
	console.log(reqURL);

	jQuery.post(reqURL).done(
			function(data) {
				// console.log(data);
				var address = "";
				switch (data.address) {
				case 1:
					address = "서울";
					break;
				case 2:
					address = "경기";
					break;
				case 3:
					address = "강원";
					break;
				case 4:
					address = "충청";
					break;
				case 5:
					address = "전라";
					break;
				case 6:
					address = "경상";
					break;
				case 7:
					address = "제주";
					break;
				default:
					alert("유효하지 않는 주소코드입니다");
					break;
				}
				
				var strArray=data.image.split('_');

				jQuery('span.name-head').text(
						data.storeName.substring(0, 1));
				jQuery('span.name-extra').text(
						data.storeName.substring(1, data.storeName.length));
				jQuery('.url').attr('href', data.url);
				jQuery('.url').text(data.url);
				jQuery('.phone').attr('href', 'tel:'+data.phone);
				jQuery('.phone').text(data.phone);
				jQuery('.address').text(address);
				jQuery('.logo').css('background-image' , 'url('+ protocol + '//' + host + 
						'/' + imagePath + '/' + 'res_' + strArray[0] +'/' + data.image +')');
	});
});
