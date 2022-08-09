<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
	.buttonBox 	{ float:right; }
	.searchBox 	{ float:right;margin-bottom:15px; }
	#searchType { border:1px solid #ccc;height:30px;margin-right:-5px;margin-left:10px; }
	#searchWord { height:30px; }
	#btnSearch 	{ margin-left:0px; }
	input[type=checkbox] { width:15px !important; }
	table tbody tr td { text-align:center !important; }
	.pointer { cursor:pointer; }
	.btnDel { width:75px;height:25px;padding:3px 5px !important;float:none !important;margin-left:0px !important;font-size:12px !important; }
	.info_com { margin-top:10px; }
	.info_com ul li a { margin-right:10px;margin-top:8px;line-height:38px; }
	.info_com ul li input[type=text] { margin-right:20px;width:200px;height:38px; }
	#btnExcel { margin-left:0px;margin-right:10px; }
	#btnAdd { width:104px; }
</style>

<BaseTag:layout>

<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">그룹 주소록</h2>
		</div>
        <!--//서브타이틀-->

<!-- 		<!-- button -->
<!--         <div class="buttonBox"> -->
<!-- <!--         	<button type="button" class="btn_table" id="btnDel">삭제</button> -->
<!-- <!--         	<button type="button" class="btn_table" id="btnMod">수정</button> -->
<!-- 			<input type="text" id="groupname" name="groupname" autocomplete="off"/> -->
<!--         	<button type="button" class="btn_table" id="btnAdd">그룹 등록</button> -->
<!--         </div> -->
<!--         //button -->

        <!-- search -->
		<form action="/addr/AddressGList" name="searchForm" method="POST">
			<input type="hidden" name="page" value="${paginationInfo.currentPageNo}"/>

			<div class="searchBox">
				<a style="margin-right:10px;">그룹명</a>
				<input type="text" id="searchWord" name="searchWord" value="${searchWord}" autocomplete="off"/>
				<button type="button" class="btn1" id="btnSearch">검색</button>
			</div>
		</form>
		<!-- //search -->

		<!-- add -->
		<div class="info_com">
			<ul>
<!-- 				<li><button type="button" class="btn_adress" id="btnExcel">엑셀 업로드</button></li> -->
<!-- 				<li><button type="button" class="btn_service" id="btnExcelForm">엑셀양식받기</button></li> -->
<!-- 				<li><a style="margin-left:401px;">그룹명</a></li> -->
				<li><a style="margin-left:607px;">그룹명</a></li>
				<li><input type="text" class="" id="groupname" autocomplete="off"/></li>
				<li><button type="button" class="btn_adress" id="btnAdd">등록</button></li>
			</ul>
		</div>
		<!-- //add -->

		<!-- table -->
		<div class="company_list tbl_type01" style="width:100%;">
			<table>
				<colgroup>
					<col width="10%" />
					<col width="45%" />
					<col width="15%" />
					<col width="25%" />
					<col width="15%" />
				</colgroup>
				<thead>
					<tr>
						<th>순번</th>
						<th>그룹이름</th>
						<th>인원수</th>
						<th>생성일</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(list) > 0}">
							<c:forEach items="${list}" var="list">
								<tr>
									<td>${list.rowNum}</td>
									<td><label class="pointer" id="${list.groupseq}">${list.groupname}</label></td>
									<td>${list.cnt}</td>
									<td>${list.createdate}</td>
									<td><button type="button" class="btn_table btnDel">삭제</button></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr style="height:37px;">
								<td colspan="5">데이터가 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<!-- //table -->

		<BaseTag:Page pageing="${paginationInfo}" formName="searchForm"/>

	</div>
</div>

<%@ include file="AddressGListScript.jsp" %>
</BaseTag:layout>