<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
		<meta name="viewport" content="width=1024">

		<title>INBIZNET</title>

		<link rel="stylesheet" href="/assets/css/base.css?20191125_006">
		<link rel="stylesheet" href="../../assets/css/style.css?20191206_001">
		<link rel="stylesheet" href="../../assets/css/slick.css?20191206_001">

		<script type="text/javascript" src="../../assets/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="../../assets/js/slick.js"></script>
		<script type="text/javascript" src="../../assets/js/style.js"></script>

		<style>
			#wrap_pop_ser {overflow:hidden; width:480px; height:90px; border: 3px solid #2a347f; background:#f8f9ff; padding:30px;}
			.ser_text {font-size:14px; line-height:20px;;}
			.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
			.ser_text ul {}
			.col_r {color:#cd2129;}
			#btnSubmit, #btnCancel { font-size:16px;padding:5px 30px; }
		</style>
	</head>

	<body>
		<div id="wrap_pop_ser">
			<div class="ser_text">
				<a>${title}</a>
				<br/>
				<span>${comment}</span>
<!-- 				<ul> -->
<!-- 					<li>로그인 후 신청하신 서비스에 대해 사용내역 등을 확인하실 수 있습니다.</li> -->
<!-- 				</ul> -->

				<div class="btn_next">
					<button type="button" class="btn_i" id="btnSubmit">확인</button>
					<button type="button" class="btn_d" id="btnCancel">취소</button>
				</div>
			</div>
		</div>
	</body>

	<script type="text/javascript">
		var type = '${type}';

		// 확인 버튼
		$("#btnSubmit").click(function(){
			window.opener.confirmCallback(type);
			window.opener.document.getElementById("formConfirm").remove();
			window.close();
		});

		// 취소 버튼
		$("#btnCancel").click(function(){
			window.opener.document.getElementById("formConfirm").remove();
			window.close();
		});
	</script>
</html>
