<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
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

		if($("#uploadFile").val() == "")
		{
			alert("선택된 파일이 없습니다.");
			return false;
		}

	    $.ajax({
			 url : "/msg/UploadFileTxt"
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
					window.opener.useTemplate(data.result.list);
					window.opener.setParams(data.result);
					window.opener.setListSize(data.result.paramMap);
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