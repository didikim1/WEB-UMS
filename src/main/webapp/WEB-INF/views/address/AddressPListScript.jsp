<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
var seqArr = new Array();

// 확인 팝업창
function openPopup(seq, name, phonenumber, title, subtitle)
{
	var myForm = document.formAddrCheck;
	var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-120);
	var option = 'width=546, height=243, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
	window.open("", 'AddressCheck_', option);

	myForm.action 				= "/addr/AddressCheck";
	myForm.method 				= "post";
	myForm.target 				= "AddressCheck_";
	myForm.title.value 			= title;
	myForm.subtitle.value 		= subtitle;
	myForm.seq.value 			= seq;
	myForm.name.value 			= name;
	myForm.phonenumber.value 	= phonenumber;
	myForm.submit();
}

// 수정 팝업창
function fn_modify(seq)
{
	var url='/addr/AddressDetail?seq='+seq;
	var popupX = window.screenLeft+(((window.outerWidth)*0.5)-273);
 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-150);
	var option = 'width=546, height=293, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
	window.open(url, 'AddressDetail', option);
}

function confirmCallback(type)
{
	if(type == "DELETE")
	{
		$.ajax({
			 url : "/addr/DeletePDatas"
			,data : {
				seqArrStr : seqArr.toString()
			}
			,success : function(){
				location.reload();
			}
		});
	}
}

$(document).ready(function(){
	$('#phonenumber').mask('000-0000-0000');

	// 검색 버튼
	$("#btnSearch").click(function(){
		$("[name=page]").val("");
		$("[name=searchForm]").submit();
	});

	// 엑셀 업로드 버튼
	$("#btnExcel").click(function(){
		var url='/addr/ExcelUpload?etc=P';
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-190);
		var option = 'width=546, height=383, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, 'ExcelUpload', option);
	});

	// 엑셀양식받기 버튼
	$("#btnExcelForm").click(function(){
		var url='/msg/ExcelForm';
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-373);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-210);
		var option = 'width=746, height=443, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, 'ExcelForm', option);
	});

	// 등록 버튼
	$("#btnAdd").click(function(){
		var name 		= $("#name").val();
		var phonenumber = $("#phonenumber").val().replace(/-/gi,"");

		if(name == null || name == "")
		{
			alert("이름은 필수 입력사항입니다.");
			$("#name").focus();
			return false;
		}

		if(phonenumber == null || phonenumber == "")
		{
			alert("전화번호는 필수 입력사항입니다.");
			$("#phonenumber").focus();
			return false;
		}
		else if(phonenumber.length < 11)
		{
			alert("전화번호를 다시 확인해주세요.");
			$("#phonenumber").focus();
			return false;
		}

		$.ajax({
			 url : "/addr/SearchDupl"
			,data : {phonenumber : phonenumber}
			,success : function(data){
				if(data.code == "200")
				{
					$.ajax({
						 url : "/addr/SelectTarget"
						,data : {
//			 				 name : name
							 phonenumber : phonenumber
						}
						,success : function(data){
							if(data.result < 1)
							{
								openPopup(0, name, phonenumber, "주소록 등록", "※ 등록할 내용을 확인해주세요.");
							}
							else
							{
								alert("이미 등록된 전화번호입니다.");
							}
						}
					});
				}
				else
				{
					alert("이미 등록된 전화번호입니다.");
				}
			}
		});



	});

	// 삭제 버튼
	$(".btnDel").click(function(){
		var seq 		= $(this).attr("id");
		var name 		= null;
		var phonenumber = null;

		for(var i=0; i<$(".name").length; i++)
		{
			if($(".name").eq(i).find("input[type=hidden]").val() == seq)
			{
				name 		= $(".name").eq(i).find("label").text();
				phonenumber = $(".phonenumber").eq(i).attr("id");
			}
		}

		openPopup(seq, name, phonenumber, "주소록 삭제", "※ 삭제할 내용을 확인해주세요.");
	});

	// 일괄 삭제 버튼
	$("#btnDelete").click(function(){
		var checkboxes 	= $("input[type=checkbox]");

		for(var i=0; i<checkboxes.length; i++)
		{
			if(checkboxes.eq(i).prop("checked"))
			{
				seqArr.push(checkboxes.eq(i).val());
			}
		}

		common.confirm('일괄 삭제', '선택하신 목록을 일괄삭제하시겠습니까?', 'DELETE');

	});
});
</script>