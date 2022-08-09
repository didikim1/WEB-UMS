<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
var ivrlogseqArr 	= new Array();
var ivrlogmapperseq = "${ivrlogmapperseq}";
var name 			= "";
var ivrlogseq 		= "";
var phonenumber 	= "";

function confirmCallback(type)
{
	if(type == "A")
	{
// 		$.ajax({
// 			 url : "/result/RepeatCancel"
// 			,data : {
// 				 ivrlogseqArr : ivrlogseqArr.toString()
// 				,ivrlogmapperseq : ivrlogmapperseq
// 			}
// 			,success : function(data){
// 				if(data.message == "DELETE")
// 				{
// 					location.href="/result/ResultList";
// 				}
// 				else
// 				{
// 					location.reload();
// 				}
// 			}
// 		});
	}
	else if(type == "B")
	{
		var myForm = document.formRetryDetailCheck;
		var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-120);
		var option = 'width=546, height=247, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open('', 'RetryDetailCheck', option);

		myForm.action 				= "/result/RetryDetailCheck";
		myForm.method 				= "POST";
		myForm.target 				= "RetryDetailCheck";
		myForm.ivrlogseq.value 		= ivrlogseq;
		myForm.name.value 			= name;
		myForm.phonenumber.value 	= phonenumber;
		myForm.submit();
	}
}

// 컬럼명 클릭 시 정렬
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
	// 달력
	$(".sendDate").datepicker({
		 dayNamesMin : ['일', '월', '화', '수', '목', '금', '토']
		,monthNames : ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
		,dateFormat : "yy-mm-dd"
	});

	// 전체 선택
	$("[name=checkAll]").click(function(){
		for(var i=0; i<$("input[name=checkbox]").length; i++)
		{
			$("input[name=checkbox]").eq(i).prop("checked", $(this).prop("checked"));
		}
	});

	// 재발송 버튼
	$(".btnResend").click(function(){
		var ivrlogseq 	= $(this).attr("id");
		var name 		= $(this).parents("tr").find(".targetName").text();
		var phonenumber = $(this).parents("tr").find(".targetPhonenumber").attr("id");

		common.confirm('재발송', name+'님께 재발송 하시겠습니까?', 'B');

	});
	
	// 다운로드 버튼
	$(".btnDownload").click(function(){
		var url 	= "/result/ExcelDownload";
		var method 	= "get";
		var inputs = "";
		
		var unindexed_array = $("[name=searchForm]").serializeArray();
	    $.map(unindexed_array, function(n, i){
	        inputs+='<input type="hidden" name="'+n['name'] +'" value="'+ n['value']+'" />';
	    });
		
	    $('<form action="'+ url +'" method="'+ (method||'post') +'">'+inputs+'</form>').appendTo('body').submit().remove();
		
	});
	
	// 일괄 재발송 버튼
	$(".btnSelectedResend").click(function(){
		$.ajax({
			 url : "/IVR/RetrySelectedIVREntrusting"
			,data : $("[name=searchForm]").serialize()
			,success : function(data){
				common.alert('재발송', '일괄 재발송이 완료되었습니다.');
			}
		});
	});

	// 예약 취소
	$("#btnRCancel").click(function(){
		var checkbox = $("[name=checkbox]");
		for(var i=0; i<checkbox.length; i++)
		{
			if(checkbox.eq(i).prop("checked"))
			{
				if(checkbox.eq(i).parent("td").parent("tr").find(".ivrlogResult").html() == "대기")
				{
					ivrlogseqArr.push(checkbox.eq(i).val());
				}
			}

		}

		if(ivrlogseqArr.length == 0)
		{
			common.alert('예약 취소', '예약콜을 선택하지 않았습니다.');
		}
		else
		{
			common.confirm('예약 취소', '총 '+ivrlogseqArr.length+'건을 예약 취소 하시겠습니까?', 'A');
		}
	});

	// 확인 버튼 클릭 시
	$("#btnCancel").click(function(){
		var ivrlogmapperseq = $("[name=ivrlogmapperseq]").val();
		location.href="/result/SMSResultList";
	});

});
</script>