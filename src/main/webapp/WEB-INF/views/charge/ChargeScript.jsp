<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">

function confirmCallback(type)
{
	if(type == "A")
	{
// 		$.ajax({
// 			 url : "/chage/payment1"
// 			,data : {
// 				bankName : bankName,
// 				account : account,
// 				owner : owner,
// 				amount : amount,
// 				seq : seq
// 			}
// 			,success : function(data){
// 				location.reload();
// 			}
// 		});

		alert("계좌이체")
	}
	else if(type == "B")
	{
// 		$.ajax({
// 			 url : "/chage/payment2"
// 			,data : {
// 				amount : amount,
// 				seq : seq
// 			}
// 			,success : function(data){
// 				location.reload();
// 			}
// 		});
		alert("가상계좌")
	}
}

$(document).ready(function(){
	// 계좌이체 입력란 숨김
	$(".bankInfo").hide();
	
	// 계좌이체 변경 버튼
	$("[name=chargeType]").click(function(){
		if ($("input[type=radio][name=chargeType]:checked").val() == "1") {
			$(".bankInfo").show();
		} else {
			$(".bankInfo").hide();
		}
	});
	

	// 충전결재 버튼
	$("#btnSubmit").click(function(){
		var amount = $("input[type=radio][name=amount]:checked").val();
		var chargeType = $("input[type=radio][name=chargeType]:checked").val();
		
		if (!$('input:radio[name=amount]').is(':checked')) {
			alert("충전금액을 선택해주세요");
			return false;
		}

		if (amount == "0") {
			var amount2 = $("[name=amount2]").val();
			
			if (amount2 == "") {
				alert("충전금액을 직접 입력해주세요");
				$("[name=amount2]").focus();
				return false;
			} else if (amount2 >= 5000) {
				amount = amount2;
			} else {
				alert("충전금액은 5,000원이상 충전 가능합니다.")	
				$("[name=amount2]").val("");
				$("[name=amount2]").focus();
				return false;
			}
		}
		
		if (amount == "") {
			alert("충전금액을 선택해주세요");
			return false;
		}
		
		if (!$('input:radio[name=chargeType]').is(':checked')) {
			alert("충전방법을 선택해주세요");
			return false;
		}

		if (chargeType == "") {
			alert("충전방법을 선택해주세요");
			return false;
		}
		
// 		alert(amount + "+" + chargeType)
		var amountComma = amount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','); 
		
		if(chargeType == "1")
		{
			var bankName = $("[name=bankName]").val();
			var account = $("[name=account]").val();
			var owner = $("[name=owner]").val();
			
			if (bankName =="") {
				alert("은행명을 입력해주세요");
				$(".bankName").focus();
				return false;
			}

			if (account =="") {
				alert("계좌번호를 입력해주세요");
				$(".account").focus();
				return false;
			}

			if (owner =="") {
				alert("예금주명을 입력해주세요");
				$(".owner").focus();
				return false;
			}

			common.confirm('충전결제', "예금주 " + owner + " 님의 " + bankName.replace("은행", "") + " 은행 계좌번호 " + account + " 계좌의 즉시이체를 이용하여, "+ amountComma +'원 충전을 진행하시겠습니까?', 'A');
		}
		else if(chargeType == "2")
		{
			common.confirm('충전결제', '가상계좌를 이용하여 '+ amountComma +'원 충전을 진행하시겠습니까?', 'B');
		} else {
			alert("충전방법을 선택해주세요");
			return false;
		}

	});

});
</script>