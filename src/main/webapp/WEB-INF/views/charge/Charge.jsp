<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<BaseTag:layout>

<style>
	.top_table td { text-align:center; }
	.bottomContainer { margin-top:10px;border:1px solid #ccc; }
	.bottomContainer .topBox { border-bottom:1px solid #ccc;text-align:center;height:30px;line-height:30px; }
	.bottomContainer .bottomBox { padding:0 10px; }
	.bottomContainer .bottomBox .title { padding-left:10px;height:30px; }
	.bottomContainer .bottomBox .title span { padding-left:15px;line-height:45px; }
	.bottomContainer .bottomBox table input[type=radio] { width:15px; }
	.bottomContainer .bottomBox table td { line-height:40px; }
	#btnSubmit { float:none; }
</style>

<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">충전하기</h2>
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
		
		<div class="tbl_type01 company_list top_table" style="margin-top:10px;">
			<table>
				<colgroup>
					<col width="25%" />
					<col width="25%" />
					<col width="25%" />
					<col width="25%" />
				</colgroup>
				<thead>
					<tr>
						<th>서비스종류</th>
						<th>발송비용</th>
						<th>100통/1달</th>
						<th>답변</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>음성메세지</td>
						<td>100원/1통</td>
						<td>5,000 원</td>
						<td>50원/1답변</td>
					</tr>
					<tr>
						<td>설문지</td>
						<td>500원/1통</td>
						<td>25,000 원</td>
						<td>50원/1답변</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="bottomContainer">
			<div class="topBox">
				현재 충전 금액 : 0 원
			</div>
			<div class="bottomBox">
				<div class="title">
					<span>1.충전금액 선택</span>
				</div>
				<div class="tbl_type01 company_list" style="margin-top:10px;">
					<table>
						<colgroup>
							<col width="20%" />
							<col width="20%" />
							<col width="20%" />
							<col width="20%" />
							<col width="20%" />
						</colgroup>
						<tbody>
							<tr>
								<td><input type="radio" name="" value="">1,000원</td>
								<td><input type="radio" name="" value="">3,000원</td>
								<td><input type="radio" name="" value="">5,000원</td>
								<td><input type="radio" name="" value="">10,000원</td>
								<td><input type="radio" name="" value="">30,000원</td>
							</tr>
							<tr>
								<td><input type="radio" name="" value="">50,000원</td>
								<td><input type="radio" name="" value="">100,000원</td>
								<td><input type="radio" name="" value="">500,000원</td>
								<td><input type="radio" name="" value="">1,000,000원</td>
								<td><input type="radio" name="" value="">5,000,000원</td>
							</tr>
							<tr>
								<td><input type="radio" name="" value="">직접입력</td>
								<td colspan="4"><input type="text" name="" value="" autocomplete="off" maxlength="20"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="title">
					<span>2.충전방법 선택</span><br/>
				</div>
				<div class="tbl_type01 company_list" style="margin-top:10px;width:50%;">
					<table>
						<colgroup>
							<col width="50%" />
							<col width="50%" />
						</colgroup>
						<tbody>
							<tr>
								<td><input type="radio" name="" value="">계좌이체</td>
								<td><input type="radio" name="" value="">가상계좌</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_table" id="btnSubmit">결 제</button>
		</div>
		<!-- //button -->
		
	</div>
</div>

</BaseTag:layout>