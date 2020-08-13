<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<BaseTag:layout>

<style>
	.top_table_box div.title { width:50%; }
	.top_table_box div.title h1 { font-size:16px;font-weight:bold;float:left; }
	.top_table_box div.title button { padding:3px 5px; }
	.top_table_box .top_table { width:50%;margin-bottom:20px; }
	.bottom_table_box div.title h1 { font-size:16px;font-weight:bold;float:left; }
	.bottom_table_box div.title button { padding:3px 5px; }
	.bottom_table_box .bottom_table { margin-bottom:20px; }
	.bottom_table_box .bottom_table td { text-align:center; }
</style>

<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">회원정보</h2>
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
		
		<!-- table1 -->
		<div class="top_table_box">
			<div class="title">
				<h1>※ 개인정보</h1>
				<button type="button" class="btn_table">상세보기</button>
			</div>
			<div style="clear:both;"></div>
			<div class="tbl_type01 company_list top_table" style="margin-top:10px;">
				<table>
					<colgroup>
						<col width="30%" />
						<col width="70%" />
					</colgroup>
					<tbody>
						<tr>
							<th>이름</th>
							<td><label>홍길동</label></td>
						</tr>
						<tr>
							<th>휴대전화번호</th>
							<td><label>010-0000-0000</label></td>
						</tr>
						<tr>
							<th>통신번호</th>
							<td><label>1588-0559</label></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- //table1 -->
		
		<!-- table2 -->
		<div class="bottom_table_box">
			<div class="title">
				<h1>※ 사용내역</h1>
				<button type="button" class="btn_table">상세보기</button>
			</div>
			<div style="clear:both;"></div>
			<div class="tbl_type01 company_list bottom_table" style="margin-top:10px;">
				<table>
					<colgroup>
						<col width="20%" />
						<col width="20%" />
						<col width="20%" />
						<col width="20%" />
						<col width="20%" />
					</colgroup>
					<thead>
						<tr>
							<th></th>
							<th>음성발송</th>
							<th>설문발송</th>
							<th>문자발송</th>
							<th>합계</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th>발송건수</th>
							<td><label>500건</label></td>
							<td><label>1000건</label></td>
							<td><label>500건</label></td>
							<td><label>2000건</label></td>
						</tr>
						<tr>
							<th>요금</th>
							<td><label>50,000원</label></td>
							<td><label>100,000원</label></td>
							<td><label>50,000원</label></td>
							<td><label>200,000원</label></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- //table2 -->
		
	</div>
</div>

</BaseTag:layout>