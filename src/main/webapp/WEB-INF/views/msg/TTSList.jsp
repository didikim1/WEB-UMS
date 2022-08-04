<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow:hidden; width:480px; height:560px; border: 3px solid #2a347f; background:#f8f9ff; padding:30px;}
	.ser_text { font-size:14px;line-height:20px;margin-bottom:3px; }
	.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
	.col_r {color:#cd2129;}

	.searchBox { text-align:end;margin-bottom:5px; }
	input[name=searchType] { margin-top:-2px; }
	#searchWord { width:150px;margin-bottom:3px;margin-left:120px;font-size:medium; }
	#btnSearch { padding:5px 10px;margin-left:0px; }
	.tbl tr { line-height:0.5;font-size:100%; }
	.tbl td { padding:0px 5px;font-size:14px; }
	table td img:first-child { cursor:pointer;width:12px;margin:11px; }
	table td img:last-child { cursor:pointer;width:11px;margin:11.5px; }
	.pointer { cursor:default; }
	.pagingArea { margin-top:10px; }
	#btnSubmit, #btnCancel { width:80px;height:30px;padding:1px 6px; }
</style>

<body>
	<div id="wrap_pop_ser">

		<div class="ser_text">
			<a>모음함</a>
		</div>

		<form name="searchForm" action="/msg/TTSList" method="POST">
			<input type="hidden" name="page" value="${paginationInfo.currentPageNo}"/>

			<!-- search -->
			<div class="searchBox">
				<input type="text" id="searchWord" name="searchWord" value="${searchWord}" autocomplete="off"/>
				<button class="btn_table" id="btnSearch">제목 검색</button>
			</div>
			<!-- //search -->

			<!-- table -->
			<div class="tbl">
				<table>
					<caption>리스트</caption>
					<colgroup>
						<col width="5%" />
						<col width="" />
						<col width="30%" />
<%-- 						<col width="20%" /> --%>
					</colgroup>
					<thead>
						<tr>
							<th></th>
							<th>제목</th>
							<th>등록일</th>
							<th>듣기</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${fn:length(list) > 0}">
								<c:forEach items="${list}" var="list">
									<tr>
										<td>
											<input type="radio" name="btnRadio" value="${list.seqarsalarmtts}" />
<%-- 											<input type="hidden" name="ttsMsg" value="${list.ttsMentIntro00}${list.ttsMentIntro01}${list.ttsMentIntro02}" /> --%>
											<input type="hidden" name="recFilePrefix" value="${list.recFilePrefix}" />
											<input type="hidden" name="recFileName" value="${list.recFileName}" />
											<input type="hidden" name="ttsMentTitle" value="${list.ttsMentTitle}" />
											<input type="hidden" name="recFileUrl" value="${list.recFileUrl}" />
										</td>
										<td><label class="pointer" title="${list.ttsMentIntro01}">${list.ttsMentTitle}</label></td>
										<td>${list.createdate}</td>
										<td>
											<img alt="재생" src="/assets/images/img_play.png" onclick="fn_playFile('${list.recFileUrl}')">
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
									<tr style="height:37px;">
										<td colspan="4">데이터가 없습니다.</td>
									</tr>
								</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<!-- //table -->

		</form>

		<BaseTag:Page pageing="${paginationInfo}" formName="searchForm"/>

		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_i" id="btnSubmit">선택</button>
			<button type="button" class="btn_can" id="btnCancel">닫기</button>
		</div>
		<!-- //button -->

	</div>

<form name="formTTSListen">
	<input type="hidden" name="fileUrl" />
	<input type="hidden" name="fileName" />
</form>

</body>

</BaseTag:layoutPopup>
<%@ include file="TTSListScript.jsp" %>