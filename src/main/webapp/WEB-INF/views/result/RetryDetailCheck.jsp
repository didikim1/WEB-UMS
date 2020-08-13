<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow:hidden; width:480px; height:180px; border: 3px solid #F7941D; background:#f8f9ff; padding:30px;}
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
			<a>재발송</a>
		</div>

		<div class="ser_text">
			<ul>
				<li>※ 재발송할 내역의 정보를 확인해주세요.</li>
			</ul>
		</div>

		<input type="hidden" name="ivrlogseq" value="${ivrlogseq}"/>
		<!-- table -->
		<div class="tbl">
			<table>
				<caption>리스트</caption>
				<colgroup>
					<col width="30%" />
					<col width="" />
				</colgroup>
				<tbody>
					<tr>
						<th>이름</th>
						<td>${name}</td>
					</tr>
					<tr>
						<th>전화번호</th>
						<td>
							<c:choose>
								<c:when test="${fn:length(phonenumber) eq 10}">
									<fmt:formatNumber var="phonenumber" value="${phonenumber}" pattern="00,0000,0000"/>
									<c:out value="${fn:replace(phonenumber, ',', '-')}"/>
								</c:when>
								<c:otherwise>
									<fmt:formatNumber var="phonenumber" value="${phonenumber}" pattern="000,0000,0000"/>
									<c:out value="${fn:replace(phonenumber, ',', '-')}"/>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
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
<%@ include file="RetryDetailCheckScript.jsp" %>