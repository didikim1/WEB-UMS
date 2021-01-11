<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<BaseTag:layout>

<style>
	#ui-datepicker-div { font-size:13px;width:200px; }
	.searchBox { float:right; }
	.searchBox label { font-size:14px;margin-right:5px; }
	.searchBox .date { width:115px;height:30px;font-size:14px; }
	.searchBox #searchType { border:1px solid #ccc;height:30px;margin-right:-5px;margin-left:10px; }
	.searchBox #searchWord { height:30px; }
	.searchBox #btnSearch { margin-left:0px; }
	input[type=checkbox] {width:15px !important;height:15px !important;margin-left:20px;}
	.company_list input[type=checkbox] { width:16px; }
	.company_list td { text-align:center; }
	.company_list td .btn_listen { height:25px;width:75px;padding:3px 5px;float:none;margin-left:0px;font-size:13px;text-align:center;line-height:18px; }
	.company_list td .btn_send { height:25px;width:75px;padding:3px 5px;float:none;margin-left:0px;font-size:13px;text-align:center;line-height:18px; }
</style>

<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">음성모음함</h2>
		</div>
    	<!--//서브타이틀-->

    	<!-- contents -->
		<div class="ars_content">

			<ul style="float:right;">
				<li class="btn_wrap">
<!-- 					<a href="#"><button class="btn_service" id="btnService">서비스 소개</button></a> -->
				</li>
			</ul>
		</div>
		<!-- //contents -->
		
		<!-- search -->
		<form action="/result/ResultList" name="searchForm" method="POST">
			<input type="hidden" name="page" value="${paginationInfo.currentPageNo}"/>
			<input type="hidden" name="sidx" value="${sidx}" />
			<input type="hidden" name="sord" value="${sord}" />

			<div class="searchBox">
				<label>등록일</label>
				<input type="text" class="date" name="sSDate" value="${sSDate}" autocomplete="off"/> ~
				<input type="text" class="date" name="sEDate" value="${sEDate}" autocomplete="off"/>
				<select id="searchType" name="searchType">
					<option value="TITLE" <c:if test="${searchType_ eq 'TITLE'}">selected</c:if> >제목</option>
				</select>
				<input type="text" id="searchWord" name="searchWord" value="${searchWord}" autocomplete="off"/>
				<button type="button" class="btn1" id="btnSearch">검색</button>
			</div>
		</form>
		<!-- //search -->
		
		<!-- table -->
		<div class="tbl_type01 company_list" style="margin-top:10px;">
			<table>
				<colgroup>
					<col width="6%" />
					<col width="35%" />
					<col width="25%" />
					<col width="17%" />
					<col width="17%" />
				</colgroup>
				<thead>
					<tr>
						<th><input type="checkbox" name="" /></th>
						<th>제목</th>
						<th>등록일</th>
						<th>듣기</th>
						<th>발송</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach begin="0" end="9" var="i">
						<tr>
							<td><input type="checkbox" name="" /></td>
							<td>제목입니다..</td>
							<td>2019.12.12</td>
							<td><input type="button" class="btn_table btn_listen" value="들어보기" /></td>
							<td><input type="button" class="btn_table btn_send" value="발송하기" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- table -->
		
		<BaseTag:Page pageing="${paginationInfo}" formName="searchForm"/>
		
		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_table" id="btnSubmit">새로등록</button>
			<button type="button" class="btn_table" id="btnDelete">삭제</button>
		</div>
		<!-- //button -->
		
	</div>
</div>

</BaseTag:layout>