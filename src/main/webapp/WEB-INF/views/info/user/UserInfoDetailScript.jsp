<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
//휴대전화번호에 하이픈 추가
function formatPhonenumber(phonenumber)
{
    return phonenumber.toString().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-");
}

// 유선전화번호 값 세팅
function setTel(tel)
{
	var telArr 	= formatPhonenumber(tel).split("-");
	var tel1 	= telArr[0];
	var tel2 	= telArr[1];
	var tel3 	= telArr[2];
	
	var options = $("[name=tel1]").find("option");
	
	for(var i=0; i<options.length; i++)
	{
		if(options.eq(i).val() == tel1)
		{
			options.eq(i).prop("selected", "selected");
			$("[name=tel2]").val(tel2);
			$("[name=tel3]").val(tel3);
			break;
		}
	}
}

// 휴대전화번호 값 세팅
function setPhonenumber(phonenumber)
{
	var phonenumberArr 	= formatPhonenumber(phonenumber).split("-");
	var phone1 			= phonenumberArr[0];
	var phone2 			= phonenumberArr[1];
	var phone3 			= phonenumberArr[2];
	
	var options = $("[name=phone1]").find("option");
	
	for(var i=0; i<options.length; i++)
	{
		if(options.eq(i).val() == phone1)
		{
			options.eq(i).prop("selected", "selected");
			$("[name=phone2]").val(phone2);
			$("[name=phone3]").val(phone3);
			break;
		}
	}
}

// 이메일주소 값 세팅
function setEmail(email)
{
	var emailAddr 	= email.split("@");
	var email1 		= emailAddr[0];
	var email2 		= emailAddr[1];
	
	var options = $("[name=email3]").find("option");
	
	for(var i=0; i<options.length; i++)
	{
		if(options.eq(i).val() == email2)
		{
			options.eq(i).prop("selected", "selected");
			break;
		}
	}
	
	$("[name=email1]").val(email1);
	$("[name=email2]").val(email2);
}

// 비밀번호 생성규칙 일치여부 확인
function passwordCheck1()
{
	var password1 = $("#password1").val();
	var policy = "^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$";	
	
	
	if(!policy.test(password1) ) { 
		common.alert('비밀번호 변경', '비밀번호 설정 규칙이 준수되지 않았습니다. (영문+숫자+특수기호 8자리 이상으로 구성하여야 합니다.)');
		return false; }
	else { 
		return true; }
}

//새비밀번호 일치여부 확인
function passwordCheck2()
{
	var password1 = $("#password1").val();
	var password2 = $("#password2").val();
	
	if(password1 != password2) { 
		common.alert('비밀번호 변경', '비밀번호 확인값과 일치하지 않습니다.');
		return false; }
	else { 
		return true; }
}

$(document).ready(function(){
	// 유선전화번호 값 세팅
	var tel = "${userInfo.tel}";
	setTel(tel);
	
	// 휴대전화번호 값 세팅
	var phonenumber = "${userInfo.phonenumber}";
	setPhonenumber(phonenumber);
	
	// 이메일주소 값 세팅
	var email = "${userInfo.email}";
	setEmail(email);
	
	// 새비밀번호 입력란 숨김
	$(".newPW").hide();
	$(".policy").hide();
	
	// 비밀번호 변경 버튼 toggle
	$("#changePassword").click(function(){
		if ($(".newPW").is(":visible")) {
			$(".newPW").hide();
			$(".policy").hide();
			$("[name=password1]").val("");
			$("[name=password2]").val("");
		} else {
			$(".newPW").show();
			$(".policy").show();
			$("[name=password1]").focus();
		}
	});
	
	// 이메일 주소 select 변경 시
	$("[name=email3]").change(function(){
		$("[name=email2]").val($("[name=email3]").val());
	});
	
	// 수정 버튼 클릭 시
	$("#btnSubmit").click(function(){

		// 비밀번호 생성규칙 일치여부 확인
		if(!passwordCheck1())
		{
			common.alert('비밀번호 변경', '비밀번호 설정 규칙이 준수되지 않았습니다. (영문+숫자+특수기호 8자리 이상으로 구성하여야 합니다.)');
// 			$("[name=password1]").val("");
			$("[name=password1]").focus();
		} else 

		// 새비밀번호 일치여부 확인
		if(!passwordCheck2())
		{
			common.alert('비밀번호 변경', '비밀번호 확인값과 일치하지 않습니다.');
			$("[name=password2]").val("");
			$("[name=password2]").focus();
		} else {
		
		$("[name=formSubmit]").submit();
		}
	});
	
	// 취소 버튼 클릭 시
	$("#btnCancel").click(function(){
		var sequser = $("[name=sequser]").val();
		location.href="/info/user/UserInfo?sequser="+sequser;
	});
	
});
</script>