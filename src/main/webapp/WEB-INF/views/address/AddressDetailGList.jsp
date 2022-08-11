<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
	.buttonBox 	{ float:left; }
	.searchBox 	{ float:right; }
	#searchType { border:1px solid #ccc;height:30px;margin-right:-4px;margin-left:10px; }
	#searchWord { height:30px; }
	#btnSearch 	{ margin-left:0px; }
	input[type=checkbox] { width:15px !important; }
	table tbody tr td { text-align:center !important; }
	.name label { cursor:pointer; }
	.btnDel {  width:75px;height:25px;padding:5px 15px !important;float:none !important;margin-left:2px !important;font-size:13px !important; }
/* 	.btnDel { height:30px;padding:5px 15px !important;float:none !important;margin-left:2px !important; } */
	.btn_service {float:left !important;}
	#btnAdd { width:100px;height:36px;padding:3px;margin-left:679px;font-size:13px;background:#7d9b9d;font-weight:500; }
	#btnExcel { margin-left:0px;margin-right:10px; }
/* 	#btnExcel { width:100px;height:36px;padding:3px;margin-right:10px; } */
	.groupName { font-size:18px;font-weight:500;text-decoration: underline;}
</style>

<BaseTag:layout>

<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">그룹 상세 관리</h2>
		</div>
        <!--//서브타이틀-->

        <div style="width:100%;margin:57px 0 25px 0;">
			<label class="groupName">그룹명 : ${groupname}</label>
		</div>

		<!-- button -->
        <div class="buttonBox">
<!--         	<ul> -->
<!--         	<button type="button" class="btn_table" id="btnMod">수정</button> -->
         		<button type="button" class="btn_adress2" id="btnExcel">엑셀 업로드</button>
				<button type="button" class="btn_service" id="btnExcelForm">엑셀양식받기</button>
				<button type="button" class="btn_table" id="btnAdd">그룹원 추가</button>
<!--  			</ul> -->
        </div>
        <!-- //button -->

        <!-- search -->
		<form action="/addr/AddressDetailGList" name="searchForm" method="POST">
			<input type="hidden" name="page" value="${paginationInfo.currentPageNo}"/>
			<input type="hidden" name="groupseq" value="${groupseq}"/>
			<input type="hidden" name="groupname" value="${groupname}"/>

<!-- 			<div class="searchBox"> -->
<%-- 				<input type="text" id="searchWord" name="searchWord" value="${searchWord}" autocomplete="off"/> --%>
<!-- 				<button class="btn1" id="btnSearch">그룹명 검색</button> -->
<!-- 			</div> -->
		</form>
		<!-- //search -->

		<!-- table -->
		<div class="company_list tbl_type01">
			<table>
				<colgroup>
					<col width="5%" />
					<col width="15%" />
					<col width="20%" />
					<col width="15%" />
					<col width="35%" />
					<col width="10%" />
				</colgroup>
				<thead>
					<tr>
						<th>순번</th>
						<th>이름</th>
						<th>전화번호</th>
						<th>소속</th>
						<th>주소</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(list) > 0}">
							<c:forEach items="${list}" var="list">
								<tr>
									<td>${list.rowNum}</td>
									<td class="name"><label onclick="javascript:fn_modify(${list.seq});">${list.name}</label></td>
									<td class="phonenumber" id="${list.phonenumber}">
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
									<td>${list.department}</td>
									<td>${list.address}</td>
									<td><button type="button" class="btn_table btnDel" id="${list.seq}">삭제</button></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr style="height:37px;">
								<td colspan="6">데이터가 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>

		<BaseTag:Page pageing="${paginationInfo}" formName="searchForm"/>

	<!-- button -->
		<div class="btn_next">
			<button class="btn_can" id="btnCancel">확인</button>
		</div>
		<!-- //button -->

<!-- 			<div class="rightTable"> -->
<!-- 				<div class="tbl"> -->
<!-- 					<table> -->
<%-- 						<colgroup> --%>
<%-- 							<col width="10%" /> --%>
<%-- 							<col width="15%" /> --%>
<%-- 							<col width="35%" /> --%>
<%-- 							<col width="40%" /> --%>
<%-- 						</colgroup> --%>
<!-- 						<thead> -->
<!-- 							<tr> -->
<!-- 								<th></th> -->
<!-- 								<th>순번</th> -->
<!-- 								<th>이름</th> -->
<!-- 								<th>전화번호</th> -->
<!-- 							</tr> -->
<!-- 						</thead> -->
<!-- 						<tbody> -->
<%-- 							<c:choose> --%>
<%-- 								<c:when test="${fn:length(list_) > 0}"> --%>
<%-- 									<c:forEach items="${list_}" var="list_"> --%>
<!-- 										<tr> -->
<%-- 											<td><input type="checkbox" value="${list_.seq}" /></td> --%>
<%-- 											<td>${list_.seq}</td> --%>
<%-- 											<td>${list_.name}</td> --%>
<%-- 											<td>${list_.phonenumber}</td> --%>
<!-- 										</tr> -->
<%-- 									</c:forEach> --%>
<%-- 								</c:when> --%>
<%-- 								<c:otherwise> --%>
<!-- 									<tr style="height:37px;"> -->
<!-- 										<td colspan="4">데이터가 없습니다.</td> -->
<!-- 									</tr> -->
<%-- 								</c:otherwise> --%>
<%-- 							</c:choose> --%>
<!-- 						</tbody> -->
<!-- 					</table> -->
<!-- 				</div> -->
<!-- 			</div> -->
		<!-- //table -->



	</div>
</div>

<form name="formAddrCheck">
	<input type="hidden" name="title" />
	<input type="hidden" name="subtitle" />
	<input type="hidden" name="seq" />
	<input type="hidden" name="name" />
	<input type="hidden" name="phonenumber" />
</form>

<%@ include file="AddressDetailGListScript.jsp" %>
</BaseTag:layout>