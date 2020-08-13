<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<BaseTag:layout>

<style>
	.main_table button { padding:3px 5px;float:left; }
	.main_table .inputPhone { width:125px; }
	.main_table .inputMail { width:200px; }
	.main_table input[type="checkbox"] { width:15px;height:20px; }
	#btnSubmit, #btnCancel { height:40px !important;margin-top:20px; }
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
		
		<div class="tbl_type01 company_list main_table" style="margin-top:10px;">
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
						<td colspan="3"><input type="text" value="홍길동" maxlength="20" autocomplete="off" /></td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td colspan="3"><button type="button" class="btn_table" id="changePassword">비밀번호 변경</button></td>
					</tr>
					<tr class="newPW">
						<th>새 비밀번호</th>
						<td><input type="password" /></td>
						<th>비밀번호 확인</th>
						<td><input type="password" /></td>
					</tr>
					<tr>
						<th>유선전화번호</th>
						<td colspan="3">
							<select>
								<option>02</option>
								<option>031</option>
							</select>
							<input type="text" name="" value=" - " disabled style="border:none;width:20px;"/>
							<input type="text" class="inputPhone" value="0000" />
							<input type="text" name="" value=" - " disabled style="border:none;width:20px;"/>
							<input type="text" class="inputPhone" value="0000" />
						</td>
					</tr>
					<tr>
						<th>무선전화번호</th>
						<td colspan="3">
							<select>
								<option>010</option>
								<option>011</option>
							</select>
							<input type="text" name="" value=" - " disabled style="border:none;width:20px;"/>
							<input type="text" class="inputPhone" value="0000" />
							<input type="text" name="" value=" - " disabled style="border:none;width:20px;"/>
							<input type="text" class="inputPhone" value="0000" />
						</td>
					</tr>
					<tr>
						<th>이메일 주소</th>
						<td colspan="3">
							<input type="text" class="inputMail" value="testmail" />
							<input type="text" name="" value=" @ " disabled style="border:none;width:30px;"/>
							<input type="text" class="inputMail" value="inbiznetcorp.com" />
							<select>
								<option>직접입력</option>
								<option>inbiznetcorp.com</option>
							</select>
							<br/>
							<br/>
							<input type="checkbox" />
							<label>이벤트, 정기 뉴스레터 수신 동의</label>
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

</BaseTag:layout>

<script>
$(document).ready(function(){
	$(".newPW").hide();
	
	// 비밀번호 변경 버튼
	$("#changePassword").click(function(){
		$(".newPW").show();
	});
});
</script>