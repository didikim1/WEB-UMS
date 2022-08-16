<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow:hidden; width:484px; height:274px; border: 1px solid #023134; background:#f8f9ff; padding:30px;}
	.ser_text {font-size:14px; line-height:20px;}
	.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
	.ser_text ul {}

	.tbl th, .tbl td { padding:10px 5px; }
</style>


<body>
	<div id="wrap_pop_ser">

		<div class="ser_text">
			<a>서버 신규 등록 및 변경</a>
		</div>

		<div>
			<a>*신규 서버 등록 및 등록 서버 정보를 변경합니다.</a>
		</div>

		<!-- 전송 내용 -->
		<input type="hidden" name="seqivrserver" value="${server.seqivrserver}" />
		<div class="tbl">
			<table>
				<caption>리스트</caption>
				<colgroup>
					<col width="30%" />
					<col width="" />
				</colgroup>
				<tbody>
					<tr>
						<th><label>서버명</label></th>
						<td><input type="text" name="serverName" value="IVR #1" autocomplete="off" maxlength="50" /></td>
<%-- 						<td><input type="text" name="serverName" value="${server.serverName}" autocomplete="off" maxlength="50" /></td> --%>
					</tr>
					<tr>
						<th><label>서버IP</label></th>
						<td><input type="text" name="serverIp" value="192.168.0.1" autocomplete="off" maxlength="50" /></td>
<%-- 						<td><input type="text" name="serverIp" value="${server.serverIp}" autocomplete="off" maxlength="50" /></td> --%>
					</tr>
					<tr>
						<th><label>CPS</label></th>
						<td><input type="text" name="cps" value="30" onKeydown='return onlyNumber(event)' autocomplete="off" maxlength="10" /></td>
<%-- 						<td><input type="text" name="cps" value="${server.cps}" onKeydown='return onlyNumber(event)' autocomplete="off" maxlength="10" /></td> --%>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- //전송 내용 -->

		<!-- Button-->
		<div class="btn_next">
			<c:choose>
				<c:when test="${isRegist eq 'Y'}">
					<button type="button" class="btn_i btnSubmit" id="btnSubmit">등록</button>
				</c:when>
				<c:otherwise>
					<button type="button" class="btn_i btnModify" id="btnModify">변경</button>
				</c:otherwise>
			</c:choose>
			<button type="button" class="btn_d" id="btnCancel">취소</button>
		</div>
		<!-- //Button-->

	</div>
</body>

</BaseTag:layoutPopup>
<%@ include file="RegistAndModifyScript.jsp" %>