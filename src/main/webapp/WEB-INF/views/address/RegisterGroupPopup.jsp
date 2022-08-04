<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow:hidden; width:480px; height:130px; border: 3px solid #2a347f; background:#f8f9ff; padding:30px;}
	.ser_text { font-size:14px;line-height:20px;margin-bottom:3px; }
	.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
	.col_r {color:#cd2129;}

	.searchBox { text-align:end;margin-bottom:5px; }
	input[name=searchType] { margin-top:-2px; }
	#searchWord { width:150px;margin-bottom:3px;margin-left:120px;font-size:medium; }
	#btnSearch { padding:5px 10px;margin-left:0px; }
	.tbl tr { line-height:0.5;font-size:100%; }
	.tbl td { padding:0px 5px;font-size:14px; }
	table td img:first-child { cursor:pointer;width:12px;margin:11px; }
	table td img:last-child { cursor:pointer;width:11px;margin:11.5px; }
	.pointer { cursor:default; }
	.pagingArea { margin-top:10px; }
	#btnSubmit, #btnCancel { width:80px;height:30px;padding:1px 6px; }
</style>

<body>
	<div id="wrap_pop_ser">

		<div class="ser_text">
			<a>그룹 추가</a>
		</div>

		<!-- table -->
		<div class="tbl">
			<table>
				<caption>리스트</caption>
				<colgroup>
					<col width="30%" />
					<col width="" />
				</colgroup>
				<tbody>
					<tr>
						<th>그룹명</th>
						<td><input type="text" name="groupname" /></td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- //table -->

		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_i" id="btnSubmit">등록</button>
			<button type="button" class="btn_can" id="btnCancel">닫기</button>
		</div>
		<!-- //button -->

	</div>
</body>

<script type="text/javascript">
$(document).ready(function(){
	$("#btnSubmit").click(function(){
		$.ajax({
			 url : "/addr/RegisterGroup"
			,data : { groupname : $("[name=groupname]").val() }
			,success : function(){
				window.opener.location.reload();
				window.close();
			}
		});
	});

	$("#btnCancel").click(function(){
		window.close();
	});
});
</script>

</BaseTag:layoutPopup>
