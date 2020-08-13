<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow-y:scroll; width:480px; height:430px; border: 3px solid #F7941D; background:#f8f9ff; padding:30px;}
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
					<c:forEach var="list" items="${list}">
						<tr>
							<td>${list.name}</td>
							<td>
								<c:choose>
									<c:when test="${fn:length(list.phonenumber) eq 10}">
										<fmt:formatNumber var="phonenumber" value="${list.phonenumber}" pattern="00,0000,0000"/>
										<c:out value="${fn:replace(phonenumber, ',', '-')}"/>
									</c:when>
									<c:otherwise>
										<fmt:formatNumber var="phonenumber" value="${list.phonenumber}" pattern="000,0000,0000"/>
										<c:out value="${fn:replace(phonenumber, ',', '-')}"/>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
<!-- 					<tr> -->
<%-- 						<td>${ttsTitle}</td> --%>
<!-- 					</tr> -->
				</tbody>
			</table>
		</div>
		<!-- //table -->

		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_i" id="btnSubmit">확인</button>
			<button type="button" class="btn_can" id="btnCancel">취소</button>
		</div>
		<!-- //button -->

	</div>
</body>

</BaseTag:layoutPopup>
<%@ include file="RetryCheckScript.jsp" %>