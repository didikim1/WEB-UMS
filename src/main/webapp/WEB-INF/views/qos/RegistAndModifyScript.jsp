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

$(document).ready(function(){
	// 등록 버튼
	$("#btnSubmit").click(function(){
// 		var inputText = $("input[type=text]");
// 		for(var i=0; i<inputText.length; i++)
// 		{
// 			if(inputText.eq(i).val() == "" || inputText.eq(i).val() == null)
// 			{
// 				common.alert('', '비어있는 항목이 있습니다.');
// 				inputText.eq(i).focus();
// 				return false;
// 			}
// 		}

// 		$.ajax({
// 			 url : ""
// 			,data : ""
// 			,success : function(){

// 			}
// 		});
	});

	// 취소 버튼
	$("#btnCancel").click(function(){
		window.close();
	});
});

</script>