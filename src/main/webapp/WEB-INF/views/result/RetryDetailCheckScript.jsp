<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	// 확인 버튼
	$("#btnSubmit").click(function(){
		var ivrlogseq = $("[name=ivrlogseq]").val();
		$.ajax({
			 url : "/IVR/RetryIVREntrusting"
			,data : { ivrlogseq : ivrlogseq }
			,success : function(){
				window.opener.location.href="/result/ResultList";
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