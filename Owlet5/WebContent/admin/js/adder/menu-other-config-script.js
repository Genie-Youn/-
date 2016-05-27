/**
 * for other menus like menu config, skin config,
 */

function MenuData(){
	this.array = new Array(5);
	this.MAX_COUNT = 5;
	this.dataCount = 0;
	this.availIndex = 0;
}

MenuData.prototype.getArrayDataAt = function(index){
	return this.array[index];
}

MenuData.prototype.setDataAt = function(index,value){
	if(index == this.MAX_COUNT){
		return -1;
	} else {
		this.array[index] = value;
		this.setAvailIndex();
		return ++this.dataCount;
	}
}

// o(n)
MenuData.prototype.setAvailIndex = function(){
	for(var i = 0; i < this.MAX_COUNT; i++){
		if(!this.array[i]){
			this.availIndex = i;
			console.log(i);
			break;
		}
		if(i == this.MAX_COUNT - 1){
			this.availIndex = this.MAX_COUNT;
		}
	}
}

MenuData.prototype.removeDataAt = function(index){
	if(index < this.MAX_COUNT){
		--this.dataCount;
	}
	
	if(!this.array[index]){
		return -1;
	}
	
	delete this.array[index];
	
//	for(var i = index; i < this.array.length-1; i++){
//		this.array[i] = this.array[i+1];
//	}
	for(var i = index; i < 5; i++){
		this.array[i] = this.array[i+1];
	}
//	delete this.array[this.array.length-1];
	delete this.array[4];
	
	this.setAvailIndex();
	return this.dataCount;
}

MenuData.prototype.getArray = function(){
	return this.array;
}

$(document).ready(function(e){
	menuData = new MenuData();
	
	//메뉴 불러오세요.
	
	//메뉴데이터 초기화하세요..ㅜㅜ
	
	
	loadGroupsAndFrames();
	
	
	enrollEventInteraction();
	
	
	$('.rect-slot').on('mouseenter', function(e){
		$('.text-viewer').text($(this).text());
	});
	
	
	$('body').on("selectstart", function(event){ return false; });
    $('body').on("dragstart", function(event){ return false; });
    
    $('.next-btn').on('click', function(e){
    	//location.assign('/admin/skinConfiguration.jsp');
    	var sendArray = $.map(menuData.array, function(e, i){
    		if(e){
    			return e;
    		}
    	});
    	
    	console.log(sendArray);
    	
    	if(sendArray.length == 0) {
    		var isContinue = confirm('구성하신 메뉴가 없습니다. 넘어가시겠습니까?');
    		if(isContinue){
    			
    		} else {
    			return false;
    		}
    	}
    	
    	$.ajax({
    		url:'/option/insertMenu.do',
    		type:'POST',
    		data : {
    			menus : sendArray
    		},
    		traditional:true,
    		success:function(data){
    			if(data == 1){
    				location.assign('/admin/skinConfiguration.jsp');
    			} else {
    				var isOk = confirm('문제가 생겨 메뉴가 정상적으로 등록되지 못했습니다. 넘어가시겠습니까?');
    				if(isOk){
    					location.assign('/admin/skinConfiguration.jsp');
    				} else {
    					return false;
    				}
    			}
    		}
    	});
    });
    $('.prev-btn').on('click', function(e){
    	location.assign('/admin/menuConfiguration.jsp');
    });
    $('.save-btn').on('click', function(e){
    	var sendArray = $.map(menuData.array, function(e, i){
    		if(e){
    			return e;
    		}
    	});
    	
    	console.log(sendArray);
    	
    	
    	$.ajax({
    		url:'/option/insertMenu.do',
    		type:'POST',
    		data : {
    			menus : sendArray
    		},
    		traditional:true,
    		success:function(data){
    			if(data == 1){
    				alert('성공적으로 저장하였습니다.')
    			} 
    		}
    	});
    	
    });
});

function loadGroupsAndFrames(){
	/*var getGroupInfos = (function(){
		
		return function(){
					//그룹 받아와요.
					$.getJSON('/adder/selectGroup.do', function(data){
						console.log(data);
						//우쪽 프레임 제거하고 새것 추가해요.
						var contList = $('.frame-list-wrapper .ul-list');
						var ddData = [];
						for(var key in data){
				
							var dData = {};
							var keySubPos = key.indexOf('_');
							var realKey = key.substring(keySubPos+1);
							var realCode = key.substring(0, keySubPos);
							
							dData.text = realKey;
							dData.value = realCode;
							dData.selected = false;
							
							for(var idx in data[key]) {
								
									data[key][idx].title = ( !data[key][idx].title )? ("무제"):(data[key][idx].title);
									
									//중복되는거 제거하세요.
//									$('.frame-list-wrapper .ul-list').find('li[data-index="'+data[key][idx].nIndex+'"]').remove();
							}
							
							$('<li></li>').addClass('group-item')
							.text(realKey)
							.attr('data-index', realCode)
							.appendTo(contList);
							;
						ddData.push(dData);
						}
						
						$('.ul-list').css('opacity', 1);
						
						
						$.getJSON('/option/selectMenu.do', function(e){
							console.log(e);
							for(var i in e){
								var type = (e[i].path.split(/\\s/)[0] < 5)?(0):(1);
								var nIndex = e[i].path.split(/\\s/)[1];
								console.log(e[i].path, type, nIndex);
								
								menuData.setDataAt(i, nIndex+" "+type);
							}
							updateMenuLiElement();
						});
						enrollEventInteraction();
					});
		}
	})();
	
	//프레임 받아와요.
	$.getJSON('/preview/framesData.do', function(data) {
		console.log(data);
	
	//왼쪽 프레임 제거하고 새것 추가해요.
	var contList = $('.frame-list-wrapper .ul-list').empty();
	for(var i in data){
		var title = (data[i].title.length==0)?('무제'):(data[i].title);
		var indexValue = data[i].nIndex;
		$('<li></li>').addClass('frame-item')
		.text(title)
		.attr('data-index', indexValue)
		.appendTo(contList);
	}
	
	//그룹쪽도 그리죠..
	getGroupInfos();
	$.getJSON('/preview/FrameListAll.do', function(data) {
		console.log(data);
		for(var i in data){
//			console.log(data[i].path.split(/\\s/));
			var type = data[i].path.split(/\\s/)[0];
			var nIndex = data[i].path.split(/\\s/)[1];
			if(type==2){
				$('<li></li>').addClass('frame-item')
				.text(data[i].title)
				.attr('data-index', nIndex)
				.appendTo($('.frame-list-wrapper .ul-list'));
				break;
			}
		}
	});
	});*/
	var contList = $('.frame-list-wrapper .ul-list').empty();
	$.getJSON('/preview/FrameListAll.do', function(data) {
		console.log(data);
		for(var i in data){
//			console.log(data[i].path.split(/\\s/));
			var type = data[i].path.split(/\\s/)[0];
			var nIndex = data[i].path.split(/\\s/)[1];
			var title = (data[i].title.length==0)?('무제'):(data[i].title);
			console.log(type, nIndex);
			if(type < 5){
				$('<li></li>').addClass('frame-item')
				.text(title)
				.attr('data-index', nIndex)
				.appendTo($('.frame-list-wrapper .ul-list'));
			} else {
				$('<li></li>').addClass('group-item')
				.text(title)
				.attr('data-index', nIndex)
				.appendTo($('.frame-list-wrapper .ul-list'));
			}
		}
		$('.ul-list').css('opacity', 1);
		
		
		$.getJSON('/option/selectMenu.do', function(e){
			console.log(e);
			for(var i in e){
				var type = (e[i].path.split(/\\s/)[0] < 5)?(0):(1);
				var nIndex = e[i].path.split(/\\s/)[1];
				console.log(e[i].path, type, nIndex);
				
				menuData.setDataAt(i, nIndex+" "+type);
			}
			updateMenuLiElement();
		});
		enrollEventInteraction();
	});
	
}

function enrollEventInteraction(){
	if($('.frame-list-wrapper .ul-list li').length == 0) {
		$('.data-info-panel').css('display', 'block');
	} else {
		$('.data-info-panel').css('display', 'none');
	}
	
	$('.frame-item, .group-item').on('click', function(e){
		var text = $(this).text();
		var classCode = ($(this).is('.frame-item'))?(0):(1);
		var nIndex = $(this).attr('data-index');
		var key = nIndex+" "+classCode;
		
		var index = $.inArray(key, menuData.getArray());
		console.log(index, key);
		
		
		
		var returnFlag = -1;
		
		if(index == -1){
			index = menuData.availIndex;
			returnFlag = menuData.setDataAt(index, key);
		} else {
			returnFlag = menuData.removeDataAt(index);
			text = '메뉴슬롯';
		}
		
		if(returnFlag >= 0){
			$('.rect-slot').eq(index).toggleClass('rect-slot-highlight')
//			.text(text);
//			$(this).toggleClass('selected');
			updateMenuLiElement();
		}
	});
}

function updateMenuLiElement(){
	$('.ul-list li').removeClass('selected');
//	for(var i in menuData.array){
	for(var i = 0; i < 5; i++){
		var item = menuData.array[i];
		console.log(i);
		if(item) {
			var type = item.split(' ')[1];
			var nIndex = item.split(' ')[0];
			var text;
			
			$('.rect-slot').eq(i).addClass('rect-slot-highlight');
			if(type == 0){ //frame
				text = $('.ul-list .frame-item[data-index="'+nIndex+'"]').addClass('selected').text();
			} else {
				text = $('.ul-list .group-item[data-index="'+nIndex+'"]').addClass('selected').text();
			}
			$('.rect-slot').eq(i).text(text);
		} else {
			$('.rect-slot').eq(i).removeClass('rect-slot-highlight').text('메뉴슬롯');
		}
	}
}