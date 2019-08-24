<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 导入core标签库 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>网上书城</title>
<link rel="stylesheet" type="text/css" href="css/style.css"/>

<script type="text/javascript" src="js/valiadate.js"></script>

<script type="text/javascript">
/**
 * 获取复选框是否被选中
 */
function checkSel() {
	var sel = document.getElementsByName("bookId");
	var isSel = false;
	for(var i = 0; i < sel.length; i++) {
		if(sel[i].checked) {
			isSel = true;
		}
	}
	if(isSel == false) {
		alert("请选择图书");
	}		
	return isSel;
}
</script>

</head>
<body>
<%
	String username = (String)session.getAttribute("loginuser");
	if(username == null) {
		response.sendRedirect("login.jsp");
	}	
	
	int no = 0;
	int totalPage = 0;
	List books = (List)request.getAttribute("books");
	if(request.getAttribute("current") != null) {
		no = (Integer)request.getAttribute("current");//获取当前页面数
		totalPage = (Integer)request.getAttribute("totalPage");//获取总页面数
	}
%>


<!-- 引入 -->
<jsp:include page="main_head.jsp"></jsp:include>

<div id="content" class="wrap">
	<div class="list bookList">
		<form method="post" name="shoping" action="CartServlet" id="myform">
			<table>
				<tr class="title">
					<th class="checker"></th>
					<th>书名</th>
					<th class="price">价格</th>
					<th class="store">库存</th>
					<th class="view">图片预览</th>
				</tr>
				
				<c:forEach var="book" items="${books }">
					<tr>
						<td><input type="checkbox" name="bookId" value="${book.bid }" /></td>
						<td class="title">${book.bookname }</td>
						<input type="hidden" name="title" value = "${book.bid }:${book.bookname}"/>
						<td>￥${book.b_price }</td>
						<input type="hidden" name="price" value = "${book.bid }:${book.b_price}"/> <!-- b_price跟数据库字段名相对应 -->
						<td>${book.stock }</td>
						<input type="hidden" name="stock" value = "${book.bid }:${book.stock}"/>
						<td class="thumb"><img src="${book.image }" /></td>
						<input type="hidden" name="image" value = "${book.bid }:${book.image}"/>
					</tr>
				</c:forEach>	
				
			</table>
			<%-- <%out.println(request.getAttribute("current")); %> --%>
			<!--分页开始-->
			<%if(request.getAttribute("current") != null) { %>
			
			<div class="page-spliter">
				<a href="SearchServlet">首页</a>
				<%for(int i = 1; i <= totalPage; i++) { %>
					<%if(i==no) { %>	<!-- 如果不是当前页显示为链接 -->
							<span class="current"><%=i %></span>
							<%continue;} %>
						<a href="SearchServlet?currentPage=<%=i %>"><%=i %></a>
				<%} %>
				<a href="SearchServlet?currentPage=<%=totalPage %>">尾页</a>
			</div>
			
			<%} %>
			<!--分页结束-->
			<div class="button"><input class="input-btn" type="submit" name="submit" value="" onclick="return checkSel()"/></div>
		</form>
	</div>
</div><!--中间内容结束-->

<div id="footer" class="wrap">
	网上书城 &copy; 版权所有
</div>

</body>

</html>