    /** 
	 * 데이터내의 '<', '>' 를 '&lt;','$gt;'로 치환한다.
	 * @parms obj : 대상객체, key : 치환할 속성명, done : 완료시 callBack 함수, parms : callback 함수의 파라미터
     * @author : genie
	 */
	function convertEscape(obj, key ,done, parms){
		for(var i in obj){
			obj[i][key] = String(obj[i][key]).replace(/</gi, '&lt;');
			obj[i][key] = String(obj[i][key]).replace(/>/gi, '&gt;');
		}
		if(typeof done === "function"){
			return done(parms);
		} else {
			return console.log("err: done is not function");
		}
	}

    /** 
	*   카카오링크 구현
	*   2016.04.14
	*   지니
	*/
    
	var pc = "win16|win32|win64|mac|macintel|linux|wince"; //pc platform 지정
	
	if(navigator.platform){
		if(pc.indexOf(navigator.platform.toLocaleLowerCase())<0){
			Kakao.init('d83e4eba1f75d589d8752c5c973c3b1e');
		    
			Kakao.Link.createTalkLinkButton({
		      container: '#kakao-link-btn',
		      label: '<%=w.getTitle()%>',
		      image: {
		        src: '<%= imgUrl %>',
		        width: '300',
		        height: '150'
		      },
		    
		      webButton: {
		        text: '사람도서관 위즈돔',
		        url: 'http://www.wisdo.me/<%=w.getSeqWisdome()%>' // 앱 설정의 웹 플랫폼에 등록한 도메인의 URL이어야 합니다.
		          }
		        });			
		} else {
			$("#kakao-link-btn").on("click",function(){
				alert("모바일에서만 가능합니다.");				
			});
		}
	}
	