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

	// 즉시발송 버튼
	$(".btnSend").click(function(){
		var nextcallDate 	= $(this).attr("id");
		var ivrlogmapperseq = "${ivrlogmapperseq}";

// 		common.confirm('즉시발송', '전송시간 [ '+nextcallDate+' ] 에 대해서 즉시 재발송 하시겠습니까?', 'A');

// 		$.ajax({
// 			 url : "/IVR/SendRepeatCall"
// 			,data : {
// 				 nextcallDate : nextcallDate
// 				,ivrlogmapperseq : ivrlogmapperseq
// 			}
// 			,success : function(){
// 				location.reload();
// 			}
// 		});
	});

	// 상세정보 버튼
	$(".btnInfo").click(function(){
		var nextcallDate 	= $(this).attr("id");
		var ivrlogmapperseq = "${ivrlogmapperseq}";

		location.href="/result/RepeatDetailListByTime?ivrlogmapperseq="+ivrlogmapperseq+"&nextcallDate="+nextcallDate;
	});

	// 확인 버튼 클릭 시
	$("#btnCancel").click(function(){
		var ivrlogmapperseq = $("[name=ivrlogmapperseq]").val();
		location.href="/result/RepeatList";
	});
	
});
</script>