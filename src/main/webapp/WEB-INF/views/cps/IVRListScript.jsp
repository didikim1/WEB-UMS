<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
function confirmCallback(type)
{
	if(type == "A")
	{
// 		var ivrserverArr = new Array();
// 		var checkbox = $("[name=checkbox]");
// 		for(var i=0; i<checkbox.length; i++)
// 		{
// 			if(checkbox.eq(i).prop("checked"))
// 			{
// 				var ivrserverObj = new Object();
// 				ivrserverObj.seqivrserver 	= checkbox.eq(i).val();
// 				ivrserverObj.serverName 	= checkbox.eq(i).parent("td").parent("tr").find("input[name=serverName]").val();
// 				ivrserverObj.serverIp 		= checkbox.eq(i).parent("td").parent("tr").find("input[name=serverIp]").val();
// 				ivrserverObj.cps 			= checkbox.eq(i).parent("td").parent("tr").find("input[name=cps]").val();
				
// 				ivrserverArr.push(ivrserverObj);
// 			}
// 		}
		
// 		$.ajax({
// 			 url : "/cps/Modify"
// 			,data : {
// 				ivrserverArr : JSON.stringify(ivrserverArr)
// 			}
// 			,success : function(data){
				
// 			}
// 		});
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
	// 달력
	$(".date").datepicker({
		 dayNamesMin : ['일', '월', '화', '수', '목', '금', '토']
		,monthNames : ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
		,dateFormat : "yy-mm-dd"
	});

	// 검색 버튼
	$("#btnSearch").click(function(){
// 		$("[name=page]").val("");
// 		$("[name=searchForm]").submit();
	});
	
	// 추가등록 및 변경 버튼
	$("#btnRegist").click(function(){
		var checkedSeqArr = new Array();
		var checkbox = $("[name=checkbox]");
		for(var i=0; i<checkbox.length; i++)
		{
			if(checkbox.eq(i).prop("checked"))
			{
				checkedSeqArr.push(checkbox.eq(i).val());
			}
		}
		
		// 추가등록
		if(checkedSeqArr.length < 1)
		{
			var url='/cps/RegistAndModify?isRegist=Y';
			var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
		 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-160);
			var option = 'width=546, height=317, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
			window.open(url, 'RegistServer', option);
		}
		// 변경
		else if(checkedSeqArr.length == 1)
		{
			var url='/cps/RegistAndModify?isRegist=N&seqivrserver='+checkedSeqArr[0];
			var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
		 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-160);
			var option = 'width=546, height=317, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
			window.open(url, 'RegistServer', option);
		}
		// 일괄변경 알림
		else
		{
			common.alert('추가등록 및 변경', '여러 항목을 일괄변경하시려면 일괄변경 버튼을 눌러주세요.');
		}
	});
	
	// 일괄변경
	$("#btnModify").click(function(){
		common.confirm('일괄변경', '일괄변경하시겠습니까?', 'A');
	});

});
</script>