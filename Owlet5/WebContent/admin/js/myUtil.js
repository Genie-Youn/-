function setConsoleMSG(msg){
	$('#console').html(msg);
}

function clearConsole(){
	$('#console').empty();
}

/**
 * e-mail 검사를 위한 정규 표현식을 만든다.
 * @returns 
 */
function getRegExpToCheckEmail(){  
	return /^[\w\.\-_\+]+@[\w-]+(\.\w{2,4})+$/;
}

function setValidInput(element){
	element.removeClass("normal");
	element.removeClass("invalid");
	element.addClass("valid");
}

function makeWarnMsg(){
	return "로그인 실패";
}

function setInValidInput(element){
	element.removeClass("normal");
	element.removeClass("valid");
	element.addClass("invalid");
}

function setNormalInput(element){
	element.removeClass("invalid");
	element.removeClass("valid");
	element.addClass("normal");
}

function checkIDInputIsEmpty(){
	if (($('#userID').val() == '')){
		return true;
	} else {
		return false;
	}
}

function checkPWInputIsEmpty(){
	if (($('#userPasswd').val() == '')){
		return true;
	} else {
		return false;
	}
}


/* 
 * 함수이름 재정의가 필요할 듯하다.
 * 초기엔 특수문자 검사를 위한 함수였으나
 * 지금은 E-mail의 정규 표현식을 검사하기 위한 기능으로 쓰인다
 * E-mail 정규 표현식에 어긋나면 false를 리턴. 그렇지 않다면 true
 */

function checkDoesIDInputContainSpecialChar(){
	// id, passwd 모두 입력되어야 실행된다.
	var idValue = $('#userID').val();
	//var mustCheck = ['.com','.kr','net','.'];
	var regExp=getRegExpToCheckEmail();

	var reValue = true;
	
	if(!idValue.match(regExp)) {// || !idValue.match(mustCheck) ){
		reValue=false;
	}
	//if(!regExp.match(idValue)){
	//	reValue=false;
	//}
	return reValue;
}

$(document).ready(function() {
	
	$('#extendsInfo').hide();
	
	$('#loginbtn').click(function(){
		var height=$('#content').height();
		
		$('body').scrollTop(height);
	});
	
	
	$('#joinbtn').bind('click', function(){location.href='/join.do'});
	
	/*
	 *  초기 검사를 login 버튼을 클릭한 문법 검사를 수동적으로 실행할 때 쓰임.
	 *  현재는 능동적으로 체크를 해주기 때문에 호출할 일이 없을듯.
	 *  따라서, 서버와 통신을 하기전 검사하는 부분을 지우거나 수정.
	 *  
	 *  (안덕환) 의견 -> 위에서 검사를 한 다음 상태값 변경을 통해 서버와의 통신을 결정하는게 어떨런지..
	 */
	
	$('#login').click(function() {
		var id = $('#userID').val();
		if (checkIDInputIsEmpty()){
			setInValidInput($('#userID'));
			setConsoleMSG("계정, 비번 입력 하세요.");
		} 
		else if (checkPWInputIsEmpty()) {
            //ID나 Passwd 둘 중 하나도 입력이 안되어 있으면 실행되지 않는다
			//alert('id, passwd 둘 다 입력');
			setInValidInput($('#userPasswd'));
			setConsoleMSG("계정, 비번 입력 하세요.");
		} else {
			if(!checkDoesIDInputContainSpecialChar()){
				setNormalInput($('#userID'));
				setNormalInput($('#userPasswd'));
				$('#userID').focusm();
				$('#userPasswd').blur();
				setConsoleMSG("이메일 형식 오류");
				
				//return false;
			}else{									//문제가 전혀 없다.
				setNormalInput($('#userID'));
				setNormalInput($('#userPasswd'));
				
				//location.href="/admin/main.jsp";
				callAjaxForLoginCheck();
			}
//			clearConsole();
			return false;
		}
	});
	// 비밀번호를 칠 때 caps lock이 걸려 있는지 검사한다.
	
	var capsFlag = false;
	$('#userPasswd').keypress(function(e) {
		var s = String.fromCharCode(e.which);
		if (s.toUpperCase() === s && s.toLowerCase() !== s && !e.shiftKey) {
			capsFlag = true;
			setConsoleMSG("경고: Caps Lock 키가 켜져 있어!");
		}
	});
	
	$('#userPasswd').focusout(function(e){
		if(!checkPWInputIsEmpty() && !capsFlag/* && checkDoesIDInputContainSpecialChar()*/){
			setValidInput($('#userPasswd'));
			clearConsole();
		} 
		else if( !checkPWInputIsEmpty() && capsFlag){
			setInValidInput($('#userPasswd'));
			setConsoleMSG("경고: Caps Lock 키가 켜져 있어!");
		}
		else {
			setInValidInput($('#userPasswd'));
			capsFlag = false;
		}
	});
	
	$('#userPasswd').keyup(function(e){
		if(!checkPWInputIsEmpty() && !capsFlag/* && checkDoesIDInputContainSpecialChar()*/){
			setValidInput($('#userPasswd'));
			clearConsole();
		} 
		else if( !checkPWInputIsEmpty() && capsFlag){
			setInValidInput($('#userPasswd'));
			setConsoleMSG("경고: Caps Lock 키가 켜져 있어!");
		}
		else {
			setInValidInput($('#userPasswd'));
			capsFlag = false;
		}
	});
	
	$('#userID').focusout(function(e){
		if(!checkIDInputIsEmpty() && checkDoesIDInputContainSpecialChar() ){
			setValidInput($('#userID'));
			clearConsole();
		}
		else{
			setInValidInput($('#userID'));
		    capsFlag = false;
		}
	});
	
	$('#userID').keyup(function(e){
		if(!checkIDInputIsEmpty() && checkDoesIDInputContainSpecialChar() ){
			setValidInput($('#userID'));
			clearConsole();
		}
		else{
			setInValidInput($('#userID'));
			capsFlag = false;
		}
	});
	
	var footerString = 'Copyright  2015 <b>SEA Insight</b> All Rights Reserved.<br>'+
	'Owlet5 is a web project of TCP club in Computer Science and Engineering,<br>'+
	 'Seoul National University of Science and Technology.';


	$('#main_copyright').append(footerString);

});

function callAjaxForLoginCheck(){
	
	//document.loginForm.submit();
	$.ajax({
		  url: "/loginCheck.do",
		  dataType: "text",
		  data: {
			  mail:$('#userID').val(),
			  passwd:$('#userPasswd').val()
		  },
		  cache:false,
		  type:'POST',
		  success:function(plainTextData){
			  if(plainTextData == '1')
				  //login후 Main으로 보낼지 App List를 보여줄지 선택
				  location.href="adder/loginMove.do";
			  else if(plainTextData == '0'){
				  $('#userID').blur().val('');
				  $('#userPasswd').blur().val('');
				  setInValidInput($('#userID'));
				  setInValidInput($('#userPasswd'));
				  setConsoleMSG("[로그인 실패] 계정과 암호를 재확인 해주세요.");
				  return false;
			  }
		  }
		}).done(function() {
		  //$( this ).addClass( "done" );
		});
}