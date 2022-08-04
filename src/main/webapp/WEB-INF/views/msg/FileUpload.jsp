<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow:hidden; width:480px; height:320px; border: 3px solid #2a347f; background:#f8f9ff; padding:30px;}
	.ser_text {font-size:14px; line-height:20px;;}
	.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
	.ser_text ul {}
	.col_r {color:#cd2129;}

	.searchBox { text-align:center;margin-top:20px; }
	#fileName { margin-right:-4px;margin-bottom:3px;width:300px;height:40px; }
	#btnFind { float:none;margin-left:0px; }
	#uploadFile { position:absolute;left:0;top:0;opacity:0; }
	#uploadConfirm { text-align:center; }
	.btn_next { margin-bottom:30px !important; }
	#btnSubmit, #btnCancel { width:80px;height:30px;padding:1px 6px; }
	#uploadNotice { border:2px solid #2a347f;padding:5px 10px; }
</style>


<body>
	<div id="wrap_pop_ser">

		<div class="ser_text">
			<a>파일 업로드</a>
		</div>

		<!-- search -->
			<div class="searchBox">
				<input type="text" id="fileName" autocomplete="off"/>
				<button class="btn_table" id="btnFind">찾아보기</button>
				<form id="searchForm" action="" method="POST" enctype="multipart/form-data">
					<input type="hidden" name="group" value="N" />
					<input type="file" id="uploadFile" name="uploadFile" accept=".txt"/>
				</form>
			</div>
		<!-- //search -->

<!-- 		<div class="ser_text" id="uploadConfirm"> -->
<!-- 			<ul> -->
<!-- 				<li>선택한 발송번호 목록을 전송하시겠습니까?</li> -->
<!-- 			</ul> -->
<!-- 		</div> -->

		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_i" id="btnSubmit">확인</button>
			<button type="button" class="btn_can" id="btnCancel">닫기</button>
		</div>
		<!-- //button -->

		<div class="ser_text" id="uploadNotice">
			<ul>
				<li>&middot; 텍스트 파일로 작성한 발송번호 목록을 가져올 수 있습니다.</li>
				<li>&middot; 파일의 종류는 .txt만 가능합니다.</li>
			</ul>
		</div>

	</div>
</body>

</BaseTag:layoutPopup>
<%@ include file="FileUploadScript.jsp" %>