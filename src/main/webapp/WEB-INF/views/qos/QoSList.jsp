<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
/* 	.ars_content { height:36px !important; } */
	.searchBox { float:right; }
	#ui-datepicker-div { font-size:13px;width:200px; }
	.searchBox label { font-size:14px;margin-right:5px; }
	.searchBox .date { width:115px;height:30px;font-size:14px; }
	.searchBox #searchType { border:1px solid #ccc;height:30px;margin-right:-5px;margin-left:10px; }
	.searchBox #searchWord { height:30px; }
	#btnSearch { margin-left:0px; }
	.pointer { cursor:pointer; }
	table tbody tr td { text-align:center !important; }
	input[type=checkbox] {width:15px !important;height:15px !important;margin-left:20px;}
</style>

<BaseTag:layout>
<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">고객사별 QoS 설정</h2>
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
				<label>등록일</label>
				<input type="text" class="date" name="sSDate" value="${sSDate}" autocomplete="off"/> ~
				<input type="text" class="date" name="sEDate" value="${sEDate}" autocomplete="off"/>
				<select id="searchType" name="searchType">
					<option value="IVRSERVER_NAME" <c:if test="${searchType_ eq 'IVRSERVER_NAME'}">selected</c:if> >고객사</option>
					<option value="IVRSERVER_IP" <c:if test="${searchType_ eq 'IVRSERVER_IP'}">selected</c:if> >고객사정보</option>
					<option value="IVRSERVER_CPS" <c:if test="${searchType_ eq 'IVRSERVER_CPS'}">selected</c:if> >QoS등급</option>
				</select>
				<input type="text" id="searchWord" name="searchWord" value="${searchWord}" autocomplete="off"/>
				<button type="button" class="btn1" id="btnSearch">검색</button>
			</div>
		</form>
		<!-- //search -->

		<!-- table -->
		<div class="tbl_type01 company_list" style="margin-top:10px;">
			<table>
				<colgroup>
					<col width="7%" />
					<col width="11%" />
					<col width="18%" />
					<col width="18%" />
					<col width="18%" />
					<col width="10%" />
					<col width="18%" />
				</colgroup>
				<thead>
					<tr>
						<th><input type="checkbox" /></th>
						<th><label class="pointer">순번</label></th>
						<th><label class="pointer">고객사</label></th>
						<th><label class="pointer">고객사정보</label></th>
						<th><label class="pointer">등록일자</label></th>
						<th><label class="pointer">QoS 등급</label></th>
						<th><label class="pointer">최종처리일자</label></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="i" begin="0" end="9">
						<tr>
							<td><input type="checkbox" /></td>
							<td>${i+1}</td>
							<td>Company #${i+1}</td>
							<td>업종</td>
							<td>2020.01.14 16:13</td>
							<td>
								<c:choose>
									<c:when test="${(i+1)%2 == 1}">일반</c:when>
									<c:otherwise>VIP</c:otherwise>
								</c:choose>
							</td>
							<td>2020.01.14 16:13</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- table -->

		<BaseTag:Page pageing="${paginationInfo}" formName="searchForm"/>

		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_table" id="btnModify">일괄변경</button>
			<button type="button" class="btn_table" id="btnRegist">추가등록 및 변경</button>
		</div>
		<!-- //button -->

	</div>
</div>
</BaseTag:layout>
<%@ include file="QoSListScript.jsp" %>