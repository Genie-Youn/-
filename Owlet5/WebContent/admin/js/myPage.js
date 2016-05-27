function test(){  
  var frm = document.mypage.pw; 
    
  if(frm.value.length >= 15){  
       alert("패스워드는 15자 이내로 입력해주세요.");
       frm.value="";  
       frm.focus();  
  } 
}

$(document).ready(function(e){
	if (window.File && window.FileList && window.FileReader){}else{alert("브라우저 사양이 기술을 지원하지 못합니다. 브라우저를 업데이트 하십시오."); return;}
	$('.file-input').on('change', doUpdateImageAndSetImgSRC);
	$('input:reset').on('click', function(e){
		var isOk = confirm('수정을 종료하시겠습니까?');
		if(isOk){
			window.close();
		} else {
			e.preventDefault();
			return false;
		}
	});
});

function doUpdateImageAndSetImgSRC(event){
	var fileReader = new FileReader();
	var file = event.target.files[0];
	if (!file.type.match('image')){
		alert('이미지 파일을 등록하여야 합니다.');
		location.reload(true);
		return false;
	}
	
	$(fileReader).on('load', function(evt){
		var data = $(this)[0].result;
		$('.logo-image').attr('src', data);
	});
	
	fileReader.readAsDataURL($(this)[0].files[0]);
}