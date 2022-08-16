<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
/* 	.ars_content { height:36px !important; } */
	.tbl_type01 th, .tbl_type01 td {padding:5px;}
	.searchBox { float:right; }
	#ui-datepicker-div { font-size:13px;width:200px; }
	.searchBox label { font-size:14px;margin-right:5px; }
	.searchBox .date { width:115px;height:30px;font-size:14px; }
	.searchBox #searchType { border:1px solid #ccc;height:30px;margin-right:-5px;margin-left:10px; }
	.searchBox #searchWord { height:30px; }
	#btnSearch { margin-left:0px; }
	#btnModify, #btnRegist { width:120px;height:30px;padding:3px 5px !important;float:none !important;margin-left:0px !important;font-size:12px !important;text-align:center; background: #7d9b9d; color:#fff;}
	.pointer { cursor:pointer; }
	table tbody tr td { text-align:center !important; }
	input[type=checkbox] {width:15px !important;height:15px !important;margin-left:20px;}
	.company_list input { text-align:center !important;font-size:13px; }
	.company_list input[name="clientName"] { width:98% }
	.company_list input[name="clientType"] { width:98% }
	.company_list input[name="clientQos"] { width:98% }
	
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
		<form action="/qos" name="searchForm" method="POST">
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
							<td><input type="checkbox" name="checkbox" 	value="${i+1}" /></td>
<%-- 							<td><input type="checkbox" name="checkbox" value="${list.ivrlogmapperseq}" /></td> --%>
<%-- 							<td>${paginationInfo.totalRecordCount - paginationInfo.recordCountPerPage * (paginationInfo.currentPageNo - 1) - status.index}</td> --%>
							<td><label>${i+1}</label></td>
							<td><input type="text" name="clientName" value="Company #${i+1}" autocomplete="off" maxlength="50" /></td>
							<td><input type="text" name="clientType" value="업종" autocomplete="off" maxlength="50" /></td>
							<td>2020.01.14 16:13</td>
							<td><input type="text" name="clientQos" value="S" autocomplete="off" maxlength="3" /></td>
							<td>2020.01.14 16:13</td>
						</tr>
					</c:forEach>
					<c:choose>
						<c:when test="${fn:length(serverList) > 0}">
							<c:forEach var="server" items="${serverList}">
								<tr>
									<td><input type="checkbox" name="checkbox" 	value="${server.seqivrserver}" /></td>
									<td><label>${server.rowNum}</label></td>
									<td><input type="text" name="clientName" value="${server.serverName}" autocomplete="off" maxlength="50" /></td>
									<td><input type="text" name="companyType" value="${server.companyType}" autocomplete="off" maxlength="50" /></td>
									<td><label>${server.createdate}</label></td>
									<td><input type="text" name="clientQos" value="${server.qos}" autocomplete="off" maxlength="10" /></td>
									<td><label>${server.updatedate}</label></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="7">데이터가 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<!-- table -->
		<!-- button -->
		<div style="width:100%; margin-top: 10px; text-align:right;">
			<label>
				<button type="button" class="btnModify" id="btnModify">일괄변경</button>
				<button type="button" class="btn_table btnRegist" id="btnRegist">추가등록 및 변경</button>
			</label>
		</div>
		<!-- //button -->

		<BaseTag:Page pageing="${paginationInfo}" formName="searchForm"/>

	</div>
</div>
</BaseTag:layout>
<%@ include file="QoSListScript.jsp" %>