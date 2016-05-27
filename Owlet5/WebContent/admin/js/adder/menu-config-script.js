/**
 * script for configure groups.
 */



/**
 * 
 * key : original key
 * code : -1 none, 0 new, 1 modified, 2 deleted
 * ckey : code key
 * 
 */function KeyStateLog(key, code, ckey){
	this.key = key;
	this.code=code;
	this.ckey=ckey;
	this.next=this;
	this.prev=this;
}

function GroupData() {
	this.selectedIndex = -1;
	this.selectedKey = undefined;
	this.groupCreatingNeglect = false;
	this.keyCount = 0;
	this.keyLogger = {};
};

/**
 * 이게 본론이거니와 이렇게 안하는 게 낫겠다.
 */
//Array.prototype.setUserData(val){
//	return null;
//}

/**
 * 배열에 데이터 추가하기위한 방안이 아닌 이 메서드의 주석을 생성하고 있었잖아 이게 뭐가.
 * @param origin
 * @param newKey
 * @returns {Boolean}
 */

GroupData.prototype.modifyKey = function(origin, newKey){
	if(origin == newKey){
		return false;
	}
	if(!this[origin]){
		return false;
	}
	if(origin == this.selectedKey){
		this.selectedKey = newKey;
	}
	this[newKey] = this[origin];
	delete this[origin];
	return true;
}

GroupData.prototype.removeKey = function(key){
	if(!this[key]){
		return false;
	}
	var elements = this[key];
	delete this[key];
	this.keyCount--;
	return elements;
}

GroupData.prototype.pushDataInKey = function(key, element){
	if(!this[key]){
		return false;
	}
	this[key].push(element);
	return true;
}

GroupData.prototype.pushData = function(element){
	if(!this.selectedKey){
		return false;
	}
	return this.pushDataInKey(this.selectedKey, element);
}

GroupData.prototype.removeDataInKey = function(key, element){
	if(!this[key]){
		return false;
	}
	
	var index = $.inArray(element, this[key]);
	if(index == -1){
		return false;
	} else {
		this[key].splice(index , 1);
		return true;
	}
}

GroupData.prototype.removeData = function(element){
	if(!this.selectedKey){
		return false;
	}
	
	return this.removeDataInKey(this.selectedKey, element);
}

GroupData.prototype.addKey = function(key, gIndex){
	if(this[key]){
		return false;
	}
	this[key] = [];
	this[key].gIndex = gIndex;
	this.keyCount++;
	return true;
}

GroupData.prototype.setSelectedIndex = function(val){
	this.selectedIndex = val;
}

GroupData.prototype.setSelectedKey = function(val){
	this.selectedKey = val;
}

GroupData.prototype.removeAllKeys = function(){
	var elements = [];
	
	for(var key in this){
		if($.isArray(this[key])){
			elements.push(this.removeKey(key));
		}
	}
	this.keyCount = 0;
	return elements;
}

GroupData.prototype.setGroupCreatingNeglect = function(value){
	this.groupCreatingNeglect = value;
}

/**
 * 시간 없어 강제로 생성한 메서드..
 * 절대 임의로 호출하지 말 것.
 * 첫 호출시에만 호출.
 * 나중에 생성자 인자로 대신하기를.
 */
GroupData.prototype.forceToSetKeyCount = function(val){
	this.keyCount = val;
}

groupData = new GroupData();

$(document).ready(function(e){

	
	var getGroupInfos = (function(){
		
		return function(){
					//그룹 받아와요.
					$.getJSON('/adder/selectGroup.do', function(data){
						console.log(data);
						//우쪽 프레임 제거하고 새것 추가해요.
						var contList = $('.group-list-wrapper .ul-list').empty();
						var ddData = [];
						for(var key in data){
				
							var dData = {};
							var keySubPos = key.indexOf('_');
							var realKey = key.substring(keySubPos+1);
							var realCode = key.substring(0, keySubPos);
							
							dData.text = realKey;
							dData.value = realCode;
							dData.selected = false;
							groupData.addKey(dData.text, dData.value);
							
							//text, value, selected
							for(var idx in data[key]) {
								
				//				if(data[key][idx].title){
									data[key][idx].title = ( !data[key][idx].title )? ("무제"):(data[key][idx].title);
									groupData.pushDataInKey(realKey, 
									
									$('<li></li>').addClass('group-item')
									.text(data[key][idx].title)
									.attr('data-index', data[key][idx].nIndex)[0]
				//					.appendTo(contList)
									);
									
									//중복되는거 제거하세요.
									$('.frame-list-wrapper .ul-list').find('li[data-index="'+data[key][idx].nIndex+'"]').remove();
				//				}
							
							}
						ddData.push(dData);
						
						}
						
						//globalMapDataStructureInit(ddData);
						/**
						 * 후에 선택 텍스트 바꿔야지 원. 그럴 필요가 있나.
						 */
						$('.group-list-wrapper .list-header .group-check').ddslick({
						    data: ddData,
						    width: 280,
						    //imagePosition: "left",
						    selectText: (ddData.length != 0)?('그룹 선택하기'):('그룹 전혀 없음'),//'그룹 선택해',//"Select your favorite social network",
						    background : 'rgba(255,255,255,1',//'transparent',
						    onSelected: function (data) {
				//		    	console.log(data, data.selectedIndex, data.selectedData.text);
				//		        groupData.setSelectedIndex(data.selectedIndex);
				//		        groupData.setSelectedKey(data.selectedData.text);
						        onSelectionMethodTask(data);
						    }
						});
						
						enrollListEvents();
						$('.ul-list').css('opacity', 1);
					});
//					$('.ul-list').css('opacity', 1);
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
	
	enrollListEvents();
	getGroupInfos();
	});
	//그룹 데이터 초기화하세요..ㅜㅜ
	
	
	//Dropdown plugin data
//	var ddData = [
//	    {
//	        text: "Facebook",
//	        value: 1,
//	        selected: false,
//	        //description: "Description with Facebook"//,
//	       // imageSrc: "http://dl.dropbox.com/u/40036711/Images/facebook-icon-32.png"
//	    },
//	    {
//	        text: "Twitter",
//	        value: 2,
//	        selected: false,
//	        //description: "Description with Twitter"//,
//	       // imageSrc: "http://dl.dropbox.com/u/40036711/Images/twitter-icon-32.png"
//	    },
//	    {
//	        text: "LinkedIn",
//	        value: 3,
//	        selected: false,
//	        //description: "Description with LinkedIn"//,
//	       // imageSrc: "http://dl.dropbox.com/u/40036711/Images/linkedin-icon-32.png"
//	    },
//	    {
//	        text: "Foursquare",
//	        value: 4,
//	        selected: false,
//	        //description: "Description with Foursquare"//,
//	       // imageSrc: "http://dl.dropbox.com/u/40036711/Images/foursquare-icon-32.png"
//	    }
//	];
	
	
	$('.group-create-btn').on('click', function(e){
		
		
		
		$('.group-config-wrapper').slideUp(
				function(){
					$('.group-container > ul').empty();
					groupData.setGroupCreatingNeglect(false);
					for(var key in groupData){
						if($.isArray(groupData[key])){
							$('.group-name').val(key);
							$('.group-add-btn').click();
						}
					}
					groupData.setGroupCreatingNeglect(true);
				}
		);
	});
	
	$('.make-frame-btn').on('click', function(e){
		location.assign('/adder/editor.do');
	});
	
	$('.group-back-btn').on('click', function(e){
		
		if($('.group-container li').has('input').length > 0){
			alert('변경이 끝나지 않은 그룹이 있습니다. 변경을 끝내주세요');
			return false;
		}
		
		$('.dd-container').ddslick('destroy');
		
		var isTrueAppear = false;
		
		var llist = $.map($('.group-container').find('li'), function(item, index){
			var textData = item.firstChild.data;
			var realKey = textData.substring(textData.lastIndexOf(' ('),-1);
			
			console.log(item.firstChild, index);
			
			var boolValue = (realKey == groupData.selectedKey)?(true):(false);
			if(boolValue){
				isTrueAppear = true;
			}
			return {
					text:realKey,
					value:index,
					selected:boolValue
				};
		});
		
		if(!isTrueAppear) {
			groupData.selectedIndex = -1;
			groupData.selectedKey = undefined;
		}
		
		$('.group-list-wrapper .list-header .group-check').ddslick({
		    data: llist,
		    width: 280,
		    //imagePosition: "left",
		    selectText: (llist.length != 0)?('그룹 선택'):('그룹 전혀 없음'),//"Select your favorite social network",
		    background : 'rgba(255,255,255,1',//'transparent',
		    onSelected: function (data) {
		        console.log(data, data.selectedIndex, data.selectedData.text);
		        onSelectionMethodTask(data);
		    }
		});
		
		console.log(llist);
		checkEachSlotIsEmpty();
		$('.group-config-wrapper').slideDown();
	});
	
	$('.group-add-btn').on('click', function(e){
		var text = $('.group-name').val();
		if(text.length==0){
			$('.group-name').val('').focus();
			return false;
		}
		
		if(groupData.groupCreatingNeglect){
			

			if(groupData.keyCount >= 5) {
				alert('더 그룹 생성하시면 안되요');
				return false;
			}
			
			var isAlreadyExistKey = false;
			if($.isArray(groupData[text]))
				isAlreadyExistKey = true;
			
			if(isAlreadyExistKey){
				alert('이미 키가 있습니다.');
				return false;
			} else {
				//groupData.addKey(text, );
			}
			
			$.ajax({
				url:'/adder/insertGroup.do',
				data: {
					name:text
				},
				type:'POST',
				success:function(e){
					console.log(e);
					if(e > 0){
						groupData.addKey(text, e);
						var parent = $('.group-container > ul');
						parent.append(createLiTag(text, $('<li></li>').addClass('group-name-list')));
						$('.group-name').val('').focus();
					} else {
						
					}
				}
			});
		}  else {
			var parent = $('.group-container > ul');
			parent.append(createLiTag(text, $('<li></li>').addClass('group-name-list')));
			$('.group-name').val('').focus();
		}
		
		
//		var parent = $('.group-container > ul');
//		parent.append(createLiTag(text, $('<li></li>').addClass('group-name-list')));
//		$('.group-name').val('').focus();
	});
	
	$('.group-container > ul li button:even').on('click', function(e){
//		var isOk = confirm('제거하시겠습니까?'+ $(this).parent().find('li')[0].firstChild.data);
		if(isOk) {
			$(this).parent().remove();
		}
	});
	
	/*$('.group-container > ul li button:odd').on('click', function(e){
		var text = $(this).val();
		$(this).on('click', function(e){
			var ths = $(this);
			var par = ths.parent();
			par.html($('<input>').attr('type','text')
					.addClass('group-modify-input').val(text).focus())
			.append($('<button>확정</button>').on('click', function(e){
				var text = par.find('.group-modify-input').val();
				if(text.length==0){
					return false;
				}
				par.html(createLiTag(text));
			})).append($('<button>취소</button>').on('click', function(){
				par.html(createLiTag(text));
			}));
		})
	});*/
	
	$('.group-delete-all-btn').on('click', function(e){
		var isOk = confirm('모두 삭제하시겠습니까?');
		if(isOk){
			$('.group-container > ul li').remove();
			
			var targetValues = groupData.removeAllKeys();
			
			//move its data to others..
			console.log(targetValues);
			$.each(targetValues, function(i, e, a){
				$(e).appendTo($('.frame-list-wrapper .ul-list'));
				addDragFrameFunctionsTo($(e));
				
				$.ajax({
					url:'/adder/deleteGroup.do',
					data:{
						index:e.gIndex
					},
					type:'POST',
					success:function(data){
						console.log(data);
						if(data == 1){
							//move its data to others..
							console.log(e);
							$(e).appendTo($('.frame-list-wrapper .ul-list'));
							addDragFrameFunctionsTo($(e));
						} else {
							
						}
					}
				});
			});
		}
	});
	
	$('body').on("selectstart", function(event){ return false; });
    $('body').on("dragstart", function(event){ return false; });
//	enrollListEvents();
	
	$('.whole-selector').on('click', function(e){
		$(this).toggleClass('whole-selected');
		
		if($(this).is('.whole-selected')){
			$(this).parent().parent().find('.ul-list li')
			.addClass('selected');
		} else {
			$(this).parent().parent().find('.ul-list li')
			.removeClass('selected');
		}
	});
	
	$('.next-btn').on('click', function(e){
		isSaveTime = true;
		n = {};
		for(var key in groupData){
			if($.isArray(groupData[key])){
				var texts = $.map(groupData[key], function(item, index){
					return $(item).attr('data-index');//$(item).text().substring(4);
				});
				
				texts.push(groupData[key].gIndex);
				texts.join();
				console.log("hi", texts);
				n[key] = texts;
			}
		}
		
//		console.log(n);
		
		nn = [];
		
		for(var key in n){
			var temp = $.map(n[key], function(element, index){
				return element;
			});
			
//			console.log(temp);
			
			nn.push([]);
			nn.push(temp);
			nn.push([]);
		}
		
		console.log(nn);
		
//		alert(nn);
		if(nn.length == 0) {
			var isOkThatThereisnoGroup = confirm("그룹이 없습니다. 넘어가시겠습니까?");
			if(isOkThatThereisnoGroup) {
				location.assign("/adder/batchConfig.do");
				return false;
			} else {
				return false;
			}
		}
		
		$.ajax({
			url:'/adder/insertConfigGroup.do',
			type:'post',
//			data : {
//				facebook:"3,4,5,6,7,8,9,0", //신규 0, 변경 1, 제거 2
//				twieeter:"3,4,5,6,7,8,9,0"
//			},
			success:function(data){
				console.log(data);
			},
			data : {
				group : nn
			},
			success : function(data){
				  location.href="/adder/batchConfig.do";
			},
			traditional : true
		});
	});
});

function createLiTag(ptext, li){
//	return $('<li></li>').html(ptext).append($('<button>-</button>').on('click', function(e){
	return li.html(ptext+" ("+ groupData[ptext].length +")").append($('<button class="delete-btn">삭제</button>').on('click', function(e){
		console.log($(this).parent()[0].firstChild.data);
		
		var tempDataWithCount = $(this).parent()[0].firstChild.data;
		var targetKey = tempDataWithCount.substring(tempDataWithCount.lastIndexOf(' ('), -1);
//		console.log($(this).prev());
		var isOk = confirm('제거하시겠습니까?');
		if(isOk){
			
			var me = $(this);
			
			$.ajax({
				url:'/adder/deleteGroup.do',
				data:{
					index:groupData[targetKey].gIndex
				},
				type:'POST',
				success:function(e){
					console.log(e);
					if(e == 1){
						me.parent().remove();
						var targetValues = groupData.removeKey(targetKey);
						
						//move its data to others..
						console.log(targetValues, targetKey);
						$(targetValues).appendTo($('.frame-list-wrapper .ul-list'));
						addDragFrameFunctionsTo($(targetValues));
					} else {
						
					}
				}
			});
			
//			$(this).parent().remove();
//			var targetValues = groupData.removeKey(targetKey);
//			
//			//move its data to others..
//			console.log(targetValues, targetKey);
//			$(targetValues).appendTo($('.frame-list-wrapper .ul-list'));
//			addDragFrameFunctionsTo($(targetValues));
		}
	})).append($('<button class="modify-btn">변경</button>').on('click', function(e){
		var ths = $(this);
		var par = ths.parent();
		par.html($('<input>').attr('type','text')
				.addClass('group-modify-input').val(ptext).focus())
		.append($('<button class="confirm-btn">확정</button>').on('click', function(e){
			var text = par.find('.group-modify-input').val();
			if(text.length==0){
				return false;
			}
			
			var isAlreadyExistKey = false;
			if($.isArray(groupData[text]))
				isAlreadyExistKey = true;
			
			if(text == ptext){
				isAlreadyExistKey = false;
			}
			
			
			if(isAlreadyExistKey){
				alert('이미 키가 있습니다.');
				return false;
			}
			
			console.log('update 로깅');
			$.ajax({
				url:'/adder/updateGroup.do',
				data:{
					index:groupData[ptext].gIndex,
					name:text
				},
				type:'POST',
				success:function(data){
					console.log(data);
					if(data == 1) {
						groupData.modifyKey(ptext, text);
						createLiTag(text, par);
					} else {
						alert('서버에서 정보가 잘못 입력되었습니다. 다시 시도해 주세요');
					}
				}
			});
			
//			createLiTag(text, par);
//			groupData.modifyKey(ptext, text);
		})).append($('<button class="cancel-btn">취소</button>').on('click', function(){
			
			createLiTag(ptext, par);
		}));
		
		//ths.remove();
	}));
}

var ismouseDown=false;	

function enrollListEvents(){
	
	addDragGroupFunctionsTo($('.group-list-wrapper .ul-list li'));
	
	
	addGroupULContainerDropEvent($('.group-list-wrapper .ul-list'));
	
	/**
	 * 추가된 사항.
	 */
	checkEachSlotIsEmpty();
	
	
	addDragFrameFunctionsTo($('.frame-list-wrapper .ul-list li'));
	
	
	addFrameULContrainerDropEvent($('.frame-list-wrapper .ul-list'));
	
}

function checkEachSlotIsEmpty(){
	if($('.group-list-wrapper .ul-list li').length != 0){
		$('.group-list-wrapper .data-info-panel').css('display', 'none');
	} else {
		$('.group-list-wrapper .data-info-panel').css('display', 'block');
	}
	
	if($('.frame-list-wrapper .ul-list li').length != 0){
		$('.frame-list-wrapper .data-info-panel').css('display', 'none');
	} else {
		$('.frame-list-wrapper .data-info-panel').css('display', 'block');
	}
}

function globalMapDataStructureInit(datas){
//	groupData = new GroupData();
	$.each(datas, function(index, element, arrays){
		groupData.addKey(element.text, element.value);
	});
}

function onSelectionMethodTask(data){
	if(groupData.selectedIndex == data.selectedIndex){
    	
    } else {
		groupData.setSelectedIndex(data.selectedIndex);
	    groupData.setSelectedKey(data.selectedData.text);
	    
	    console.log(groupData[groupData.selectedKey]);
	    
	    
	    $('.group-list-wrapper .ul-list').empty();
	    //데이터 초기화..
	    $.each($(groupData[groupData.selectedKey]), function(index, element, array){
	    	//console.log(element);
	    	$(element).appendTo($('.group-list-wrapper .ul-list'));
	    	addDragGroupFunctionsTo($(element));
	    });
	    
	    checkEachSlotIsEmpty();
    }
}

isSaveTime = false;

$(window).bind('beforeunload', function() {
	if(!isSaveTime){
		return '나가시면 변동한 내용은 저장되지 않습니다.';
	}
}); 


function addDragFrameFunctionsTo(listGroup){
	
	listGroup.addClass('frame-contained')
	.removeClass('group-contained')
	.off()
	.on('click', function(e){
		if($(this).is('.selected')){
			$(this).removeClass('selected');
		} else {
			$(this).addClass('selected');
		}
		
		$('.group-contained').removeClass('selected');
		
	})
	.on('mousemove', function(){
		return false;
	})
	.on( 'dragstart', function( event, element ){
		$.drop({ mode: 'mouse' });
		this.ele= $( this ).clone()
			.appendTo( $('body') )
			.css({
				opacity: .2,
				position:'absolute',
				top: event.offsetY,
				left: event.offsetX
				});
		event.dragProxy = this.ele;
		this.ele.orign = this;
		ismouseDown = true;
		return this.ele;
		
		})
	.on( 'drag', {distance: 10},function( event ){
		this.ele.css({ top:event.pageY-20, left:event.pageX-150 });
		})
	.drag("init",function(){
		if ( $( this ).is('.selected') )
			return $('.selected');
	})
	.on( 'dragend', function( event ){
		this.ele.fadeOut(function(){ $(this).remove() });
		this.ele = undefined;
	});
	
}

function addDragGroupFunctionsTo(listGroup){
	listGroup.addClass('group-contained')
	.removeClass('frame-contained')
	.off().on('click', function(e){
		if($(this).is('.selected')){
			$(this).removeClass('selected');
		} else {
			console.log($(this));
			$(this).addClass('selected');
		}
		$('.frame-contained').removeClass('selected');
	})
	.on( 'dragstart', function( event ){
		$.drop({ mode: 'mouse'/*'overlap'*/ });
		this.ele= $( this ).clone()
			.appendTo( $('body') )
			.css({
				opacity: .2,
				position:'absolute',
				top: event.offsetY,
				left: event.offsetX
				});
		event.dragProxy = this.ele;
		this.ele.orign = this;
		ismouseDown = true;
		return this.ele;
		
		})
	.on( 'drag', {distance: 10},function( event ){
		this.ele.css({ top:event.pageY-20, left:event.pageX-150 });
		})
	.drag("init",function(){
		if ( $( this ).is('.selected') )
			return $('.selected');
	})
	.on( 'dragend', function( event ){
		this.ele.fadeOut(function(){ $(this).remove() });
		this.ele = undefined;
		console.log(this.ele);
		});
}

function addGroupULContainerDropEvent(ulElement){
	ulElement.off()
	.on( 'dropstart',function(event, element){
		if(groupData.selectedIndex != -1){
			
			var isTargetSuit = $(element.drag).is('.group-contained');
			if(isTargetSuit) return !isTargetSuit;
			
			$( this ).addClass('active');
			$('.group-list-wrapper .drag-info-panel').css('display', 'block');
			}
		}
	)
	.on( 'drop', function( event , eles){
		if(groupData.selectedIndex != -1){
		
			console.log('dropped', eles);
			var isTargetSuit = $(eles.drag).is('.group-contained');
			if(isTargetSuit) return !isTargetSuit;
			
			$(eles.drag).removeClass('frame-contained')
			.addClass('group-contained')
			.off('click').on('click', function(e){
				if($(this).is('.selected')){
					$(this).removeClass('selected');
				} else {
					console.log($(this));
					$(this).addClass('selected');
				}
				$('.frame-contained').removeClass('selected');
			})
			.removeClass('selected')
			.appendTo(this);
			
			groupData.pushData(eles.drag);
			
			var other = $('.frame-list-wrapper .ul-list');
			if(other.find('li').length == 0){
				other.parent().find('.data-info-panel').css('display', 'block');
			}
			
			$(this).parent().find('.data-info-panel').css('display', 'none');
			ismouseDown = false;
			}
		}
	
	)
	.on( 'dropend', function( event, eles ){
		if(groupData.selectedIndex != -1){
			
			$( this ).removeClass('active');
			$('.group-list-wrapper .drag-info-panel').css('display', 'none');
		}
		});
}


function addFrameULContrainerDropEvent(ulElement){
	ulElement.off().on( 'dropstart',function(event, element){
		var isTargetSuit = $(element.drag).is('.frame-contained');
		if(isTargetSuit) return !isTargetSuit;
		
		$( this ).addClass('active');
		$('.frame-list-wrapper .drag-info-panel').css('display', 'block');
		})
	.on( 'drop', function( event, eles ){
		ismouseDown = false;
		
		var isTargetSuit = $(eles.drag).is('.frame-contained');
		if(isTargetSuit) return !isTargetSuit;
		
		groupData.removeData(eles.drag);
		
		$(eles.drag).removeClass('group-contained')
		.addClass('frame-contained')
		.off('click').on('click', function(e){
			if($(this).is('.selected')){
				$(this).removeClass('selected');
			} else {
				console.log($(this));
				$(this).addClass('selected');
			}
			$('.group-contained').removeClass('selected');
		})
		.removeClass('selected')
		.appendTo(this);
		
		var other = $('.group-list-wrapper .ul-list');
		if(other.find('li').length == 0){
			other.parent().find('.data-info-panel').css('display', 'block');
		}
		$(this).parent().find('.data-info-panel').css('display', 'none');
		
		})
	.on( 'dropend', function( event, eles ){
		$( this ).removeClass('active');
		$('.frame-list-wrapper .drag-info-panel').css('display', 'none');
		
		});
}