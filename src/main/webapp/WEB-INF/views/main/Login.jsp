<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow:hidden; width:525px; height:265px; border: 3px solid #F7941D; padding:30px;margin:0 auto;margin-top:150px; }
	.ser_text { font-size:14px;line-height:20px;margin-bottom:3px; }
	.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
	.col_r {color:#cd2129;}
	.tbl tr {line-height:0.5;}
	.tbl th {font-size:15px;font-weight:bold;}
	.tbl td { font-size:90%; }
	#btnSubmit { width:115px;height:40px;padding:1px 6px; }
	#container { width:100% }
	#privacy { color:#F7931D;cursor:pointer;font-size:12px; }
	.pageTitle { color:#F7941D;text-align:center;padding:10px 0 40px; }
	.pageTitle:after, .pageTitle:before { width:0px;height:0px; }
	html, body { height:100% }
	body { margin:0 }
	#container { min-height:100% }
	#wrap {padding-bottom:100px;}
	#footer { margin-top:-100px; }
</style>


<body>
	<div id="container">
		<div id="wrap">
			<div id="wrap_pop_ser">
	<!-- 			<div class="ser_text"> -->
					<h1 class="pageTitle">통합 UMS 메시지 발송 서비스</h1>
	<!-- 			</div> -->

				<!-- table -->
				<div class="tbl">
					<form action="/LoginSubmit" name="formLogin" method="POST">
						<table>
							<caption>리스트</caption>
							<colgroup>
								<col width="30%" />
								<col width="" />
							</colgroup>
							<tbody>
								<tr>
									<th>아이디</th>
									<td><input type="text" name="userId" autocomplete="off" maxlength="40" /></td>
								</tr>
								<tr>
									<th>비밀번호</th>
									<td><input type="password" name="userPw" autocomplete="off" maxlength="40" /></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				<!-- //table -->

				<!-- button -->
				<div class="btn_next">
					<button type="button" class="btn_i" id="btnSubmit">로그인</button>
				</div>
				<!-- //button -->
			</div>
		</div>
	</div>

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

</body>

<script type="text/javascript">
$(document).ready(function(){
// 	var innerHeight = window.innerHeight;
// 	var footerHeight = $("#footer").height();
// 	console.log("innerHeight : ", innerHeight);
// 	console.log("footerHeight : ", footerHeight);
// 	$(".wrap").css("height", innerHeight-footerHeight-160);
	
	var message = "${message}";
	if(message != "" && message != null)
	{
		common.alert("로그인 실패", message);
	}
	
	// 엔터키 입력 시 로그인 버큰 클릭
	$("input").on("keyup", function(event){
		if(event.keyCode == 13)
		{
			$("#btnSubmit").click();
		}
	});
	
	// 로그인 버튼
	$("#btnSubmit").click(function(){
		$("[name=formLogin]").submit();
	});
});
</script>

</BaseTag:layoutPopup>
