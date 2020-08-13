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
	#searchWord { height:40px; }
	#btnSearch { margin-left:0px; }
	table tbody tr td { text-align:center !important; }
	.btnResult { height:25px;padding:3px 5px !important;float:none !important;margin-left:0px !important;font-size:13px !important; }
	.pointer { cursor:pointer; }
	input[type=checkbox] {width:15px !important;height:15px !important;margin-left:13px;}
</style>

<BaseTag:layout>

<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">설문지 결과 상세 내역</h2>
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
			<label>${mapperInfo.createdate} &nbsp;[${mapperInfo.title}] &nbsp;총발신 ${mapperInfo.cnt1}명 &nbsp;처리완료 ${mapperInfo.cnt2}명 &nbsp;수신성공 ${mapperInfo.cnt3}명 &nbsp;회신 ${mapperInfo.cnt4}명 &nbsp;현재 ${mapperInfo.statusCompletion}</label>
		</div>

		<!-- search -->
		<form action="/survey/DetailList" name="searchForm" method="POST">
			<input type="hidden" name="page" value="${paginationInfo.currentPageNo}"/>
			<input type="hidden" name="ivrlogmapperseq" value="${ivrlogmapperseq}"/>
			<input type="hidden" name="sidx" value="${sidx}" />
			<input type="hidden" name="sord" value="${sord}" />
			<input type="hidden" name="sendTime" value="${sendTime}" />

			<div class="searchBox">
				<label>수신시간</label>
				<input type="hidden" name="searchDType" value="CALLRESPONSEDATE" />
				<input type="text" class="sendDate" name="sSDate" value="${sSDate}" autocomplete="off"/> ~
				<input type="text" class="sendDate" name="sEDate" value="${sEDate}" autocomplete="off"/>
				<select id="searchType" name="searchType">
					<option value="NAME" <c:if test="${searchType eq 'NAME'}">selected</c:if> >이름</option>
					<option value="PHONENUMBER" <c:if test="${searchType eq 'PHONENUMBER'}">selected</c:if> >전화번호</option>
					<option value="RESULT" <c:if test="${searchType eq 'RESULT'}">selected</c:if> >전송결과</option>
					<option value="USER_INPUT" <c:if test="${searchType eq 'USER_INPUT'}">selected</c:if> >회신결과</option>
				</select>
				<input type="text" id="searchWord" name="searchWord" value="${searchWord}" autocomplete="off"/>
				<button class="btn_table" id="btnSearch">검색</button>
			</div>
		</form>
		<!-- //search -->

		<!-- table -->
		<div class="tbl_type01 company_list" style="margin-top:10px;">
			<table>
				<colgroup>
					<col width="7%" />
					<col width="15%" />
					<col width="10%" />
					<col width="10%" />
					<col width="10%" />
					<col width="10%" />
				</colgroup>
				<thead>
					<tr>
						<th><label class="pointer" onclick="javacript:fn_Sorting('ROW_NUM');">순번</label></th>
						<th><label class="pointer" onclick="javacript:fn_Sorting('CALLRESPONSEDATE');">전화수신시간</label></th>
						<th><label class="pointer" onclick="javacript:fn_Sorting('NAME');">이름</label></th>
						<th>전화번호</th>
						<th><label class="pointer" onclick="javacript:fn_Sorting('RESULT');">전송결과</label></th>
						<th>설문결과 확인</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(list) > 0}">
							<c:forEach var="data" items="${list}">
								<tr>
									<td>${data.rowNum}</td>
									<td>${data.callresponsedate}</td>
									<td>${data.name}</td>
									<td>
										<c:choose>
											<c:when test="${fn:length(data.phonenumber) eq 10}">
												<fmt:formatNumber var="phonenumber" value="${data.phonenumber}" pattern="00,0000,0000"/>
												<c:set var="phone" value="${fn:replace(phonenumber, ',', '-')}"/>
												<c:out value="${fn:split(phone, '-')[0]}-****-${fn:split(phone, '-')[2]}" />
											</c:when>
											<c:otherwise>
												<fmt:formatNumber var="phonenumber" value="${data.phonenumber}" pattern="000,0000,0000"/>
												<c:set var="phone" value="${fn:replace(phonenumber, ',', '-')}"/>
												<c:out value="${fn:split(phone, '-')[0]}-****-${fn:split(phone, '-')[2]}" />
											</c:otherwise>
										</c:choose>
									</td>
									<td>${data.result}</td>
									<td>
										<c:choose>
											<c:when test="${data.userInput ne '-'}">
												<button type="button" class="btn_table btnResult" id="${data.ivrlogseq}">설문결과</button>
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
								<td colspan="6">데이터가 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
<%-- 					<c:forEach var="i" begin="0" end="9"> --%>
<!-- 						<tr> -->
<%-- 							<td>${i+1}</td> --%>
<!-- 							<td>2019-12-26 13:41</td> -->
<!-- 							<td>홍길동</td> -->
<%-- 							<td>010****000${i}</td> --%>
<!-- 							<td>성공</td> -->
<%-- 							<td><button type="button" class="btn_table btnResult" id="seq${i+1}">설문결과</button></td> --%>
<!-- 						</tr> -->
<%-- 					</c:forEach> --%>
				</tbody>
			</table>
		</div>
		<!-- //table -->

		<BaseTag:Page pageing="${paginationInfo}" formName="searchForm"/>

	</div>
</div>

<!-- 설문결과 버튼 -->
<form name="formResult">
</form>
<!-- //설문결과 버튼 -->

</BaseTag:layout>
<%@ include file="DetailListScript.jsp" %>