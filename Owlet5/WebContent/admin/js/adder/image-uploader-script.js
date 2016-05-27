var isImageLimitActivated = false;
/**
 *  super man's script. i am a super man!

 */
 /**
 * 사진 등록 슬롯의 정보를 담는다.
 * 아직 안 쓰이는 것도 있다.
 */
function Box(index, row, col, group){
	this.index = index;
	this.group = group;
	this.width = 135;							//reserved
	this.height = 135;							//reserved
	this.topleft = [index%col, Math.floor(index/col)];			//덕환이의 알고리즘을 위한 자료구조
	this.rightbottom = [index%col, Math.floor(index/col)];		//이는 아래의 코딩을 가능하게 하였다.
}
/**
 * 등록 슬롯 정보 제어기.
 * 눈에 보이는 모든 처리를 담당한다.
 */
function BoxController(row, column){
	this.indexValue = 0;										//reserved
	this.selectedIndex = -1;  									//none of slot is selected
	this.row = row;
	this.column = column;
	this.arrays = new Array();
	
	//박스정보 넣어야지..
	for(var i = 0; i<row*column; i++){
		this.arrays.push(new Box(
				this.indexValue,
				this.row,
				this.column,
				this.indexValue++)
		);
	}
	
	this.renderDiv();											//화면에 사각형 그리고...
	this.isDrag = 5;											//숫자 패드가 방향입니다. 5는 방향이 아니에요, 클릭 안했단 뜻이야.
	this.calculatedTopleft = [];
	this.calculatedRightbottom = [];
	
	var instance = this;
	
	/**
	 * 박스 조작을 위한 이벤트 동작 등록부. 간략한 설명.
	 * 
	 * 클릭하면, 8개의 꼭지점을 생성한다.
	 * 
	 * 8개의 꼭지점을 클릭한 후 드래그하면, 인덱스 정보로 좌상단 우하단 경계를 조사하며, 가상의 사각형으로 크기 변화를 보여준다.
	 * 
	 * 마우스를 떼면, 가상의 사각형과 현 사각형의 크기를 일치시킨다.
	 * 
	 * 이때, 고려해야할 사항은 크게 2가지이다.
	 * 
	 *  첫째, 확장
	 *  
	 *       이때는 가려지는 요소가 지워지어야 한다. 모든 사각형 요소를 탐색하며 갱신된 사각형의 좌상단 우하단 정보를 기반으로 그 내부에 포함되는 사각형이 있는지 살핀다.
	 *       
	 *       	이 때 두 가지 처리를 해야 하는데,
	 *       
	 *       	하나는 완전 포함되는 경우이고 두번째는 부분 포함되는 경우이다. 전자의 경우, 아예 삭제하면 된다. 허나, 후자의 경우는 남은 영역에 대하여만 모두 단일 사각형을 만들어야 한다.
	 *  둘째, 축소
	 *  
	 *  	 이때는 가려지는 요소란 없다. 내 영역에서 비게 되는 부분을 반드시 사각형으로 채우기만 하면 된다.
	 *  
	 *  이상.
	 * 
	 */
	$('.pic-slot-box-wrapper').off('mousedown mousemove mouseup mouseleave');
	$('.pic-slot-box-wrapper').on('mousedown', function(e){
	}).on('mousemove', function(e){
		if(instance.isDrag != 5){
			var width = Math.floor(parseInt($(this).css('width'))/instance.column);
			var height = Math.floor(parseInt($(this).css('height'))/instance.row);
			
			var wrapperOffset = $(this).offset();

			var xPos = e.pageX - wrapperOffset.left;
			var yPos = e.pageY - wrapperOffset.top;
			
			xPos = Math.abs(xPos-2);
			yPos = Math.abs(yPos-2);
			
			var xIndex = Math.floor(xPos/width);
			var yIndex = Math.floor(yPos/height);
			
			if(isNaN(xPos) || isNaN(yPos)){
				return;
			}
			//nMove는 가운데가 1, 
			
			//클릭된 점은 instance.isDrag 정보이다.
			//정규화 값에 절대값을 1 추가해야 한다.
			var target = $('.pic-slot-selected');
			
			//바깥 요소가 딱 1번씩 드래그 범위를 벗어난다...
			
			var tl = instance.arrays[target.attr('data-index')].topleft;
			var rb = instance.arrays[target.attr('data-index')].rightbottom;
			
			tl = tl.slice(0);
			rb = rb.slice(0);
			
			//왜 number로 강제치환해야 하는가...
			switch(Number(instance.isDrag)){
				case 1:
					if(xIndex <= rb[0])
						tl[0] = xIndex;
					if(yIndex >= tl[1])
						rb[1] = yIndex;
					break;
				case 2:
					if(yIndex >= tl[1])
						rb[1] = yIndex;
					break;
				case 3:
					if(xIndex >= tl[0])
						rb[0] = xIndex;
					if(yIndex >= tl[1])
						rb[1] = yIndex;
					break;
				case 4:
					if(xIndex <= rb[0])
						tl[0] = xIndex;
					break;
				case 6:
					if(xIndex >= tl[0])
						rb[0] = xIndex;
					break;
				case 7:
					if(xIndex <= rb[0])
						tl[0] = xIndex;
					if(yIndex <= rb[1])
						tl[1] = yIndex;
					break;
				case 8:
					if(yIndex <= rb[1])
						tl[1] = yIndex;
					break;
				case 9:
					if(xIndex >= tl[0])
						rb[0] = xIndex;
					if(yIndex <= rb[1])
						tl[1] = yIndex;
					break;
			}
			
			var wdistance = rb[0] - tl[0]+1;
			var hdistance = rb[1] - tl[1]+1;
			
			var calculatedWidth = width*wdistance;
			var calculatedHeight = height*hdistance;
			var calculatedLeft = tl[0]*width;
			var calculatedTop = tl[1]*height;
			var calculatedIndex = tl[0] + tl[1]*instance.column;
			
			if(isNaN(calculatedLeft) || isNaN(calculatedTop) || isNaN(calculatedWidth) || isNaN(calculatedHeight)){
				return;
			}
			
				var substitute;
				
				if(!$('.virtual-box')[0]){
					substitute = $('<div></div>').addClass('virtual-box');
					substitute.appendTo($('.pic-slot-box-wrapper'));
				} else {
					substitute = $('.virtual-box');
				}
				
				substitute
				.css('width', calculatedWidth-6-2)
				.css('height', calculatedHeight-6-2)
				.css('left', calculatedLeft)
				.css('top', calculatedTop)
				.attr('data-index', calculatedIndex);
				;
				
				instance.calculatedRightbottom = rb;
				instance.calculatedTopleft = tl;
		}
	}).on('mouseup', function(e){
		instance.isDrag = 5;
		var virtualBox = $('.virtual-box');
		if(!virtualBox[0]){
			return false;
		}
		
		var selectedBox = $('.pic-slot-selected');
		
		selectedBox
		.css('width', virtualBox.css('width'))
		.css('height', virtualBox.css('height'))
		.css('left', virtualBox.css('left'))
		.css('top', virtualBox.css('top'))
		;
		//할일... 211, 212의 인덱스 값을 변경한다. 319구문 해결한다... ---------▽의 구문으로 해결. 된 듯 했으나.. 모두 attr로 처리.. 아 뭐가 어떻게 된거야!!
		var toBeChangedIndex = 0;
		if(instance.arrays[$('.pic-slot-selected').attr('data-index')]/* && virtualBox.data('index')*/) {
			if(virtualBox.attr('data-index')){
				toBeChangedIndex = virtualBox.attr('data-index'); 
			}
			if(instance.calculatedRightbottom.length == 0 || instance.calculatedTopleft.length == 0){
				return false;
			}
			//겹칩 요소를 지워요. 쪼개거나 빈 공간을 채워요.
			
			//새 영역의 모든 인덱스를 구한다.
			var newTl = instance.calculatedTopleft.slice(0);
			var newRb = instance.calculatedRightbottom.slice(0);
			var indexes = [];
			
			for(var i = newTl[1]; i <= newRb[1]; i++){  //i는 y 변수 , j는 x 변수.
				for(var j = newTl[0]; j <=newRb[0]; j++){
					indexes.push(i*instance.column + j);
				}
			}
			//그룹을 살핀다. > 그룹이 다르면 같게 맞춘다. tl과 rb 정보까지! 바꾸기 전, 해당 div를 제거한다(모든 거 다 지우고 다시 집어넣으면 되지).
			
			var group = -1;
			group = instance.arrays[$('.pic-slot-selected').attr('data-index')].group;
			var boxinfo;
			for(var i = 0; i<indexes.length; i++){
				boxinfo = instance.arrays[indexes[i]];
				//비교시 그룹값은 추가/제거 여부를 판단하는데 적합하다. index로 기존 위치를 알아야 한다.
				if(boxinfo.group != group){
					if(boxinfo.topleft.equals(boxinfo.rightbottom)){ //1칸짜리다.
						$('.pic-slot-box[data-index="'+indexes[i]+'"').remove();
						boxinfo.group = group;
						boxinfo.topleft = newTl.slice(0);
						boxinfo.rightbottom = newRb.slice(0);
					} else {//한칸이 아니다.
						//같은 그룹의 인덱스 모두 얻어오고, 모두 일일이 하나로 만든다. //코드가 지저분하군... -> 인덱스들 찾을 필요가 없다... 어차피 하나일 것 아닌가...
						var subindexes = [];
						
						for(var w = boxinfo.topleft[1]; w <= boxinfo.rightbottom[1]; w++){//w는 세로, j는 가로
							for(var j = boxinfo.topleft[0]; j <=boxinfo.rightbottom[0]; j++){
								subindexes.push(w*instance.column + j);
							}
						}//이 인덱스들에 하나하나 새 블럭을 생성시킨다.. 그러나, 지금 현재 group과 같아선 아니 된다.
						var mainIndex = boxinfo.topleft[0] + boxinfo.topleft[1]*instance.column;
						$('.pic-slot-box[data-index="'+mainIndex+'"').remove();				
						
						boxinfo.group = group;
						boxinfo.topleft = newTl.slice(0);
						boxinfo.rightbottom = newRb.slice(0);
						
						//group이 아닌 곳을
						for(var k = 0; k < subindexes.length; k++){
							if(instance.arrays[subindexes[k]].group != group) {
								instance.arrays[subindexes[k]].group = instance.indexValue++;
								instance.arrays[subindexes[k]].topleft = [subindexes[k]%instance.column, Math.floor(subindexes[k]/instance.column)];
								instance.arrays[subindexes[k]].rightbottom = [subindexes[k]%instance.column, Math.floor(subindexes[k]/instance.column)];
							
								instance.createNewPictureBox(subindexes[k]);
							} else {
							}
						}
					}
				} else {  //추적한 위치 값의 그룹값이 같다면, tl, rb만을 갱신해 주어야 합니다.
					boxinfo.group = group;
					boxinfo.topleft = newTl.slice(0);
					boxinfo.rightbottom = newRb.slice(0);
				}
			}
			//광속으로 정보를 찾아라. 축소는 현 상태의 그룹만 사라지면 되는 것이뢰다.
			//group변수가 바로 그놈이제.
			//유효 범위가 아니라는 판단 기준은 다음과 같다.
			/*
			 * 실제 변경된 tl, rb의 값들이 서로 다르다는 것!
			 */
			var targetBox;
			for(var l = 0; l < instance.row*instance.column; l++){
				targetBox = instance.arrays[l]; 
				if(targetBox.group == group) {//적용 타겟!
					if(targetBox.topleft.equals(newTl.slice(0)) && targetBox.rightbottom.equals(newRb.slice(0))){
					} else {
						//갱신하고 그려요.. 중복 코드라니..
						targetBox.group = instance.indexValue++;
						targetBox.topleft = [l%instance.column, Math.floor(l/instance.column)];
						targetBox.rightbottom = [l%instance.column, Math.floor(l/instance.column)];
						
						instance.createNewPictureBox(l);
					}
				}
			}
			
			if(virtualBox.attr('data-index')){
				selectedBox.attr('data-index', virtualBox.attr('data-index'));
			}
			$('.virtual-box').remove('.virtual-box');
		}
		return false;
	})
	.on('mouseleave', function(e){
		instance.isDrag = 5;
		var virtualBox = $('.virtual-box');
		$('.virtual-box').remove('.virtual-box');
		return false;
	})
	;
}

BoxController.prototype.getSelectedIndex = function(e){
	//css 클래스의 값을 이용한다!
	//그 값을 분리하여, 반환하도록 하자.
	return this.selectedIndex;
}

BoxController.prototype.setSelectedIndex = function(index){
	this.selectedIndex = index;
}

/**
 * 8개의 꼭지점 생성
 * @param instance
 * @param target
 */
BoxController.prototype.createEightPoint = function(instance, target){
	//해당 인덱스를 둘러싸세요.
	for(var j = 1; j <= 9; j++){
		if(j == 5) continue;
		$('<div></div>').addClass('point-box point-'+j).appendTo(target);
	}
	$("div[class^='point-']")
	.on('mousedown', function(e){
		//당기는 방향 체크 필요.
		instance.isDrag = $(this).attr('class').split(' ')[1].substring(6);
		
		instance.calculatedRightbottom = instance.arrays[$('.pic-slot-selected').attr('data-index')].rightbottom;
		instance.calculatedTopleft = instance.arrays[$('.pic-slot-selected').attr('data-index')].topleft;
		
		return false;
	});
}

/**
 * 첫 사각형의 생성.
 */
BoxController.prototype.renderDiv = function(){
	//그려요. 무조건 이건 1회 호출될 겁니다.
	//왜냐하면 처음에는 반드시 모두 1칸만을 차지할 것입니다.
	var instance = this;
	for(var i = 0; i < this.arrays.length; i++){
		this.createNewPictureBox(i);
	}
}

/**
 * 단일 사각형 생성.
 * @param i
 */
BoxController.prototype.createNewPictureBox = function(i){
	var instance = this;
	
	var parent = $('.pic-slot-box-wrapper');
	
	var box = $('<div></div>').addClass('pic-slot-box').attr('data-index', this.arrays[i].index)
	.on('click', function(){ 
		instance.setSelectedIndex(instance.arrays[$(this).attr('data-index')].index);
		$('.pic-slot-box').children().remove('div[class^="point-"]');
		if($(this).hasClass('pic-slot-selected')){
			$('.pic-slot-box').removeClass('pic-slot-selected');
			return;
		} else {
			$('.pic-slot-box').removeClass('pic-slot-selected');
			$(this).addClass('pic-slot-selected');
			instance.createEightPoint(instance, $(this));
		}
	})
	.appendTo($('.pic-slot-box-wrapper'));
	
	var margin = 1;//parseInt(box.css('margin'));
	var border = 3;//parseInt(box.css('border-width'));
	var width = Math.floor(parseInt(parent.css('width'))/instance.column);
	var height = Math.floor(parseInt(parent.css('height'))/instance.row);
	
	box.css({
		'left':	(i%instance.column)*(width),
		'top' :	(Math.floor(i/instance.column))*(height),
		'width' : (width-margin*2-border*2),
		'height': (height-margin*2-border*2)
	}).droppable({
		accept:'.pic-box',
		drop: function(event, ui) {
			$(this).children().remove('img, div[class="remove-div-element"]');
			
			var targetImg = $(ui.draggable).find('img').clone();
			targetImg.appendTo($(this));
			
			$('<div></div>').addClass('remove-div-element').on('click',function(e){
			//remove this and iamges.
				$(this).parent().children().remove('img, div[class="remove-div-element"]');
				return false;
			}).appendTo($(this)).html('X');
			
			$(this).find('img').off('mousedown')
			.on('mousedown', function(e){return false;});
		}
	});
}

BoxController.prototype.setArraySize = function(row, column){
	this.indexValue = 0;
	this.arrays = new Array();
	
	for(var i = 0; i<i<row*column; i++){
		this.arrays.push(new Box(this.indexValue, this.indexValue++));
	}
}

function adaptTemplate(){
	delete boxCont;
	$('.pic-slot-box-wrapper').html('');
	boxCont = new BoxController($('.row-btn-selected').attr('data-value'), $('.column-btn-selected').attr('data-value')); 
}

Array.prototype.equals = function (array) {
    // if the other array is a falsy value, return
    if (!array)
        return false;

    // compare lengths - can save a lot of time 
    if (this.length != array.length)
        return false;

    for (var i = 0, l=this.length; i < l; i++) {
        // Check if we have nested arrays
        if (this[i] instanceof Array && array[i] instanceof Array) {
            // recurse into the nested arrays
            if (!this[i].equals(array[i]))
                return false;       
        }           
        else if (this[i] != array[i]) { 
            // Warning - two different object instances will never be equal: {x:20} != {x:20}
            return false;   
        }           
    }       
    return true;
}

var fileNumberLoaded = 0;

/**
 * 파일 업로딩에 관한 상세를 등록.
 */
$(document).ready(function(){
	
	adaptTemplate();
	
	$('.row-btn').on('click', function(e){
		$('.row-btn').removeClass('row-btn-selected');
		$(this).addClass('row-btn-selected');
		adaptTemplate();
	});
	
	$('.column-btn').on('click', function(e){
		$('.column-btn').removeClass('column-btn-selected');
		$(this).addClass('column-btn-selected');
		adaptTemplate();
	});

	if (window.File && window.FileList && window.FileReader){}else{alert("브라우저 사양이 기술을 지원하지 못합니다. 브라우저를 업데이트 하십시오."); return;}
	
	fileNumberLoaded = 0;
	data = [];
	enrollFileEvent();
	
	$('.btn-add-picture').on('click', doFileUploading);
	
	$('.btn-delete-picture').on('click', doFileDeleting);
	
	$('.btn-selective-delete-picture').on('click', doSelectiveDeleting);
	
	$('.image-submit-btn').on('click', doMakingTagFormatAndSubmit);
	
	$('.input-limit-size').on('click', function(e){
		if($(this).is(":checked")){
			delete boxCont;
			$('.pic-slot-box-wrapper').html('');
			boxCont = new BoxController(1, 1);
			$('.block-template').css('display', 'block');
			isImageLimitActivated = true;
//			$('.pic-slot-box').css('background-size', 'contain');
		} else {
			adaptTemplate();
			$('.block-template').css('display', 'none');
			isImageLimitActivated = false;
//			$('.pic-slot-box').css('background-size', 'normal');
		}
		//disable template;
	});
});

/**
 * 파일 업로드 클릭시 멀티플 인풋을 활성화한다.
 */
function doFileUploading(){
	if(fileNumberLoaded >= $('.file-element').length){
		alert(" 더 이상 가져올 수 없습니다. ");
		return;
	} 
	$('.file-element-multiple').trigger('click');
}


function doFileDeleting(){
	fileNumberLoaded = 0;
	$('.pic-box').remove();
	for(var i = 0; i < 9; i++){
		$('<div></div>').addClass('pic-box').attr('data-index', i)
		.html('<input type="file" class="file-element">')
		.css('background', 'url("http://placehold.it/50x50") center')
		.appendTo('.left-picture-list-container')
		;
	}
	enrollFileEvent();
	$('.pic-slot-box').children().remove('img, div[class="remove-div-element"]');
	$('.file-element-multiple').val('');
	data = [];
}

function enrollFileEvent(){
		$('.file-element-multiple').off('change').on('change', function(e){
			var basement = $(this);
			var isContinue = false;
			var notAllowedIndexes = [];
			var j = 0, num = 0, k = $(this)[0].files.length;
			
			for (var i = 0; i < k; i++) {
				if(isContinue){
					continue;
				}
				loadedUnidentifiedFile = $(this)[0].files[i];
				//타입 체크
				if (!loadedUnidentifiedFile.type.match('image')) {
					logging(loadedUnidentifiedFile.name + "> 해당파일은 이미지 파일이 아닙니다");
					notAllowedIndexes.push(i);
				} else if(loadedUnidentifiedFile.size > 5242880){ //1024*1024*5 -> 5MB
					logging(loadedUnidentifiedFile.name + "> 파일 용량이 너무 큽니다. 5MB 이하로 등록 부탁드립니다.");
					notAllowedIndexes.push(i);
				} else 
				if(j < 9-fileNumberLoaded){
			        var reader = new FileReader();
			        
			        reader.myTraceValue = fileNumberLoaded + j;
			        
			        $(reader).on('load', function (evt) {
//			        	console.log($(this)[0].myTraceValue + "번이 로딩이 먼저 되었다");
			            if (evt.target.readyState == FileReader.DONE) {
			            	while($.inArray(num, notAllowedIndexes) !== -1){
			            		num++;
			            	}
			            	data[$('.pic-box').eq(fileNumberLoaded).attr('data-index')-1] = $('.file-element-multiple')[0].files[num];
//			            	data[$('.pic-box').eq($(this)[0].myTraceValue).attr('data-index')-1] = $('.file-element-multiple')[0].files[num];
			                
//			                var parent = $('.pic-box').eq(fileNumberLoaded);
			            	var parent = $('.pic-box').eq($(this)[0].myTraceValue);
			                
			                fileNumberLoaded++;
			                var imageToBeAttached = $('<img/>').attr('src', $(this)[0].result)
			    			.attr('data-index', parent.attr('data-index'))
			    			;
			    			
			    			imageToBeAttached
			    			.css('width', '100%')
			    			.css('height', '100%')
			    			.appendTo(parent);
			    			;
			    			
			    			parent.draggable({
			    				helper:'clone',
			    				zIndex:100,
			    				scroll:false,
			    				revert:'invalid'
			    			}).off('click').on('click', function(){
			    				if($(this).hasClass('pic-box-selected')){
			    					$(this).removeClass('pic-box-selected');
			    				} else {
			    					$(this).addClass('pic-box-selected');
			    				}
			    			});
			    			num++;
			    			if(k == num){
			    				num = 0;
			    			}
			            }
			        });
		        
		        	reader.readAsDataURL($(this)[0].files[i]);
			        j++;
				} else {
				}
		    }
			
			
		});
	}

function doSelectiveDeleting() {
	var target = $('.pic-box.pic-box-selected');
	var length = target.length;
	fileNumberLoaded -= length;
	
	//삭제된 놈들의 인덱스를 찾는다.
	var deletedIndexes = [];
	target.each(function(i, el){
		deletedIndexes.push($(this).attr('data-index'));
	});
	
	//삭제될 놈들을 제거한다.
	$('.pic-slot-box img').each(function(i, el){
		if($.inArray($(this).attr('data-index'), deletedIndexes) != -1){
			$(this).parent().children().remove('img, div[class="remove-div-element"]');
		}
	});
	var tempData = data.slice(0);
	data = [];
	for(var i = 0; i < tempData.length; i++){
		if($.inArray((i+1)+"", deletedIndexes) === -1){
			data.push(tempData[i]);
		} else {
			data.push(undefined);
		}
	}
	
	target.remove();
	
	for(var i = 0; i < length; i++){
		$('<div></div>').addClass('pic-box').attr('data-index', deletedIndexes[i])
		.html('<input type="file" class="file-element">')
		.css('background', 'url("http://placehold.it/50x50") center')
		.appendTo('.left-picture-list-container')
		;
	}
	enrollFileEvent();
}


/**
 * 쿼리 전송하는 넘이다. 그런데 데이터 전송에 많은 애를 먹었다..
 */
function doMakingTagFormatAndSubmit(){
	if($('.pic-slot-box img').length == 0){
		alert("이미지가 없습니다."); 
		return;
	}
	
	var datasOfFormsButNotActualForm = new FormData();
	
	var imageAttachedIndexes = [];
	$('.pic-slot-box img').each(function(index, element){
		if($.inArray($(this).attr('data-index'), imageAttachedIndexes) === -1)
			imageAttachedIndexes.push($(this).attr('data-index'));
	});
	
	$('.pic-box img').each(function(i, el){
	});
	
	for(var indexOfLoaded = 0; indexOfLoaded < imageAttachedIndexes.length; indexOfLoaded++){
		datasOfFormsButNotActualForm.append('file', data[imageAttachedIndexes[indexOfLoaded]-1]);
	}
	
	//console.log(imageAttachedIndexes);
	
	$.ajax({
		url: '/file/uploadAdderFile.do',
        type: "post",
        dataType: "text",
        data: datasOfFormsButNotActualForm,
        async:true,
        cache: false,
        processData: false,
        contentType: false,
        success: function(data, textStatus, jqXHR) {
        	var pathDatas = $.parseJSON(data);
        	$('.pic-slot-box-wrapper img').each(function(index , el){
        		//$(this).attr('src', pathDatas[$(this).attr('data-index')-1]);
	        		$(this).attr('src', pathDatas[$.inArray($(this).attr('data-index'), imageAttachedIndexes)]+"");
	        		//console.log($(this).attr('data-index'), $.inArray($(this).attr('data-index'), imageAttachedIndexes), pathDatas[$.inArray($(this).attr('data-index'), imageAttachedIndexes)]);
        	});
        	
        	//console.log(pathDatas);
        	$('.pic-slot-box-wrapper').parent().find('div[class="remove-div-element"], div[class^="point-"]').remove();
        	
        	var html = $('.pic-slot-box-wrapper').parent().html();
        	
        	changeStyleOfTagElementsToBeAttachedIntoAddersContents();
        	
        	if(!isImageLimitActivated) 
        		html = $('.pic-slot-box-wrapper').parent().html();
        	else 
        		html = $('.pic-slot-box-wrapper').html();
        	
        	window.opener.getReturnValueImage(html);
        	window.close();
        },
        error: function(jqXHR, textStatus, errorThrown) {}
    });
}

/**************************************
 
  드래그를 구현하는 방법은 2가지가 있다.
   첫째는 http://www.w3schools.com/html/html5_draganddrop.asp를 참조하는 것,
   둘째는 JQuery-UI 플러그인을 사용하는 것.
   
   필자는 후자를 택한다.
 
***************************************/

function adaptDragFunction(){
}
/**
 * 이미지를 전송하기 위해 Adder에 적용될 스타일을 일일이 박아두는게 편리하였다.
 * 아예 그대로 태그를 박아두고 stylesheet를 적용하는 방법이 있으나, 별도 파일을 동일하게 만들어 관리하는 게 싫었다.
 */
function changeStyleOfTagElementsToBeAttachedIntoAddersContents(){
	//get All styles of superContainer;
	$('.pic-slot-box-wrapper').css({
		'text-align':'center',
		'margin':'0 auto',
		'width':'429px',
		'height':'429px',
		'position':'relative'
	}).off();
	//get All styles of each pic-slot-box division.
	if(!isImageLimitActivated){
		$('.pic-slot-box').each(function(index, element){
			$(this).find('img').css({
				//'width':$(this).css('width'),
				//'height':$(this).css('height'),
				'width':parseInt($(this).css('width'))/429*100+'%',
				'height':parseInt($(this).css('height'))/429*100+'%',
				'margin':'1px',
				'position':'absolute',
				'left':parseInt($(this).css('left'))/429*100+'%',
				'top':parseInt($(this).css('top'))/429*100+'%',
			}).appendTo($('.pic-slot-box-wrapper')).off().removeClass()
			.parent();//.off().removeClass().remove();//end의 기능이 뭐여..
		}).remove();
	} else {
		$('.pic-slot-box').each(function(index, element){
			$(this).find('img').css({
				
			}).removeAttr('style')
			.css('width', '100%')
			.css('width', '100%')
			.appendTo($('.pic-slot-box-wrapper')).off().removeClass()
			.parent();
		}).remove();
	}
}


function logging(msg){
	$('.log-console').append("<br>"+msg);
}