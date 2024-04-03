<%@page import="com.model2.mvc.common.util.CommonUtil"%>
<%@page import="com.model2.mvc.service.domain.Product"%>
<%@page import="java.util.*"%>
<%@ page import="com.model2.mvc.common.*"%>

<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>

<%-- <%
//HashMap<String, Object> map = (HashMap<String,Object>)request.getAttribute("map");
List<Product> list = (List<Product>)request.getAttribute("list");
Search search = (Search)request.getAttribute("searchVO");
Page resultPage=(Page)request.getAttribute("resultPage");

String searchCondition = CommonUtil.null2str(search.getSearchCondition());
String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());


%> --%>




<html>
<head>
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">

function fncGetUserList(currentPage){
	document.getElementById("currentPage").value = currentPage;
	var queryString = window.location.search;
    var params = new URLSearchParams(queryString);
    params.set('currentPage', currentPage);
    
 
	var newUrl = window.location.pathname + '?' + params.toString();
    document.detailForm.action = newUrl;
	document.detailForm.submit();
}

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">

		<form name="detailForm" action="/listProduct.do?menu=${param.menu }"
			method="post">

			<table width="100%" height="37" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
						width="15" height="37" /></td>
					<td background="/images/ct_ttl_img02.gif" width="100%"
						style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="93%" class="ct_ttl01">
								<%-- <%
								if (request.getParameter("menu").equals("search")) {
								%>

								<%
								} else if (request.getParameter("menu").equals("manage")) {
								%>
											상품 관리
								<%
								}
								%> --%>
								<c:if test="${param.menu == 'search' }">
											상품 목록조회								
								</c:if>
								<c:if test="${param.menu == 'manage' }">
											상품 관리								
								</c:if>
								</td>
							</tr>
						</table>
					</td>
					<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"
						width="12" height="37" /></td>
				</tr>
			</table>


			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>

					<td align="right"><select name="searchCondition" class="ct_input_g" style="width: 80px">
							<option value="0" ${! empty search.searchCondition && search.searchCondition == 0 ? "selected" : ""} >상품번호</option>
							<option value="1" ${search.searchCondition.trim().equals("1") ? "selected" : ""}>상품명</option>
							<option value="2" ${search.searchCondition.trim().equals("2") ? "selected" : ""}>상품가격</option>
					</select> <input type="text" name="searchKeyword" value="${search.searchKeyword}" class="ct_input_g"
						style="width: 200px; height: 19px" /></td>


					<td align="right" width="70">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="17" height="23"><img
									src="/images/ct_btnbg01.gif" width="17" height="23"></td>
								<td background="/images/ct_btnbg02.gif" class="ct_btn01"
									style="padding-top: 3px;"><a
									href="javascript:fncGetProductList(1);">검색</a></td>
								<td width="14" height="23"><img
									src="/images/ct_btnbg03.gif" width="14" height="23"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>


			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td colspan="11">전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지
					</td>
				</tr>
				<tr>
					<td class="ct_list_b" width="100">No</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">상품명</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">가격</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">등록일</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">현재상태</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>

				<%-- <%
						for(int i=0; i<list.size(); i++) {
						Product vo = list.get(i);
				%> --%>
				<tr>
				<c:set var="i" value="0"></c:set>
				<c:forEach var="list" items="${list}">
					<c:set var="i" value="${i+1}"></c:set>
					<tr class="ct_list_pop">
					<td align="center">${i}</td>
					<td></td>
					<td align="left">
					
					
					<c:url var="getProduct" value="/getProduct.do" >
						<c:param name="prodNo" value="${list.prodNo}"/>
						<c:param name="name" value="${param.menu}"/>
					</c:url>
					<c:url var="updateProductView" value="/updateProductView.do">
						<c:param name="prodNo" value="${list.prodNo}"/>
						<c:param name="name" value="${param.menu}"/>
					</c:url>
					
					
					<c:if test="${param.menu eq 'search' }">
					<a href="${getProduct}">${list.prodName}</a>
					</c:if>
					<c:if test="${param.menu eq 'manage' }">
					<a href="${updateProductView}">${list.prodName}</a>
					</c:if>
					
					
					</td>
					<td></td>
					<td align="left">${list.price}</td>
					<td></td>
					<td align="left">${list.regDate}</td>
					<td></td>
					<td>
					<c:choose>
						<c:when test='${list.proTranCode.equals("1")}'>판매중</c:when>
						<c:when test='${list.proTranCode.trim().equals("2")}'>
							구매완료
							<c:if test='${param.menu.equals("manage") }'>
								<a href="/updateTranCodeByProd.do?prodNo=${list.prodNo}&tranCode=${list.proTranCode}">
								(배송시작)
								</a>
							</c:if>
						</c:when>
						<c:when test='${list.proTranCode.trim().equals("3")}'>배송중</c:when>
						<c:when test='${list.proTranCode.trim().equals("4")}'>배송완료</c:when>
					</c:choose>
					</td>
				
					<%-- <td align="center"><%=i + 1 %></td>
					<td></td>

					<td align="left">
					<%if (request.getParameter("menu").equals("search")) { %>
					<a 	href="/getProduct.do?prodNo=<%=vo.getProdNo() %>&menu=<%=request.getParameter("menu")%>"><%=vo.getProdName() %></a>
								<%} else if (request.getParameter("menu").equals("manage")) {%>
					<a 	href="/updateProductView.do?prodNo=<%=vo.getProdNo() %>&menu=<%=request.getParameter("menu")%>"><%=vo.getProdName() %></a></td>
								<% } %>
					

					<td></td>
					<td align="left"><%=vo.getPrice() %></td>
					<td></td>
					<td align="left"><%=vo.getRegDate() %></td>
					<td></td>
					<td align="left">판매중</td> --%>
				</tr>
				<tr>
					<td colspan="11" bgcolor="D6D7D6" height="1"></td>
				</tr>
			</c:forEach>
		

			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td align="center">
						<input type="hidden" id="currentPage" name="currentPage" value=""/>
						
						<%-- <c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
								◀ 이전
						</c:if>
						<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
								<a href="javascript:fncGetUserList('${ resultPage.currentPage-1}')">◀ 이전</a>
						</c:if>
						
						<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
							<a href="javascript:fncGetUserList('${ i }');">${ i }</a>
						</c:forEach>
						
						<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
								이후 ▶
						</c:if>
						<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
								<a href="javascript:fncGetUserList('${resultPage.endUnitPage+1}')">이후 ▶</a>
						</c:if> --%>
						
						<jsp:include page="../common/pageNavigator.jsp"/>	
						
					</td>
				</tr>
			</table>
			<!--  페이지 Navigator 끝 -->

		</form>

	</div>
</body>
</html>
