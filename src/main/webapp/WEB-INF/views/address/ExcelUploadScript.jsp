<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	console.log($("[name=seqgroupinfo]").val());
	// 찾아보기 버튼
	$("#btnFind").click(function(){
		$("#uploadFile").click();
	});
	$("#uploadFile").change(function(){
		var fileObj 	= $("#uploadFile").val();
		var fileName 	= fileObj.split("\\")[fileObj.split("\\").length-1];

		$("#fileName").val(fileName);
	});

	// 확인 버튼
	$("#btnSubmit").click(function(){
		var myForm = document.getElementById('searchForm');
		var formData = new FormData(myForm);

	    $.ajax({
			 url : "/msg/UploadFile"
			,type : "POST"
			,contentType: 'multipart/form-data'
			,data : formData
			,async:false
	        ,cache:false
			,contentType : false
	        ,processData : false
			,success : function(data){
				if(data.code != 200)
				{
					alert(data.result);
				}
				else
				{
					if(data.result.length > 0)
					{
						alert(data.result);
					}
					window.opener.location.reload();
				}
				window.close();
			}
		});
	});

	// 닫기 버튼
	$("#btnCancel").click(function(){
		window.close();
	});
});
</script>