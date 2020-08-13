<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	// 확인 버튼
	$("#btnSubmit").click(function(){
		var title 		= "${title}";
		var seq 		= "${seq}";
		var name 		= "${name}";
		var phonenumber = "${phonenumber}";

		if(title == "주소록 등록")
		{
			$.ajax({
				 url : "/addr/RegisterData"
				,data : {
					 name 			: name
					,phonenumber 	: phonenumber
				}
				,success : function(){
					window.opener.location.reload();
					window.close();
				}
			});
		}
		else if(title == "주소록 삭제")
		{
			console.log();

			// 개인주소록에서 삭제
			if(seq.split("|").length == "1")
			{
				$.ajax({
					 url : "/addr/DeletePData"
					,data : {
						seq : seq
					}
					,success : function(){
						window.opener.location.reload();
						window.close();
					}
				});
			}
			// 그룹주소록에서 삭제
			else if(seq.split("|").length == "2")
			{
				$.ajax({
					 url : "/addr/DeleteData"
					,data : {
						seq : seq
					}
					,success : function(){
						window.opener.location.reload();
						window.close();
					}
				});
			}

		}




	});

	// 취소 버튼
	$("#btnCancel").click(function(){
		window.close();
	});
});
</script>