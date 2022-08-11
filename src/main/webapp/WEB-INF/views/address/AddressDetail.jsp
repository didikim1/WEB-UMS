<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow:hidden; width:544px; height:280px; border: 1px solid #023134; background:#f8f9ff; padding:30px;}
	.ser_text { font-size:14px;line-height:20px;margin-bottom:3px; }
	.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
	.col_r {color:#cd2129;}

	.tbl tr {line-height:0.5;}
	.tbl th, .tbl td { font-size:100%;padding:5px; }
	#playBtnBox { width:220px;height:30px;margin:auto; }
	#checkTTSMent { vertical-align:text-top; }
	#notice { text-align:center; }
	#btnListen, #btnStop, #btnSubmit, #btnCancel { width:80px;height:30px;padding:1px 6px; }
	#btnListen { margin-left:75px; }

</style>

<body>
	<div id="wrap_pop_ser">

		<div class="ser_text">
			<a>주소확인</a>
		</div>

		<form name="formSubmit" method="POST">
			<input type="hidden" name="seq" value="${data.seq}" />

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
							<td><input type="text" name="name" value="${data.name}" autocomplete="off" /></td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td>
								<c:choose>
									<c:when test="${fn:length(data.phonenumber) eq 10}">
										<fmt:formatNumber var="phonenumber" value="${data.phonenumber}" pattern="00,0000,0000"/>
<%-- 										<c:out value="${fn:replace(phonenumber, ',', '-')}"/> --%>
										<input type="text" name="phonenumber" value="${fn:replace(phonenumber, ',', '-')}" autocomplete="off" />
									</c:when>
									<c:otherwise>
										<fmt:formatNumber var="phonenumber" value="${data.phonenumber}" pattern="000,0000,0000"/>
<%-- 										<c:out value="${fn:replace(phonenumber, ',', '-')}"/> --%>
										<input type="text" name="phonenumber" value="${fn:replace(phonenumber, ',', '-')}" autocomplete="off" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>소속</th>
							<td><input type="text" name="department" value="${data.department}" autocomplete="off" /></td>
						</tr>
						<tr>
							<th>주소</th>
							<td><input type="text" name="address" value="${data.address}" autocomplete="off" /></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- //table -->
		</form>

		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_i" id="btnSubmit">수정</button>
			<button type="button" class="btn_d" id="btnCancel">닫기</button>
		</div>
		<!-- //button -->

	</div>
</body>

</BaseTag:layoutPopup>
<%@ include file="AddressDetailScript.jsp" %>