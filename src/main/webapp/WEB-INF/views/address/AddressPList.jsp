<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
	.info_com ul li a { margin-right:10px;margin-top:8px;line-height:38px; }
	.info_com ul li input[type=text] { margin-right:20px;width:200px;height:38px; }
	.searchBox 	{ float:right;margin-bottom:15px; }
	#searchType { border:1px solid #ccc;height:30px;margin-right:-5px;margin-left:10px; }
	#searchWord { height:30px; }
	#btnSearch 	{ margin-left:0px; }
	table tbody tr td { text-align:center !important; }
	.company_list tr { line-height:0.5 !important;font-size:100% !important; }
	.company_list td { padding:0px 5px !important;font-size:14px !important; }
	.name label { cursor:pointer; }
/* 	.btnDel { height:30px;padding:5px 15px !important;float:none !important;margin-left:2px !important;font-size:13px !important; } */
	#btnExcel { margin-left:0px;margin-right:10px; }
	input[type=checkbox] {width:15px !important;margin-left:13px;}
	input[name=name], input[name=department] { width:145px !important; }
	input[name=phonenumber] { width:195px !important; }
	input[name=address] { width:350px !important; }
</style>

<BaseTag:layout>

<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">개인 주소록</h2>
		</div>
        <!--//서브타이틀-->

        <!-- search -->
		<form action="/addr/AddressPList" name="searchForm" method="POST">
			<input type="hidden" name="page" value="${paginationInfo.currentPageNo}"/>


			<div class="searchBox">
				<select id="searchType" name="searchType">
					<option value="NAME" <c:if test="${searchType eq 'NAME'}">selected</c:if> >이름</option>
					<option value="PHONENUMBER" <c:if test="${searchType eq 'PHONENUMBER'}">selected</c:if> >전화번호</option>
					<option value="DEPARTMENT" <c:if test="${searchType eq 'DEPARTMENT'}">selected</c:if> >소속</option>
					<option value="ADDRESS" <c:if test="${searchType eq 'ADDRESS'}">selected</c:if> >주소</option>
				</select>
				<input type="text" id="searchWord" name="searchWord" value="${searchWord}" autocomplete="off"/>
				<button type="button" class="btn1" id="btnSearch">검색</button>
			</div>
		</form>
		<!-- //search -->

		<!-- add -->
		<div class="info_com">
			<ul>
        		<li><button type="button" class="btn_adress2" id="btnExcel">엑셀 업로드</button></li>
				<li><button type="button" class="btn_service" id="btnExcelForm">엑셀양식받기</button></li>
				<li><a style="margin-left:138px;">이름</a></li>
				<li><input type="text" class="" id="name" maxlength="40" autocomplete="off"/></li>
				<li><a>전화번호</a></li>
				<li><input type="text" class="" id="phonenumber" autocomplete="off"/></li>
				<li><button type="button" class="btn_adress" id="btnAdd">등록</button></li>
			</ul>
		</div>
		<!-- //add -->

		<!-- table -->
		<div class="tbl_type01 company_list" style="margin-top:10px;">
			<table>
				<colgroup>
					<col width="5%" />
					<col width="5%" />
					<col width="15%" />
					<col width="20%" />
					<col width="15%" />
					<col width="35%" />
<%-- 					<col width="10%" /> --%>
				</colgroup>
				<thead>
					<tr>
						<th></th>
						<th>순번</th>
						<th>이름</th>
						<th>전화번호</th>
						<th>소속</th>
						<th>주소</th>
<!-- 						<th>삭제</th> -->
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(list) > 0}">
							<c:forEach items="${list}" var="list">
								<tr>
									<td><input type="checkbox" name="" value="${list.seq}"></td>
									<td>${list.rowNum}</td>
									<td class="name">
										<input type="hidden" value="${list.seq}" />
<%-- 										<label><input type="text" name="name" value="${list.name}" maxlength="40" autocomplete="off" /></label> --%>
										<label onclick="javascript:fn_modify('${list.seq}');">${list.name}</label>
									</td>
									<td class="phonenumber" id="${list.phonenumber}">
										<c:choose>
											<c:when test="${fn:length(list.phonenumber) eq 10}">
												<fmt:formatNumber var="phonenumber" value="${list.phonenumber}" pattern="00,0000,0000"/>
												<c:set var="phone" value="${fn:replace(phonenumber, ',', '-')}"/>
												<c:set var="number" value="${fn:split(phone, '-')[0]}-****-${fn:split(phone, '-')[2]}" />
												<c:out value="${number}"/>
<%-- 												<input type="text" name="phonenumber" value="${number}" maxlength="12" autocomplete="off" /> --%>
											</c:when>
											<c:otherwise>
												<fmt:formatNumber var="phonenumber" value="${list.phonenumber}" pattern="000,0000,0000"/>
												<c:set var="phone" value="${fn:replace(phonenumber, ',', '-')}"/>
												<c:set var="number" value="${fn:split(phone, '-')[0]}-****-${fn:split(phone, '-')[2]}" />
												<c:out value="${number}"/>
<%-- 												<input type="text" name="phonenumber" value="${number}" maxlength="12" autocomplete="off" /> --%>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										${list.department}
<%-- 										<input type="text" name="department" value="${list.department}" autocomplete="off" /> --%>
									</td>
									<td>
										${list.address}
<%-- 										<input type="text" name="address" value="${list.address}" autocomplete="off" /> --%>
									</td>
<%-- 									<td><button type="button" class="btn_table btnDel" id="${list.seq}">삭제</button></td> --%>
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
		<!-- //table -->

		<!-- button -->
		<div class="btn_next2">
			<button class="btn_table" id="btnDelete">일괄 삭제</button>
<!-- 			<button class="btn_table" id="btnModify">일괄 수정</button> -->
		</div>
		<!-- //button -->

		<BaseTag:Page pageing="${paginationInfo}" formName="searchForm"/>

	</div>
</div>

<form name="formAddrCheck">
	<input type="hidden" name="title" />
	<input type="hidden" name="subtitle" />
	<input type="hidden" name="seq" />
	<input type="hidden" name="name" />
	<input type="hidden" name="phonenumber" />
</form>

<%@ include file="AddressPListScript.jsp" %>
</BaseTag:layout>