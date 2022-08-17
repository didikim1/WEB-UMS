<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<BaseTag:layout>

<style>
	.tbl_type03 th, .tbl_type03 td {padding:5px;}
	.main_table button { padding:3px 5px;float:left; }
	.main_table .inputPhone { width:125px; height:30px;}
	.main_table .inputMail { width:200px;height:30px; }
	.main_table input[type="checkbox"] { width:15px;height:20px; }
/* 	#btnSubmit, #btnCancel { height:40px !important;margin-top:20px; } */
	.changePassword { width:100px;height:30px;padding:3px 5px !important;float:none !important;margin-left:0px !important;font-size:12px !important;text-align:center; background: #7d9b9d; color:#fff;}
</style>

<form name="formSubmit" action="/info/user/ModifyUserInfo" method="POST">
<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">회원 가입</h2>
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
		
		<div class="tbl_type03 company_list main_table" style="margin-top:10px;">
			<table>
				<colgroup>
					<col width="20%" />
					<col width="30%" />
					<col width="20%" />
					<col width="30%" />
				</colgroup>
				<tbody>
					<tr>
						<th>이름</th>
						<td colspan="3"><input type="text" name="userName" value="${userInfo.userName}" maxlength="40"  style="height:30px;" autocomplete="off" /></td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td colspan="3"><input type="text" name = "pwd" value="${userInfo.pwd}" maxlength="40"  style="height:30px;" autocomplete="off" />
					</tr>
					<tr>
						<th>비밀번호 확인</th>
						<td colspan="3"><input type="text" name = "pwd" value="${userInfo.pwdCheck}" maxlength="40"  style="height:30px;" autocomplete="off" />
					</tr>
					<tr>
						<th>유선전화번호</th>
						<td colspan="3">
							<select name="tel1" class="joinPhone">
								<option>02</option>
								<option>031</option>
								<option>032</option>
								<option>033</option>
								<option>041</option>
								<option>042</option>
								<option>043</option>
								<option>051</option>
								<option>052</option>
								<option>053</option>
								<option>054</option>
								<option>055</option>
								<option>061</option>
								<option>062</option>
								<option>063</option>
								<option>064</option>
								<option>070</option>
								<option>010</option>
								<option>011</option>
								<option>016</option>
								<option>017</option>
								<option>018</option>
								<option>019</option>
							</select>
							<input type="text" name="" value=" - " disabled style="border:none;width:20px;"/>
							<input type="text" class="inputPhone" name="tel2" />
							<input type="text" name="" value=" - " disabled style="border:none;width:20px;"/>
							<input type="text" class="inputPhone" name="tel3" />
						</td>
					</tr>
					<tr>
						<th>휴대전화번호</th>
						<td colspan="3">
							<select name="phone1" class="joinPhone">
								<option>010</option>
								<option>011</option>
								<option>016</option>
								<option>017</option>
								<option>018</option>
								<option>019</option>
							</select>
							<input type="text" name="" value=" - " disabled style="border:none;width:20px;"/>
							<input type="text" class="inputPhone" name="phone2" />
							<input type="text" name="" value=" - " disabled style="border:none;width:20px;"/>
							<input type="text" class="inputPhone" name="phone3" />
						</td>
					</tr>
					<tr>
						<th>주소</th>
							<td>
								<input type="text" class="inputAdd" name="inputAdd" />
								<input type="text" class="inputAdd2" name="inputAdd2" />
								<input type="text" class="inputAdd3" name="inputAdd3" />
							</td>
					</tr>
					<tr>
						<th>이메일 주소</th>
						<td colspan="3">
							<input type="text" class="inputMail" name="email1" />
							<input type="text" name="" value=" @ " disabled style="border:none;width:30px;"/>
							<input type="text" class="inputMail" name="email2" />
							<select name="email3" style="height:30px;" class="emailSelect">
								<option>직접입력</option>
								<option>naver.com</option>
								<option>nate.com</option>
								<option>paran.com</option>
								<option>hanmail.net</option>
								<option>gmail.com</option>
								<option>hitel.net</option>
								<option>hanmir.com</option>
								<option>netian.com</option>
								<option>dreamwiz.com</option>
								<option>lycos.co.kr</option>
								<option>yahoo.co.kr</option>
								<option>chollian.net</option>
								<option>orgio.net</option>
								<option>korea.com</option>
								<option>freechal.com</option>
								<option>hotmail.com</option>
								<option>unitel.co.kr</option>
								<option>empal.com</option>
								<option>nownuri.net</option>
								<option>hanafos.com</option>
								<option>kornet.net</option>
							</select>
							<br/>
							<br/>
							<input type="checkbox" name="eventAgree" <c:if test='${userInfo.eventAgree eq "Y"}'>checked</c:if> value="Y" />
							<label>이벤트, 정기 뉴스레터 수신 동의</label>
						</td>
					</tr>
					<tr>
						<th>사용자 유형</th>
						<td style="border-right:none !important;">
							<label>UMS</label><input type="radio" name="userType" <c:if test='${userInfo.userType eq "1"}'>checked</c:if> value="1" />
						</td>
						<td style="border-right:none !important;">
							<label>VMS</label><input type="radio" name="userType" <c:if test='${userInfo.userType eq "2"}'>checked</c:if> value="2" />
						</td>
						<td>
							<label>SMS</label><input type="radio" name="userType" <c:if test='${userInfo.userType eq "3"}'>checked</c:if> value="3" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<!-- button -->
		<div class="btn_next">
			<button class="btn_out" id="btnSubmit">수정</button>
			<button class="btn_can" id="btnCancel">취소</button>
		</div>
		<!-- //button -->
		
	</div>
</div>

<input type="hidden" name="sequser" value="${paramMap.sequser}" />

</form>

<%@ include file="JoinScript.jsp" %>
</BaseTag:layout>
