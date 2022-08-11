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
	.searchBox .date { width:115px;height:30px;font-size:14px; }
	.searchBox #searchType { border:1px solid #ccc;height:30px;margin-right:-5px;margin-left:10px; }
	.searchBox #searchWord { height:30px; }
	.searchBox #btnSearch { margin-left:0px; }
	.btnSubmit, .btnDelete { width:100px;height:30px;padding:3px 5px !important;float:none !important;margin-left:0px !important;font-size:12px !important;text-align:center; background: #7d9b9d; color:#fff;}
	input[type=checkbox] {width:15px !important;height:15px !important;margin-left:15px;}
	.company_list input[type=checkbox] { width:16px; }
	.company_list td { text-align:center; }
	.company_list td .btn_listen { height:25px;width:75px;padding:3px 5px;float:none;margin-left:0px;font-size:12px;text-align:center;line-height:18px; }
	.company_list td .btn_send { height:25px;width:75px;padding:3px 5px;float:none;margin-left:0px;font-size:12px;text-align:center;line-height:18px; }
</style>

<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">설문모음함</h2>
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
					<option value="TITLE" <c:if test="${searchType_ eq 'TITLE'}">selected</c:if> >제목</option>
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
					<col width="6%" />
					<col width="7%" />
					<col width="25%" />
					<col width="25%" />
					<col width="17%" />
					<col width="17%" />
				</colgroup>
				<thead>
					<tr>
						<th><input type="checkbox" name="" /></th>
						<th>순번</th>
						<th>제목</th>
						<th>등록일</th>
						<th>항목수</th>
						<th>발송</th>
					</tr>
				</thead>
<!-- 				<tbody> -->
<%-- 					<c:forEach begin="1" end="10" var="i"> --%>
<!-- 						<tr> -->
<!-- 							<td><input type="checkbox" name="" /></td> -->
<!-- 							<td>제목입니다..</td> -->
<!-- 							<td>2019.12.12</td> -->
<%-- 							<td>${i}</td> --%>
<!-- 							<td><input type="button" class="btn_table btn_send" value="즉시발송" /></td> -->
<!-- 						</tr> -->
<%-- 					</c:forEach> --%>
<!-- 				</tbody> -->
				<tbody>
					<c:choose>
						<c:when test="${fn:length(list) > 0}">
							<c:forEach var="list" items="${list}" varStatus="status">
								<tr>
									<td><input type="checkbox" name="checkbox" value="${list.ivrlogmapperseq}" /></td>
									<td>${paginationInfo.totalRecordCount - paginationInfo.recordCountPerPage * (paginationInfo.currentPageNo - 1) - status.index}</td>
									<td>${list.createdate}</td>
									<td>${list.nextcallDate}</td>
									<td>
										<label class="pointer title" id="${list.ivrlogmapperseq}">${list.title}</label>
										<input type="hidden" name="ttsMentIntro01" value="${list.ttsMentIntro01}" />
									</td>
									<td>
										<label class="pointer ment" id="${list.ttsMentIntro01}">
											<c:set var="fullment" value="${list.ttsMentIntro01}"/>
											<c:choose>
												<c:when test="${fn:indexOf(fullment, '.wav') > 0}">
													<c:set var="ment" value="${fullment}" />
												</c:when>
												<c:when test="${fn:indexOf(fullment, '?') > 0}">
													<c:set var="ment" value="${fn:split(fullment, '?')[0]}?" />
												</c:when>
												<c:when test="${fn:indexOf(fullment, '.') > 0}">
													<c:set var="ment" value="${fn:split(fullment, '.')[0]}." />
												</c:when>
												<c:when test="${fn:indexOf(fullment, ' ') > 0}">
													<c:set var="ment" value="${fn:split(fullment, ' ')[0]}" />
												</c:when>
												<c:otherwise>
													<c:set var="ment" value="-" />
												</c:otherwise>
											</c:choose>

											<c:choose>
												<c:when test="${ment eq '?' || ment eq '.'}">
													<c:out value="-" />
												</c:when>
												<c:when test="${fn:length(ment) < 26}">
													<c:out value="${ment}" />
												</c:when>
												<c:otherwise>
													<c:out value="${fn:substring(ment, 0, 26)}..." />
												</c:otherwise>
											</c:choose>
										</label>
									</td>
<%-- 									<td>${list.cnt1}</td> --%>
<%-- 									<td>${list.cnt2}</td> --%>
<%-- 									<td>${list.cnt3}</td> --%>
<%-- 									<td>${list.cnt4}</td> --%>
									<td class="statusCompletion">
										<c:choose>
											<c:when test="${list.statusCompletion eq '전송완료'}">
												<button type="button" class="btn_table btnComplete">전송완료</button>
											</c:when>
											<c:otherwise>
												${list.statusCompletion}
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:if test="${list.statusCompletion eq '전송완료'}">
											<button type="button" class="btn_table btnResend">재발송</button>
											<input type="hidden" name="status" value="${list.statusCompletion}" />
										</c:if>
										<c:if test="${list.statusCompletion eq '대기'}">
											<button type="button" class="btn_table btnSend">즉시발송</button>
										</c:if>
										<c:if test="${list.statusCompletion eq '진행중'}">
											-
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="6">데이터가 없습니다.</td>
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
				<button type="button" class="btnDelete" id="btnDelete">선택 일괄삭제</button>
				<button type="button" class="btn_table btnSubmit" id="btnSubmit">새로등록</button>
			</label>
		</div>
		<!-- //button -->
				
		<BaseTag:Page pageing="${paginationInfo}" formName="searchForm"/>
		
	</div>
</div>

<form name="TTSMentPopup">
	<input type="hidden" name="ttsMentIntro01" />
</form>

</BaseTag:layout>
<%@ include file="SurveyListScript.jsp" %>