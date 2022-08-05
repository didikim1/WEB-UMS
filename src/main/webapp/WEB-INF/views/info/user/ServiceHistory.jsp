<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<BaseTag:layout>

<style>
	.searchBox { float:right; }
	.searchBox label { font-size:14px;margin-right:5px; }
	.searchBox .date { width:115px;height:30px;font-size:14px; }
	.searchBox #searchType { border:1px solid #ccc;height:30px;margin-right:-5px;margin-left:10px; }
	.searchBox #searchWord { height:30px; }
	#btnSearch { margin-left:0px; }
	.btnResend {width: 75px;height:25px;padding:3px 5px !important;float:none !important;margin-left:0px !important;font-size:12px !important;}
	.main_table td { text-align:center; }
	.main_table button { padding:3px 5px;float:none;margin-left:0; }
</style>

<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">서비스 사용 내역</h2>
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
					<option value="TITLE" <c:if test="${searchType_ eq 'TITLE'}">selected</c:if> >제목</option>
					<option value="STATUS_COMPLETION" <c:if test="${searchType_ eq 'STATUS_COMPLETION'}">selected</c:if> >처리여부</option>
				</select>
				<input type="text" id="searchWord" name="searchWord" value="${searchWord}" autocomplete="off"/>
				<button type="button" class="btn1" id="btnSearch">검색</button>
			</div>
		</form>
		<!-- //search -->
		
		<div class="tbl_type01 company_list main_table" style="margin-top:10px;">
			<table>
				<colgroup>
					<col width="7%" />
					<col width="15%" />
					<col width="10%" />
					<col width="15%" />
					<col width="10%" />
					<col width="10%" />
					<col width="10%" />
					<col width="10%" />
					<col width="13%" />
				</colgroup>
				<thead>
					<tr>
						<th>순번</th>
						<th>날짜</th>
						<th>메세지유형</th>
						<th>제목</th>
						<th>발신</th>
						<th>처리여부</th>
						<th>성공</th>
						<th>실패</th>
						<th>재처리</th>
					</tr>
				</thead>
<!-- 				<tbody> -->
<%-- 					<c:forEach var="i" begin="1" end="10"> --%>
<!-- 						<tr> -->
<%-- 							<td>${i}</td> --%>
<!-- 							<td>2018.12.01</td> -->
<!-- 							<td>음성메세지</td> -->
<!-- 							<td>이번달 동기모임</td> -->
<!-- 							<td>50통</td> -->
<!-- 							<td>완료</td> -->
<!-- 							<td>45</td> -->
<!-- 							<td>5</td> -->
<!-- 							<td><button type="button" class="btn_table btnResend">재발송</button></td> -->
<!-- 						</tr> -->
<%-- 					</c:forEach> --%>
<!-- 				</tbody> -->
				<tbody>
					<c:choose>
						<c:when test="${fn:length(list) > 0}">
							<c:forEach var="list" items="${list}" varStatus="status">
								<tr>
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
								<td colspan="9">데이터가 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<!-- table -->
		
		<BaseTag:Page pageing="${paginationInfo}" formName="searchForm"/>
		
	</div>
</div>

</BaseTag:layout>