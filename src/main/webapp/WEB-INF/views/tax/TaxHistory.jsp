<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<BaseTag:layout>

<style>
	#ui-datepicker-div { font-size:13px;width:200px; }
	.searchBox { float:right; }
	.searchBox label { font-size:14px;margin-right:5px; }
	.searchBox .date { width:115px;height:40px;font-size:14px; }
	.searchBox #searchType { border:1px solid #ccc;height:40px;margin-right:-5px;margin-left:10px; }
	.searchBox #searchWord { height:40px; }
	.searchBox #btnSearch { margin-left:0px; }
	.company_list td { text-align:center; }
</style>

<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">서비스 정산내역</h2>
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
				<select id="searchType" name="searchType">
					<option value="SERVICE_TYPE" <c:if test="${searchType_ eq 'SERVICE_TYPE'}">selected</c:if> >메세지유형</option>
				</select>
				<input type="text" id="searchWord" name="searchWord" value="${searchWord}" autocomplete="off"/>
				<button type="button" class="btn_table" id="btnSearch">검색</button>
			</div>
		</form>
		<!-- //search -->
		
		<!-- table -->
		<div class="tbl_type01 company_list" style="margin-top:10px;">
			<table>
				<colgroup>
					<col width="20%" />
					<col width="20%" />
					<col width="20%" />
					<col width="20%" />
					<col width="20%" />
				</colgroup>
				<thead>
					<tr>
						<th>날짜</th>
						<th>메세지유형</th>
						<th>발신건</th>
						<th>정산건</th>
						<th>비용</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>2018.12.01</td>
						<td>음성메세지</td>
						<td>50통</td>
						<td>45</td>
						<td>5,000</td>
					</tr>
					<tr>
						<td>2018.12.01</td>
						<td>음성메세지</td>
						<td>50통</td>
						<td>50</td>
						<td>8,000</td>
					</tr>
					<tr>
						<td>2018.12.01</td>
						<td>설문지</td>
						<td>50통</td>
						<td>40</td>
						<td>10,000</td>
					</tr>
					<tr>
						<td>총합</td>
						<td></td>
						<td>150</td>
						<td>135</td>
						<td>23,000</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- table -->
		
		<BaseTag:Page pageing="${paginationInfo}" formName="searchForm"/>
		
	</div>
</div>

</BaseTag:layout>