<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	// 수정 버튼
	$("#btnSubmit").click(function(){
		$.ajax({
			 url :"/addr/ModifyPData"
			,data : $("[name=formSubmit]").serialize()
			,success : function(data){
				if(data.code == "200")
				{
					common.alert('', '수정이 완료되었습니다.');
					window.opener.location.reload();
				}
				else
				{
					common.alert('', '이미 등록된 전화번호입니다.');
				}
			}
		});
	});

	// 닫기 버튼
	$("#btnCancel").click(function(){
		window.close();
	});

});
</script>