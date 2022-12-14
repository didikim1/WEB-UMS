<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow:hidden; width:529px; height:269px; border: 1px solid #023134; padding:30px;margin:0 auto;margin-top:150px; }
	.ser_text { font-size:14px;line-height:20px;margin-bottom:3px; }
	.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
	.col_r {color:#cd2129;}
	.tbl tr {line-height:0.5;}
	.tbl th {font-size:15px;font-weight:bold;}
	.tbl td { font-size:90%; }
	#btnSubmit { width:115px;height:40px;padding:1px 6px; }
	#container { width:100% }
	.pageTitle { color:#023134;text-align:center;padding:10px 0 40px; }
	.pageTitle:after, .pageTitle:before { width:0px;height:0px; }
	html, body { height:100% }
	body { margin:0 }
	#container { min-height:100% }
	#wrap {padding-bottom:100px;}
	.logo { float: right;margin-top: 15%;margin-right: 25px; }
	#btnSubmit, #btnCancel { height:38px !important; width:140px !important;font-size:16px !important; }
	.btn_i { margin-left:100px; }
	.btn_service { width:100px;text-align:center; }
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
					<button type="button" class="btn_service" onclick="btnjoin()" >회원가입</button>
				</div>
				<!-- //button -->
			</div>
		</div>
	</div>

	<!-- footer -->
		<div id="footer">
			<div class="section row" style="width: 80%;">
				<div class="col-3"
					style="width: 20%; float: left; text-align: center;">
					<img style="margin-top: 20px; margin-right: 15px; float: right;"
						alt="인비즈넷 로고" src="/assets/images/inbiznetLogo_nobg.png">
				</div>
				<div class="col-9" style='width: 80%; float: right;'>
					<ul class="company">
						<li>Copyrightⓒ2012 INBIZNET CO.LTD All Right Reserved.</li>
						<li>인비즈넷(주)&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;사업자등록번호 :
							129-86-61831&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;대표이사 :
							정현철&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;소재지 : 서울시 마포구 마포대로 49,
							1007호 (도화동, 성우빌딩)</li>
						<li>고객센터 : 02-3487-5100 (평일 10:00 ~ 17:00 주말,공휴일 휴무)</li>
						<li>서비스 이용약관&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp; <label
							id="privacy">개인정보처리방침</label>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;서비스
							사용 가이드class="menuTitle"
						</li>
					</ul>
				</div>
			</div>
		</div>
	
		<!-- //footer -->
</body>

<script type="text/javascript">

function btnjoin(){
	location.href="/main/Join.do";
}

$(document).ready(function(){
// 	var innerHeight = window.innerHeight;
// 	var footerHeight = $("#footer").height();
// 	console.log("innerHeight : ", innerHeight);
// 	console.log("footerHeight : ", footerHeight);
// 	$(".wrap").css("height", innerHeight-footerHeight-160);
	
	var message = "${message}";
	if(message != "" && message != null)
	{
		common.alert("로그인 실패", " 로그인 정보를 확인해주세요. (" + message + ")");
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
