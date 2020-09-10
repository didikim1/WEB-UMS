<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<BaseTag:layout>

<style>
	.ars_content { height:36px !important; }
	.topContent td { text-align:center; }
	.bottomContent { width:100%; }
	.question { width:100%;min-height:300px; }
	.half { width:50%;float:left; }
	.piechart { width:99%;margin:0 auto; }
	.clear { clear:both; }
</style>

<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">설문지 결과 Reporting</h2>
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
		
		<div class="topContent">
			<table class="tbl_type01 company_list">
				<colgroup>
					<col width="15%" />
					<col width="10%" />
					<col width="10%" />
					<col width="10%" />
					<col width="10%" />
					<col width="10%" />
				</colgroup>
				<thead>
					<tr>
						<th>발송일시</th>
						<th>대상</th>
						<th>응답수</th>
						<th>전화연결안됨</th>
						<th>최종성공</th>
						<th>전화연결 시도</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>2019.11.15 10:00 ~ 총 5시간 소요</td>
						<td>무작위 50명</td>
						<td>35명</td>
						<td>5건</td>
						<td>25건</td>
						<td>평균 2회</td>
					</tr>
				</tbody> 
			</table>
		</div>
		
		<div class="bottomContent">
			<div class="q1 question">
				<div class="half">
					<table class="tbl_type01 company_list">
						<colgroup>
							<col width="10%" />
							<col width="10%" />
						</colgroup>
						<thead>
							<tr>
								<th colspan="2">귀하의 나이는 어떻게 되십니까?</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>70대 이상</td>
								<td>5%</td>
							</tr>
							<tr>
								<td>60대</td>
								<td>10%</td>
							</tr>
							<tr>
								<td>50대</td>
								<td>15%</td>
							</tr>
							<tr>
								<td>40대</td>
								<td>25%</td>
							</tr>
							<tr>
								<td>30대</td>
								<td>15%</td>
							</tr>
							<tr>
								<td>20대 이하</td>
								<td>5%</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="half">
					<div class="piechart" id="piechart1"></div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="q2 question">
				<div class="half">
					<table class="tbl_type01 company_list">
						<colgroup>
							<col width="10%" />
							<col width="10%" />
						</colgroup>
						<thead>
							<tr>
								<th colspan="2">귀하의 성별은 무엇입니까?</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>여성</td>
								<td>25%</td>
							</tr>
							<tr>
								<td>남성</td>
								<td>45%</td>
							</tr>
							<tr>
								<td>기타</td>
								<td>30%</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="half">
					<div class="piechart" id="piechart2"></div>
				</div>
			</div>
			<div class="q3 question"></div>
			<div class="q4 question"></div>
			<div class="q5 question"></div>
			<div class="q6 question"></div>
			<div class="q7 question"></div>
			<div class="q8 question"></div>
			<div class="q9 question"></div>
			<div class="q10 question"></div>
		</div>
		
		<div class="clear"></div>
	</div>
</div>

</BaseTag:layout>
<%@include file="ReportingScript.jsp" %>