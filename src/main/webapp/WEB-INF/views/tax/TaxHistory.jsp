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
				<button type="button" class="btn1" id="btnSearch">검색</button>
			</div>
		</form>
		<!-- //search -->
		
		<!-- table -->
		<div class="tbl_type01 company_list" style="margin-top:10px;">
			<table>
				<colgroup>
					<col width="7%" />
					<col width="20%" />
					<col width="20%" />
					<col width="20%" />
					<col width="20%" />
					<col width="20%" />
				</colgroup>
				<thead>
					<tr>
						<th>순번</th>
						<th>날짜</th>
						<th>메세지유형</th>
						<th>발신건</th>
						<th>정산건</th>
						<th>비용</th>
					</tr>
				</thead>
<!-- 				<tbody> -->
<!-- 					<tr> -->
<!-- 						<td>2018.12.01</td> -->
<!-- 						<td>음성메세지</td> -->
<!-- 						<td>50통</td> -->
<!-- 						<td>45</td> -->
<!-- 						<td>5,000</td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<td>2018.12.01</td> -->
<!-- 						<td>음성메세지</td> -->
<!-- 						<td>50통</td> -->
<!-- 						<td>50</td> -->
<!-- 						<td>8,000</td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<td>2018.12.01</td> -->
<!-- 						<td>설문지</td> -->
<!-- 						<td>50통</td> -->
<!-- 						<td>40</td> -->
<!-- 						<td>10,000</td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<td>총합</td> -->
<!-- 						<td></td> -->
<!-- 						<td>150</td> -->
<!-- 						<td>135</td> -->
<!-- 						<td>23,000</td> -->
<!-- 					</tr> -->
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
								<td colspan="6">데이터가 없습니다.</td>
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