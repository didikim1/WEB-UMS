<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow:hidden; width:480px; height:250px; border: 3px solid #F7941D; background:#f8f9ff; padding:30px;}
	.ser_text {font-size:14px; line-height:20px;}
	.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
	.ser_text ul {}

	.tbl th, .tbl td { padding:10px 5px; }
	#btnSubmit, #btnCancel { width:80px;height:30px;padding:1px 6px; }
</style>


<body>
	<div id="wrap_pop_ser">

		<div class="ser_text">
			<a>고객사 신규 등록 및 변경</a>
		</div>

		<div>
			<a>*신규 고객사 등록 및 등록 고객사 정보를 변경합니다.</a>
		</div>

		<!-- 전송 내용 -->
		<div class="tbl">
			<table>
				<caption>리스트</caption>
				<colgroup>
					<col width="30%" />
					<col width="" />
				</colgroup>
				<tbody>
					<tr>
						<th><label>고객사명</label></th>
						<td><input type="text" name="companyName" value="${abc}" autocomplete="off" /></td>
					</tr>
					<tr>
						<th><label>고객사정보</label></th>
						<td><input type="text" name="companyInfo" autocomplete="off" /></td>
					</tr>
					<tr>
						<th><label>QoS</label></th>
						<td><input type="text" name="companyQoS" onKeydown='return onlyNumber(event)' autocomplete="off" /></td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- //전송 내용 -->

		<!-- Button-->
		<div class="btn_next">
			<button type="button" class="btn_i" id="btnSubmit">등록</button>
			<button type="button" class="btn_d" id="btnCancel">취소</button>
		</div>
		<!-- //Button-->

	</div>
</body>

</BaseTag:layoutPopup>
<%@ include file="RegistAndModifyScript.jsp" %>