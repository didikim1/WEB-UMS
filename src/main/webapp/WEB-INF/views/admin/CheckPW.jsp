<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<BaseTag:layout>

<style>
	.main_container { text-align:center;padding:100px; }
	.main_container .passwdBox { text-align:center; }
	.bottom_container { text-align:center; }
	#btnSubmit { float:none; }
</style>

<div id="contents">
	<div class="section">

		<!--서브타이틀-->
		<div class="pageTop">
			<h2 class="pageTitle">비밀번호 확인</h2>
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
		
		<div class="main_container">
			<div class="passwdBox">
				<span>비밀번호 입력&nbsp;&nbsp;</span>
				<input type="password" name="" autocomplete="off" maxlength="20">
			</div>
		</div>
		<div class="bottom_container">
			<span>※서비스 상태보기를 사용하기 위해서는 서비스 가입 시 설정하신 비밀번호를 입력해 주십시오.</span>
		</div>
		
		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_table" id="btnSubmit">본인확인</button>
		</div>
		<!-- //button -->
		
	</div>
</div>

</BaseTag:layout>