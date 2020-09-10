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

	    $.ajax({
			 url : "/msg/UploadTTSFile"
			,type : "POST"
			,contentType: 'multipart/form-data'
			,data : formData
			,async:false
	        ,cache:false
			,contentType : false
	        ,processData : false
			,success : function(data){
				console.log(data);
				console.log(data.result.uploadPath);
				console.log(data.result.uploadPath.split("uploadfile"));

				$(opener.document).find("[name=recFilePrefix]").val(data.result.uploadPath);
				$(opener.document).find("[name=recFileName]").val(data.result.orignlFileNm);
// 				$(opener.document).find("[name=detailPath]").val(data.result.uploadPath.split("uploadfile")[1]);
				$(opener.document).find("[name=detailPath]").val(data.result.uploadPath.split("C:\\Temp\\TempVMS\\")[1]);
				$(opener.document).find("[name=seqfile]").val(data.result.seq);
				$(opener.document).find("#recFileName").html(data.result.orignlFileNm);
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