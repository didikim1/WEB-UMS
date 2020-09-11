<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<BaseTag:layout>

<style>
	.searchBox { float:right; }
	.searchBox label { font-size:14px;margin-right:5px; }
	.date { width:115px;height:30px;font-size:14px; }
	#btnSearch { margin-left:0px; }
	.main_table td { text-align:center; }
	.main_table button { padding:3px 5px;float:none;margin-left:0; }
</style>

<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">서비스 사용 요금</h2>
		</div>
    	<!--//서브타이틀-->

    	<!-- contents -->
		<div class="ars_content">

			<ul style="float:right;">
				<li class="btn_wrap">
<!-- 					<a href="#"><button class="btn_service" id="btnService">서비스 소개</button></a> -->
				</li>
			</ul>
		</div>
		<!-- //contents -->
		
		<!-- search -->
		<form action="/result/ResultList" name="searchForm" method="POST">
			<input type="hidden" name="page" value="${paginationInfo.currentPageNo}"/>
			<input type="hidden" name="sidx" value="${sidx}" />
			<input type="hidden" name="sord" value="${sord}" />

			<div class="searchBox">
				<label>날짜</label>
				<input type="text" class="date" name="sSDate" value="${sSDate}" autocomplete="off"/> ~
				<input type="text" class="date" name="sEDate" value="${sEDate}" autocomplete="off"/>
				<button type="button" class="btn1" id="btnSearch">검색</button>
			</div>
		</form>
		<!-- //search -->
		
		<div class="tbl_type01 company_list main_table" style="margin-top:10px;">
			<table>
				<colgroup>
					<col width="7%" />
					<col width="13%" />
					<col width="15%" />
					<col width="15%" />
					<col width="15%" />
					<col width="15%" />
				</colgroup>
				<thead>
					<tr>
						<th>순번</th>
						<th>날짜</th>
						<th>총 사용 요금</th>
						<th>문자 발송 요금</th>
						<th>음성 발송 요금</th>
						<th>설문 발송 요금</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="i" begin="1" end="10">
						<tr>
							<td>${i}</td>
							<td>2020.${i}</td>
							<td>50,000</td>
							<td>15,000</td>
							<td>20,000</td>
							<td>15,000</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<BaseTag:Page pageing="${paginationInfo}" formName="searchForm"/>
		
	</div>
</div>

</BaseTag:layout>