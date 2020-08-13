<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	// 다운받기 버튼
	$("#btnSubmit").click(function(){
		location.href="/msg/ExcelFormDown";
// 		$.ajax({
// 			 url : "/msg/ExcelFormDown"
// 			,success : function(){

// 			}
// 		});
	});

	// 닫기 버튼
	$("#btnCancel").click(function(){
		window.close();
	});
});
</script>