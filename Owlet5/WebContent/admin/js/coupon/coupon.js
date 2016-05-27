function div_OnOff(v, id) {
	// 라디오 버튼 value 값 조건 비교
	if (v == "0") {//image
		document.getElementById("image").style.display = "";
		document.getElementById("number").style.display = "none";
		$('#image > input.textbox').attr('name', 'count');
		$('div.coupon_count > input.textbox').attr('name', 'nocount');
	} else if (v == "1") { //number
		document.getElementById("image").style.display = "none";
		document.getElementById("number").style.display = "";
		$('#image > input.textbox').attr('name', 'nocount');
		$('div.coupon_count > input.textbox').attr('name', 'count');
	}
}

function image_edit() {
	alert("image_edit()");
}

function submit_edit() {
	document.info_form.submit();
}

function cancle_edit() {
	window.close();
}

window.onload = function() {

	if (window.File && window.FileList && window.FileReader) {
		var filesInput = document.getElementById("files");

		filesInput.addEventListener("change", function(event) {

			var files = event.target.files;

			var output = document.getElementById("edit_image");
			var inner = "";

			for (var i = 0; i < files.length; i++) {
				var file = files[i];
				var div = document.createElement("div");

				if (!file.type.match('image')) {
					alert("해당파일은 이미지 파일이 아닙니다");
					location.reload(true);
					break;
				}

				var picReader = new FileReader();

				picReader.addEventListener("load", function(event) {

					var picFile = event.target;

					var prevImg = document.getElementById("thumbnail");
					//이전에 미리보기가 있다면 삭제
					if (prevImg) {
						output.removeChild(prevImg);
					}

					var img = document.createElement("img");
					img.src = picFile.result;
					img.id = "thumbnail";

					output.insertBefore(img, null);

				});

				picReader.readAsDataURL(file);
			}

		});
	} else {
		console.log("Your browser does not support File API");
	}
};

$(window).load(function() {
	var intervalFunc = function() {
		$('#file-name').html($('#file').val());
	};
	$('#browse-click').on('click', function() {// use .live() for older versions of jQuery
		$('#files').click();
		setInterval(intervalFunc, 1);
		return false;
	});
	
	$('input:submit').off('click').on('click', function(e){
		console.log('hi');
		
		if($('#coupon_title').val().length == 0){
			$('.title_warn').removeClass('warning-hide');
			return false;
		} else {
			$('.title_warn').addClass('warning-hide');
		}
		
		if($('div.coupon_select > input:checked').length == 0){
			$('.radio_warn').removeClass('warning-hide');
			return false;
		} else {
			$('.radio_warn').addClass('warning-hide');
		}
		
//		if($('div.coupon_select > input').eq(0).attr('checked') == 'checked'){ //이미지면,,
		if($('div.coupon_select > input:checked').val() == '0'){
			if(!$('#files').val()){
				e.preventDefault();
				return false;
			}
		}
		
		if($('div.coupon_select > input:checked').val() == '1'){ //숫자면,,
			if($('#coupon_count').val().length == 0 || isNaN($('#coupon_count').val())){
				e.preventDefault();
				$('.count_warn').removeClass('warning-hide');
				return false;
			}else {
				$('.count_warn').addClass('warning-hide');
			}
		} 
		
		if($('#coupon_startDate').val().length == 0){
			$('.date_warn').removeClass('warning-hide');
			return false;
		}
		
		if($('#coupon_endDate').val().length == 0){
			$('.date_warn').removeClass('warning-hide');
			return false;
		} else {
			$('.date_warn').addClass('warning-hide');
		}
		
		$('#coupon_startDate').val($('#coupon_startDate').val().replace('/','').replace('/',''));
		$('#coupon_endDate').val($('#coupon_endDate').val().replace('/','').replace('/',''));
		
		$('input[disabled]').removeAttr('disabled');
	});
	
	$('input:reset').off('click').on('click', function(e){
		history.back();
	});
	
	$('#coupon_startDate').datepicker({
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
			           changeYear: true,
			           dateFormat:'yy/mm/dd'
	  });
	
	$('#coupon_startDate').dblclick(function(){
		$('#coupon_startDate').datepicker('hide');
	});
	
	$('#coupon_endDate').datepicker({
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
			           changeYear: true,
			           dateFormat:'yy/mm/dd'
	  });
	
	$('#coupon_endDate').dblclick(function(){
		$('#coupon_endDate').datepicker('hide');
	});
	  
	  $('#coupon_startDate').datepicker("option", "onClose", function ( selectedDate ) {
		  if(selectedDate)
	        $("#coupon_endDate").datepicker( "option", "minDate", selectedDate );
	    });
	  $('#coupon_endDate').datepicker("option", "onClose", function ( selectedDate ) {
		  if(selectedDate)
	        $("#coupon_startDate").datepicker( "option", "maxDate", selectedDate );
	    });
});


//location의  search 를 살펴 있으면 내용을 강제 기입합니다.
//check if there's search data, if then, force input data into each input.
$(document).ready(function(){
	var search = location.search;
	if(search == '' || !search)
		return;
	
	var params = search.split('&'); 
		
	var param1 = params[0].substring(1).split('=');
	var key = param1[0];
	var value = param1[1];
	
	var param2Values = params[1].split('=')[1].split('-');	
	
	$.ajax({
		url: '/event/getSelectCoupon.do',
		type: 'get',
		data: {
			index : value
		},
		success : function(data){
			console.log(data);
			fillInputTagsCorrectly(data, param2Values);
			$('#coupon_form').attr('action', '/event/updateCoupons.do');
		}
	});
});


function fillInputTagsCorrectly(jsondata, enableBits){
	globalJson = jsondata;
	
	var nisImageCoupon = 1; // 1 means no, 0 menas yes
	
	//$('div.coupon_name > input').attr('disabled', 'disabled');
	$('div.coupon_name > input').val(jsondata.name);
	
	$('div.coupon_select > input').attr('disabled', 'disabled');
	
	$('div.coupon_alarm').append($('<input>').attr('name', 'index').attr('type', 'hidden').val(jsondata.genIndex));
	
	if(jsondata.originType == 0) {
		nisImageCoupon = 0;
		
		var imagePath = '/tcpFile/res_'+jsondata.storeCode+"/coupons/"+jsondata.image;
		$('#edit_image').empty().append($('<img/>').attr('id', 'thumbnail').attr('src', imagePath));
		
	} else {
		$('div.coupon_count > input').attr('disabled', 'disabled');
		$('div.coupon_count > input').val(jsondata.count);
	}
	
	$('div.coupon_select > input').eq(nisImageCoupon).click();
	$('div.coupon_select > input').eq(nisImageCoupon).attr('checked', 'checked');
	$('div.coupon_date > input').eq(0).val(jsondata.startDate);
	$('div.coupon_date > input').eq(1).val(jsondata.endDate);
	
	$('div.coupon_pop > input').eq(1 - enableBits[1]).attr('checked', 'checked');
	$('div.coupon_alarm > input').eq(1 - enableBits[0]).attr('checked', 'checked');
	
}