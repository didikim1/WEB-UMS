<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
// 숫자만 입력받도록
function onlyNumber(event){
    event = event || window.event;
    var keyID = (event.which) ? event.which : event.keyCode;
    // 48~57:0~9(상단 키패드), 96~105:0~9(우측 키패드), 8:backspace, 46:delete, 37:왼쪽방향키, 39:오른쪽방향키, 9:tab
    if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 || keyID == 9)
        return;
    else
        return false;
}

function confirmCallback(type)
{
	if(type == "A")
	{
// 		$.ajax({
// 			 url : "/cps/InsertIVRServer"
// 			,type : "POST"
// 			,data : {
// 				 serverName : $("[name=serverName]").val()
// 				,serverIp 	: $("[name=serverIp]").val()
// 				,cps 		: $("[name=cps]").val()
// 			}
// 			,success : function(data){
// 				window.opener.location.reload();
// 				window.close();
// 			}
// 		});
	}
	else if(type == "B")
	{
// 		$.ajax({
// 			 url : "/cps/UpdateIVRServer"
// 			,type : "POST"
// 			,data : {
// 				 seqivrserver 	: $("[name=seqivrserver]").val()
// 				,serverName 	: $("[name=serverName]").val()
// 				,serverIp 		: $("[name=serverIp]").val()
// 				,cps 			: $("[name=cps]").val()
// 			}
// 			,success : function(data){
// 				window.opener.location.reload();
// 				window.close();
// 			}
// 		});
	}
}

$(document).ready(function(){
	// 등록 버튼
	$("#btnSubmit").click(function(){
		var inputText = $("input[type=text]");
		for(var i=0; i<inputText.length; i++)
		{
			if(inputText.eq(i).val() == "" || inputText.eq(i).val() == null)
			{
				common.alert('', '비어있는 항목이 있습니다.');
				inputText.eq(i).focus();
				return false;
			}
		}
		
		common.confirm('서버 신규 등록', '서버명 [ '+$("[name=serverName]").val()+' ] 등록하시겠습니까?', 'A');
	});
	
	// 변경 버튼
	$("#btnModify").click(function(){
		var inputText = $("input[type=text]");
		for(var i=0; i<inputText.length; i++)
		{
			if(inputText.eq(i).val() == "" || inputText.eq(i).val() == null)
			{
				common.alert('', '비어있는 항목이 있습니다.');
				inputText.eq(i).focus();
				return false;
			}
		}
		
		common.confirm('서버 변경', '서버명 [ '+$("[name=serverName]").val()+' ] 변경하시겠습니까?', 'B');
	});

	// 취소 버튼
	$("#btnCancel").click(function(){
		window.close();
	});
});

</script>