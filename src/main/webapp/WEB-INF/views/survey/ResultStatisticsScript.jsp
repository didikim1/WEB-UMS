<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	// 설문 결과 버튼
	$(".btnResult").click(function(){
		var mapperseq 	= "${paramMap.ivrlogmapperseq}";
		var qNumber 	= $(this).parent("td").prev("td").text();

		var myForm = document.formStatisticsGraph;
		var popupX = window.opener.opener.screenLeft+(((window.opener.opener.outerWidth)*0.5)-383);
		var popupY = window.opener.opener.screenTop+(((window.opener.opener.outerHeight)*0.5)-267);
		var option = 'width=766, height=537, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open('', "formStatisticsGraph", option);

		myForm.action 				= "/survey/StatisticsGraph";
		myForm.method 				= "POST";
		myForm.target 				= "formStatisticsGraph";
		myForm.mapperseq.value 		= mapperseq;
		myForm.qNumber.value 		= qNumber;
		myForm.submit();
	});

	// 닫기 버튼
	$("#btnCancel").click(function(){
		window.close();
	});
});
</script>