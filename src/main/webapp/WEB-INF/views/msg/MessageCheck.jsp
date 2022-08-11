<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow-y:scroll; width:484px; height:494px; border: 1px solid #023134; background:#f8f9ff; padding:30px;}
	.ser_text { font-size:14px;line-height:20px;margin-bottom:3px; }
	.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
	.col_r {color:#cd2129;}

	.tbl tr {line-height:0.5;}
	.tbl th, .tbl td { font-size:95%; }
	#playBtnBox { width:220px;height:30px;margin:auto; }
	#checkTTSMent { vertical-align:text-top; line-height:1.5; }
	#notice { text-align:center; }
	#btnListen, #btnStop, #btnSubmit, #btnCancel { width:80px;height:30px;padding:1px 6px; }
	#btnListen { margin-left:75px; }

</style>

<body>
	<div id="wrap_pop_ser">

		<div class="ser_text">
			<a>전송요청 확인</a>
		</div>

		<div class="ser_text">
			<ul>
				<li>※ 전송할 내용을 확인해주세요.</li>
			</ul>
		</div>

		<form action="" name="formSubmit">
			<input type="hidden" name="mentArr" 		value="" />
			<input type="hidden" name="callerID" 		value="${paramMap.callerID}" />
			<input type="hidden" name="ttsTitle" 		value="${paramMap.ttsTitle}" />
			<input type="hidden" name="ttsMent" 		value="${ttsMent}" />
			<input type="hidden" name="ttsMent0" 		value="${ment0}" />
			<input type="hidden" name="ttsMent1" 		value="${ment1}" />
			<input type="hidden" name="ttsMent2" 		value="${ment2}" />
			<input type="hidden" name="targetArr" 		value="${paramMap.targetArr}" />
			<input type="hidden" name="callType" 		value="${paramMap.callType}" />
			<input type="hidden" name="sendTime" 		value="${paramMap.sendTime}" />
			<input type="hidden" name="sendType" 		value="${paramMap.sendType}" />
			<input type="hidden" name="isSave" 			value="${paramMap.isSave}" />
			<input type="hidden" name="nextcallDate" 	value="" />
			<input type="hidden" name="repeatDate" 		value="" />
			<input type="hidden" name="repeatType" 		value="${paramMap.repeatType}" />
			<input type="hidden" name="originTTSMent1" 	value='${originTTSMent1}' />
			<input type="hidden" name="originTTSMent2" 	value='${originTTSMent2}' />
			<input type="hidden" name="originTTSMent3" 	value='${originTTSMent3}' />
			<input type="hidden" name="seqgroup" 		value="${paramMap.seqgroup}" />
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
							<th>유형</th>
							<td>음성메세지</td>
						</tr>
						<tr>
							<th>제목</th>
							<td>${paramMap.ttsTitle}</td>
						</tr>
						<tr>
							<th>발신번호</th>
							<td>${paramMap.callerID}</td>
						</tr>
						<tr>
							<th>발송시간</th>
							<td>
								<c:choose>
									<c:when test="${paramMap.sendTime eq 'A'}">
										즉시
									</c:when>
									<c:when test="${paramMap.sendTime eq 'B'}">
										${paramMap.sendDay} &nbsp; ${paramMap.sendHour}시 ${paramMap.sendMinute}분
									</c:when>
									<c:otherwise>
										${paramMap.repeatDay} &nbsp; ${paramMap.repeatHour}시 ${paramMap.repeatMinute}분
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>발송내용</th>
							<td>
								<label id="checkTTSMent">
									<c:choose>
										<c:when test="${fn:trim(ttsMent) eq '<br/>'}">${paramMap.recFileName}</c:when>
										<c:otherwise>${ttsMent}</c:otherwise>
									</c:choose>
								</label>
<!-- 								<div id="playBtnBox"> -->
<!-- 									<button type="button" class="btn_adress" id="btnListen">들어보기</button> -->
<!-- 								</div> -->
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- //table -->
		</form>

		<div class="ser_text" id="notice">
			<ul>
				<li>&middot; 발송 크기 및 지역에 따라 시간이 지연되거나 실패할 수 있습니다.</li>
			</ul>
		</div>

		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_i" id="btnSubmit">보내기</button>
			<button type="button" class="btn_can" id="btnCancel">취소</button>
		</div>
		<!-- //button -->

	</div>
</body>

</BaseTag:layoutPopup>
<%@ include file="MessageCheckScript.jsp" %>