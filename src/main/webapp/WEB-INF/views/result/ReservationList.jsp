<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
	.ars_content { height:36px !important; }
	.searchBox { float:right; }
	#ui-datepicker-div { font-size:13px;width:200px; }
	.searchBox label { font-size:14px;margin-right:5px; }
	.sendDate { width:115px;height:40px;font-size:14px; }
	#searchType { border:1px solid #ccc;height:40px;margin-right:-5px;margin-left:10px; }
	#searchDType { border:1px solid #ccc;height:40px;margin-right:-4px;margin-left:10px; }
	#searchWord { height:40px; }
	#btnSearch { margin-left:0px; }
	.pointer { cursor:pointer; }
	table tbody tr td { text-align:center !important; }
	.btnResend, .btnSend, .btnComplete { height:25px;padding:3px 5px !important;float:none !important;margin-left:0px !important;font-size:13px !important; }
	.tbl_type01 th, .tbl_type01 td { padding:5px 0 !important; }
	input[type=checkbox] {width:15px !important;height:15px !important;margin-left:7px;}
</style>

<BaseTag:layout>
<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">예약메세지 조회</h2>
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
		<form action="/result/ReservationList" name="searchForm" method="POST">
			<input type="hidden" name="page" value="${paginationInfo.currentPageNo}"/>
			<input type="hidden" name="sidx" value="${sidx}" />
			<input type="hidden" name="sord" value="${sord}" />

			<div class="searchBox">
<!-- 				<label>요청일</label> -->
				<select id="searchDType" name="searchDType">
					<option value="CREATEDATE" <c:if test="${searchDType eq 'CREATEDATE'}">selected</c:if> >등록일</option>
					<option value="NEXTCALL_DATE" <c:if test="${searchDType eq 'NEXTCALL_DATE'}">selected</c:if> >예약일</option>
				</select>
				<input type="text" class="sendDate" name="sSDate_" value="${sSDate_}" autocomplete="off"/> ~
				<input type="text" class="sendDate" name="sEDate_" value="${sEDate_}" autocomplete="off"/>
				<select id="searchType" name="searchType_">
					<option value="TITLE" <c:if test="${searchType_ eq 'TITLE'}">selected</c:if> >제목</option>
<%-- 					<option value="TTS_MENT_INTRO_01" <c:if test="${searchType_ eq 'TTS_MENT_INTRO_01'}">selected</c:if> >전송내용</option> --%>
					<option value="STATUS_COMPLETION" <c:if test="${searchType_ eq 'STATUS_COMPLETION'}">selected</c:if> >전송상태</option>
				</select>
				<input type="text" id="searchWord" name="searchWord_" value="${searchWord_}" autocomplete="off"/>
				<button type="button" class="btn_table" id="btnSearch">검색</button>
			</div>
		</form>
		<!-- //search -->

		<!-- table -->
		<div class="tbl_type01 company_list" style="margin-top:10px;">
			<table>
				<colgroup>
					<col width="3%" />
					<col width="5%" />
					<col width="13%" />
					<col width="13%" />
					<col width="14%" />
					<col width="14%" />
<%-- 					<col width="6%" /> --%>
<%-- 					<col width="6%" /> --%>
<%-- 					<col width="6%" /> --%>
<%-- 					<col width="6%" /> --%>
					<col width="7%" />
					<col width="7%" />
				</colgroup>
				<thead>
					<tr>
						<th><input type="checkbox" name="checkAll" /></th>
						<th><label class="pointer" onclick="javascript:fn_Sorting('ROW_NUM')">순번</label></th>
						<th><label class="pointer" onclick="javascript:fn_Sorting('CREATEDATE')">등록일시</label></th>
						<th><label class="pointer" onclick="javascript:fn_Sorting('NEXTCALL_DATE')">전송예약일시</label></th>
						<th><label class="pointer" onclick="javascript:fn_Sorting('TITLE')">제목</label></th>
						<th><label>전송내용</label></th>
<!-- 						<th><label class="pointer" onclick="javascript:fn_Sorting('CNT1')">발신건수</label></th> -->
<!-- 						<th><label class="pointer" onclick="javascript:fn_Sorting('CNT2')">처리건수</label></th> -->
<!-- 						<th><label class="pointer" onclick="javascript:fn_Sorting('CNT3')">수신건수</label></th> -->
<!-- 						<th><label class="pointer" onclick="javascript:fn_Sorting('CNT4')">회신여부</label></th> -->
						<th><label>전송상태</label></th>
						<th><label>수동처리</label></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(list) > 0}">
							<c:forEach var="list" items="${list}">
								<tr>
									<td><input type="checkbox" name="checkbox" value="${list.ivrlogmapperseq}" /></td>
									<td>${list.rowNum}</td>
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
												<c:when test="${fn:length(ment) < 16}">
													<c:out value="${ment}" />
												</c:when>
												<c:otherwise>
													<c:out value="${fn:substring(ment, 0, 15)}..." />
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
								<td colspan="8">데이터가 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<!-- table -->

		<BaseTag:Page pageing="${paginationInfo}" formName="searchForm"/>

		<!-- button -->
		<div class="btn_next">
			<button class="btn_table" id="btnRCancel">예약 취소</button>
		</div>
		<!-- //button -->

	</div>
</div>

<form name="TTSMentPopup">
	<input type="hidden" name="ttsMentIntro01" />
</form>

</BaseTag:layout>
<%@ include file="ReservationListScript.jsp" %>