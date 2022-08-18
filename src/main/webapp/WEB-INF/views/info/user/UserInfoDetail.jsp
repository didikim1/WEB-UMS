<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<BaseTag:layout>

<style>
	.main_table th, .tbl_type03 td {padding:5px; }
	.main_table li { line-height:38px;display: inline-block; }
	.main_table button { padding:3px 5px;float:left; }
	.main_table label { margin-left:5px;margin-right:20px; }

 	.main_table input, .main_table select { height: 38px !important;font-size:14px !important;margin-left:5px; }
	.main_table input[name="userName"] { width:430px; }
	.main_table .inputPhone { width:125px;; }
	.main_table .inputMail { width:200px; }
	.main_table select[name="tel1"], .main_table select[name="phone1"] { width:100px; }
	.main_table select[name="email3"] { width:200px; }
	
	.main_table input[type="checkbox"] { width:15px;height:20px !important;margin-right:3px; }
	.main_table input[type="radio"] {margin:4px 3px 0 5px; }
/* 	#btnSubmit, #btnCancel { height:40px !important;margin-top:20px; } */
	.changePassword { width:100px;height:30px;padding:3px 5px !important;float:none !important;margin-left:0px !important;font-size:12px !important;text-align:center; background: #7d9b9d; color:#fff;}
	.policy { color:red !important;margin:10px 0 0 40px; }
</style>

<form name="formSubmit" action="/info/user/ModifyUserInfo" method="POST">
<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">회원 상세정보</h2>
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
						<td colspan="3"><input type="text" name="userName" value="${userInfo.userName}" maxlength="40"  autocomplete="off" /></td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td colspan="3">
							<button type="button" class="changePassword" style="font-size:13px;" id="changePassword">비밀번호 변경</button>
							<span class="policy">* 비밀번호는 대소문자, 숫자, 특수기호를 포함하여 8자리 이상으로 구성해주시기 바랍니다.</span>
						</td>
					</tr>
					<tr class="newPW">
						<th>새 비밀번호</th>
						<td><input type="password" id="password1" name="password1" /></td>
						<th>비밀번호 확인</th>
						<td><input type="password" id="password2" name="password2"/></td>
					</tr>
					<tr>
						<th>유선전화번호</th>
						<td colspan="3">
						<ul>
							<li>
							<select name="tel1">
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
							</li>
							<li>
							<input type="text" name="" value=" - " disabled style="border:none;width:22px;"/>
							<input type="text" class="inputPhone" name="tel2" />
							<input type="text" name="" value=" - " disabled style="border:none;width:22px;"/>
							<input type="text" class="inputPhone" name="tel3" />
							</li>
						</ul>
						</td>
					</tr>
					<tr>
						<th>휴대전화번호</th>
						<td colspan="3">
						<ul>
							<li>
							<select name="phone1">
								<option>010</option>
								<option>011</option>
								<option>016</option>
								<option>017</option>
								<option>018</option>
								<option>019</option>
							</select>
							</li>
							<li>
							<input type="text" name="" value=" - " disabled style="border:none;width:22px;"/>
							<input type="text" class="inputPhone" name="phone2" />
							<input type="text" name="" value=" - " disabled style="border:none;width:22px;"/>
							<input type="text" class="inputPhone" name="phone3" />
							</li>
						</ul>
						</td>
					</tr>
					<tr>
						<th>이메일 주소</th>
						<td colspan="3">
							<input type="text" class="inputMail" name="email1" />
							<input type="text" name="" value=" @ " disabled style="border:none;width:30px;"/>
							<input type="text" class="inputMail" name="email2" />
							<select name="email3">
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
							<br/><br/>
							<input type="checkbox" name="eventAgree" <c:if test='${userInfo.eventAgree eq "Y"}'>checked</c:if> value="Y" />
							<label>이벤트, 정기 뉴스레터 수신 동의</label>
						</td>
					</tr>
					<tr>
						<th>사용자 유형</th>
						<td colspan="3">
							<ul>
								<li>
									<label>UMS 통합 서비스 사용<input type="radio" name="userType" <c:if test='${userInfo.userType eq "1"}'>checked</c:if> value="1" checked/></label>
								</li>
								<li>
									<label>음성메세지 서비스만 사용<input type="radio" name="userType" <c:if test='${userInfo.userType eq "2"}'>checked</c:if> value="2"/></label>
								</li>
								<li>
									<label>문자메세지 서비스만 사용<input type="radio" name="userType" <c:if test='${userInfo.userType eq "3"}'>checked</c:if> value="3"/></label>
								</li>
							</ul>
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

<%@ include file="UserInfoDetailScript.jsp" %>
</BaseTag:layout>
