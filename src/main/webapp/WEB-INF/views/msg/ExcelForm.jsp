<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow:hidden; width:684px; height:384px; border: 1px solid #2a347f; background:#f8f9ff; padding:30px;}
	.ser_text { font-size:14px;line-height:20px;text-align:center; }
	.ser_text a { font-size:18px;font-weight:bold;margin-bottom:10px;display:inline-block;text-decoration:underline; }
	.ser_text ul {}
	.col_r {color:#cd2129;}

	#tip { font-weight:normal;font-size:13px; }
	input[name=searchType] { margin-top:-2px; }
	.tbl tr { line-height:0.5;font-size:100%; }
	.tbl td { font-size:14px;height:8px; }
	#title th { background-color:#ccc; }
	#subtitle td { background-color:#f4f4f4; }
	.btn_next { text-align:end; }
	#btnSubmit, #btnCancel { width:80px;height:30px;padding:1px 6px; }
	.pagingArea { margin-top:10px; }
</style>

<body>
	<div id="wrap_pop_ser">

		<div class="ser_text">
			<a>발신주소록 엑셀 양식</a>
		</div>

		<div>
			<a id="tip">*양식 내 필수값 반드시 입력 필요</a>
		</div>

		<!-- table -->
		<div class="tbl">
			<table>
				<caption>리스트</caption>
				<colgroup>
					<col width="15%" />
					<col width="35%" />
					<col width="15%" />
					<col width="50%" />
				</colgroup>
				<thead>
					<tr id="title">
						<th>이름</th>
						<th>전화번호</th>
						<th>소속</th>
						<th>주소</th>
					</tr>
				</thead>
				<tbody>
					<tr id="subtitle">
						<td>필수</td>
						<td>필수</td>
						<td>옵션</td>
						<td>옵션</td>
					</tr>
					<tr>
						<td>홍길동</td>
						<td>01012345678</td>
						<td>A</td>
						<td>서울시 마포대로 49</td>
					</tr>
					<tr>
						<td>성춘향</td>
						<td>01087654321</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>#</td>
						<td><label style="color:red;">이줄은 절대 지우지마십시오.</label></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- //table -->

		<div class="ser_text" id="uploadNotice">
			<ul>
				<li>&middot; 양식의 1~2줄은 안내를 위한 정보입니다. 지운후 사용하시기 바랍니다.</li>
				<li>&middot; 마지막 줄의 #은 절대 지우지마십시오. 주소는 이 줄위에 추가하여 주십시오.</li>
			</ul>
		</div>

		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_i" id="btnSubmit">다운받기</button>
			<button type="button" class="btn_can" id="btnCancel">닫기</button>
		</div>
		<!-- //button -->

	</div>
</body>

</BaseTag:layoutPopup>
<%@ include file="ExcelFormScript.jsp" %>