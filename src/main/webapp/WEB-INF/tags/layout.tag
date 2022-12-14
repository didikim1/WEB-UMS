<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
<meta name="viewport" content="width=1024">
<title>INBIZNET</title>

<link rel="shortcut icon" href="/assets/inbiznet_16.ico">

<link rel="stylesheet" href="/assets/css/base.css?20191125_006">
<link rel="stylesheet" href="/assets/css/slick.css?20191114_004">
<link rel="stylesheet" href="/assets/css/style.css?20191127_001">

<link rel="stylesheet"
	href="/assets/js/jqueryui/jquery-ui.css?20191111_001">
<link rel="stylesheet"
	href="/assets/js/jqueryui/jquery.custom.css?20191111_001">

<script type="text/javascript"
	src="/assets/js/jquery-1.11.2.min.js?20191109_001"></script>
<script type="text/javascript"
	src="/assets/js/jqueryui/jquery-ui.js?20191109_001"></script>
<script type="text/javascript"
	src="/assets/js/jquery.function.js?20191109_001"></script>

<script type="text/javascript" src="/assets/js/common.js?20200910_001"></script>
<script type="text/javascript" src="/assets/js/slick.js?20191114_001"></script>
<script type="text/javascript" src="/assets/js/style.js?20191125_003"></script>

<script type="text/javascript" src="/assets/js/dmaps/postcode.v2.js"></script>

<!-- datepicker -->
<link rel="stylesheet" href="/assets/js/datepicker/jquery-ui-1.8.18.css"
	type="text/css" />
<script type="text/javascript"
	src="/assets/js/datepicker/jquery-1.7.1.min.js"></script>
<script type="text/javascript"
	src="/assets/js/datepicker/jquery-ui-1.8.18.min.js"></script>
<!-- //datepicker -->

<script type="text/javascript" src="/assets/js/jquery.mask.js"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

<style>
#privacy {
	color: #045d3f;
	cursor: pointer;
	font-size: 13px;
	font-weight: 700;
}

 #btnLogout {
	height: 35px;
	margin-right: 150px;
	margin-top: 20px;
	float :right;
}
 
.menuTitle {
	padding-top: 5px !important;
}

.logo {
	float: right;
	margin-top: 15%;
	margin-right: 25px;
}

</style>

<jsp:useBean id="frameworkBeans"
	class="com.inbiznetcorp.acs.framework.beans.FrameworkBeans" />

</head>
<body>
	<div id="wrap">

<!-- 		<button type="button" class=" btn_join" onclick="btnjoin()" >????????????</button> -->
		<!-- header -->
		<div id="header">
			<button type="button" class="btn_adress" id="btnLogout">????????????</button>
			<div class="section">
				<!-- 				<h1 class="logo"><a href="#">INBIZNET</a></h1> -->
				<ul class="gnb" style="right: 100px;">
					<li><a class="menuTitle" href="/msg/SendMessage">UMS ????????? ??????</a>
						<ul>
							<li style="margin-left: 15px;"><a href="/msg/SendMessage">???????????????
									??????</a></li>
							<li><a href="/msg/sms/SMSSendMessage">??????????????? ??????</a></li>
						</ul></li>
					<li><a class="menuTitle" href="/result/ResultList">???????????? ??????</a>
						<ul>
							<li style="margin-left: -290px;"><a
								href="/result/ResultList">?????????????????? ??????</a></li>
							<li><a href="/result/SMSResultList">?????????????????? ??????</a></li>
							<li><a href="/survey/SurveyResultList">????????? ??????</a></li>
							<li><a href="/result/ReservationList">??????????????? ??????</a></li>
							<li><a href="/result/RepeatList">??????(??????)????????? ??????</a></li>
						</ul></li>
					<li><a class="menuTitle" href="/addr/AddressPList">?????????</a>
						<ul>
							<li style="margin-left: -80px;"><a href="/addr/AddressPList">??????
									?????????</a></li>
							<li><a href="/addr/AddressGList">?????? ?????????</a></li>
						</ul></li>
					<li><a class="menuTitle" href="/received/">????????? ?????????</a>
						<ul>
							<li style="margin-left: -188px"><a href="/received/">???????????????</a></li>
							<%-- 						<c:if test="${frameworkBeans.findSessionBean().getGrade() eq 'A'}"> --%>
							<!-- 							<li style="margin-left:-10px"><a href="/received/">???????????????</a></li> -->
							<%-- 						</c:if> --%>
							<%-- 						<c:if test="${frameworkBeans.findSessionBean().getGrade() ne 'A'}"> --%>
							<!-- 							<li style="margin-left:-10px"><a href="/received/">???????????????</a></li> -->
							<%-- 						</c:if> --%>
							<li><a href="/collection/VoiceList">???????????????</a></li>
							<li><a href="/collection/SMSList">???????????????</a></li>
							<li><a href="/collection/SurveyList">???????????????</a></li>
						</ul></li>
					<li><a class="menuTitle" href="/info/user/UserInfo">????????????</a>
						<ul>
							<li style="margin-left: -160px"><a
								href="/info/user/UserInfo">????????????</a></li>
							<%-- 						<c:if test="${frameworkBeans.findSessionBean().getGrade() eq 'A'}"> --%>
							<!-- 							<li style="margin-left:-300px"><a href="/info/user/UserInfo">????????????</a></li> -->
							<%-- 						</c:if> --%>
							<%-- 						<c:if test="${frameworkBeans.findSessionBean().getGrade() ne 'A'}"> --%>
							<!-- 							<li style="margin-left:-300px"><a href="/info/user/UserInfo">????????????</a></li> -->
							<%-- 						</c:if> --%>
							<li><a href="/charge">????????????</a></li>
							<li><a href="/info/user/ServiceHistory">????????? ????????????</a></li>
							<li><a href="/tax">????????? ????????????</a></li>
						</ul></li>
					<%-- 					<c:if test="${frameworkBeans.findSessionBean().getGrade() eq 'A'}"> --%>
					<li><a class="menuTitle" href="/cps">????????? ??????</a>
						<ul>
							<li style="margin-left: -67px"><a href="/cps">CPS ??????</a></li>
							<li><a href="/qos">QOS ??????</a></li>
						</ul></li>
					<%-- 					</c:if> --%>

				</ul>
			</div>
		</div>
	<!-- //header -->

	<!-- contents -->
	<jsp:doBody />
	<!-- //contents -->


	<!-- footer -->
	<div id="footer">
		<div class="section row" style="width: 80%;">
			<div class="col-3"
				style="width: 20%; float: left; text-align: center;">
				<img style="margin-top: 20px; margin-right: 15px; float: right;"
					alt="???????????? ??????" src="/assets/images/inbiznetLogo_nobg.png">
			</div>
			<div class="col-9" style='width: 80%; float: right;'>
				<ul class="company">
					<li>Copyright???2012 INBIZNET CO.LTD All Right Reserved.</li>
					<li>????????????(???)&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;????????????????????? :
						129-86-61831&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;???????????? :
						?????????&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;????????? : ????????? ????????? ???????????? 49,
						1007??? (?????????, ????????????)</li>
					<li>???????????? : 02-3487-5100 (?????? 10:00 ~ 17:00 ??????,????????? ??????)</li>
					<li>????????? ????????????&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp; <label
						id="privacy">????????????????????????</label>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;?????????
						?????? ?????????
					</li>
				</ul>
			</div>
		</div>
	</div>
	</div>

	<!-- //footer -->
</body>
<script>
	$(document).ready(function() {
		$(".gnb li ul li").each(function() {
			$(this).removeClass("on");
		});
		$(".gnb li").each(function() {
			$(this).removeClass("on");
		});

		$(".gnb li ul li a").each(function() {
			var url = window.location.href;
			var aTagHref = $(this).attr("href");

			// url : ?????? ??????, aTagHref : a?????? href ??????
			if (url.indexOf("AddressDetailGList") > -1) {
				url = "/addr/AddressGList";
			} else if (url.indexOf("SMSResultDetailList") > -1) {
				url = "/result/SMSResultList";
			} else if (url.indexOf("ResultDetailList") > -1) {
				url = "/result/ResultList";
			} else if (url.indexOf("/survey/DetailList") > -1) {
				url = "/survey/SurveyResultList";
			} else if (url.indexOf("ReservationDetailList") > -1) {
				url = "/result/ReservationList";
			} else if (url.indexOf("RepeatDetailList") > -1) {
				url = "/result/RepeatList";
			}

			// 		console.log(url);
			// 		console.log(aTagHref);

			if (url.indexOf(aTagHref.split("?")[0]) > -1) {
				$(this).parent("li").addClass("on");
				$(this).parent("li").parents("li").addClass("on");
			}
		});

		$(".tbl_type01 tbody tr").on("mouseover", function() {
			$(this).css("background-color", "#c8ddd5");
			//$( this).children("td").css( "cursor", "pointer" );
		});
		$(".tbl_type01 tbody tr").on("mouseleave", function() {
			$(this).css("background-color", "white");
		});

		$(".receiverBox ul li").on("mouseover", function() {
			$(this).css("background-color", "#c8ddd5");
		});
		$(".receiverBox ul li").on("mouseleave", function() {
			$(this).css("background-color", "white");
		});

		$("#btnLogout").click(function() {
			location.href = "/Logout";
		});

	});
		function btnjoin(){
			location.href="/main/Join.do";
		}
</script>
</html>

