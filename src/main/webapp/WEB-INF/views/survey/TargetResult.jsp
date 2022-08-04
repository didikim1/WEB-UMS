<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow-y:hidden; width:484px; height:454px; border: 1px solid #2a347f; background:#f8f9ff; padding:30px;}
	.ser_text { font-size:14px;line-height:20px;margin-bottom:3px; }
	.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
	.col_r {color:#cd2129;}

	.tbl_type01 th, .tbl_type01 td { padding-left:0;padding:0;font-size:86%;text-align:center; }
	.tbl_type01 tr {line-height:0.5;}
	.tbl_type01 td {background-color:#fff;}
	#btnStatistic, #btnCancel { width:120px;height:30px;padding:1px 6px; }
</style>

<body>
	<div id="wrap_pop_ser">

		<div class="ser_text">
			<a>
				<c:choose>
					<c:when test="${fn:length(data.phonenumber) eq 10}">
						<fmt:formatNumber var="phonenumber" value="${data.phonenumber}" pattern="00,0000,0000"/>
						<c:set var="phone" value="${fn:replace(phonenumber, ',', '-')}"/>
						<c:out value="${fn:split(phone, '-')[0]}-****-${fn:split(phone, '-')[2]} 설문응답결과" />
					</c:when>
					<c:otherwise>
						<fmt:formatNumber var="phonenumber" value="${data.phonenumber}" pattern="000,0000,0000"/>
						<c:set var="phone" value="${fn:replace(phonenumber, ',', '-')}"/>
						<c:out value="${fn:split(phone, '-')[0]}-****-${fn:split(phone, '-')[2]} 설문응답결과" />
					</c:otherwise>
				</c:choose>
			</a>
		</div>

		<div class="ser_text">
			<ul>
				<li>[${data.ttsMentTitle}] 처리 결과</li>
			</ul>
		</div>

		<!-- table -->
		<div class="tbl_type01">
			<table>
				<caption>리스트</caption>
				<colgroup>
					<col width="25%" />
					<col width="25%" />
					<col width="25%" />
					<col width="25%" />
				</colgroup>
				<thead>
					<tr>
						<th>수신번호</th>
						<th>수신시간</th>
						<th>종료시간</th>
						<th>처리여부</th>
					</tr>
				</thead>
				<tbody>
					<tr>
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
						<td>${data.callstartdate}</td>
						<td>${data.callenddate}</td>
						<td>${data.result}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- //table -->

		<div class="ser_text">
			<ul>
				<li>※ 설문 결과</li>
			</ul>
		</div>

		<!-- table -->
		<div class="tbl_type01">
			<table>
				<caption>리스트</caption>
				<colgroup>
					<col width="20%" />
					<col width="30%" />
				</colgroup>
				<thead>
					<tr>
						<th>설문항</th>
						<th>응답결과</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="dtmf" value="${fn:split(data.userInput, ',')}"/>
					<c:forEach var="number" items="${dtmf}" varStatus="i">
						<tr>
							<td>${i.index + 1}</td>
							<td>${number}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- //table -->

		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_i" id="btnStatistic">응답통계보기</button>
			<button type="button" class="btn_i" id="btnCancel">닫기</button>
		</div>
		<!-- //button -->

	</div>

	<!-- 응답통계보기 버튼 -->
	<form name="formStatistics">
	</form>
	<!-- //응답통계보기 버튼 -->

</body>

</BaseTag:layoutPopup>
<%@ include file="TargetResultScript.jsp" %>