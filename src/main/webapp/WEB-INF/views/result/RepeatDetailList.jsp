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
	.searchBox .date { width:115px;height:30px;font-size:14px; }
	.searchBox #searchType { border:1px solid #ccc;height:30px;margin-right:-5px;margin-left:10px; }
	.searchBox #searchWord { height:30px; }
	#btnSearch { margin-left:0px; }
	.pointer { cursor:pointer; }
	table tbody tr td { text-align:center !important; }
	.btnSend, .btnInfo { height:25px;padding:3px 5px !important;float:none !important;margin-left:0px !important;font-size:13px !important; }
	.tbl_type01 th, .tbl_type01 td { padding:5px 0 !important; }
	input[type=checkbox] {width:15px !important;height:15px !important;margin-left:20px;}
</style>

<BaseTag:layout>
<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">반복메세지 조회</h2>
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

		<div style="width:100%;margin-bottom:30px;">
			<label>${list_.createdate} &nbsp;[&nbsp;${list_.title}&nbsp;] &nbsp;총발신 ${list_.cnt1}명 &nbsp;처리완료 ${list_.cnt2}명 &nbsp;수신성공 ${list_.cnt3}명 &nbsp;회신 ${list_.cnt4}명 &nbsp;현재 ${list_.statusCompletion}</label>
		</div>

		<!-- search -->
		<form action="/result/RepeatDetailList" name="searchForm" method="POST">
			<input type="hidden" name="page" value="${paginationInfo.currentPageNo}"/>
			<input type="hidden" name="ivrlogmapperseq" value="${ivrlogmapperseq}" />
			<input type="hidden" name="sidx" value="${sidx}" />
			<input type="hidden" name="sord" value="${sord}" />
			<input type="hidden" name="sendTime" value="${sendTime}" />

			<div class="searchBox">
				<label>전송시간</label>
				<input type="hidden" name="searchDType" value="NEXTCALL_DATE" />
				<input type="text" class="date" name="sSDate_" value="${sSDate_}" autocomplete="off"/> ~
				<input type="text" class="date" name="sEDate_" value="${sEDate_}" autocomplete="off"/>
				<select id="searchType" name="searchType_">
					<option value="TITLE" <c:if test="${searchType_ eq 'TITLE'}">selected</c:if> >제목</option>
					<option value="TTS_MENT_INTRO_01" <c:if test="${searchType_ eq 'TTS_MENT_INTRO_01'}">selected</c:if> >전송내용</option>
					<option value="STATUS_COMPLETION" <c:if test="${searchType_ eq 'STATUS_COMPLETION'}">selected</c:if> >처리결과</option>
				</select>
				<input type="text" id="searchWord" name="searchWord_" value="${searchWord_}" autocomplete="off"/>
				<button type="button" class="btn1" id="btnSearch">검색</button>
			</div>
		</form>
		<!-- //search -->

		<!-- table -->
		<div class="tbl_type01 company_list" style="margin-top:10px;">
			<table>
				<colgroup>
					<col width="5%" />
					<col width="9%" />
					<col width="20%" />
					<col width="10%" />
					<col width="10%" />
					<col width="10%" />
					<col width="12%" />
					<col width="12%" />
				</colgroup>
				<thead>
					<tr>
						<th><input type="checkbox" name="checkAll" /></th>
						<th><label class="pointer" onclick="javascript:fn_Sorting('ROW_NUM')">순번</label></th>
						<th><label class="pointer" onclick="javascript:fn_Sorting('NEXTCALL_DATE')">전송시간</label></th>
						<th><label class="pointer" onclick="javascript:fn_Sorting('CNT1')">발신건수</label></th>
						<th><label class="pointer" onclick="javascript:fn_Sorting('CNT2')">처리건수</label></th>
						<th><label class="pointer" onclick="javascript:fn_Sorting('CNT3')">수신건수</label></th>
						<th><label>수동처리</label></th>
						<th><label>상세정보</label></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(list) > 0}">
							<c:forEach var="list" items="${list}">
								<tr>
									<td><input type="checkbox" name="checkbox" value="${list.ivrlogmapperseq}"></td>
									<td>${list.rowNum}</td>
									<td>${list.nextcallDate}</td>
									<td>${list.cnt1}</td>
									<td>${list.cnt2}</td>
									<td>${list.cnt3}</td>
									<td>
										<c:choose>
											<c:when test="${list.statusCompletion eq '대기'}">
												<button type="button" class="btn_table btnSend" id="${list.nextcallDate}">즉시발송</button>
											</c:when>
											<c:when test="${list.statusCompletion eq '진행중'}">
												진행중
											</c:when>
											<c:otherwise>
												-
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<button type="button" class="btn_table btnInfo" id="${list.nextcallDate}">상세정보</button>
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

	</div>
</div>

<form name="TTSMentPopup">
	<input type="hidden" name="ttsMentIntro01" />
</form>

</BaseTag:layout>
<%@ include file="RepeatDetailListScript.jsp" %>