<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow-y:scroll; width:484px; height:434px; border: 1px solid #2a347f; background:#f8f9ff; padding:30px;}
	.ser_text { font-size:14px;line-height:20px;margin-bottom:3px; }
	.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
	.col_r {color:#cd2129;}

	.tbl tr {line-height:0.5;}
	.tbl th, .tbl td { font-size:86%; }
	#btnSubmit, #btnCancel { width:80px;height:30px;padding:1px 6px; }
</style>

<body>
	<div id="wrap_pop_ser">

		<div class="ser_text">
			<a>전송 대상 확인</a>
		</div>

		<div class="ser_text">
			<ul>
				<li>※ 전송 대상을 확인해주세요.&nbsp;&nbsp;중복된 인원은 하나만 표시됩니다.</li>
			</ul>
		</div>

		<input type="hidden" name="ivrlogmapperseq" value="${ivrlogmapperseq}"/>
		<!-- table -->
		<div class="tbl">
			<table>
				<caption>리스트</caption>
				<colgroup>
					<col width="40%" />
					<col width="" />
				</colgroup>
				<thead>
					<tr>
						<th>이름</th>
						<th>전화번호</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(list) > 0}">
							<c:forEach var="list" items="${list}" varStatus="status">
								<tr>
									<td><input type="checkbox" name="checkbox" value="${list.ivrlogmapperseq}" /></td>
									<td>${paginationInfo.totalRecordCount - paginationInfo.recordCountPerPage * (paginationInfo.currentPageNo - 1) - status.index1}</td>
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
								<td colspan="8">데이터가 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<!-- //table -->
		<BaseTag:Page pageing="${paginationInfo}" formName="searchForm"/>

		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_i" id="btnSubmit">확인</button>
			<button type="button" class="btn_can" id="btnCancel">취소</button>
		</div>
		<!-- //button -->

	</div>
</body>

<form name="TTSMentPopup">
	<input type="hidden" name="ttsMentIntro01" />
</form>

</BaseTag:layoutPopup>
<%@ include file="RetryCheckScript.jsp" %>