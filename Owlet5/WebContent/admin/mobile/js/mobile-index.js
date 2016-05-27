	

var appInfo_bColor = null;
var appInfo_fColor = null;
var appInfo_image = null;

jQuery(function() {
	// 1차 post요청 : App 정보 받아오기
	// url의 get 파라미터로 요청값 생성하여 던짐
	var urlObject = jQuery(location);
	var protocol = urlObject.attr("protocol");
	var host = urlObject.attr("host");	
	var apiName = 'mobile/getAppInfo.do';
	var param = location.search;
	var reqURL = protocol + '//' + host + '/' + apiName + param;
	
	console.log(reqURL);
	jQuery.post(reqURL).done(
			function(data) {
				console.log(data);
				//헤더, 네비게이션은 fColor
				//아이프레임 문서 배경색은 bColor
				appInfo_fColor = data.fColor;
				appInfo_bColor = data.bColor;
				
				//스플래시 div 배경색을 fColor로 설정
				jQuery('#splash').css('background-color', appInfo_fColor);
				
				//스플래시 이미지 변경
				var imagePath = 'tcpFile';
				var strArray=data.image.split('_');
				var urlObject = jQuery(location);
				var protocol = urlObject.attr("protocol");
				var host = urlObject.attr("host");
				appInfo_splash = protocol + '//' + host + '/' + imagePath + '/' + 'res_' + strArray[0] + '/' + data.image;
				console.log("appInfo_splash : " + appInfo_splash);
				jQuery('#splash').css('background-image' , 'url('+ appInfo_splash +')');
				
				//헤더 설정(글자, 배경색)
				jQuery('#appName').text(data.appName);
				jQuery('div[data-role=header]').css('background-color', appInfo_fColor);
				jQuery('div[data-role=header]').css('text-shadow', 'none');
				
				
				// 2차 post요청 : 메뉴정보 받아오기
				var urlObject = jQuery(location);
				var protocol = urlObject.attr("protocol");
				var host = urlObject.attr("host");	
				var apiName = 'mobile/getMenuList.do';
				var param = location.search;
				var reqURL = protocol + '//' + host + '/' + apiName + param;
				 
				console.log(reqURL);
				jQuery.post(reqURL).done(
					function(data) {
						console.log(data);
						var menuList = jQuery('#menuList');
												
						//네비게이션 메뉴 요소를 실제 받아온 메뉴만큼만 남김
						for (var i = 0; i < (5 - data.length); i++)	{
							console.log
							menuList.children().last().remove();
						}	
						
						//네비게이션 정보 갱신
						for (var i in data)	{
							var split = data[i].path.split('?');
							var data_href = split[0].replace('send', 'mobile');
							
							var data_get = split[1];
							var title = data[i].title ;
							
							console.log(data_get);
							
							var target = menuList.find('a').eq(i);
							target.attr('data-href', data_href);
							target.attr('data-get', data_get);
							console.log(target.attr('data-get'));
							target.text(title);	
						};
						console.log(jQuery('#menuList').html());
						
						//네비게이션 각 메뉴의 너비를 조정하기 위해 관련 요소를 css로 선택함
						var nav_children = jQuery('.ui-grid-d').children();
						//console.log("length : " + length);
						//가로사이즈 조정
						nav_children.each(function(i){
							jQuery(this).css('width', (100/nav_children.length) + "%");
							
						});
						
						
						//아이프레임 초기 src를 첫번째 메뉴로 설정
						jQuery('#innerWindow').attr('src', menuList.find('a').eq(0).attr('data-href'));
						jQuery('#innerWindow').attr('data-get', menuList.find('a').eq(0).attr('data-get'));
					});
				 
	});
	
	
	jQuery('#splash').height(jQuery(window).height());
});

jQuery(window).load(
		function() {
						
			/* 네비게이션 설정 */
						
			var navbarWidth = jQuery(window).width();

			jQuery('div[data-role=navbar]').css({
				position : 'absolute',
				width : navbarWidth,
				bottom : '0%'
			});

			var fontSize = 0;
			var nav_num = jQuery('div[data-role=navbar] ul li').length;

			switch (nav_num) {
			case 1:
				fontSize = 5;
				break;
			case 2:
				fontSize = 5;
				break;
			case 3:
				fontSize = 5;
				break;
			case 4:
				fontSize = 3.5;
				break;
			case 5:
				fontSize = 3;
				break;
			default:
				fontSize = 3;
				break;
			}

			jQuery('div[data-role=navbar] a[data-href]').each(function() {
				jQuery(this).css({
					'font-size' : fontSize + 'vw',
					'font-weight' : 'normal',
					'padding-left' : 0,
					'padding-right' : 0
				});
			});
			/** *********************************** 네비게이션 설정 끝 */
			
			
			
			/* 아이프레임 설정(반드시 네비게이션 설정부 밑으로 넣을 것) */
			
			var a = jQuery(window).height();
			var b = jQuery('div[data-role=header]').height();
			var c = parseInt(jQuery('div[data-role=navbar] ul li a').css('padding-bottom'));
			// console.log('a : ' + a);
			// console.log('b : ' + b);
			// console.log('c : ' + c);
			var innerWindowHeight = (a - b - c);
			
			jQuery('#innerWindow').css({
				position : 'relative',
				overflow : 'hidden',
				left : 0,
				top : 0,
				height : innerWindowHeight,
				width : '100%'
			});
			
			/** *********************************** 아이프레임 설정 끝 */
			
			/* 모든 로딩이 끝나면, 스플래시 div 닫기 */
			setTimeout(function(){
				
				jQuery('#splash').slideToggle("slow");
			}, 3000);
			
			
			
		});

// 네비게이션 버튼 클릭시 아이프레임 내용 변함, 이때 동적 초기화 위한 get 파라미터도 같이 변경
jQuery(document).on('click', 'div[data-role=navbar] a[data-href]', function() {
	jQuery('#innerWindow').attr('src', jQuery(this).attr('data-href'));
	jQuery('#innerWindow').attr('data-get', jQuery(this).attr('data-get'));
	//console.log(jQuery(this).attr('data-get'));
	//console.log(jQuery('#innerWindow').attr('data-get'));
});

//function changeBGColor()	{
//	var contents = jQuery('#innerWindow').contents();
//	contents.find('body').css('background-color', appInfo_bColor);
//}

// mobile-store.html 페이지 동적 초기화 함수
function InitMobileStore() {
	var contents = jQuery('#innerWindow').contents();
	
//	changeBGColor();

	var urlObject = jQuery(location);
	// var url = urlObject.attr("href");
	var protocol = urlObject.attr("protocol");
// var host = urlObject.attr("host").substring(0,
// urlObject.attr("host").indexOf(':'));
	
	var host = urlObject.attr("host");
	
	var apiName = 'storeInfo.do';
	
	var imagePath= 'tcpFile';
	
	// var param = location.search;
	var param = jQuery('#innerWindow').attr('data-get');
	
// var port = urlObject.attr("port");
	var reqURL = protocol + '//' + host + '/' + apiName + '?' + param;
	

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

				contents.find('span.name-head').text(
						data.storeName.substring(0, 1));
				contents.find('span.name-extra').text(
						data.storeName.substring(1, data.storeName.length));
				contents.find('.url').attr('href', data.url);
				contents.find('.url').text(data.url);
				contents.find('.phone').attr('href', 'tel:'+data.phone);
				contents.find('.phone').text(data.phone);
				contents.find('.address').text(address);
				contents.find('.logo').css('background-image' , 'url('+ protocol + '//' + host + 
						'/' + imagePath + '/' + 'res_' + strArray[0] +'/' + data.image +')');
	});
}

// mobile-coupon-list.html 페이지 동적 초기화 함수
function InitMobileCouponList() {
	var contents = jQuery('#innerWindow').contents();
//	changeBGColor();
	
	var urlObject = jQuery(location);
	// var url = urlObject.attr("href");
	var protocol = urlObject.attr("protocol");
// var host = urlObject.attr("host").substring(0,
// urlObject.attr("host").indexOf(':'));
	
	var host = urlObject.attr("host");
	
	var apiName = 'getCouponList.do';
	
	// var param = location.search;
	var param = jQuery('#innerWindow').attr('data-get');
	
// var port = urlObject.attr("port");
	var reqURL = protocol + '//' + host + '/mobile/' + apiName + '?' + param;
	// console.log(reqURL);
	
	jQuery.post(reqURL).done(
			function(data) {
				// console.log(data);
				jQuery.each(data, function(i, item) {
					if(item.type==0){
						contents.find('#coupon-list').append('<li><a href="#" data-href="mobile-img-coupon.html" '
								+ 'data-get="' +'num='+item.genIndex+'&code='+item.storeCode+'" '+'class="ui-btn ui-btn-icon-right ui-icon-carat-r">' 
								+ item.name +'</a></li>');
					}else{
						contents.find('#coupon-list').append('<li><a href="#" data-href="mobile-num-coupon.html" '
								+ 'data-get="' +'num='+item.genIndex+'&code='+item.storeCode+'"'+'class="ui-btn ui-btn-icon-right ui-icon-carat-r">' 
								+ item.name +'</a></li>');
					}
					
					contents.find('#coupon-list').find('a[data-href]').click(function() {
						jQuery('#innerWindow').attr('src', jQuery(this).attr('data-href'));
						jQuery('#innerWindow').attr('data-get', jQuery(this).attr('data-get'));
					});
				});
			});
}


// mobile-img-coupon.html 페이지 동적 초기화 함수
function InitMobileImgCoupon() {
	var contents = jQuery('#innerWindow').contents();
//	changeBGColor();
	
	var urlObject = jQuery(location);
	var protocol = urlObject.attr("protocol");
	// var host = urlObject.attr("host").substring(0,
// urlObject.attr("host").indexOf(':'));

	var host = urlObject.attr("host");

	var apiName = 'getImgCoupon.do';

	var index;
	
	var param = jQuery('#innerWindow').attr('data-get');
	
	var port = urlObject.attr("port");
	var reqURL = protocol + '//' + host + '/mobile/' + apiName + '?' + param;
	
	var imagePath= 'tcpFile';
	
	jQuery.post(reqURL).done(
			function(data) {
				// console.log(data);
				
				var array=param.split('&');
				
				var strArray=data.image.split('_');
				
				index=data.codeIndex;
				
				contents.find('.coupon-header').attr('data-href', 'mobile-coupon-list.html');
				contents.find('.coupon-header').attr('data-get', array[0]+'&'+array[1]);
				contents.find('.coupon-header').click(function() {
					jQuery('#innerWindow').attr('src', jQuery(this).attr('data-href'));
					jQuery('#innerWindow').attr('data-get', jQuery(this).attr('data-get'));
				});
				
				
				contents.find('.coupon-title').text(data.name);
				contents.find('.coupon-img').attr('src' , ''+ protocol + '//' + host
						+ '/' + imagePath + '/' + 'res_' + strArray[0] +'/coupons/' + data.image+'');
				contents.find('.date').text(data.startDate + '-' + data.endDate);
				
				contents.find('.coupon-img').css('width' , '100%');
				
				contents.find('#coupon-btn').click(function(){					
					if (confirm("쿠폰을 사용하시겠습니까?") == true)	{
						const SUCCESS = 1;
						var usePath='imgCouponCount.do';
						
						var url= protocol + '//' + host + '/mobile/' + usePath+'?num='+index;
						console.log(url);
						jQuery.post(url).done(function(data){
							if(data == SUCCESS)	{
								alert("쿠폰을 사용하였습니다");
							}
						}).fail(function(){
							alert("쿠폰을 사용하지 못했습니다");
						}).always(function(){
							contents.find('.coupon-header').trigger('click');
						});
					};					
				});
			});
	
}

// mobile-num-coupon.html 페이지 동적 초기화 함수
function InitMobileNumCoupon() {
	var contents = jQuery('#innerWindow').contents();
//	changeBGColor();
	
	var urlObject = jQuery(location);
	var protocol = urlObject.attr("protocol");
	// var host = urlObject.attr("host").substring(0,
// urlObject.attr("host").indexOf(':'));

	var host = urlObject.attr("host");

	var apiName = 'getNumCoupon.do';

	var index;
	
	var param = jQuery('#innerWindow').attr('data-get');
	
	var port = urlObject.attr("port");
	var reqURL = protocol + '//' + host + '/mobile/' + apiName + '?' + param;
	
	var imagePath= 'tcpFile';
	
	jQuery.post(reqURL).done(
			function(data) {
			
				// console.log(data);
				
				var strArray=data.image.split('_');
				var codeArray=data.codePath.split('-');
				
				var array=param.split('&');
				
				index=data.codeIndex;
				
				contents.find('.coupon-header').attr('data-href', 'mobile-coupon-list.html');
				contents.find('.coupon-header').attr('data-get', array[0]+'&'+array[1]);
				contents.find('.coupon-header').click(function() {
					jQuery('#innerWindow').attr('src', jQuery(this).attr('data-href'));
					jQuery('#innerWindow').attr('data-get', jQuery(this).attr('data-get'));
				});
				
				contents.find('.coupon-title').text(data.name);
				contents.find('#coupon1').val(codeArray[0]);
				contents.find('#coupon2').val(codeArray[1]);
				contents.find('#coupon3').val(codeArray[2]);
				contents.find('#coupon4').val(codeArray[3]);
				contents.find('.date').text(data.startDate + '-' + data.endDate);
				
				contents.find('#coupon-btn').click(function(){					
					if (confirm("쿠폰을 사용하시겠습니까?") == true)	{
						const SUCCESS = 1;
						var usePath='numCouponCount.do';
						
						var url= protocol + '//' + host + '/mobile/' + usePath+'?num='+index;
						console.log(url);
						jQuery.post(url).done(function(data){
							if(data == SUCCESS)	{
								alert("쿠폰을 사용하였습니다");
							}
						}).fail(function(){
							alert("쿠폰을 사용하지 못했습니다");
						}).always(function(){
							contents.find('.coupon-header').trigger('click');
						});
					};					
				});
			});
	
}


// mobile-list.html 페이지 동적 초기화 함수
function InitMobileList() {
	
	var contents = jQuery('#innerWindow').contents();
//	changeBGColor();
	
	var urlObject = jQuery(location);
	// var url = urlObject.attr("href");
	var protocol = urlObject.attr("protocol");
// var host = urlObject.attr("host").substring(0,
// urlObject.attr("host").indexOf(':'));
	
	var host = urlObject.attr("host");
	
	var apiName = 'getNoticeList.do';

	var param = jQuery('#innerWindow').attr('data-get');

// var port = urlObject.attr("port");
	var reqURL = protocol + '//' + host + '/mobile/' + apiName + '?' + param;

	var array=param.split('&');
	
	
	jQuery.post(reqURL).done(
			function(data) {
				jQuery.each(data, function(i, item) {
					contents.find('#notice-list').append('<li><a href="#" data-href="mobile-notice.html" '
							+ 'data-get="' +'num='+item.nIndex+'&'+array[1]+'&'+'type='+item.type+'"'+'class="ui-btn ui-btn-icon-right ui-icon-carat-r">' 
							+ item.title +'</a></li>');
				});
				contents.find('#notice-list').find('a[data-href]').click(function() {
					jQuery('#innerWindow').attr('src', jQuery(this).attr('data-href'));
					jQuery('#innerWindow').attr('data-get', jQuery(this).attr('data-get'));
				});
			});	
}

// mobile-notice.html 페이지 동적 초기화 함수
function InitMobileNotice() {
	
	var contents = jQuery('#innerWindow').contents();
//	changeBGColor();
	
	var urlObject = jQuery(location);
	var protocol = urlObject.attr("protocol");
	// var host = urlObject.attr("host").substring(0,
// urlObject.attr("host").indexOf(':'));

	var host = urlObject.attr("host");

	// var port = urlObject.attr("port");

	var apiName = 'getAlarmDataInfo.do';
	
	var param = jQuery('#innerWindow').attr('data-get');
	
	var reqURL = protocol + '//' + host + '/mobile/' + apiName + '?' + param;

	console.log(reqURL);
	
	jQuery.post(reqURL).done(
			function(data) {
				console.log(data);
				
				var allHtml=data.context;
				
				var array=param.split('&');
				
				contents.find('.notice-header').attr('data-href', 'mobile-list.html');
				contents.find('.notice-header').attr('data-get', array[0]+'&'+array[1]);
				contents.find('.notice-header').click(function() {
					jQuery('#innerWindow').attr('src', jQuery(this).attr('data-href'));
					jQuery('#innerWindow').attr('data-get', jQuery(this).attr('data-get'));
				});
				
				contents.find('.frame-title').text(data.name);
				contents.find('.frame-content').html(allHtml);
				contents.find('.pic-slot-box-wrapper').css('width' , '100%');
			});
};


function InitGroupListFrame() {
	var contents = jQuery('#innerWindow').contents();
//	mobile-img-list.html 페이지 동적 초기화 함수
	var urlObject = jQuery(location);
//	var url = urlObject.attr("href");
	var protocol = urlObject.attr("protocol");
//	var host = urlObject.attr("host").substring(0,
//	urlObject.attr("host").indexOf(':'));

	var host = urlObject.attr("host");

	var apiName = 'groupInfo.do';

	var param = jQuery('#innerWindow').attr('data-get');

	var port = urlObject.attr("port");
	var reqURL = protocol + '//' + host  + '/mobile/' + apiName+'?'+param;

	var array=param.split('&');


	jQuery.post(reqURL).done(
			function(data) {
				$.each(data, function(i, item) {
					var allHtml=item.content;

					var img=$(allHtml).find('img').attr('src');

					contents.find('#list-name').text(item.groupName);

					contents.find('#notice-list').append('<li class="ui-li-has-thumb">'
							+ '<img src="'+ protocol + '//' + host + img +'"' +'style="width:80px; height:80px;"' +'/>'
							+ '<a href="#" data-href="img-frame.html" '
							+ 'data-get="'
							+'num='+item.nIndex+'&'+array[1]+'&'+array[2]+'"'+'class="ui-btn ui-btn-icon-right ui-icon-carat-r">' 
							+ item.title + '&nbsp;&nbsp;&nbsp;&nbsp;'+'</a>' +'</li>');
				});
				
				contents.find('#notice-list').find('a[data-href]').click(function() {
					jQuery('#innerWindow').attr('src', jQuery(this).attr('data-href'));
					jQuery('#innerWindow').attr('data-get', jQuery(this).attr('data-get'));
				});
			});
};

//mobile-img-frame.html 페이지 동적 초기화 함수
function InitImgMobileFrame(){
	var contents = jQuery('#innerWindow').contents();
//	changeBGColor();

	var urlObject = jQuery(location);
	var protocol = urlObject.attr("protocol");
	// var host = urlObject.attr("host").substring(0,
//	urlObject.attr("host").indexOf(':'));

	var host = urlObject.attr("host");

	// var port = urlObject.attr("port");

	var apiName = 'getImgFrames.do';

	var param = jQuery('#innerWindow').attr('data-get');
	
	console.log(param);

	var reqURL = protocol + '//' + host + '/mobile/' + apiName + '?' + param;

	console.log(reqURL);

	jQuery.post(reqURL).done(
			function(data) {
				console.log(data);
				
				var allHtml=data.content;

				// console.log(allHtml);
				var currentWidth = jQuery(window).width();
				
				var array=param.split('&');
				
				contents.find('.notice-header').attr('data-href', 'mobile-img-list.html');
				contents.find('.notice-header').attr('data-get', array[0]+'&'+array[1]+'&'+array[2]);
				contents.find('.notice-header').click(function() {
					jQuery('#innerWindow').attr('src', jQuery(this).attr('data-href'));
					jQuery('#innerWindow').attr('data-get', jQuery(this).attr('data-get'));
				});

				if(allHtml.substring(allHtml.lastIndexOf('{"lat"'), allHtml.lastIndexOf(');</script>'))){
					mapJson = JSON.parse(allHtml.substring(allHtml.lastIndexOf('{"lat"'), allHtml.lastIndexOf(');</script>')));
					// console.log(mapJson);
					allHtml = jQuery.parseHTML(allHtml);

					// console.log(allHtml);

					jQuery(allHtml).find('div[id^="doksoft_maps"]');

					jQuery(allHtml).find('span:not([style^="font-family"])').css('font-family', 'inherit');

					contents.find('.frame-content').html(allHtml);

					_loadmap("doksoft_maps2",mapJson);

					jQuery('#doksoft_maps3').css('width', '100%');
				}else{
					jQuery('.frame-content').html(allHtml);

					contents.find('.pic-slot-box-wrapper').css('width' , currentWidth);
				}

				contents.find('.frame-content').html(allHtml);

				contents.find('.pic-slot-box-wrapper').each(function()	{
					var imgWidth = this.clientWidth;
					var imgHeight = this.clientHeight;
					var ratio = 1;

					if(imgWidth > currentWidth)	{
						ratio = (currentWidth) / imgWidth;
						jQuery(this).width(imgWidth * ratio);
						jQuery(this).height(imgHeight * ratio);
					};
					console.log(jQuery(this).width());
				}); 

				contents.find('p.frame-title').text(data.title);
			});
}


// mobile-frame.html 페이지 동적 초기화 함수
function InitMobileFrame() {
	
	var contents = jQuery('#innerWindow').contents();
//	changeBGColor();
	
	var urlObject = jQuery(location);
	var protocol = urlObject.attr("protocol");
	// var host = urlObject.attr("host").substring(0,
// urlObject.attr("host").indexOf(':'));

	var host = urlObject.attr("host");

	// var port = urlObject.attr("port");

	var apiName = 'getFrames.do';
	
	var param = jQuery('#innerWindow').attr('data-get');
	console.log(param);
	
	var reqURL = protocol + '//' + host + '/mobile/' + apiName + '?' + param;

	console.log(reqURL);
	
	jQuery.post(reqURL).done(
			function(data) {
				 var allHtml=data.content;
				 
				// console.log(allHtml);
				 var currentWidth = jQuery(window).width();
				 
				 if(allHtml.substring(allHtml.lastIndexOf('{"lat"'), allHtml.lastIndexOf(');</script>'))){
					 mapJson = JSON.parse(allHtml.substring(allHtml.lastIndexOf('{"lat"'), allHtml.lastIndexOf(');</script>')));
					 // console.log(mapJson);
					 allHtml = jQuery.parseHTML(allHtml);
					 
					 // console.log(allHtml);

					 jQuery(allHtml).find('div[id^="doksoft_maps"]');

					 jQuery(allHtml).find('span:not([style^="font-family"])').css('font-family', 'inherit');

					 contents.find('.frame-content').html(allHtml);

					 _loadmap("doksoft_maps2",mapJson);
					 
					 jQuery('#doksoft_maps3').css('width', '100%');
				 }else{
					 jQuery('.frame-content').html(allHtml);
					 
					 contents.find('.pic-slot-box-wrapper').css('width' , currentWidth);
				 }
				
				 contents.find('.frame-content').html(allHtml);
				 
				 contents.find('.pic-slot-box-wrapper').each(function()	{
					 var imgWidth = this.clientWidth;
						var imgHeight = this.clientHeight;
						var ratio = 1;
						
						if(imgWidth > currentWidth)	{
							ratio = (currentWidth) / imgWidth;
							jQuery(this).width(imgWidth * ratio);
							jQuery(this).height(imgHeight * ratio);
						};
						console.log(jQuery(this).width());
				 }); 
				 
				contents.find('p.frame-title').text(data.title);
	});
};

_loadmap = function(id, json) {
	var contents = jQuery('#innerWindow').contents();
	
	var canva = contents.find('div[id^="doksoft_maps"]').last()[0];
	
	// console.log(canva);
	
	canva.style.width = (json.width > (jQuery(window).width()))? "100%":json.width + "px";
	
	
	canva.style.height = json.height + "px";
	var map = new google.maps.Map(canva, {
		zoom : parseInt(json.zoom),
		center : new google.maps.LatLng(parseFloat(json.lat),
				parseFloat(json.lng)),
		mapTypeId : json.type
	});
	if (json.settings) {
		for ( var id in json.settings)
			map.set(id, json.settings[id] ? true : false);
	}
	;
	if (json.objects)
		for ( var type in json.objects) {
			for ( var i in json.objects[type]) {
				var object = 0;
				switch (type) {
				case 'Marker':
					object = new google.maps.Marker({
						position : new google.maps.LatLng(
								json.objects[type][i][0],
								json.objects[type][i][1]),
						map : map,
						title : json.objects[type][i][2]
					});
					(function(txt) {
						google.maps.event.addListener(object, 'click',
								function() {
									(new google.maps.InfoWindow({
										content : txt
									})).open(map, object);
								});
					})(json.objects[type][i][2]);
					break;
				case 'Rectangle':
					object = new google.maps.Rectangle({
						bounds : new google.maps.LatLngBounds(
								new google.maps.LatLng(
										json.objects[type][i][0][0],
										json.objects[type][i][0][1]),
								new google.maps.LatLng(
										json.objects[type][i][1][0],
										json.objects[type][i][1][1])),
						map : map,
					});
					break;
				case 'Polygon':
				case 'Polyline':
					var path = json.objects[type][i], array_path = [];
					for ( var j in path)
						array_path.push(new google.maps.LatLng(path[j][0],
								path[j][1]));
					object = new google.maps[type]({
						path : array_path,
						map : map,
					});
					break;
				case 'Text':
					object = new MarkerWithLabel({
						position : new google.maps.LatLng(
								json.objects[type][i][0],
								json.objects[type][i][1]),
						map : map,
						labelContent : json.objects[type][i][2],
						labelAnchor : new google.maps.Point(22, 0),
						labelClass : "labels",
						labelStyle : {
							opacity : 1.0,
							minWidth : '200px',
							textAlign : 'left'
						},
						icon : {}
					});
					break;
				case 'Circle':
					object = new google.maps.Circle({
						radius : json.objects[type][i][2],
						center : new google.maps.LatLng(
								json.objects[type][i][0],
								json.objects[type][i][1]),
						map : map,
					});
					break;
				case 'WeatherLayer':
					object = new google.maps.weather.WeatherLayer(
							{
								temperatureUnits : google.maps.weather.TemperatureUnit.FAHRENHEIT
							});
					object.setMap(map);
					break;
				case 'TrafficLayer':
					object = new google.maps.TrafficLayer();
					object.setMap(map);
					break;
				}
			}
		}
};

loadmap = function(id, json) {
	google.maps.event.addDomListener(window, 'load', function() {
		_loadmap(id, json)
	});
};