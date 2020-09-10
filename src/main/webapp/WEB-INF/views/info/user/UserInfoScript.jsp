<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
// 전화번호에 하이픈 추가
function formatPhonenumber(x)
{
    return x.toString().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-");
}

$(document).ready(function(){
	// 휴대전화번호에 하이픈 추가
	var phonenumber = "${userInfo.phonenumber}";
	$("#phonenumber").html(formatPhonenumber(phonenumber));
	
	// 발신번호에 하이픈 추가
	var callerId = "${userInfo.callerId}";
	$("#callerId").html(formatPhonenumber(callerId));
	
	// 개인정보 상세보기 버튼 클릭 시
	$("#btnUserInfoDetail").click(function(){
		var sequser = $("[name=sequser]").val();
		location.href="/info/user/UserInfoDetail?sequser="+sequser;
	});
});
</script>