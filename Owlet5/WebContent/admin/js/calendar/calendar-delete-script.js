/**
 * 
 */

$(document).ready(function() {
	
	var deletedTime = 400;
	var movingTime = 400;
	
	$('.check-all').click(function() {
		$(".delete").prop('checked', this.checked);
	});
	
	$('.item-slot').off('click').on('click', function(e){
		if($(this).is('.selected')){
			$(this).removeClass('selected');
		} else {
			$(this).addClass('selected');
		}
	}).off('mouseenter').on('mouseenter', function(e){
		var btn = $(this).find('.x-btn');
		btn.css('display', 'block')		
		.off('click').on('click', function(e){
			//remove this after asyn ajax...
			var isOk = confirm('정말로 선택한 요소들을 삭제하시겠습니까?');
			if(!isOk)
				return;
			
			$(this).parent().animate({opacity:0}, 
					{ duration: deletedTime,
					  queue: false,
					  complete :function(){/*$(this).remove();*/
						  
						  var nowIndex = $('.item-slot').index($(this));
						  //alert(nowIndex);
						  var list = $('.item-slot:gt('+nowIndex+')');
//						  $(this).remove();
						  console.log(list, list.length);
						  
						  var targetToBeDeleted = $(this);
						  
						  var outLeft = $('.item-container').position().left;
						  
						  function completeFunc(ele){
							 return function(){
								 //$('.item-slot').removeAttr('style');
//								 if($('.item-slot').index($('.item-slot').last()) == $('.item-slot').length-1){
//								 if($('.item-slot').index($('.item-slot').last()) == ele){
								 console.log($(ele).attr('data-index'), $('.item-slot:last').attr('data-index'));
//								 if($('.item-slot').last() == $(ele)){
								 if($(ele).attr('data-index') == $('.item-slot:last').attr('data-index')){
									 callAsynDeleteURL(new Array(targetToBeDeleted.find('.values').text()));
									 targetToBeDeleted.remove();
								 }
								 $(this).removeAttr('style');
							 }
						  }
						  
						  list.each(function(index, ele){
							 var left = $(this).position().left - outLeft;
							 //console.log(left);
							 //$(this).css('left', left);
							 if( left > 3){
								 $(this).animate({left:'-=269.59375'}, movingTime,
									completeFunc(ele)
									);
							 } else {
								 if($(this).position().top - $(this).parent().position().top > 3)
									 $(this).animate({left:'+=539.1875', top:'-=200'}, movingTime,
									completeFunc(ele)
									);
							 }
						  });
						  if(list.length == 0){
							  callAsynDeleteURL(new Array($(this).find('.values').text()));
							  $(this).remove();
						  }
					  } 
			});
			
		});
	}).on('mouseleave', function(e){
		$(this).find('.x-btn').css('display', 'none');
	});
	
	$('.delete-part-btn').on('click', function(){
		
		var nums = $('.selected').length;
		
		if(nums == 0){
			alert('선택하신 요소가 없습니다.');
			return;
		}
		
		var isOk = confirm('정말로 선택한 요소들을 삭제하시겠습니까?');
		if(!isOk)
			return;
		
		var array = [];
		$('.selected').each(function(i, e){
			array.push($(this).find('.values').text());
			$(this).animate({opacity:0}, {duration:deletedTime, queue:false, complete:
		
			function(){
				$(e).remove();
			}});
		});
		callAsynDeleteURL(array);
	});
	
	$('.delete-all-btn').on('click', function(){
		
		var isOk = confirm('정말로 모든 요소를 삭제하시겠습니까?');
		if(!isOk)
			return;
		
		var array = [];
		$('.item-slot').each(function(i, e){
			array.push($(this).find('.values').text());
			$(this).animate({opacity:0}, {duration:deletedTime, queue:false, complete:
				
			function(){
				$(e).remove();
			}});
		});
		callAsynDeleteURL(array);
	});
	
	$('.came-back-btn').on('click', function(){
		history.back();
	});
});

function callAsynDeleteURL(array){
	console.log(array);
	$.ajax({
		url : '/calendar/bulkDelete.do',
		type : 'POST',
		data : {
			tcp : '',
			del_check : array
		},
		success : function(data) {
			//alert(data);
			console.log(data);
		},
		traditional : true
	});
}