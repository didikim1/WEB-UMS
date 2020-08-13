<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	// 설문결과
	$(".btnResult").click(function(){
		var ivrlogmapperseq = "${paramMap.ivrlogmapperseq}";
		
		var myForm  = document.formResult;
		var url 	= "/survey/TargetResult?ivrlogseq="+$(this).attr("id")+"&ivrlogmapperseq="+ivrlogmapperseq;
		var popupX 	= window.screenLeft+(((window.outerWidth)*0.5)-300);
		var popupY 	= window.screenTop+(((window.outerHeight)*0.5)-259);
		var option 	= 'width=546, height=517, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open(url, "TargetResult", option);

	});
});
</script>