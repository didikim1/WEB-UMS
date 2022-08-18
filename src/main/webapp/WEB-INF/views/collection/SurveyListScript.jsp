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
			 url : "/IVR/SendWaitingCall"
			,data : {
				ivrlogmapperseq : ivrlogmapperseq
			}
			,success : function(data)
			{
				location.reload();
			}
		});
	}
	else if(type == "C")
	{
		$.ajax({
			 url : "/result/ReservationCancel"
			,data : {
				ivrlogmapperseq : ivrlogmapperseqArrStr
			}
			,success : function(data)
			{
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
// 	$("[name=checkAll]").click(function(){
// 		for(var i=0; i<$("input[name=checkbox]").length; i++)
// 		{
// 			$("input[name=checkbox]").eq(i).prop("checked", $(this).prop("checked"));
// 		}
// 	});

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
	});
		
	// 제목 클릭
	$(".title").click(function(){
		location.href = "/result/ReservationDetailList?ivrlogmapperseq="+$(this).attr("id")+"&BType=true&sendTime=B";
	});

	// 전송내용 클릭
	$(".ment").click(function(){
		var ttsMentIntro01 = $(this).attr("id");

		var myForm = document.TTSMentPopup;
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-190);
		var option = 'width=546, height=440, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open("", 'TTSMentPopup', option);

		myForm.action 				= "/result/TTSMentPopup";
		myForm.method 				= "post";
		myForm.target 				= "TTSMentPopup";
		myForm.ttsMentIntro01.value = ttsMentIntro01;
		myForm.submit();
	});

	// 재발송 버튼
	$(".btnResend").click(function(){
		if($(this).next("[name=status]").val() != "전송완료")
		{
			common.alert('재발송', '전송완료된 그룹만 재발송이 가능합니다.');
			return false;
		}

		var ttsTitle 	= $(this).parents("tr").find(".title").text();
		ivrlogmapperseq = $(this).parents("tr").find(".title").attr("id");

		common.confirm('재발송', '[ '+ttsTitle+' ] 에 대해서 즉시 재발송 하시겠습니까?', 'A');

	});

	// 즉시발송 버튼
	$(".btnSend").click(function(){
		var ttsTitle 	= $(this).parents("tr").find(".title").text();
		ivrlogmapperseq = $(this).parents("tr").find(".title").attr("id");
		common.confirm('즉시발송', '[ '+ttsTitle+' ] 에 대해서 즉시발송 하시겠습니까?', 'B');
	});

	// 전송완료 버튼
	$(".btnComplete").click(function(){
		$(this).parents("tr").find(".title").click();
	});

	// 예약 취소 버튼
	$("#btnRCancel").click(function(){
		var ivrlogmapperseqArr = new Array();
		var checkbox = $("[name=checkbox]");
		for(var i=0; i<checkbox.length; i++)
		{
			if(checkbox.eq(i).prop("checked"))
			{
				if(checkbox.eq(i).parent("td").parent("tr").find(".statusCompletion").html().trim() == "대기")
				{
					ivrlogmapperseqArr.push(checkbox.eq(i).val());
				}
			}
		}

		ivrlogmapperseqArrStr = ivrlogmapperseqArr.toString();

		if(ivrlogmapperseqArr.length == 0)
		{
			common.alert('예약 취소', '대기중인 예약메세지를 선택하지 않았습니다.');
		}
		else
		{
			common.confirm('예약 취소', '총 '+ivrlogmapperseqArr.length+'건의 대기중인 예약메세지를 취소 하시겠습니까?', 'C');
		}
	});

	// 일괄삭제 버튼
	$("#btnDelete").click(function(){
		var surveylogmapperseqArr = new Array();
		var checkbox = $("[name=checkbox]");
		for(var i=0; i<checkbox.length; i++)
		{
			if(checkbox.eq(i).prop("checked"))
			{
				surveylogmapperseqArr.push(checkbox.eq(i).val());
			}
		}

		surveylogmapperseqArrStr = surveylogmapperseqArr.toString();

		if(surveylogmapperseqArr.length == 0)
		{
			common.alert('설문지 파일 선택 삭제', '삭제할 설문지 파일을 선택하지 않았습니다.');
		}
		else
		{
			common.confirm('설문지 파일 선택 삭제', '총 '+surveylogmapperseqArr.length+'건의 설문지 파일을 삭제 하시겠습니까?', 'C');
		}
	});

	// 엑셀업로드 버튼
	$("#btnSubmit").click(function(){
		initWebSocket();
		
		var url='/collection/FileSearch';
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-190);
		var option = 'width=546, height=287, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, 'FileSearch', option);
	});
	
});
</script>