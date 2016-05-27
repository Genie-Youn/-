<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" href="/res/img/favicon.ico">
<link rel="stylesheet" href="/admin/css/crossbrowser.css">
<link rel="stylesheet" href="/admin/css/bootstrap.min.css">
<link rel="stylesheet" href="/admin/css/join-style-proto.css">
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
<script src="/admin/js/myUtil.js"></script>
<script src="/admin/js/join.js"></script>
<title>Owlet5</title>
</head>
<body>
	<div id="header-wrapper">
		<!-- �ΰ� ǥ���մϴ�. -->

		<div id="main_header_wrapper">
			<header class="main-header"> </header>
		</div>
	</div>


	<form id="joinForm" method="post" action="/memberInsert.do"
		modelAttribute="memberBean" modelAttribute="storeInfo" modelAttribute="storeKindsBean"
		class="form-horizontal" role="form">

		<div class="container" id="basicInfo">
			<br /> <br />


			<!--   <div class="form-group">
		    	<label class="col-xs-2 control-label"><b>������ : </b></label>
		    	<div class="col-xs-5">
		      		<select name="address" class="form-control">
						<option value="01">����
						<option value="02">���
						<option value="03">����
						<option value="04">��û
						<option value="05">����
						<option value="06">���
						<option value="07">����
					</select>
		    	</div>
		    	
		    	
		  	</div> -->

			<div class="form-group">
				<label><b><font size="5" face="�������">�������� �Է� </font></b></label>
			</div>
			<hr />
			<div class="form-group">
				<label class="customlabel"><b>�̸�</b></label>
				<div class="textField">
					<input type="text" class="textField-control" name="name">
				</div>
			</div>

			<div class="form-group">
				<label class="customlabel"><b>�̸���</b></label>
				<div class="textField notice_msg">
					<input type="text" class="textField-control" name="mail">
				</div>
			</div>

			<div class="form-group notice_margin">
				<label class="customlabel"><b></b></label>
				<div class="textField notice_msg">
					<span class="notice_msg">*�̸��� �ּҴ� �α��� �� ���̵�� ���˴ϴ�.</span>
				</div>
			</div>

			<div class="form-group">
				<label class="customlabel"><b>��й�ȣ</b></label>
				<div class="textField">
					<input type="password" class="textField-control" name="passwd"
						maxlength="18">
				</div>
				<div class="icon passwd">
					<div class="icon-check"></div>
					<div class="icon-x"></div>
				</div>
			</div>

			<div class="form-group">
				<label class="customlabel"><b>��й�ȣ Ȯ��</b></label>
				<div class="textField">
					<input type="password" class="textField-control"
						name="passwdConfirm" maxlength="18">
				</div>
				<div class="icon passwdConfirm">
					<div class="icon-check"></div>
					<div class="icon-x"></div>
				</div>
			</div>

			<div class="form-group notice_margin">
				<label class="customlabel"><b></b></label>
				<div class="textField notice_msg">
					<span class="notice_msg">*��й�ȣ�� 4�ڸ� �̻�, 15�ڸ� �̳��� �Է����ּ���.</span>
				</div>
			</div>

			<hr />
			<div class="form-group">
				<div class="col-xs-12" align="center">
					<input type="button" value="����" class="btn" id="next" />
				</div>
			</div>
		</div>

		<div class="container" id="extendsInfo">
			<br /> <br />
			<!--   <div class="form-group">
		    	<label class="col-xs-2 control-label"><b>������ : </b></label>
		    	<div class="col-xs-5">
		      		<select name="address" class="form-control">
						<option value="01">����
						<option value="02">���
						<option value="03">����
						<option value="04">��û
						<option value="05">����
						<option value="06">���
						<option value="07">����
					</select>
		    	</div>
		    	
		    	
		  	</div> -->

			<div class="form-group">
				<label><b><font size="5" face="�������">��ü���� �Է�</font></b></label>
			</div>
			<hr />

			<div class="form-group">
				<label class="customlabel"><b>* ��ü �з�</b></label>
				<div class="col-xs-5">
					<select name="groupType" class="form-control">
						<option value="01">����Ŀ��/����/��
						<option value="02">����Ʈ/ī��
						<option value="03">����/ġŲ/����
						<option value="04">������/��Ʈ
						<option value="05">�Ƿ�/��ȭ
						<option value="06">����
					</select>
				</div>
			</div>

			<div class="form-group">
				<label class="customlabel"><b>* ��ü��</b></label>
				<div class="textField">
					<input type="text" class="textField-control" name="storeName">
				</div>
			</div>

			<div class="form-group">
				<label class="customlabel"><b>* �����ȣ</b></label>
				<div class="textField">
					<button class="post_button">�����ȣ ã��</button>
					<input name="postCode" value="POSTCODE_TEST_DATA" hidden="true">
					<label id="postCode"></label>
				</div>
			</div>

			<div class="form-group">
				<label class="customlabel"><b>* ���ּ�</b></label>
				<div class="textField">
					<textarea class="address textField-control" name="address"
						maxlength="100"></textarea>
				</div>
			</div>

			<div class="form-group">
				<input type="checkbox" class="phone" id="check_phone" /> <label
					class="customlabel"><b>* ��ȭ��ȣ</b></label>
				<div class="textField phone">
					<input name="phone" value="02-123-4567" hidden="true">
					<input type="text" class="textField-control" maxlength="3"
						id="phone0">
					<div>-</div>
					<input type="text" class="textField-control" maxlength="4"
						id="phone1">
					<div>-</div>
					<input type="text" class="textField-control" maxlength="4"
						id="phone2">
				</div>
			</div>

			<div class="form-group">
				<input type="checkbox" class="phone" id="check_cellphone" /> <label
					class="customlabel"><b>* �޴�����ȣ</b></label>
				<div class="textField phone">
					<input name="cellphone" value="010-1234-5678" hidden="true">
					<select id="cellphone0" class="textField-control">
						<option value="010">010
						<option value="011">011
						<option value="017">017
						<option value="018">018
					</select>
					<div>-</div>
					<input type="text" class="textField-control" maxlength="4"
						id="cellphone1" />
					<div>-</div>
					<input type="text" class="textField-control" maxlength="4"
						id="cellphone2" />
				</div>
			</div>

			<div class="form-group">
				<label class="customlabel"><b>* �����ð�</b></label>
				<input name="time" value="TIME_TEST_DATA" hidden="true">
				<div class="textField right">
					<span>24��</span> <input type="checkbox" id="check24hours" />
				</div>
			</div>

			<div class="form-group">
				<label class="customlabel"> </label>
				<div class="textField days">
					<ul class="days">
						<li class="selected">��</li>
						<li class="selected">ȭ</li>
						<li class="selected">��</li>
						<li class="selected">��</li>
						<li class="selected">��</li>
						<li>��</li>
						<li>��</li>
					</ul>
				</div>
			</div>

			<div class="form-group">
				<label class="customlabel"> </label>
				<div class="textField time">
					<label class="openLabel">OPEN</label> 
					<select id="openHour" class="textField-control" name = "startTimeHour">
						<option value="01">01
						<option value="02">02
						<option value="03">03
						<option value="04">04
						<option value="05">05
						<option value="06">06
						<option value="07">07
						<option value="08">08
						<option value="09">09
						<option value="10">10
						<option value="11">11
						<option value="12">12
						<option value="13">13
						<option value="14">14
						<option value="15">15
						<option value="16">16
						<option value="17">17
						<option value="18">18
						<option value="19">19
						<option value="20">20
						<option value="21">21
						<option value="22">22
						<option value="23">23
					</select>
					<div> </div>
					<select id="openMin" class="textField-control" name = "startTimeMin">
						<option value="00">00
						<option value="10">10
						<option value="20">20
						<option value="30">30
						<option value="40">40
						<option value="50">50


					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="customlabel"> </label>
				<div class="textField time">
					<label class="closeLabel">CLOSE</label> 
					<select id="closeHour" class="textField-control" name = "endTimeHour">
						<option value="01">01
						<option value="02">02
						<option value="03">03
						<option value="04">04
						<option value="05">05
						<option value="06">06
						<option value="07">07
						<option value="08">08
						<option value="09">09
						<option value="10">10
						<option value="11">11
						<option value="12">12
						<option value="13">13
						<option value="14">14
						<option value="15">15
						<option value="16">16
						<option value="17">17
						<option value="18">18
						<option value="19">19
						<option value="20">20
						<option value="21">21
						<option value="22">22
						<option value="23">23
						
					</select>
					<div> </div>
					<select id="closeMin" class="textField-control" name = "endTimeMin">
						<option value="00">00
						<option value="10">10
						<option value="20">20
						<option value="30">30
						<option value="40">40
						<option value="50">50
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="customlabel"><b>* ���ô±�</b></label>
				<div class="textField">
					<input type="text" class="textField-control" name="map_address">
				</div>
			</div>
			
			<div class="form-group">
				<label class="customlabel"></label>
				<div class="textField map_thumbnail">
					�������� �����
				</div>
			</div>
			
			<hr />
			
			<div class="form-group">
				<label class="customlabel"><b>�ΰ�(����)</b></label>
				<div class="textField">
					<input name="logo" value="logo_TEST_DATA" hidden="true">
					<input id="file_upload" type="file" class="textField-control">
				</div>
			</div>
			
			<div class="form-group">
				<label class="customlabel homepage_label"><b>������Ʈ / <br>��α�</b></label>
				<div class="textField">
					<input type="text" class="textField-control" name="site">
				</div>
			</div>
			
			<div class="form-group">
				<label class="customlabel"><b>��ü�Ұ�</b></label>
				<div class="textField">
					<textarea class="address textField-control" name="products"
						maxlength="50">50�� �̳��� �Է����ּ���.</textarea>
				</div>
			</div>
			
			<hr />
			<div class="form-group">
				<div class="col-xs-12" align="center">
					<input type="submit" value="����" class="btn" id="submit" />
				</div>
			</div>
		</div>

	</form>

	<footer id="main_footer">
		<div id="footer_wrapper">
			<div class="footer-container">
				<div id="main_copyright"></div>
			</div>
		</div>
	</footer>
</body>
</html>

