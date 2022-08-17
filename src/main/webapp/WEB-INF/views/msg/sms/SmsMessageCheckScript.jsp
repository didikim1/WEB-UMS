<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	var originTTSMent1 = '${originTTSMent1}';
	var originTTSMent2 = '${originTTSMent2}';
	var originTTSMent3 = '${originTTSMent3}';
	
	var hInput = $("input[type=hidden]");
	for(var i= 0 ; i < hInput.length ; i++){
		console.log("name : " + $(hInput[i]).attr("name") + "\t/ value :"+$(hInput[i]).val());
	}
	
// 	console.log(originTTSMent2);
// 	console.log(originTTSMent3);
	
	
// 	console.log(JSON.parse('${mentArr}'));
	$("[name=mentArr]").val('${mentArr}');
	
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
			url 	= "/smsSender/directSend";
			message = "전송요청하었습니다. \n결과는 발송 내역 관리에서 확인 가능합니다.";
			reqUrl 	= "/result/SMSResultList";
		}
		// 예약
		else if (sendTime == "B")
		{
// 			console.log("sendType B");
// 			url 	= "/IVR/IVRReservation";
// 			message = "예약 되었습니다.";
// 			reqUrl 	= "/result/ReservationList";
		}
		// 주기
		else if (sendTime == "C")
		{
// 			console.log("sendType C");
// 			url 	= "/IVR/IVRRepeat";
// 			message = "예약 되었습니다.";
// 			reqUrl 	= "/result/RepeatList";
		}
		$.ajax({
			 url : url
			,method : "POST"
			,data : $("[name=formSubmit]").serialize()
			,success : function(data){
				console.log(data);
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
	});

	// 취소 버튼
	$("#btnCancel").click(function(){
		window.close();
	});
});
</script>