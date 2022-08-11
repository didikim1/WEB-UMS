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
	#searchDType { border:1px solid #ccc;height:40px;margin-right:-4px;margin-left:10px; }
	.searchBox .date { width:115px;height:30px;font-size:14px; }
	.searchBox #searchType { border:1px solid #ccc;height:30px;margin-right:-5px;margin-left:10px; }
	.searchBox #searchWord { height:30px; }
	#btnSearch { margin-left:0px; }
	table tbody tr td { text-align:center !important; }
	.btnResend, .btnVoice { width:75px;height:25px;padding:3px 5px !important;float:none !important;margin-left:0px !important;font-size:12px !important; }
	.pointer { cursor:pointer; }
	input[type=checkbox] {width:15px !important;height:15px !important;margin-left:13px;}
</style>

<BaseTag:layout>
<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">예약메세지 상세 내역</h2>
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
			<label>${list_.createdate} &nbsp;[${list_.title}] &nbsp;총발신 ${list_.cnt1}명 &nbsp;처리완료 ${list_.cnt2}명 &nbsp;수신성공 ${list_.cnt3}명 &nbsp;회신 ${list_.cnt4}명 &nbsp;현재 ${list_.statusCompletion}</label>
		</div>

		<!-- search -->
		<form action="/result/ReservationDetailList" name="searchForm" method="POST">
			<input type="hidden" name="page" value="${paginationInfo.currentPageNo}"/>
			<input type="hidden" name="ivrlogmapperseq" value="${ivrlogmapperseq}"/>
			<input type="hidden" name="sidx" value="${sidx}" />
			<input type="hidden" name="sord" value="${sord}" />
			<input type="hidden" name="sendTime" value="${sendTime}" />

			<div class="searchBox">
<!-- 				<label>예약일</label> -->
				<select id="searchDType" name="searchDType">
					<option value="CALLRESPONSEDATE" <c:if test="${searchDType eq 'CALLRESPONSEDATE'}">selected</c:if> >수신시간</option>
					<option value="NEXTCALL_DATE" <c:if test="${searchDType eq 'NEXTCALL_DATE'}">selected</c:if> >예약시간</option>
				</select>
				<input type="text" class="date" name="sSDate" value="${sSDate}" autocomplete="off"/> ~
				<input type="text" class="date" name="sEDate" value="${sEDate}" autocomplete="off"/>
				<select id="searchType" name="searchType">
					<option value="NAME" <c:if test="${searchType eq 'NAME'}">selected</c:if> >이름</option>
					<option value="PHONENUMBER" <c:if test="${searchType eq 'PHONENUMBER'}">selected</c:if> >전화번호</option>
					<option value="RESULT" <c:if test="${searchType eq 'RESULT'}">selected</c:if> >전송결과</option>
					<option value="USER_INPUT" <c:if test="${searchType eq 'USER_INPUT'}">selected</c:if> >회신결과</option>
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
					<col width="5%" />
					<col width="7%" />
					<col width="15%" />
					<col width="15%" />
					<col width="10%" />
					<col width="10%" />
					<col width="10%" />
					<col width="10%" />
					<col width="12%" />
				</colgroup>
				<thead>
					<tr>
						<th><input type="checkbox" name="checkAll" /></th>
						<th><label class="pointer" onclick="javacript:fn_Sorting('ROW_NUM');">순번</label></th>
						<th><label class="pointer" onclick="javacript:fn_Sorting('CALLRESPONSEDATE');">전화수신시간</label></th>
						<th><label class="pointer" onclick="javacript:fn_Sorting('NEXTCALL_DATE');">예약시간</label></th>
						<th><label class="pointer" onclick="javacript:fn_Sorting('NAME');">이름</label></th>
						<th>전화번호</th>
						<th><label class="pointer" onclick="javacript:fn_Sorting('RESULT');">전송결과</label></th>
						<th><label class="pointer" onclick="javacript:fn_Sorting('USER_INPUT');">회신결과</label></th>
						<th>수동처리</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(list) > 0}">
							<c:forEach var="list" items="${list}">
								<tr>
									<td><input type="checkbox" name="checkbox" value="${list.ivrlogseq}" /></td>
									<td>${list.rowNum}</td>
									<td>${list.callresponsedate}</td>
									<td>${list.nextcallDate}</td>
									<td class="targetName">${list.name}</td>
									<td class="targetPhonenumber" id="${list.phonenumber}">
										<c:choose>
											<c:when test="${fn:length(list.phonenumber) eq 10}">
												<fmt:formatNumber var="phonenumber" value="${list.phonenumber}" pattern="00,0000,0000"/>
												<c:set var="phone" value="${fn:replace(phonenumber, ',', '-')}"/>
												<c:out value="${fn:split(phone, '-')[0]}-****-${fn:split(phone, '-')[2]}" />
											</c:when>
											<c:otherwise>
												<fmt:formatNumber var="phonenumber" value="${list.phonenumber}" pattern="000,0000,0000"/>
												<c:set var="phone" value="${fn:replace(phonenumber, ',', '-')}"/>
												<c:out value="${fn:split(phone, '-')[0]}-****-${fn:split(phone, '-')[2]}" />
											</c:otherwise>
										</c:choose>
									</td>
									<td><label class="ivrlogResult">${list.result}</label></td>
									<td>
										<c:choose>
											<c:when test="${list.userInput eq 'voice'}">
												<button type="button" class="btn_table btnVoice" id="${list.ivrlogseq}">음성회신</button>
											</c:when>
											<c:otherwise>
												${list.userInput}
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${list.result eq '성공' || list.result eq '실패'}">
												<button type="button" class="btn_table btnResend" id="${list.ivrlogseq}">재발송</button>
											</c:when>
											<c:otherwise>
												-
											</c:otherwise>
										</c:choose>
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

		<!-- button -->
		<div class="btn_next">
			<button class="btn_table" id="btnRCancel">예약 취소</button>
		</div>
		<!-- //button -->

		<BaseTag:Page pageing="${paginationInfo}" formName="searchForm"/>

	</div>
	<!-- button -->
	<div class="btn_next">
		<button class="btn_can" id="btnCancel">확인</button>
	</div>
	<!-- //button -->
</div>

<!-- <form name="TTSMentPopup"> -->
<!-- 	<input type="hidden" name="ttsMentIntro01" /> -->
<!-- </form> -->

<form action="/result/RetryDetailCheck" name="formRetryDetailCheck" method="POST">
	<input type="hidden" name="ivrlogseq" value="" />
	<input type="hidden" name="name" value="" />
	<input type="hidden" name="phonenumber" value="" />
</form>

</BaseTag:layout>
<%@ include file="ReservationDetailListScript.jsp" %>