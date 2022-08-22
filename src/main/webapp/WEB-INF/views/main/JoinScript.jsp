<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">

//우편번호 선택
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("sample6_extraAddress").value = extraAddr;
            
            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample6_postcode').value = data.zonecode;
            document.getElementById("sample6_address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("sample6_detailAddress").focus();
        }
    }).open();
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
		common.alert('비밀번호 변경', '비밀번호 설정 규칙이 준수되지 않았습니다. (대소문자+숫자+특수기호 8자리 이상으로 구성하여야 합니다.)');
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
	// 이메일 주소 select 변경 시
	$("[name=email3]").change(function(){
		$("[name=email2]").val($("[name=email3]").val());
	});
	
	// 가입 버튼 클릭 시
	$("#btnSubmit").click(function(){
		var pwsRegExp	= /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/; 										//	비밀번호는 8자 이상이어야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.
		
		var userId 			= $("#userId").val();
		var userName 		= $("#userName").val();
		var password1 		= $("#password1").val();
		var password2 		= $("#password2").val();
		var phone1 			= $("#phone1").val();
		var phone2 			= $("#phone2").val();
		var phone3 			= $("#phone3").val();
		var postcode 		= $("#postcode").val();
		var address 		= $("#address").val();
		var detailAddress 	= $("#detailAddress").val();
		var email1 			= $("#email1").val();
		var email2 			= $("#email2").val();
		var email3 			= $("#email3").val();
		


		if(userId == null || userId == "")
		{
			alert("ID를 입력해주세요.");
			$("[name=userId]").focus();
			return false;
		}
		else if(userName.length < 1)
		{
			alert("이름을 입력해주세요.");
			$("[name=userName]").focus();
			return false;
		}
		else if(password1.length < 1)
		{
			alert("비밀번호를 입력해주세요.");
			$("[name=password1]").focus();
			return false;
		}
		else if(!pwsRegExp.test(password1))
		{
			alert("비밀번호는 8자 이상이어야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.");
			$("[name=password1]").focus();
			return false;
		}
		else if(password2.length < 1)
		{
			alert("비밀번호 확인란을 입력해주세요.");
			$("[name=password2]").focus();
			return false;
		}
		else if(password2 != password1)
		{
			alert("비밀번호가 일치하지 않습니다.");
			$("[name=password2]").focus();
			return false;
		}
		else if(phone1.length < 1)
		{
			alert("휴대전화번호를 입력해주세요.");
			$("[name=phone1]").focus();
			return false;
		}
		else if(phone2.length < 1)
		{
			alert("휴대전화번호를 입력해주세요.");
			$("[name=phone2]").focus();
			return false;
		}
		else if(phone3.length < 1)
		{
			alert("휴대전화번호를 입력해주세요.");
			$("[name=phone3]").focus();
			return false;
		}
		else if(postcode.length < 1)
		{
			alert("우편변호를 입력해주세요.");
			$("[name=postcode]").focus();
			return false;
		}
		else if(address.length < 1)
		{
			alert("주소를 입력해주세요.");
			$("[name=address]").focus();
			return false;
		}
		else if(detailAddress.length < 1)
		{
			alert("상세주소를 입력해주세요.");
			$("[name=detailAddress]").focus();
			return false;
		}
		else if(email1.length < 1)
		{
			alert("이메일 입력해주세요.");
			$("[name=email1]").focus();
			return false;
		}
		else if(email2.length < 1)
		{
			alert("이메일을 입력해주세요.");
			$("[name=email2]").focus();
			return false;
		}
		
		$("[name=formSubmit]").submit();
		
	});
	
	// 취소 버튼 클릭 시
/* 	$("#btnCancel").click(function(){
		var sequser = $("[name=sequser]").val();
		location.href="/info/user/UserInfo?sequser="+sequser;
	}); */
	
});
</script>