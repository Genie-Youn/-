/**
 *  이 스크립트의 역할은 단 한가지.
 *  서버로부터 통신하여, 데이터를 가져오고  form의 action 필드를 변경한다.
 *  
 *  파라미터의 check 분기법.
 *  0 : notice for create
 *  1 : notice for update
 *  2 : event for create
 *  3 : event for update
 *  4 : coupon for create -- not yet used
 *  5 : coupon for update -- not yet used
 *  
 */

$(document).ready(function(){
	
	cancelIndex = 1;
	
	$('#using_alarm select').on('change', function(){
		console.log('hi');
		disableBlock($(this));
	});
	
	$('#undo-btn').on('click',function(){
		console.log('is excute?');
	})
	setInit();
	enrollDatePicker();
	
	var parlist = location.search;
	parlist = parlist.substring(1);
	
	var parArr = parlist.split('&');
	
	var check = parArr[0].split('=')[1];
	console.log(check);
	
	var formTag = $('form[name="formsEditor"]');
	
	switch(Number(check)){
		default:
			break;
		case 0:		//notice
			formTag.attr('action', '/calendar/calendarInsert.do');
			$('#hidden_input_type').val(0);
			$('#editor').attr('name', 'content');
			console.log('pass here 0');
			break;
		case 1:      //notice
			formTag.attr('action','/calendar/updateCalendarInfo.do')
			$('#hidden_input_type').val(0);
			$('#editor').attr('name', 'content');
			
			console.log('pass here 1');
			break;
		case 2:
			formTag.attr('action', '/calendar/calendarInsert.do');
			$('#hidden_input_type').val(1);
			$('#editor').attr('name', 'contents');
			console.log('pass here 2');
			break;
		case 3:
			formTag.attr('action','/calendar/updateCalendarInfo.do')
			$('#hidden_input_type').val(1);
			$('#editor').attr('name', 'contents');
			console.log('pass here 3');
			break;
	}
	
	if(parlist.match("start=") != null){
		var startType = parlist.substring(parlist.lastIndexOf('&start=')+7);
		console.log(startType);
		$('.from-date-input').val(startType);
		$('.to-date-input').val(startType); // but actually it is endDate;
	}else{
		$('.from-date-input').val('');
		$('.to-date-input').val(''); // but actually it is endDate;
	}
	
	console.log('do this accepted?');
	
	if(check == 1 || check ==3 ){
		var nIndex = parArr[1].split('=')[1];
		
		$.ajax({
				url : '/calendar/getCalendarInfo.do',
				type :'POST',
				dataType : 'JSON',
				cache:false,
				data : {
					tcp : "",
					calID : nIndex 
				},
				success : function(data){
					dataFromServer = data;
					
					dataFromServer.startDate = dataFromServer.startDate.split(/\s/)[0];
					dataFromServer.endDate = dataFromServer.endDate.split(/\s/)[0];
					
					CKEDITOR.instances.editor1.setData(dataFromServer.contents);
					
					$('.title_input').val(dataFromServer.title);
					$('input[name="calID"]').val(data.nIndex);
					$('input[name="startDate"]').val(dataFromServer.startDate);
					$('input[name="endDate"]').val(dataFromServer.endDate);
					$('select[name="popupEnable"] option').eq(dataFromServer.popupEnable).attr('selected', 'selected');
					$('select[name="alarmEnable"] option').eq(dataFromServer.alarmEnable).attr('selected', 'selected');
					$('select[name="alarmMethod"] option').eq(dataFromServer.alarmMethod).attr('selected', 'selected');
					$('select[name="alarmPeriod"] option').eq(dataFromServer.alarmPeriod).attr('selected', 'selected');
					$('select[name="eventType"] option').eq(dataFromServer.alarmPeriod).attr('selected', 'selected');
					
					setEventType(check , data.nIndex);
					disableBlock($('#using_alarm select'));
				},
				error : function(msg){
					console.log(msg);
				}
			}
		);
		console.log("pass here");
	}
});


function enrollDatePicker(){
	$('.from-date-input').datepicker({
//		  minDate:"+0m +0w",
		  monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)',
			           '7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
			           monthNamesShort: ['1월','2월','3월','4월','5월','6월',
			           '7월','8월','9월','10월','11월','12월'],
			           dayNames: ['일','월','화','수','목','금','토'],
			           dayNamesShort: ['일','월','화','수','목','금','토'],
			           dayNamesMin: ['일','월','화','수','목','금','토'],
			           yearRange: 'c-99:c+99',
			           changeMonth: true,
			           changeYear: true
	  });
	  $('.to-date-input').datepicker({
//		  minDate:"+0m +0w",
		  monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)',
			           '7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
			           monthNamesShort: ['1월','2월','3월','4월','5월','6월',
			           '7월','8월','9월','10월','11월','12월'],
			           dayNames: ['일','월','화','수','목','금','토'],
			           dayNamesShort: ['일','월','화','수','목','금','토'],
			           dayNamesMin: ['일','월','화','수','목','금','토'],
			           yearRange: 'c-99:c+99',
			           changeMonth: true,
			           changeYear: true
	  });
	  
	  $('.from-date-input').datepicker("option", "onClose", function ( selectedDate ) {
		  if(selectedDate)
	        $(".to-date-input").datepicker( "option", "minDate", selectedDate );
	    });
	  $('.to-date-input').datepicker("option", "onClose", function ( selectedDate ) {
		  if(selectedDate)
	        $(".from-date-input").datepicker( "option", "maxDate", selectedDate );
	    });
	    
	    //prevent default function from anything.
	    $('.date-span-btn').each(function(i){
	    	$(this).on('click', function(){
	    		arrayDateSelectFunc[i]();
	    		return false;
	    	});
	    });
}

function disableBlock(selectTag){
	var isClick = selectTag.val();
	
	console.log(isClick);

	if(isClick == 1){
		$('select[name="alarmMethod"] ').attr("disabled","disabled");
		$('select[name="alarmPeriod"] ').attr("disabled","disabled");
	}
	else{
		$('select[name="alarmMethod"] ').removeAttr("disabled");
		$('select[name="alarmPeriod"] ').removeAttr("disabled");
	}
}

function setInit(){
	$('.title_input').on('change', setChangedVaraibleForSubmit);
	$('input[name="calID"]').on('change', setChangedVaraibleForSubmit);
	$('input[name="startDate"]').on('change', setChangedVaraibleForSubmit);
	$('input[name="endDate"]').on('change', setChangedVaraibleForSubmit);
	$('select').on('change', setChangedVaraibleForSubmit);
}

/**
 * 컨텐츠 내용의 변경을 확인하여 화면 전환시 변경 안했다는 출력 메시지를 없애고자 한다.
 */
function setChangedVaraibleForSubmit(){
	isCkeditorContentChanged = true;
}

/**
 * Event 타입을 설정하도록 한다.
*/
function setEventType(check , nIndex){
	if(check ==3 ){
		console.log("calID : " + nIndex);
		
		$.ajax({
				url : '/calendar/getEventInfo.do',
				type :'POST',
				dataType : 'JSON',
				cache:false,
				data : {
					tcp : "",
					calID : nIndex
				},
				success : function(data){
					dataFromServer=data;
					
					console.log(dataFromServer);
					
					$('select[name="eventType"] option').eq(dataFromServer.optionIndex).attr('selected', 'selected');
					
					disableBlock($('#using_alarm select'));
				},
				error : function(msg){
					console.log(msg);
				}
			}
		);
		console.log("pass here");
	}
}