<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
function confirmCallback(type)
{
	if(type == "A")
	{
// 		var cpsserverArr = new Array();
// 		var checkbox = $("[name=checkbox]");
// 		for(var i=0; i<checkbox.length; i++)
// 		{
// 			if(checkbox.eq(i).prop("checked"))
// 			{
// 				var cpsserverObj = new Object();
// 				cpsserverObj.seqcpsserver 	= checkbox.eq(i).val();
// 				cpsserverObj.serverName 	= checkbox.eq(i).parent("td").parent("tr").find("input[name=serverName]").val();
// 				cpsserverObj.serverIp 		= checkbox.eq(i).parent("td").parent("tr").find("input[name=serverIp]").val();
// 				cpsserverObj.cps 			= checkbox.eq(i).parent("td").parent("tr").find("input[name=cps]").val();
				
// 				cpsserverArr.push(cpsserverObj);
// 			}
// 		}
		
// 		$.ajax({
// 			 url : "/cps/Modify"
// 			,data : {
// 				cpsserverArr : JSON.stringify(cpsserverArr)
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
			var option = 'width=546, height=337, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
			window.open(url, 'RegistServer', option);
		}
		// 변경
		else if(checkedSeqArr.length == 1)
		{
			var url='/cps/RegistAndModify?isRegist=N&seqcpsserver='+checkedSeqArr[0];
			var popupX = window.screenLeft+(((document.body.clientWidth)*0.5)-273);
		 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-160);
			var option = 'width=546, height=337, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
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
		var cpslogmapperseqArr = new Array();
		var checkbox = $("[name=checkbox]");
		for(var i=0; i<checkbox.length; i++)
		{
			if(checkbox.eq(i).prop("checked"))
			{
				cpslogmapperseqArr.push(checkbox.eq(i).val());
			}
		}

		cpslogmapperseqArrStr = cpslogmapperseqArr.toString();

		if(cpslogmapperseqArr.length == 0)
		{
			common.alert('일괄 변경 경고', '변경할 IVR을 선택하지 않았습니다. 변경할 서버를 선택하시고 수정값을 입력하세요.');
		}
		else
		{
			common.confirm('일괄변경', '총 '+cpslogmapperseqArr.length + ' 건을 일괄변경하시겠습니까?', 'A');
		}
	});

});
</script>