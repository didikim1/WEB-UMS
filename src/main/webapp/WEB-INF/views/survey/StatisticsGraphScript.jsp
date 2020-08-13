<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<script type="text/javascript">
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

function drawChart()
{
	var ivrlogmapperseq = "${paramMap.mapperseq}"
	var qNumber = "${paramMap.qNumber}"
	
	$.ajax({
		 url : "/survey/getUserInput"
		,data : {
			 ivrlogmapperseq : ivrlogmapperseq
			,qNumber : qNumber
		}
		,success : function(data){
			console.log(data.result);
		}
	});
	
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Pizza');
    data.addColumn('number', 'Populartiy');
    data.addRows([
    	['20대 이하', 	11],
		['30대', 	2],
		['40대',  	3],
		['50대', 	2],
		['60대',    	7],
		['70대 이상', 	5]
    ]);

	var options = {
		 title: '1번 항목'
	};

	var chart = new google.visualization.PieChart(document.getElementById('piechart'));

	chart.draw(data, options);
}

$(document).ready(function(){
	// 설문 결과 버튼
	$(".btnResult").click(function(){
		var mapperseq 	= "";
		var qNumber 	= "";

		var myForm = document.formStatisticsGraph;
		var popupX = window.opener.opener.screenLeft+(((window.opener.opener.outerWidth)*0.5)-300);
		var popupY = window.opener.opener.screenTop+(((window.opener.opener.outerHeight)*0.5)-182);
		var option = 'width=546, height=363, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
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