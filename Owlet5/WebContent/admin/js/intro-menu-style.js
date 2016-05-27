/**
 * This is written to applying css style dynamically.
 * 2014. 07. 29. 22:05:04:032; TUE. 
 */
(function($) {
$(function(){
	
	//applyLavaLi();
	
//	$(document).tooltip();
});

//애벌레 메뉴 구현
function applyLavaLi(){
	var mis = {};
	mis.nav = $('#nav_frame');
	mis.current_item = mis.nav.find('.focus');
	mis.lava = $('<li class="lava"/>');
	
	mis.nav.css('position', 'relative')
		.find('a').css({
			position:"relative",
			zIndex:100
		});
	
	var reset;
	
	
	//스타일 적용.
	mis.lava.css({
		position:"absolute",
		top 	:mis.current_item.position().top,
		left	:mis.current_item.position().left,
		width: mis.current_item.outerWidth(),
		height: mis.current_item.outerHeight(),
		backgroundColor:'#619031'
	}).appendTo(mis.nav.find('ul'));
	
	
	//이벤트 핸들러 구현[이동하는 것]
	mis.nav.find('li')
	.bind('mouseover focusin', function(){
		clearTimeout(reset);
		mis.lava.animate({
			left	: $(this).position().left,
			width	: $(this).outerWidth()
		},
		{
			duration : 400,
			easing	: 'easeOutQuart',
			queue	: false
		});//easing: 'easeInOutElastic',
	})
	.bind('mouseout focusout', function(){
		reset = setTimeout(function(){
			mis.lava.animate({
				left	: mis.current_item.position().left,
				width	: mis.current_item.outerWidth()
			}, {
				duration : 200
			});
		}, 300);
	})
	.click(function() {
				$(this)
					.siblings().removeClass('focus')
				.end().addClass('focus');
				mis.current_item = $(this);
			});
	
	$('#nav_frame').find('a').click(function(e) {
		e.preventDefault();						
	});
}
})(jQuery);

$(window).ready(function(){
	var loadingIconLocator = $('#loading_icon_locator');
	if(loadingIconLocator){
		loadingIconLocator.css('display', 'none');
	}
	$('#page_wrapper').css('display', 'block');
	
	var linkList = new Array();
	
	var nameList = new Array();
	
	console.log(location.href); 
	linkList.push('/analysis/main.do');
	linkList.push('/analysis/list.do');
	linkList.push('/adder/appMaker.do'); 
	linkList.push('/notice/noticeList.do');
	linkList.push('/event/eventList.do');
	linkList.push('/event/couponList.do');
	
	nameList.push('홈');
	nameList.push('통계분석');
	nameList.push('앱 관리');
	nameList.push('공지사항');
	nameList.push('이벤트');
	nameList.push('쿠폰');
		
	
	$('#nav-menu a').each(function(index){
		var value = nameList[index];
		$(this).text(value);
		if(index < 3){ //공지사항 이벤트 쿠폰 disabled 처리 - 윤지수 2015-11-10
		$(this).on('click', function(){
			location.href = linkList[index];
			return false;
		})};
	});
	
	$('p.logout > button.btn.btn-1').on('click', function(){location.href='/logout.do';});
	$('p.mypage > button.btn.btn-1').on('click', function(){(window.open('/myPage.do','win_mypage','width=900,height=800,toolbar=0,scrollbars=0,resizable=0')).focus();});
	$('p.totalUrl > button.btn.btn-1').on('click', function(){(window.open('/preview/totalUrl.do','win_url','width=950,height=850,toolbar=0,scrollbars=0,resizable=0')).focus();});
//	$('p.cal > button.btn.btn-1').bind('click', function(){(window.open('/calendar/calendar.do','win_mypage','width=900,height=800,toolbar=0,scrollbars=0,resizable=0')).focus();});
	
	$('#main_logo').bind('click', function(){location.href='/analysis/main.do'});
	
	$('p.cal > button.btn.btn-1').bind('click', function(){location.href='/calendar/calendar.do'});
	
	$('p.home > button.btn.btn-1').bind('click', function(){location.href='/analysis/main.do'});
	
	$('.addNotice').bind('click' , function(){location.href='/adder/enrollPage.do?check=0'});
	
	$('.addEvent').bind('click' , function(){location.href='/adder/eventEditPage.do?check=2&start='});
	
	$('.addApp').bind('click' , function(){location.href='/adder/appMaker.do'});
	
	$('.addFrame').bind('click' , function(){location.href='/adder/editor.do'});

	$('.addMenu').bind('click' , function(){location.href='/adder/menu.do'});

	
	var footerString = 'Copyright  2015 <b>SEA Insight</b> All Rights Reserved.<br>'+
					'Owlet5 is a web project of TCP club in Computer Science and Engineering,<br>'+
					 'Seoul National University of Science and Technology.';
	

	$('#main_copyright').append(footerString);
	

});

//Popup Location 설정 구현
function PopupCenterDual(url, title, w, h) {
	// Fixes dual-screen position Most browsers Firefox
	var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : screen.left;
	var dualScreenTop = window.screenTop != undefined ? window.screenTop : screen.top;
	width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
	height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

	var left = ((width / 2) - (w / 2)) + dualScreenLeft;
	var top = ((height / 2) - (h / 2)) + dualScreenTop;
	var newWindow = window.open(url, "Test", 'location=no, resizable=no, scrollbars=no, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);

	// Puts focus on the newWindow
	if (window.focus) {
		newWindow.focus();
	}
}