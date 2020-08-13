<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
//확인 팝업창
function openPopup(seq, name, phonenumber, title, subtitle)
{
	var myForm = document.formAddrCheck;
	var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-120);
	var option = 'width=546, height=243, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
	window.open("", 'AddressCheck__', option);

	myForm.action 				= "/addr/AddressCheck";
	myForm.method 				= "post";
	myForm.target 				= "AddressCheck__";
	myForm.title.value 			= title;
	myForm.subtitle.value 		= subtitle;
	myForm.seq.value 			= seq;
	myForm.name.value 			= name;
	myForm.phonenumber.value 	= phonenumber;
	myForm.submit();
}

$(document).ready(function(){
	// 그룹명 검색 버튼
	$("#btnSearch").click(function(){
		$("[name=page]").val("");
		$("[name=searchForm]").submit();
	});

	// 그룹 추가
	$("#btnAdd").click(function(){
		var groupname = $("#groupname").val();

		if(groupname.length < 1)
		{
			alert("그룹명을 적어주세요.");
			$("#groupname").focus();
			return false;
		}

		$.ajax({
			 url : "/addr/GroupNameDupl"
			,data : {groupname : $("#groupname").val()}
			,success : function(data){
				if(data.code == "200")
				{
					$.ajax({
						 url : "/addr/RegisterGroup"
						,data : {groupname : $("#groupname").val()}
						,success : function(){
							location.reload();
						}
					});
				}
				else
				{
					alert("이미 등록된 그룹명입니다.");
				}

			}
		});


// 		var url='/addr/RegisterGroupPopup';
// 		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
// 	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-100);
// 		var option = 'width=546, height=193, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
// 		window.open(url, 'RegisterGroupPopup', option);
	});

	// 그룹이름 클릭
	$(".pointer").click(function(){
		var groupname 	= $(this).parent("td").parent("tr").find(".pointer").text();
		location.href="/addr/AddressDetailGList?groupname="+groupname+"&groupseq="+$(this).attr("id");
	});

	// 삭제 버튼
	$(".btnDel").click(function(){
		var groupseq 	= $(this).parent("td").parent("tr").find(".pointer").attr("id");
		var groupname 	= $(this).parent("td").parent("tr").find(".pointer").text();

		var url ='/addr/GroupNameCheck?groupseq='+groupseq+'&groupname='+groupname;
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-120);
		var option = 'width=546, height=243, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, 'GroupNameCheck', option);

	});

});
</script>