<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow-y:hidden; width:480px; height:300px; border: 3px solid #F7941D; background:#f8f9ff; padding:30px;}
	.ser_text { font-size:14px;line-height:20px;margin-bottom:3px; }
	.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
	.col_r {color:#cd2129;}

	.tbl_type01 th, .tbl_type01 td { padding-left:0;padding:0;font-size:86%;text-align:center; }
	.tbl_type01 tr {line-height:0.5;}
	.tbl_type01 td {background-color:#fff;}
	.btnResult { height:25px;padding:3px 5px !important;float:none !important;margin-left:0px !important;font-size:13px !important; }
	#btnCancel { width:120px;height:30px;padding:1px 6px; }
</style>

<body>
	<div id="wrap_pop_ser">

		<div class="ser_text">
			<a>[설문지 #1] 응답 결과 통계 자료</a>
		</div>

		<!-- table -->
		<div class="tbl_type01">
			<table>
				<caption>리스트</caption>
				<colgroup>
					<col width="20%" />
					<col width="30%" />
					<col width="20%" />
					<col width="30%" />
				</colgroup>
				<thead>
					<tr>
						<th>설문항</th>
						<th>응답결과</th>
						<th>설문항</th>
						<th>응답결과</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td><button type="button" class="btn_table btnResult">설문결과</button></td>
						<td>6</td>
						<td><button type="button" class="btn_table btnResult">설문결과</button></td>
					</tr>
					<tr>
						<td>2</td>
						<td><button type="button" class="btn_table btnResult">설문결과</button></td>
						<td>7</td>
						<td><button type="button" class="btn_table btnResult">설문결과</button></td>
					</tr>
					<tr>
						<td>3</td>
						<td><button type="button" class="btn_table btnResult">설문결과</button></td>
						<td>8</td>
						<td><button type="button" class="btn_table btnResult">설문결과</button></td>
					</tr>
					<tr>
						<td>4</td>
						<td><button type="button" class="btn_table btnResult">설문결과</button></td>
						<td>9</td>
						<td><button type="button" class="btn_table btnResult">설문결과</button></td>
					</tr>
					<tr>
						<td>5</td>
						<td><button type="button" class="btn_table btnResult">설문결과</button></td>
						<td>10</td>
						<td><button type="button" class="btn_table btnResult">설문결과</button></td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- //table -->

		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_i" id="btnCancel">닫기</button>
		</div>
		<!-- //button -->

	</div>

	<!-- 설문결과 버튼 -->
	<form name="formStatisticsGraph">
		<input type="hidden" name="mapperseq" />
		<input type="hidden" name="qNumber" />
	</form>
	<!-- //설문결과 버튼 -->

</body>

</BaseTag:layoutPopup>
<%@ include file="ResultStatisticsScript.jsp" %>