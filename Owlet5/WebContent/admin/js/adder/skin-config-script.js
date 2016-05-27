/**
 * 
 */

function SkinConfig(){
	this.menuColor = unidentified;
	this.backColor = unidentified;
	this.introImageSrc = unidentified;
}

SkinConfig.prototype.setMenuColor = function(colorHexCode){
	this.menuColor = colorHexCode;
}

SkinConfig.prototype.setBackColor = function(colorHexCode){
	this.backColor = colorHexCode;
}

SkinConfig.prototype.setIntroImageSrc = function(srcText){
	this.setIntroImageSrc = srcText;
}

$(document).ready(function(e){
	
	//app-setting-info bean 에 맞추어라.
	
	$('body').on("selectstart", function(event){ return false; });
    $('body').on("dragstart", function(event){ return false; });
    $('.next-btn').on('click', function(e){
    	//location.assign('/admin/confirmConfiguration.jsp');
    	var formDatas = new FormData();
    	
    	if(!$('.file-intro-input')[0].files[0]){
    		//alert('이미지가')
    		//return false;
    	}
    	
    	formDatas.append('fcolor', rgb2hex($('.menu-element').css('background-color')));
    	formDatas.append('bcolor', rgb2hex($('.background-element').css('background-color')));
    	formDatas.append('ch', isChange);
    	formDatas.append('image', $('.file-intro-input')[0].files[0]);
    	
    	
    	
    	console.log(formDatas);
    	
    	$.ajax({
    		url:'/option/updateOption.do',
    		contentType:false,//'multipart/form-data',
    		dataType: "text",
    		type:'POST',
            cache: false,
            processData: false,
    		data:formDatas,
    		success:function(data){
    			console.log(data);
    			if(data == 1){
    				location.assign('/admin/confirmConfiguration.jsp');
    			} else {
    				alert('잠시 문제가 생겨 정상 처리되지 않았습니다. 다시 시도해 주세요.');
    			}
    		}
    	});
    });
    $('.prev-btn').on('click', function(e){
    	location.assign('/admin/menuBatchConfiguration.jsp');
    });
    $('.save-btn').on('click', function(e){
    	//location.assign('/admin/confirmConfiguration.jsp');
    	var formDatas = new FormData();
    	
    	if(!$('.file-intro-input')[0].files[0]){
    		//alert('이미지가')
    		//return false;
    	}
    	
    	formDatas.append('fcolor', rgb2hex($('.menu-element').css('background-color')));
    	formDatas.append('bcolor', rgb2hex($('.background-element').css('background-color')));
    	formDatas.append('ch', isChange);
    	formDatas.append('image', $('.file-intro-input')[0].files[0]);
    	
    	
    	
    	console.log(formDatas);
    	
    	$.ajax({
    		url:'/option/updateOption.do',
    		contentType:false,//'multipart/form-data',
    		dataType: "text",
    		type:'POST',
            cache: false,
            processData: false,
    		data:formDatas,
    		success:function(data){
    			console.log(data);
    			if(data == 1){
    				alert("정상적으로 저장되었습니다.");
    			} else {
    				alert('잠시 문제가 생겨 정상 처리되지 않았습니다. 다시 시도해 주세요.');
    			}
    		}
    	});
    	
    });
    
    
    //css 바꾸고, 배경, 모바일 적용하고
    $('.color-container').each(function(index, item, all){
    	
    	$(this).find('.color-item').off().on('click', function(e){
    		var colorCode = $(this).css('background-color');
    		$(this).parent().find('li').removeClass('color-selected');
    		$(this).addClass('color-selected');
    		if(index % 2 == 0) {
        		//메뉴색
    			$('.menu-element').css('background-color', colorCode);
        	} else {
        		$('.background-element').css('background-color', colorCode);
        	}
    	});
    });
    
    //파일 api 적용하고...
    
//    $('.file-intro-input').trigger('click');
    isChange=0;
    $('.file-intro-input').on('change', function(e){
    	isChange=1;
    	var thisfile = $(this)[0].files[0];
    	var reader = new FileReader();
    	$(reader).on('load', function(e){
    		$('<img>').attr('src', $(this)[0].result)
    		.css('width', '100%')
    		.css('height', '100%')
    		.appendTo($('div.viewport-div.background-port').empty());
//    		$('div.viewport-div.background-port').css('background', 'url("'+$(this)[0].result+'")')
//    		.css('background-size', 'contain')
//    		.css('background-repeat', 'no-repeat')
//    		.css('background=position', 'center center');
    	});
    	reader.readAsDataURL(thisfile);
    });
    
    
    //상호 통신하여 리스트 받아오고 넘기고..
    $.getJSON('/option/getOptionInfo.do', function(data){
    	console.log(data);
    	//{layout: 0, color: "", image: "", icon: "", pathFile: ""} 
    	if(data.color){
    		console.log('color');
    		$('.front .color-item').removeClass('color-selected');
    		$('.menu-element').css('background-color', data.color);
    		$.each($('.front .color-item'), function(index, item, array){
    			if(rgb2hex($(this).css('background-color')) == data.color){
    				$(this).addClass('color-selected');
    			}
    		});
    	}
    	
    	if(data.image){
    		console.log('image');
    		var code = data.image.substring(0, data.image.indexOf('_'));
    		var pathName = '/tcpFile/res_'+code+'/'+data.image;
    		$('<img>').attr('src', pathName)
    		.css('width', '100%')
    		.css('height', '100%')
    		.appendTo($('div.viewport-div.background-port').empty());
    	}
    	
    	if(data.bgColor){
    		console.log('bgColor');
    		$('.back .color-item').removeClass('color-selected');
    		$('.background-element').css('background-color', data.bgColor);
    		$.each($('.back .color-item'), function(index, item, array){
    			if(rgb2hex($(this).css('background-color')) == data.bgColor){
    				$(this).addClass('color-selected');
    			}
    		});
    	}
    	
    	if(data.pathFile) {
    		console.log('pathFile');
    	}
    });
    
});

function rgb2hex(rgb){
	 rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
	 return "#" +
	  ("0" + parseInt(rgb[1],10).toString(16)).slice(-2) +
	  ("0" + parseInt(rgb[2],10).toString(16)).slice(-2) +
	  ("0" + parseInt(rgb[3],10).toString(16)).slice(-2);
	}