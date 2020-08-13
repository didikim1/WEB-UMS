<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
var ivrlogmapperseq 		= "";
var ivrlogmapperseqArrStr 	= "";
function confirmCallback(type)
{
	if(type == "A")
	{
		var url='/result/RetryCheck?ivrlogmapperseq='+ivrlogmapperseq;
		var popupX = window.screenLeft+(((window.outerWidth)*0.5)-273);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-250);
		var option = 'width=546, height=493, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, 'RetryCheck', option);
	}
	else if(type == "B")
	{
		$.ajax({
			 url : "/result/RepeatCancel"
			,data : {
				ivrlogmapperseq : ivrlogmapperseqArrStr
			}
			,success : function(data){
				location.reload();
			}
		});
	}
}

function fn_Sorting(sidx)
{
	var currentSidx = $("[name=sidx]").val();
	var currentSord = $("[name=sord]").val();
	var changeSidc  = "";
	var changeSord  = "";

	if(currentSidx == sidx)
	{
		changeSidc = sidx;
		changeSord = (currentSord == "DESC") ? "ASC" : "DESC";
	}
	else
	{
		changeSidc = sidx;
		changeSord = "DESC";
	}

	$("[name=sidx]").val(changeSidc);
	$("[name=sord]").val(changeSord);

	$("[name=searchForm]").submit();
}

$(document).ready(function(){
	// 전체 선택
	$("[name=checkAll]").click(function(){
		for(var i=0; i<$("input[name=checkbox]").length; i++)
		{
			$("input[name=checkbox]").eq(i).prop("checked", $(this).prop("checked"));
		}
	});

	// 서비스 소개 버튼
	$("#btnService").click(function(){
		var url='/msg/ServiceIntro';
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-190);
		var option = 'width=546, height=378, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, 'ServiceIntro', option);
	});

	// 달력
	$(".sendDate").datepicker({
		 dayNamesMin : ['일', '월', '화', '수', '목', '금', '토']
		,monthNames : ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
		,dateFormat : "yy-mm-dd"
	});

	// 검색 버튼
	$("#btnSearch").click(function(){
		$("[name=page]").val("");
		$("[name=searchForm]").submit();
	});

	// 제목 클릭
	$(".title").click(function(){
		location.href = "/result/RepeatDetailList?ivrlogmapperseq="+$(this).attr("id")+"&CType=true&sendTime=C";
	});

	// 전송내용 클릭
	$(".ment").click(function(){
		var ttsMentIntro01 = $(this).attr("id");

		var myForm = document.TTSMentPopup;
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-190);
		var option = 'width=546, height=378, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open("", 'TTSMentPopup', option);

		myForm.action 				= "/result/TTSMentPopup";
		myForm.method 				= "post";
		myForm.target 				= "TTSMentPopup";
		myForm.ttsMentIntro01.value = ttsMentIntro01;
		myForm.submit();
	});

	// 재발송 버튼
	$(".btnResend").click(function(){
		if($(this).parent("td").parent("tr").find("td").eq(9).html() != "전송완료")
		{
			common.alert('재발송', '전송완료된 그룹만 재발송이 가능합니다.');
			return false;
		}

		var ttsTitle 	= $(this).parents("tr").find(".pointer").text();
		ivrlogmapperseq = $(this).parents("tr").find(".pointer").attr("id");

		common.confirm('재발송', '[ '+ttsTitle+' ] 에 대해서 즉시 재발송 하시겠습니까?', 'A');

	});

	// 삭제 버튼
	$("#btnCancel").click(function(){
		var ivrlogmapperseqArr = new Array();
		var checkbox = $("[name=checkbox]");
		for(var i=0; i<checkbox.length; i++)
		{
			if(checkbox.eq(i).prop("checked"))
			{
				ivrlogmapperseqArr.push(checkbox.eq(i).val());
			}
		}

		ivrlogmapperseqArrStr = ivrlogmapperseqArr.toString();

		if(ivrlogmapperseqArr.length == 0)
		{
			common.alert('반복메세지 삭제', '반복메세지를 선택하지 않았습니다.');
		}
		else
		{
			common.confirm('반복메세지 삭제', '총 '+ivrlogmapperseqArr.length+'건의 반복메세지를 삭제 하시겠습니까?', 'B');
		}
	});

});
</script>