<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<!doctype html>
<html lang="ko">
 <head>
  <meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
	<meta name="viewport" content="width=1024">
 	<title>INBIZNET</title>

 	<link rel="shortcut icon" href="/assets/inbiznet_16.ico">

	<link rel="stylesheet" href="/assets/css/base.css?20191125_006">
	<link rel="stylesheet" href="/assets/css/slick.css?20191114_004">
	<link rel="stylesheet" href="/assets/css/style.css?20191127_001">

	<link rel="stylesheet" href="/assets/js/jqueryui/jquery-ui.css?20191111_001">
	<link rel="stylesheet" href="/assets/js/jqueryui/jquery.custom.css?20191111_001">

	<script type="text/javascript" src="/assets/js/jquery-1.11.2.min.js?20191109_001"></script>
	<script type="text/javascript" src="/assets/js/jqueryui/jquery-ui.js?20191109_001"></script>
	<script type="text/javascript" src="/assets/js/jquery.function.js?20191109_001"></script>

	<script type="text/javascript" src="/assets/js/common.js?20200428_001"></script>
	<script type="text/javascript" src="/assets/js/slick.js?20191114_001"></script>
	<script type="text/javascript" src="/assets/js/style.js?20191125_003"></script>

	<script type="text/javascript" src="/assets/js/dmaps/postcode.v2.js"></script>

	<!-- datepicker -->
	<link rel="stylesheet" href="/assets/js/datepicker/jquery-ui-1.8.18.css" type="text/css" />
	<script type="text/javascript" src="/assets/js/datepicker/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/assets/js/datepicker/jquery-ui-1.8.18.min.js"></script>
	<!-- //datepicker -->

	<script type="text/javascript" src="/assets/js/jquery.mask.js"></script>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

	<style>
		#privacy { color:#F7931D;cursor:pointer;font-size:12px; }
		#btnLogout { float:right;margin-right:-380px; }
	</style>

 </head>
<body>
	<div id="wrap">

		<!-- header -->
		<div id="header">
			<div class="section">
<!-- 				<h1 class="logo"><a href="#">INBIZNET</a></h1> -->
				<ul class="gnb">
					<li>
						<a href="/msg/SendMessage">음성메세지 발송</a>
						<ul>
							<li style="margin-left:75px;"><a href="/msg/SendMessage">음성메세지 발송</a></li>
						</ul>
					</li>
					<li>
						<a href="/addr/AddressPList">주소록</a>
						<ul>
							<li style="margin-left:-80px;"><a href="/addr/AddressPList">개인 주소록</a></li>
							<li><a href="/addr/AddressGList">그룹 주소록</a></li>
						</ul>
					</li>
					<li>
						<a href="/result/ResultList">발송내역 관리</a>
						<ul>
							<li style="margin-left:-195px;"><a href="/result/ResultList">발송내역 조회</a></li>
							<li><a href="/survey/SurveyResultList">설문지 조회</a></li>
							<li><a href="/result/ReservationList">예약메세지 조회</a></li>
							<li><a href="/result/RepeatList">반복메세지 조회</a></li>
						</ul>
					</li>
					<li>
						<a href="/cps">관리자 설정</a>
						<ul>
							<li style="margin-left:-65px"><a href="/cps">CPS 설정</a></li>
							<li><a href="/qos">QOS 설정</a></li>
						</ul>
					</li>
					<li>
						<a href="/info/user/UserInfo">사용자</a>
						<ul>
							<li style="margin-left:-100px"><a href="/info/user/UserInfo">회원정보</a></li>
							<li><a href="/charge">충전하기</a></li>
							<li><a href="/info/user/ServiceHistory">서비스 사용내역</a></li>
							<li><a href="/tax">서비스 정산확인</a></li>
						</ul>
					</li>
					<li>
						<a href="/received">메세지 보관함</a>
						<ul>
							<li style="margin-left:-150px"><a href="/received">수신함</a></li>
							<li><a href="/collection">음성모음함</a></li>
							<li><a href="/collection/SurveyList">설문모음함</a></li>
						</ul>
					</li>
				</ul>
				<button type="button" class="btn_adress" id="btnLogout">로그아웃</button>
			</div>
		</div>
		<!-- //header -->

		<!-- contents -->
		<jsp:doBody/>
		<!-- //contents -->

		<!-- footer -->
		<div id="footer">
			<div class="section">
				<ul class="company">
					<li>드림라인(주) @ 2019 DREAMLINE Co.,Ltd. All Rights Reserved.</li>
					<li>대표이사 : 김창호&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;소재지 : 서울시 구로구 디지털로 31길 61 드림마크원 데이터센터 7,8층</li>
					<li>고객센터 : 1877-1817 (평일 09:00 ~ 18:00 주말,공휴일 휴무) </li>
					<li>서비스 이용약관&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<label id="privacy">개인정보처리방침</label>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;서비스 사용 가이드</li>
				</ul>
			</div>
		</div>
		<!-- //footer -->

	</div>
 </body>
 <script>
 $(document).ready(function(){
	$(".gnb li ul li").each(function(){
		$(this).removeClass("on");
	});
	$(".gnb li").each(function(){
		$(this).removeClass("on");
	});

	$(".gnb li ul li a").each(function(){
		var url = window.location.href;
		var aTagHref = $(this).attr("href");

		// url : 주소 전체, aTagHref : a태그 href 속성
		if(url.indexOf("AddressDetailGList") > -1)
		{
			url = "/addr/AddressGList";
		}
		else if(url.indexOf("ResultDetailList") > -1)
		{
			url = "/result/ResultList";
		}
		else if(url.indexOf("ReservationDetailList") > -1)
		{
			url = "/result/ReservationList";
		}
		else if(url.indexOf("RepeatDetailList") > -1)
		{
			url = "/result/RepeatList";
		}
		else if(url.indexOf("/survey/DetailList") > -1)
		{
			url = "/survey/SurveyResultList";
		}
		
// 		console.log(url);
// 		console.log(aTagHref);

		if(url.indexOf(aTagHref.split("?")[0]) > -1 )
		{
			$(this).parent("li").addClass("on");
			$(this).parent("li").parents("li").addClass("on");
		}
	});
	
	$("#btnLogout").click(function(){
		location.href="/Logout";
	});
});
 </script>
</html>

