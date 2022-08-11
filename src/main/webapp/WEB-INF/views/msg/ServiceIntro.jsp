<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow:hidden; width:484px; height:319px; border: 1px solid #023134; background:#f8f9ff; padding:30px;}
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
                           	<li>&middot; 입력하신 제목으로 전화가 발신됩니다.</li>
<!--                            	<li>&middot; 입력하신 내용이 TTS 음성으로 전달됩니다.</li> -->
<!--                            	<li>&middot; 내용은 마이크를 이용하여 내 목소리로 녹음된 내용 <br/>&nbsp;&nbsp;&nbsp; 또는 TTS 변환된 음성내용이 전달됩니다.</li> -->
                           	<li>&middot; 입력하신 내용은 TTS 변환된 음성내용이 전달됩니다.</li>
                           	<li>&middot; 모음함에서 보냈던 내용을 선택하실 수 있습니다.</li>
<!--                            	<li>&middot; 회신을 음성 또는 예문 번호로 확인하실 수 있습니다.</li> -->
                           	<li>&middot; 회신 상태는 예문에 대해 수신자가 입력하신 결과값으로 확인하실 수 있습니다.</li>
                           	<li>&middot; 선택하신 시간에 발신됩니다.</li>
                           	<li>&middot; 보낸 음성메일의 내용을 모음함에 저장 가능합니다.</li>
<!--                            	<li>&middot; ACS 발신은 건당 100원 / 월 100통 5000원을 충전 후 음성메세지 보내기가 <br/>&nbsp;&nbsp;&nbsp; 가능합니다.</li> -->

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
