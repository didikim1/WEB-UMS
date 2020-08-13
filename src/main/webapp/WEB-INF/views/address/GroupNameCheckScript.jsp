<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	// 확인 버튼
	$("#btnSubmit").click(function(){
		var groupseq = $("[name=groupseq]").val();
		$.ajax({
			 url : "/addr/DeleteGroup"
			,data : {
				groupseq : groupseq
			}
			,success : function(){
				window.opener.location.reload();
				window.close();
			}
		});
	});

	// 취소 버튼
	$("#btnCancel").click(function(){
		window.close();
	});
});
</script>