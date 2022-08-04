<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow:hidden; width:529px; height:269px; border: 1px solid #2a347f; background:#f8f9ff; padding:30px;}
	.ser_text { font-size:14px;line-height:20px;margin-bottom:3px; }
	.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
	.col_r {color:#cd2129;}

	.tbl tr {line-height:0.5;}
	.tbl th, .tbl td { font-size:100%; }
	#btnSubmit, #btnCancel { width:115px;height:30px;padding:1px 6px; }
	#uploadNotice { border:2px solid #2a347f;padding:5px 10px; }
</style>

<body>
	<div id="wrap_pop_ser">

		<div class="ser_text">
			<a>음성들어보기</a>
		</div>

		<div class="ser_text" id="uploadNotice">
			<ul>
				<li>&middot; 하기 입력하시는 전화번호로 해당 입력내용이 전달됩니다.</li>
				<li>&middot; 1877-1875에서 전화가 걸려오면 수신후 입력하신 내용을 확인하시기 바랍니다.</li>
				<li>&middot; 총 3회까지 확인가능합니다.</li>
				<li>&middot; 쉼표(,) 사용 또는 소리나는대로 입력하시면 자연스러운 전달이 가능합니다.</li>
			</ul>
		</div>

		<!-- table -->
		<div class="tbl">
			<table>
				<caption>리스트</caption>
				<colgroup>
					<col width="30%" />
					<col width="" />
				</colgroup>
				<tbody>
					<tr>
						<th>수신하실 전화번호</th>
						<td><input type="text" name="phonenumber" id="phonenumber" autocomplete="off" maxlength="13" /></td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- //table -->

		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_i" id="btnSubmit">음성듣기 요청</button>
			<button type="button" class="btn_can" id="btnCancel">취소</button>
		</div>
		<!-- //button -->

	</div>
</body>

<script type="text/javascript">
$(document).ready(function(){
	$('#phonenumber').mask('000-0000-0000');

	// 음성듣기 요청 버튼
	$("#btnSubmit").click(function(){
		var phonenumber = $("[name=phonenumber]").val();

		if(phonenumber.length < 11)
		{
			alert("전화번호를 확인해주세요.");
			$("[name=phonenumber]").focus();
			return false;
		}

		var TTS_DELAY_500 	= "<VTML_PAUSE TIME=\"500\" />";
		var ment1 			= "";
		var ment2 			= "";
		var ment3 			= "";
		var ttsMent 		= "";

		// 알림형
		if($(opener.document).find("[name=exampleCnt]").val() == "0")
		{
			ment1 = $(opener.document).find("[name=ttsMent1]").val();
			ment2 = $(opener.document).find("[name=ttsMent2]").val();

			ttsMent = ment1 + TTS_DELAY_500 + ment2;
		}
		// 응답형(질문 1개)
		else if($(opener.document).find("[name=exampleCnt]").val() == "1")
		{
			ment1 = $(opener.document).find("[name=ttsMent1]").val();
			ment2 = $(opener.document).find("[name=ttsMent2]").val();
			ment3 = $(opener.document).find("[name=ttsMent3]").val();

			ttsMent  = ment1 + TTS_DELAY_500 + ment2 + TTS_DELAY_500 + ment3;
			ttsMent += "을 눌러주십시오.";
		}
		// 응답형 (질문 2개 이상)
		else
		{

			ment1 = $(opener.document).find("[name=ttsMent1]");
			ment2 = $(opener.document).find("[name=ttsMent2]");
			ment3 = $(opener.document).find("[name=ttsMent3]");

			ttsMent += ment1.val();

			for(var i=0; i<ment2.length; i++)
			{
				ttsMent += TTS_DELAY_500;
				ttsMent += ment2.eq(i).val();
				ttsMent += TTS_DELAY_500;
				ttsMent += ment3.eq(i).val();
				ttsMent += "을 눌러주십시오.";
			}

		}

		common.alert('ARS로 듣기', '잠시후 전화가 걸려옵니다.');
		
		setTimeout(function(){
			$.ajax({
				 url : "/IVR/IVREntrustingListen"
				,data : {
					 msg : ttsMent
					,phonenumber : phonenumber
					,callerid : $(opener.document).find("[name=callerID]").val()
				}
				,success : function(data){
					
				}
			});
		}, 500);
		
	});

	// 취소 버튼
	$("#btnCancel").click(function(){
		window.close();
	});
});
</script>

</BaseTag:layoutPopup>
