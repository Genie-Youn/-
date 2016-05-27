function tab_color(element) {
	$('#tab_wrapper').css('background-color', element.css('background-color'));
	
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

function previewImage(targetObj, show_intro) {

	var preview = document.getElementById(show_intro);
	//div id
	var ua = window.navigator.userAgent;

	if (ua.indexOf("MSIE") > -1) {//ie일때

		targetObj.select();

		try {
			var src = document.selection.createRange().text;
			// get file full path
			var ie_preview_error = document.getElementById("ie_preview_error_" + previewId);

			if (ie_preview_error) {
				preview.removeChild(ie_preview_error);
				//error가 있으면 delete
			}

			var img = document.getElementById(previewId);
			//이미지가 뿌려질 곳

			img.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + src + "', sizingMethod='scale')";
			// 이미지 로딩, sizingMethod는 div에 맞춰서 사이즈를 자동조절 하는 역할
		} catch (e) {
			if (!document.getElementById("ie_preview_error_" + show_intro)) {
				var info = document.createElement("<p>");
				info.id = "ie_preview_error_" + show_intro;
				info.innerHTML = "a";
				preview.insertBefore(info, null);
			}
		}
	} else {//ie가 아닐때
		var files = targetObj.files;
		for (var i = 0; i < files.length; i++) {

			var file = files[i];

			var imageType = /image.*/;
			//이미지 파일일경우만.. 뿌려준다.
			if (!file.type.match(imageType)) {
				alert("해당파일은 이미지 파일이 아닙니다");
				continue;
			}

			var prevImg = document.getElementById("prev_" + show_intro);
			//이전에 미리보기가 있다면 삭제
			if (prevImg) {
				preview.removeChild(prevImg);
			}

			var img = document.createElement("img");
			//크롬은 div에 이미지가 뿌려지지 않는다. 그래서 자식Element를 만든다.
			img.id = "prev_" + show_intro;
			img.classList.add("obj");
			img.file = file;
			img.style.width = '205px';
			//기본설정된 div의 안에 뿌려지는 효과를 주기 위해서 div크기와 같은 크기를 지정해준다.
			img.style.height = '320px';

			preview.appendChild(img);

			if (window.FileReader) {// FireFox, Chrome, Opera 확인.
				var reader = new FileReader();
				reader.onloadend = (function(aImg) {
					return function(e) {
						aImg.src = e.target.result;
					};
				})(img);
				reader.readAsDataURL(file);
			} else {// safari is not supported FileReader
				//alert('not supported FileReader');
				if (!document.getElementById("sfr_preview_error_" + show_intro)) {
					var info = document.createElement("p");
					info.id = "sfr_preview_error_" + show_intro;
					info.innerHTML = "not supported FileReader";
					preview.insertBefore(info, null);
				}
			}
		}
	}
}

window.onload = function() {

	if (window.File && window.FileList && window.FileReader) {
		var filesInput = document.getElementById("files2");

		filesInput.addEventListener("change", function(event) {

			var files = event.target.files;

			var output = document.getElementById("show_logo");
			var inner = "";

			for (var i = 0; i < files.length; i++) {
				var file = files[i];
				var div = document.createElement("div");

				if (!file.type.match('image')) {
					alert("해당파일은 이미지 파일이 아닙니다");
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
	$('#intro-click').on('click', function() {// use .live() for older versions of jQuery
		$('#files').click();
		setInterval(intervalFunc, 1);
		return false;
	});
	
	var intervalFunc2 = function() {
		$('#file-name').html($('#file').val());
	};
	$('#logo-click').on('click', function() {// use .live() for older versions of jQuery
		$('#files2').click();
		setInterval(intervalFunc2, 1);
		return false;
	});
	
	$('ul#colorlist li a').on('click', function(){
//		console.log('나 클릭되었다', $(this), $(this).id);
		tab_color($(this));
		return false;
	});
});

function Init() {
	for (var i = 1; i <= 5; i++) {
		$('<div></div>').html("메뉴").attr('id', 'create' + i).css({
			border : '1px',
			margin : '2px',
			width : '54px',
			height : '45px',
			backgroundColor : 'rgb(255,180,4)',
			float : 'left'
		}).appendTo($('#tab_location'));
	}
	$('div[id^="create"]').draggable({
		containment : ".mobile_locator",
		scroll : false
	});
}
