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
			#btnOk { font-size:16px;padding:5px 30px; }
		</style>
	</head>

	<body>
		<div id="wrap_pop_ser">
			<div class="ser_text">
				<a>${title}</a>
				<br/>
				<span>${comment}</span>
				<div class="btn_next">
					<button type="button" class="btn_d" id="btnOk">확인</button>
				</div>
			</div>
		</div>
	</body>

	<script type="text/javascript">
		// 확인 버튼
		$("#btnOk").click(function(){
			window.opener.document.getElementById("formAlert").remove();
			window.close();
		});
	</script>
</html>
