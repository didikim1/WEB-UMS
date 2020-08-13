<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	// 확인 버튼
	$("#btnSubmit").click(function(){
		var ivrlogmapperseq = $("[name=ivrlogmapperseq]").val();
		$.ajax({
			 url : "/IVR/RetryGroupIVREntrusting"
			,data : { ivrlogmapperseq : ivrlogmapperseq }
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