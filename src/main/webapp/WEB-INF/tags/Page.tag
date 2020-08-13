<%@ tag language="java" pageEncoding="UTF-8"%><%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="pageing" type="egovframework.pagination.PaginationInfo" %>
<%@ attribute name="formName"%>
<script type="text/javascript">
function goPage(page) {
    $("[name=${formName}]").find("[name=page]").val(page);
    $("[name=${formName}]").submit();
};
</script>

<div class="pagingArea">
	<div class="paging">
		<c:choose>
			<c:when test="${pageing.totalPageCount == 1}">
			</c:when>
		   	<c:when test="${pageing.currentPageNo == 1}">
				<a href="javascript:goPage('1')" class="first">First</a>
				<a href="javascript:goPage('1')" class="prev">Prev</a>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${pageing.currentPageNo-10 < 1}">
						<a href="javascript:goPage('1')" class="first">First</a>
						<a href="javascript:goPage('1')" class="prev">Prev</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:goPage('1')" class="first">First</a>
						<a href="javascript:goPage(${pageing.currentPageNo-10})" class="prev">Prev</a>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>

		<c:forEach var="i" begin="${pageing.firstPageNoOnPageList}" end="${pageing.lastPageNoOnPageList}" step="1">
			<c:choose>
				<c:when test="${i == pageing.currentPageNo}">
					 <span onclick="javascript:goPage(${i})">${i}</span>
				</c:when>
				<c:otherwise>
					 <a href="javascript:goPage(${i})">${i}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<c:choose>
	    	<c:when test="${pageing.currentPageNo<pageing.totalPageCount}">
	    		<c:choose>
	    			<c:when test="${pageing.currentPageNo+10 > pageing.totalPageCount}">
	    				<a href="javascript:goPage(${pageing.totalPageCount})" class="next">Next</a>
						<a href="javascript:goPage(${pageing.totalPageCount})" class="last">Last</a>
	    			</c:when>
	    			<c:otherwise>
	    				<a href="javascript:goPage(${pageing.currentPageNo+10})" class="next">Next</a>
						<a href="javascript:goPage(${pageing.totalPageCount})" class="last">Last</a>
	    			</c:otherwise>
	    		</c:choose>
	    	</c:when>
	    	<c:otherwise>
	    	</c:otherwise>
	    </c:choose>
	</div>
</div>