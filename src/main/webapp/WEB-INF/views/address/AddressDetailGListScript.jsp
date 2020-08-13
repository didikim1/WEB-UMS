<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
// 확인 팝업창
function openPopup(seq, name, phonenumber, title, subtitle)
{
	var myForm = document.formAddrCheck;
	var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-120);
	var option = 'width=546, height=247, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
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
	var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-140);
	var option = 'width=546, height=287, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
	window.open(url, 'AddressDetail', option);
}

$(document).ready(function(){
	$('#phonenumber').mask('000-0000-0000');

	// 그룹원 추가 버튼
	$("#btnAdd").click(function(){
		var groupseq = "${groupseq}";
		var url='/msg/AddressList?isGroup=Y&seqgroupinfo='+groupseq;
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-333);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-310);
		var option = 'width=666, height=667, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, 'AddressList', option);
	});

	// 엑셀 업로드 버튼
	$("#btnExcel").click(function(){
		var url='/addr/ExcelUpload?etc=Y&seqgroupinfo='+$("[name=groupseq]").val();
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-190);
		var option = 'width=546, height=387, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, 'ExcelUpload', option);
	});

	// 엑셀양식받기 버튼
	$("#btnExcelForm").click(function(){
		var url='/msg/ExcelForm';
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-373);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-210);
		var option = 'width=746, height=447, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, 'ExcelForm', option);
	});

	// 삭제 버튼
	$(".btnDel").click(function(){
		var seq 		= $(this).attr("id");
		var name 		= null;
		var phonenumber = null;

		for(var i=0; i<$(".name").length; i++)
		{
			if($(".btnDel").eq(i).attr("id") == seq)
			{
				name 		= $(".name").eq(i).find("label").text();
				phonenumber = $(".phonenumber").eq(i).attr("id");
			}
		}

		seq += "|" + $("[name=groupseq]").val();

		openPopup(seq, name, phonenumber, "주소록 삭제", "※ 삭제할 내용을 확인해주세요.");
	});
});
</script>