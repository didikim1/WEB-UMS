<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	// 응답통계보기
	$("#btnStatistic").click(function(){
		var ivrlogmapperseq = "${paramMap.ivrlogmapperseq}";

		var myForm  = document.formStatistics;
		var url  	= "/survey/ResultStatistics?ivrlogmapperseq="+ivrlogmapperseq;
		var popupX 	= window.opener.screenLeft+(((window.opener.outerWidth)*0.5)-300);
		var popupY 	= window.opener.screenTop+(((window.opener.outerHeight)*0.5)-182);
		var option 	= 'width=546, height=367, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, "formStatistics", option);
	});

	// 닫기 버튼
	$("#btnCancel").click(function(){
		window.close();
	});
});
</script>