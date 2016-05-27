<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TCP-Owlet5</title>

<link rel="shortcut icon" href="/res/img/favicon.ico">
<link rel="stylesheet" href="/admin/css/jquery-ui.min.css">
<link rel="stylesheet" href="/admin/css/adder/image-uploader-style.css">

<script src="/admin/js/external/jquery/jquery.js"></script>
<!--[if lt IE 9]>  
    <script src="jquery-1.9.0.js"></script>  
<![endif]-->  
<!--[if (gte IE 9) | (!IE)]><!-->  
    <!-- <script src="jquery-2.0.0.js"></script> -->
    <!-- <script src="/admin/js/jquery-2.1.1.min.js"></script> -->  
<!--<![endif]-->

<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/adder/image-uploader-script.js"></script>
</head>
<body>

<div id="popup_wrapper">
	<div class="top-title panel">
		용량 제한 : 5MB 확장자 : BMP, PNG, GIF , JPG
	</div>
	
	<!-- 사진 등록, 삭제, 부분 삭제 인터페이스 -->
	
	<div class="top-subtitle panel">
		<button class="btn-add-picture">사진추가</button>
		<button class="btn-delete-picture">전체삭제</button>
		<button class="btn-selective-delete-picture">선택삭제</button>
	</div>
	
	
	<!-- 좌측 이미지 등록 리스트 출력부 -->
	
	<div class="left-picture-list-container panel">
		<div class="empty-box">
			<input type="file" class="file-element-multiple" multiple>
		</div>
	
		<div class="pic-box" data-index='1'>
			<input type="file" class="file-element">
		</div>
		<div class="pic-box" data-index='2'>
			<input type="file" class="file-element">
		</div>
		<div class="pic-box" data-index='3'>
			<input type="file" class="file-element">
		</div>
		<div class="pic-box" data-index='4'>
			<input type="file" class="file-element">
		</div>
		<div class="pic-box" data-index='5'>
			<input type="file" class="file-element">
		</div>
		
		<div class="pic-box" data-index='6'>
			<input type="file" class="file-element">
		</div>
		<div class="pic-box" data-index='7'>
			<input type="file" class="file-element">
		</div>
		<div class="pic-box" data-index='8'>
			<input type="file" class="file-element">
		</div>
		<div class="pic-box" data-index='9'>
			<input type="file" class="file-element">
		</div>
	</div>
	
	<!-- 중앙 이미지 배치부 -->
	
	<div class="main-container panel">
		<div class="pic-slot-box-wrapper">
		</div>
	</div>
	
	<!-- 우측 행렬 크기 지정부 -->
	
	<div class="right-menu-container panel">
		<div class="direct-selector">
			<div class="row-wrapper">
			<p>세로칸 수</p>
				<div class="row-btn row-btn-selected" data-value='1'>1</div>
				<div class="row-btn" data-value='2'>2</div>
				<div class="row-btn" data-value='3'>3</div>
			</div>
			<div class="column-wrapper">
			<p>가로칸 수</p>
				<div class="column-btn  column-btn-selected" data-value='1'>1</div>
				<div class="column-btn" data-value='2'>2</div>
				<div class="column-btn" data-value='3'>3</div>
			</div>
			<div>
				<input type="checkbox" class="input-limit-size">원본 이미지<br>
				<span class="ss-pan">(사진 사이즈 그대로 적용/<br>왼쪽은 틀에 맞추어 보임)</span>
			</div>
			<div class="block-template">
			</div>
		</div>
		
		<div class="log-console">
	
		</div>
	</div>
	
	
	
	<!-- 하단 최종 등록부 -->
	
	<div class="bottom-btn-container panel">
		<button class="image-submit-btn">올리기</button>
	</div>
</div>

</body>
</html>