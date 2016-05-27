function trim(str_var) {
	str = str_var.replace(/(^\s*)|(\s*$)/, " ");
	return str;
}

var eventColor = {
		notice : '#378	006',
		events : '#212222',
		coupon : '#000000'
};

var infoGroup = {
		start: -1,
	    end:-1
};

Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";
 
    var weekName = ["일", "월", "화", "수", "목", "금", "토"];
    var d = this;
     
    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "yy": return (d.getFullYear() % 1000).zf(2);
            case "MM": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mm": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
            default: return $1;
        }
    });
};

String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};

//변수명 변경 요망.
//var flag = false;

$(document).ready(function() {
	
	var newDate = new Date();
	var year = newDate.getFullYear();
	var month = newDate.getMonth()+1;
	var day = newDate.getDate();
	var toDay = year + "-" + month + "-" + day;

	$('#calendar').fullCalendar({
		header : {
			left : 'prev,next today',
			center : 'title',
			right : 'month,agendaWeek,agendaDay'
		},
		defaultDate : newDate,
		selectable : true,
		selectHelper : true,
		select : function(start,end,e,four) {
			console.log('is click');
			var destX = e.pageX;
			var destY = e.pageY;
			$('#content_viewer').removeClass('got-content');
			
			adjustDialogInformation(destX, destY, false, start);
			
//			flag = true;
//			console.log(flag);
		},
		editable : false,
		eventLimit : true, // allow "more" link when too many events
		// ajax 비동기 식으로 받아온다
		events :  {
				url: '/calendar/allCalendarInfo.do',
				type: 'POST',
				data:{
					tcp:""
				},
				 success:function(data){
					 console.log(data);
					 colorPallete = ['orange', 'blue', 'green'];
					 $.each(data,function(index, element){
						 element.start = element.startDate;
						 d = new Date(element.endDate);
						 d.setDate(d.getDate()+1);
						 element.end = d.format('yyyy-MM-dd');
						 element.eventColor= 'yellow';//'#378006';backgroundColor
						 //element.backgroundColor= 'yellow';//'#378006';
						 element.color= colorPallete[element.type]//'#378006';
					 });
					 return data;
				 },
						
				error: function(){
					alert('fail to load data');
					}
				},	
				droppable: false,
				
		//  Server로 요청데이터를 보낸당
		// 이제 ajax를 통해, 서버로 통신하는 코드로 바꿔야 한다 ~_~ 
		eventClick : function(eventContent, e) {
			
			console.log(eventContent, e);
			
			createContentOfViewer(eventContent)
			
			// 팝업창이 나와있다면
				var destX = e.pageX;
				var destY = e.pageY;
				
				adjustDialogInformation(destX, destY, true);
				var nIndex = eventContent.nIndex;
				var type = eventContent.type;
				
				if(type >=2){
					$('#dialog_form .edit-btn').off('click').on('click', function(){
						window.location.assign('/event/coupon.do?index='+nIndex+'&ptype='+eventContent.alarmEnable+'-'+eventContent.popupEnable);
					});
					return;
				}
				$('#dialog_form .edit-btn').off('click').on('click',function(){
					window.location.href = '/adder/enrollPage.do?check='+ (type*2+1) +'&nIndex='+nIndex;});
//				flag = true;
//				console.log(flag);
				},
		eventColor: 'red'
	});
	
	//force delete-btn to be appended into fc-left classified tag.
	$('.fc-left').append($('.fc-delete-button'));
	
		$('body').on(' mousemove', function(e) {
			var calenDiv = $('#calendar');
			
			var left = calenDiv.offset().left;
			var top = calenDiv.offset().top + 40;
			var right = left + parseInt(calenDiv.css('width'));
			var bottom = top + parseInt(calenDiv.css('height'));
			
			var dleft = $('#dialog_form').offset().left;
			var dtop = $('#dialog_form').offset().top;
			var dright = dleft + parseInt($('#dialog_form').css('width'));
			var dbottom = dtop + parseInt($('#dialog_form').css('height'));
			
			console.log(dleft, dtop, dright, dbottom);
			
			var mouseX = e.pageX;
			var mouseY = e.pageY;
			
			var isMouseInRect = true;
			var isMouseInDialog = true;
			
			if(!(mouseX > dleft && mouseX < dright)){
				isMouseInDialog = false;
			}
			if(!( mouseY < dbottom && mouseY > dtop)){
				isMouseInDialog = false;
			}
			
			if(!(mouseX > left && mouseX < right)){
				isMouseInRect = false;
			}
			if(!( mouseY < bottom && mouseY > top)){
				isMouseInRect = false;
			}
			
			if(isMouseInRect || isMouseInDialog){
				//console.log('mouse move', e);
			} else {
				$('#dialog_form').css('display', 'none');
			}
			
//			if(flag==true){
//			$('#dialog_form').css('display', 'none')
//			flag = false;
//			}
		});

	$('.delete-btn').on('click',function(){
		window.location.href = '/calendar/deletePage.do?tcp=';
	});
	
	$('.delete-btn').mouseover(function(){
		$(this).addClass("fc-state-hover");
	});
	$('.delete-btn').mouseout( function() {
        $(this).removeClass('fc-state-hover').removeClass('fc-state-active');
	});

	$('.delete-btn').mousedown( function() {
		$(this).addClass('fc-state-active');
	});

	$('.delete-btn').mouseup(function(){
        $(this).removeClass('fc-state-active');
	});
	
	/**
	 *  다이얼로그 띄우고 설정하기 기능 등록.
	 */
	
	$('#dialog_form').css('display', 'none')
	.css('position', 'absolute');
	

	
});

function adjustDialogInformation(x, y, isEdit, start){
	var dialog = $('#dialog_form');
	
	var startDate = "";
	if(start) {
		var v = new Date(start._d);
			console.log(v.format("MM/dd/yyyy"));
		
		 startDate = '&start='+ v.format("MM/dd/yyyy");
	}
	
	dialog.css('display', 'block')
	.css('left', x)
	.css('top', y);
	
	if(isEdit){ 			//수정하기만 보여준다.
		dialog.find('h3').html('수정여부선택');
		dialog.find('button')
		.removeClass('visible')
		.addClass('unvisible')
		.filter('.edit-btn')
		.removeClass('unvisible')
		.addClass('visible');
		
	} else {					//수정하기 빼고 보여준다.
		dialog.find('h3').html('등록할 요소 선택');
		dialog.find('button')
		.removeClass('unvisible')
		.addClass('visible')
		.filter('.edit-btn')
		.removeClass('visible')
		.addClass('unvisible');
	}
	
	$('#dialog_form .close-btn').on('click', function() {
		$(this).parent().css('display', 'none')});
	
	$('#dialog_form .notice-btn').off('click').on('click', function(){
		window.location.href = '/adder/enrollPage.do?check=0' + startDate;
		console.log('notice clicked');
	});
	$('#dialog_form .event-btn').off('click').on('click', function(){
		window.location.href = '/adder/enrollPage.do?check=2' + startDate;
		console.log('event clicked');
	});
	$('#dialog_form .coupon-btn').off('click').on('click', function(){
		window.location.href = '/event/coupon.do';
		console.log('coupon clicked');
	});
}

function createContentOfViewer(eventObject){
	var viewer = $('#content_viewer').addClass('got-content');
	
	var title = eventObject.title;
	var contents = eventObject.contents;
	var startDate = eventObject.startDate;
	var endDate = eventObject.endDate;
	var aable = eventObject.alarmEnable;
	switch(aable){
		case 0 :
			aable="알람설정, ";
			break;
		case 1 :
			aable="알람미설정, ";
	}
	var amethod = eventObject.alarmMethod;
	switch(amethod){
		case 0:
			amethod="SMS, ";
			break;
		case 1:
			amethod="E-MAIL, ";
			break;
		case 2:
			amethod="SMS&E-MAIL, ";
			break;
	}
	var aperiod = eventObject.alarmPeriod;
	switch(aperiod){
		case 0:
			aperiod = "매시, "
			break;
		case 1:
			aperiod = "매일, ";
			break;
		case 2:
			aperiod = "매주, ";
			break;
	}
	var pable = eventObject.popupEnable;
	switch(pable){
		case 0:
			pable = "팝업제공 ";
			break;
		case 1:
			pable = "팝업비제공 ";
			break;
	}
	
	console.log(title, contents, startDate, endDate, aable, amethod, aperiod, pable);
	
	viewer.find('.title').empty().html(title).prepend('제목 : ');
	viewer.find('.content').empty().html(contents);
	viewer.find('.time-span').empty().html(startDate + " ~ " + endDate).prepend('기간 : ');
	viewer.find('.menu-option').empty().append('설정 : ').append(aable).append(amethod).append(aperiod).append(pable);
}


	
	

