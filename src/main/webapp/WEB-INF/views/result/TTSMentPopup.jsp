<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow-y:scroll; width:484px; height:377px; border: 1px solid #2a347f; background:#f8f9ff; padding:30px;}
	.ser_text {font-size:14px; line-height:20px;;}
	.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
	.ser_text ul {}

	.col_r {color:#cd2129;}
	#btnSubmit { width:80px;height:30px;padding:1px 6px; }
</style>


<body>
	<div id="wrap_pop_ser">

		<div class="ser_text">

			<a>전송 내용 확인</a>

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
							<th>발송 내용</th>
							<td>
								<c:choose>
									<c:when test="${empty ttsMentIntro01}">-</c:when>
									<c:otherwise>${ttsMentIntro01}</c:otherwise>
								</c:choose>
								
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- //전송 내용 -->

			<!-- Button-->
			<div class="btn_next">
				<button type="button" class="btn_i" id="btnSubmit">확인</button>
			</div>
			<!-- //Button-->

		</div>

	</div>
</body>

<script type="text/javascript">
$("#btnSubmit").click(function(){
	window.close();
});
</script>
</BaseTag:layoutPopup>
