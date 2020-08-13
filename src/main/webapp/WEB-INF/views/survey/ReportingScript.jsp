<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<script type="text/javascript">
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

function fn_Draw(title, list, id)
{
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'key');
    data.addColumn('number', 'value');
    data.addRows(list);
    
    var options = {
   		 title: title
   		,'height':300
   	};
    
    var chart = new google.visualization.PieChart(document.getElementById(id));
    
    chart.draw(data, options);
}

function drawChart()
{
	var list1 =
		[
			['70대 이상', 	5],
			['60대',    	10],
			['50대', 	15],
			['40대',  	25],
			['30대', 	15],
	    	['20대 이하', 	5]
	    ];
	var list2 =
		[
			['여성', 	25],
			['남성', 	45],
			['기타', 	30]
	    ];
	fn_Draw('귀하의 나이는 어떻게 되십니까?', list1, 'piechart1');
	fn_Draw('귀하의 성별은 무엇입니까?', list2, 'piechart2');
}

$(document).ready(function(){
	
});
</script>