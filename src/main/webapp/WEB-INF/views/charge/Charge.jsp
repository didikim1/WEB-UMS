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

	.top_table td { text-align:center; }
	.bottomContainer { margin-top:10px;}
 	.bottomContainer .topBox { font-size:19px; font-weight:700; text-align:center;height:30px;line-height:30px;color:#023134; } 
	.bottomContainer .bottomBox { border:1px solid #ccc; padding:0 10px; }
	.bottomContainer .bottomBox .title { padding-left:10px;height:30px; }
	.bottomContainer .bottomBox .title span { padding-left:15px;line-height:45px; }
	.bottomContainer .bottomBox table input[type=radio] { height:30px;margin:6px; }
	table td input { height:30px !important;padding: 5px; }
	.bottomContainer .bottomBox table td { line-height:40px; }
	.bankName { width:148px !important; }
	.account { width:263px !important; }
	.owner { width:150px !important; }
	#btnSubmit, #btnCancel { height:38px !important; width:140px !important;font-size:16px !important;float:none; }
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
		
		<div class="top_table_box">
			<div class="title">
				<h1>※ 가격정보</h1>
			</div>
			<div style="clear:both;"></div>
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
							<td>8,000 원</td>
							<td>10원/1답변</td>
						</tr>
						<tr>
							<td>단문메세지</td>
							<td>25원/1통</td>
							<td>2,000 원</td>
							<td>10원/1답변</td>
						</tr>
						<tr>
							<td>장문메세지</td>
							<td>45원/1통</td>
							<td>4,000 원</td>
							<td>30원/1답변</td>
						</tr>
						<tr>
							<td>설문지</td>
							<td>300원/1통</td>
							<td>25,000 원</td>
							<td>100원/1답변</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		
		<div class="bottom_table_box">
			<div class="title">
				<h1>※ 충전하기</h1>
			</div>
			<div class="bottomContainer">
				<div class="topBox">
					충전 금액 : 100,000 원 / 잔액 : 5,000 원
				</div>
				<div class="bottomBox">
					<div class="title">
						<span>1. 충전금액 선택</span>
					</div>
					<div class="tbl_type03 company_list" style="margin-top:10px;">
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
									<td><input type="radio" class="inputRadio" name="amount" value="5000">5,000원</td>
									<td><input type="radio" class="inputRadio" name="amount" value="10000">10,000원</td>
									<td><input type="radio" class="inputRadio" name="amount" value="20000">20,000원</td>
									<td><input type="radio" class="inputRadio" name="amount" value="30000">30,000원</td>
									<td><input type="radio" class="inputRadio" name="amount" value="40000">40,000원</td>
								</tr>
								<tr>
									<td><input type="radio" class="inputRadio" name="amount" value="50000">50,000원</td>
									<td><input type="radio" class="inputRadio" name="amount" value="100000">100,000원</td>
									<td><input type="radio" class="inputRadio" name="amount" value="500000">500,000원</td>
									<td><input type="radio" class="inputRadio" name="amount" value="1000000">1,000,000원</td>
									<td><input type="radio" class="inputRadio" name="amount" value="5000000">5,000,000원</td>
								</tr>
								<tr>
									<td><input type="radio" class="inputRadio" name="amount" value="0">직접입력</td>
									<td colspan="4"><input type="text" name="amount2" value="" autocomplete="off" maxlength="20"></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="title">
						<span>2. 충전방법 선택</span><br/>
					</div>
					<div class="tbl_type03 company_list" style="margin-top:10px;">
						<table>
							<colgroup>
								<col width="10%" />
								<col width="15%" />
								<col width="10%" />
								<col width="25%" />
								<col width="10%" />
								<col width="15%" />
							</colgroup>
							<tbody>
								<tr>
									<td colspan="3"><input type="radio" class="inputRadio" name="chargeType" value="1">계좌이체</td>
									<td colspan="3"><input type="radio" class="inputRadio" name="chargeType" value="2">가상계좌</td>
								</tr>
								<tr class="bankInfo">
									<th>은행명</th>
									<td><input class="bankName" name="bankName"/></td>
									<th>계좌번호</th>
									<td><input type="number" class="account" name="account"/></td>
									<th>예금주</th>
									<td><input class="owner" name="owner"/></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		
		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_table" id="btnSubmit">충전결제하기</button>
		</div>
		<!-- //button -->
		
	</div>
</div>

<%@ include file="ChargeScript.jsp" %>
</BaseTag:layout>