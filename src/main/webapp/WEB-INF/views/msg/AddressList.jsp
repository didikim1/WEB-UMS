<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="BaseTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<BaseTag:layoutPopup>

<style>
	#wrap_pop_ser {overflow:hidden; width:604px; height:604px; border: 1px solid #023134; background:#f8f9ff; padding:30px;}
	.ser_text {font-size:14px; line-height:20px;;}
	.ser_text a {font-size:18px; font-weight:bold; margin-bottom:10px; display:inline-block; }
	.ser_text ul {}
	.col_r {color:#cd2129;}

	.btnAddressList { width:100%;margin-bottom:10px;display:inline-block; }
	#btnAddressList { padding:5px 10px;background:#7d9b9d !important; }
	.searchBox { text-align:end;margin-bottom:5px; }
	.searchBox label { font-size:18px; margin-right:30px;float:left; }
	.searchBox span { font-size:18px; }
	input[name=searchType] { margin-top:-2px; }
	#searchWord { width:220px;margin-bottom:3px;float:right;font-size:medium;height:30px; }
	#btnSearch { margin-left:2px;float:right; }
	.tbl_type01 th { padding:0 5px; }
	.tbl_type01 tr { line-height:2.5;font-size:100%; }
	.tbl_type01 td { padding:0px 5px;font-size:14px;text-align:center !important; }
	.btn_next { text-align:end; }
	#btnSubmit, #btnCancel { width:80px;height:30px;padding:1px 6px; }
	.pagingArea { margin-top:10px; }
	input[type=checkbox] {width:15px;margin-left: 10px; }
</style>

<body>
	<div id="wrap_pop_ser">

		<div class="ser_text">
			<a>주소록 관리</a>
		</div>

		<!-- button -->
		<div class="btnAddressList">
			<button type="button" class="btn_table" id="btnAddressList">주소록 목록가기</button>
		</div>
		<!-- //button -->

		<form name="searchForm" action="/msg/AddressList">
			<input type="hidden" name="page" value="${Data.paginationInfo.currentPageNo}"/>
			<input type="hidden" name="isGroup" value="${isGroup}"/>
			<input type="hidden" name="seqgroupinfo" value="${seqgroupinfo}"/>

			<!-- search -->
			<div class="searchBox">
				<label><input type="radio" class="inputRadio" name="searchType" value="P" <c:if test="${searchType eq 'P'}">checked</c:if> /><span>개인</span></label>
				<label style="margin-right:-6px;"><input type="radio" class="inputRadio" name="searchType" value="G" <c:if test="${searchType eq 'G'}">checked</c:if> /><span>그룹</span></label>
				<button type="button" class="btn1" id="btnSearch">이름 검색</button>
				<input type="text" id="searchWord" name="searchWord" value="${searchWord}" autocomplete="off"/>
			</div>
			<!-- //search -->

			<!-- 개인주소록 -->
			<div class="tabsCont on" id="pAddress">
				<div class="tbl_type01 company_list">
					<table>
						<caption>리스트</caption>
						<colgroup>
							<col width="7%" />
							<col width="40%" />
							<col width="53" />
						</colgroup>
						<thead>
							<tr>
								<th scope="col"><input type="checkbox" value="" id="checkAll" /></th>
								<th scope="col">이름</th>
								<th scope="col">수신번호</th>
							</tr>
						</thead>
						<tbody id="pList">
							<c:choose>
								<c:when test="${fn:length(pList) > 0}">
									<c:forEach items="${pList}" var="pList">
										<tr>
											<td><input type="checkbox" value="${pList.seq}" /></td>
											<td>${pList.name}</td>
											<td>
												<c:choose>
													<c:when test="${fn:length(pList.phonenumber) eq 10}">
														<fmt:formatNumber var="phonenumber" value="${pList.phonenumber}" pattern="00,0000,0000"/>
														<c:out value="${fn:replace(phonenumber, ',', '-')}"/>
													</c:when>
													<c:otherwise>
														<fmt:formatNumber var="phonenumber" value="${pList.phonenumber}" pattern="000,0000,0000"/>
														<c:out value="${fn:replace(phonenumber, ',', '-')}"/>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr style="height:37px;">
										<td colspan="3">데이터가 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>

				<BaseTag:Page pageing="${pPaginationInfo}" formName="searchForm"/>

			</div>
			<!-- //개인주소록 -->

			<!-- 그룹주소록 -->
			<div class="tabsCont" id="gAddress">
				<div class="tbl_type01 company_list">
					<table>
						<caption>리스트</caption>
						<colgroup>
							<col width="7%" />
							<col width="60%" />
							<col width="33" />
						</colgroup>
						<thead>
							<tr>
								<th scope="col"><input type="checkbox" value="" id="checkGAll" /></th>
								<th scope="col">그룹이름</th>
								<th scope="col">그룹원 수</th>
							</tr>
						</thead>
						<tbody id="gList">
							<c:choose>
								<c:when test="${fn:length(gList) > 0}">
									<c:forEach items="${gList}" var="gList">
										<tr>
											<td><input type="checkbox" value="${gList.groupseq}" /></td>
											<td>${gList.groupname}</td>
											<td>${gList.cnt} 명</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr style="height:37px;">
										<td colspan="3">데이터가 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>

				<BaseTag:Page pageing="${gPaginationInfo}" formName="searchForm"/>

			</div>
			<!-- //그룹주소록 -->
		</form>


		<!-- button -->
		<div class="btn_next">
			<button type="button" class="btn_i" id="btnSubmit">선택</button>
			<button type="button" class="btn_can" id="btnCancel">닫기</button>
		</div>
		<!-- //button -->

	</div>
</body>

</BaseTag:layoutPopup>
<%@ include file="AddressListScript.jsp" %>