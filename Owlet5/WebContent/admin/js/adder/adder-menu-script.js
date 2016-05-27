/**
 * 
 */

$(document).ready(function(e){
	isCkeditorContentChanged = false;
	fixFixedPosition();
	
	var frameTitle;
	
	//그래 넌 Adder를 가는걸로 확정
	$('.form-to-adder').bind('click', function(){
		var title=$('input.title_input').val();
		
		if(frameTitle!=title){
			isCkeditorContentChanged=true;
		}
		
		if(isCkeditorContentChanged){
			$(window).unbind('beforeunload'); 
			document.forms.formsEditor["continue"].value=0;
			document.forms.formsEditor.submit();
		} else {
			alert("변경 안하셨어요");
		};
	});
	
	//Preview로 꺼져버려
	$('.form-to-preview').bind('click', function(){
		var title=$('input.title_input').val();
		
		if(frameTitle!=title){
			isCkeditorContentChanged=true;
		}
		
		if(isCkeditorContentChanged){
			$(window).unbind('beforeunload');
			document.forms.formsEditor["continue"].value=1;
			document.forms.formsEditor.submit();
		} else {
			alert("변경 안하셨어요");
		};
	});
	
	cancelUrlToRedirect = ['/preview/frames.do',
	                       '/calendar/calendar.do']
	;
	cancelIndex = 0;
	$('.form_reset').on('click', function(){
		isCkeditorContentChanged = false;
		location.href = cancelUrlToRedirect[cancelIndex];
	});
	
	$('#go_preview_btn, #return_preview_btn, .go-preview-btn').bind('click', function(){
		
		var editor = $('#editor_wrapper');
		
		if(targetPosition == 100){			//미리보기할때
			allHtml = CKEDITOR.instances.editor1.getData();
			
			//mobile_locator의 속성을 변경
			$('#mobile_locator').css('font-family', 'initial');
			
			
			if(allHtml.substring(allHtml.lastIndexOf('{"lat"'), allHtml.lastIndexOf(');</script>'))){
				mapJson = JSON.parse(allHtml.substring(allHtml.lastIndexOf('{"lat"'), allHtml.lastIndexOf(');</script>')));
				
				allHtml = $.parseHTML(allHtml);
				
				$(allHtml).find('div[id^="doksoft_maps"]');
				
				$(allHtml).find('span:not([style^="font-family"])').css('font-family', 'inherit');
				
				$('#mobile_locator').html(allHtml);
				
				_loadmap("doksoft_maps2",mapJson);
			}else{
				allHtml = $.parseHTML(allHtml);
				
				$(allHtml).find('span:not([style^="font-family"])').css('font-family', 'inherit');
				
				$('#mobile_locator').html(allHtml);
			}
			
//			
//			$(allHtml).find('span:not([style^="font-family"])').css('font-family', 'inherit');
////			
////			console.log(allHtml);
//			$('#mobile_locator').append(allHtml);
		}
		
		var viewer = editor.next();//$('#viewer_wrapper');
		
			editor.stop(true).animate({
				height:800 * (100 - targetPosition)/100 + "px",
				opacity:(100-targetPosition)/100
			}, 800);
			
			viewer.stop(true).animate({
				opacity:(targetPosition)/100
			}, 800, function(){
				
				console.log("animation switching!");
				
				if(targetPosition == 100){
					editor.css('display', "none");
					$('#main_section').css('height', '1100');
				} else {
					editor.css('display', "block");
					$('#main_section').css('height', '800');
					
					CKEDITOR.instances.editor1.resize(); //가끔 화면이 안보인다.. 그래서 넣었다. 갱신 스스로 하도록.
				}
				
				targetPosition = (targetPosition + 100) % 200;
			});
	});
	
	var formTag = $('form[name="formsEditor"]');
	
//	console.log(formTag)
	
	var param = location.search;
	
	var index = param.substring(1).split('=')[1];
	
//	console.log(index);
	
	if(index!=undefined){
//		console.log("항상 변하나..?");
		formTag.attr('action', '/adder/updateFrame.do');
		$('#hidden_index').val(index);
		
		$.ajax({
			url : '/adder/getSelectFrame.do',
			type :'POST',
			dataType : 'JSON',
			cache:false,
			data : {
				index : index
			},
			success : function(data){
				dataFromServer = data;
				
				CKEDITOR.instances.editor1.setData(dataFromServer.content);
				
				$('.title_input').val(dataFromServer.title);
				
				frameTitle=dataFromServer.title;
			//	$('input[name="calID"]').val(data.nIndex);
				
			},
			error : function(msg){
				console.log(msg);
			}
		});
	}
	
	//전송을 처리한다.
	
	/*$('.button_area > .form_submit').on('click', function(e){
		if(isCkeditorContentChanged == false){
			alert("변경된 게 없어요..");
			return false;
		}
		
		if(!CKEDITOR.instances.editor1.getDatas()){
			alert('no data');
		} else {
			alert('yes data');
		}
		
		alert('hi');
		
		return false;
	});*/
});

function fixFixedPosition(){
	var mainContainer = $('#main_section');

	targetPosition = 100; //100이면 에디터가 왼쪽으로 0이면 오른쪽으로 이동한다.
	
	var esinterface = $('#editor_switch_interface');
	
	mainContainer.css('height', '800');
}

$(window).bind('beforeunload', function() {
	if(isCkeditorContentChanged){
		return '페이지 이동 시 작업하신 내용은 저장되지 않습니다.';
	}
}); 
