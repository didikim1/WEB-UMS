<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	var originTTSMent1 = '${originTTSMent1}';
	var originTTSMent2 = '${originTTSMent2}';
	var originTTSMent3 = '${originTTSMent3}';
// 	console.log(originTTSMent1);
// 	console.log(originTTSMent2);
// 	console.log(originTTSMent3);

	
// 	console.log(JSON.parse('${mentArr}'));
	$("[name=mentArr]").val(JSON.stringify(JSON.parse('${mentArr}')));
	
	var sendDay 	= "${paramMap.sendDay}";
	var sendHour 	= "${paramMap.sendHour}";
	var sendMinute 	= "${paramMap.sendMinute}";
	(sendDay.length < 1) ? $("[name=nextcallDate]").val("") : $("[name=nextcallDate]").val(sendDay + " " + sendHour + ":" + sendMinute);
	
	var repeatDay 		= "${paramMap.repeatDay}";
	var repeatHour 		= "${paramMap.repeatHour}";
	var repeatMinute 	= "${paramMap.repeatMinute}";
	(repeatDay.length < 1) ? $("[name=repeatDate]").val("") : $("[name=repeatDate]").val(repeatDay + " " + repeatHour + ":" + repeatMinute);
	
	// 보내기 버튼
	$("#btnSubmit").click(function(){
		var sendTime 	= "${paramMap.sendTime}";
		var callType 	= "${paramMap.callType}";
		var sendType 	= "${paramMap.sendType}";
		var url 		= "";
		var message 	= "";
		var reqUrl 		= "";

		// 즉시
		if(sendTime == "A")
		{
			url 	= "/IVR/IVREntrusting";
			message = "전송 되었습니다.";
			if(callType == "A")
			{
				reqUrl 	= "/result/ResultList";
			}
			else if(callType == "B")
			{
				reqUrl 	= "/survey/SurveyResultList";
			}
		}
		// 예약
		else if (sendTime == "B")
		{
			url 	= "/IVR/IVRReservation";
			message = "예약 되었습니다.";
			reqUrl 	= "/result/ReservationList";
		}
		// 주기
		else if (sendTime == "C")
		{
			url 	= "/IVR/IVRRepeat";
			message = "예약 되었습니다.";
			reqUrl 	= "/result/RepeatList";
		}
		
		// 음성파일 첨부
		if(sendType == "C")
		{
			window.opener.fn_SendCallByWav();
			alert(message);
			window.close();
		}
		// 그 외 나머지
		else
		{
			$.ajax({
				 url : url
				,method : "POST"
				,data : $("[name=formSubmit]").serialize()
	// 			,data : $(opener.document).find("[name=formSubmit]").serialize()
				,success : function(data){
					if(data.code == "200")
					{
						alert(message);
						window.close();
						window.opener.location.href=reqUrl;
					}
					else
					{
						alert("다시 시도해주세요.");
						window.close();
					}
				}
			});
		}

	});

	// 취소 버튼
	$("#btnCancel").click(function(){
		window.close();
	});
});
</script>