/**
 * script.
 */

$(document).ready(function(e){
	$('body').on("selectstart", function(event){ return false; });
    $('body').on("dragstart", function(event){ return false; });
    $('.next-btn').on('click', function(e){
    	location.assign('#');
    });
    $('.prev-btn').on('click', function(e){
    	location.assign('/admin/skinConfiguration.jsp');
    });
    $('.next-btn').on('click', function(e){
    	
    	var isOk = confirm('확인을 끝내고 완료하시겠습니까?');
    	if(isOk) {
    		$.ajax({
        		url:'/mobile/ConfirmAppMake.do',
        		type:'GET',
        		success:function(data){
        			console.log(data);
        			if(data == 1){
        				location.assign('/analysis/main.do');
        			} else {
        				alert('처리에 문제가 생겼습니다. 다시 시도하시거나 관리자에게 문의하십시오.');
        			}
        		}
        	});
    		
    	}
    });
});

function reSize()
{
	try{	
	var oBody	=	ifrm.document.body;
	var oFrame	=	document.all("confirm-frame");
		
	oFrame.style.height = oBody.scrollHeight + (oBody.offsetHeight - oBody.clientHeight);
	oFrame.style.width = oBody.scrollWidth + (oBody.offsetWidth - oBody.clientWidth);
	}
	//An error is raised if the IFrame domain != its container's domain
	catch(e)
	{
	window.status =	'Error: ' + e.number + '; ' + e.description;
	}
}