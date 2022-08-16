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
	var option = 'width=606, height=343, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
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
// 	$('#phonenumber').mask('000-0000-0000');

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
		else if((phonenumber.length < 9) || (11 < phonenumber.length))
		{
			alert("전화번호를 다시 확인해주세요.");
			$("#phonenumber").focus();
			return false;
		}

		// 등록된 번호인지 체크
		$.ajax({
			 url : "/addr/SearchDupl"
			,data : {phonenumber : phonenumber}
			,success : function(data){
				// 등록된 번호가 아닐 때
				if(data.result < 1)
				{
					openPopup(0, name, phonenumber, "주소록 등록", "※ 등록할 내용을 확인해주세요.");
				}
				// 등록된 번호일 때
				else
				{
					alert("이미 등록된 전화번호입니다.");
				}
			}
		});
	});
	
	// 전체선택
	$("thead input[type=checkbox]").click(function(){
		var checkboxes = $("tbody input[type=checkbox]");
		for(var i=0; i<checkboxes.length; i++)
		{
			checkboxes.eq(i).prop("checked", $(this).prop("checked"));
		}
	});
	
	// 체크박스 클릭 시
	$("tbody input[type=checkbox]").click(function(){
		var checkboxes = $("tbody input[type=checkbox]");
		for(var i=0; i<checkboxes.length; i++)
		{
			if(!checkboxes.eq(i).prop("checked"))
			{
				$("thead input[type=checkbox]").prop("checked", false);
				return;
			}
		}
		
		$("thead input[type=checkbox]").prop("checked", true);
	});

	// 일괄 삭제 버튼
	$("#btnDelete").click(function(){
		var seqArr = new Array();
		var checkboxes 	= $("tbody input[type=checkbox]");

		for(var i=0; i<checkboxes.length; i++)
		{
			if(checkboxes.eq(i).prop("checked"))
			{
				seqArr.push(checkboxes.eq(i).val());
			}
		}
		
		if(seqArr.length == 0)
		{
			common.alert('일괄 삭제', '삭제할 주소를 선택하지 않았습니다.');
		}
		else
		{
			common.confirm('일괄 삭제', '선택하신 목록을 일괄삭제하시겠습니까? (총 '+seqArr.length+' 명)', 'DELETE');
		}
	});
});
</script>