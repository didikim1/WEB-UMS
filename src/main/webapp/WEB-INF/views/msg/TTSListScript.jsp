<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
// 재생 버튼
function fn_playFile(fileUrl)
{
	var myForm = document.formTTSListen;
	var popupX 	= window.screenLeft+(((window.outerWidth)*0.5)-211);
 	var popupY 	= window.screenTop+(((window.outerHeight)*0.5)-50);
	var option 	= 'width=422, height=96, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
	window.open("", 'TTSListen', option);

	myForm.action 				= "/IVR/recordPlay";
	myForm.method 				= "POST";
	myForm.target 				= "TTSListen";
	myForm.fileUrl.value 		= fileUrl;
	myForm.submit();
}

$(document).ready(function(){
	// 선택 버튼
	$("#btnSubmit").click(function(){
		var seqarsalarmtts 	= 0;
		var recFilePrefix 	= null;
		var recFileName 	= null;
		var ttsMentTitle 	= null;
		var recFileUrl 		= null;

		var btnRadio 	= $("input[name=btnRadio]");

		for(var i=0; i<btnRadio.length; i++)
		{
			if(btnRadio.eq(i).prop("checked"))
			{
				seqarsalarmtts 	= btnRadio.eq(i).val();
				recFilePrefix 	= btnRadio.eq(i).parent("td").find("[name=recFilePrefix]").val();
				recFileName 	= btnRadio.eq(i).parent("td").find("[name=recFileName]").val();
				ttsMentTitle 	= btnRadio.eq(i).parent("td").find("[name=ttsMentTitle]").val();
				recFileUrl 	= btnRadio.eq(i).parent("td").find("[name=recFileUrl]").val();
				break;
			}
		}

		$(opener.document).find("[name=recFilePrefix]").val(recFilePrefix);
		$(opener.document).find("[name=recFileName]").val(recFileName);
		$(opener.document).find("[name=recFileUrl]").val(recFileUrl);
		$(opener.document).find("#recFileName").html(ttsMentTitle);
		window.close();

	});

	// 닫기 버튼
	$("#btnCancel").click(function(){
		window.close();
	});

});
</script>