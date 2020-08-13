<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	// 확인 버튼
	$("#btnSubmit").click(function(){
		var seqgroup 	= "${seqgroup}";
		var name 		= "${name}";
		var phonenumber = "${phonenumber}";

		var dataArr = new Array();

		var addValue = {
			 name : name
			,phonenumber : phonenumber
		}

		dataArr.push(addValue);
		
		// ..START
		if(seqgroup == "")
		{
			$.ajax({
				 url : "/msg/InsertTempGroup"
				,data : {
					 name : name
					,phonenumber : phonenumber
				}
				,success : function(data){
					window.opener.document.getElementById("formSubmit").seqgroup.value = data.result.seqgroup;
					window.opener.document.getElementById("formAddrCheck").seqgroup.value = data.result.seqgroup;
					window.opener.fn_UploadComplete();
					window.opener.setListSize();
					window.close();
				}
			});
		}
		else
		{
			$.ajax({
				 url : "/msg/InsertTempTarget"
				,data : {
					 seqgroup : seqgroup
					,name : name
					,phonenumber : phonenumber
				}
				,success : function(data){
					window.opener.fn_UploadComplete();
					window.opener.setListSize();
					window.close();
				}
			});
		}
		// ..END

// 		window.opener.useTemplate(dataArr);
// 		window.close();
	});

	// 취소 버튼
	$("#btnCancel").click(function(){
		window.close();
	});
});
</script>