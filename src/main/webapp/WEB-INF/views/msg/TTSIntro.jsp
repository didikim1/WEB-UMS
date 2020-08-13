<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<BaseTag:layoutPopup>
<style>
	#wrap_pop_ser {overflow:hidden; width:820px; height:330px; border: 3px solid #F7941D; background:#f8f9ff; padding:30px;}
	.ser_text {font-size:14px; line-height:20px;;}
	.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
	.ser_text ul {}

	.col_r {color:#cd2129;}
	#btnSubmit { width:80px;height:30px;padding:1px 6px; }
</style>

 <body>


     <div id="wrap_pop_ser">

             <!-- pop_서비스 소개 -->


                    <div class="ser_text">
<!--                            <a>휴대폰 본인확인 서비스 신청하기</a> -->

                           <ul>
                            <li>&middot; 인사말내의 [OO] 대신에 업체명을 입력하여주시기 바랍니다.</li>
                            <li>&middot; 안내사항은 [입력] 대신 기재하시면 됩니다.</li>
                            <li>&middot; 하단의 질의예문은 안내사항에 맞춰 변경하시면됩니다. 질의 선택지는 1~9개까지 구성이 가능합니다.</li>
                            <li>예문 1개 : “모임안내입니다. 11월 28일 동기 송년모임이 진행예정이오니 반드시 참석을 부탁드립니다. 확인하셨으면 1번”</li>
                            <li>예문 2개 : “이상 동의하시면 1번, 동의하지 않으시는 경우 2번”</li>
                            <li>예문 4개 : “동문 송년모임 개최일을 논의하고자 합니다. 11월이 좋으시면 1번, 12월이 좋으시면 2번, 내년 1월이 좋으시면 3번 , 그외는 4번”</li>
                           	<li>&middot; 내용 입력시 소리나는대로 입력하시면 자연스러운 음성이 구성됩니다.</li>
                           	<li>&middot; , . 띄어쓰기 등을 활용하시면 자연스러운 음성이 구성됩니다.</li>
                           	<li>&middot; 괄호는 안내가 되지 않습니다. -(하이픈)로 변경하여 사용하시기 바랍니다.</li>
                           	<li>예시 : 12345계좌(홍길동)로 송금 가능 -> 1,2,3,4,5 계좌-홍길동으로 송금 가능</li>

                           </ul>

						<div class="btn_next">
							<button type="button" class="btn_i" id="btnSubmit">확인</button>
						</div>

                    </div>

	</div>
 </body>

<script type="text/javascript">
$("#btnSubmit").click(function(){
	window.close();
});
</script>
</BaseTag:layoutPopup>