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
				
				<c:forEach var="book" items="${pb.beanList}">
					<tr>
						<td><input type="checkbox" name="bookId" value="${book.bid }" /></td>
						<td class="title">${book.bookname }</td>
						<input type="hidden" name="title" value = "${book.bid }:${book.bookname}"/>
						<td>￥${book.price }</td>
						<input type="hidden" name="price" value = "${book.bid }:${book.price}"/> <!-- b_price跟数据库字段名相对应 -->
						<td>${book.stock }</td>
						<input type="hidden" name="stock" value = "${book.bid }:${book.stock}"/>
						<td class="thumb"><img src="${book.image }" /></td>
						<input type="hidden" name="image" value = "${book.bid }:${book.image}"/>
					</tr>
				</c:forEach>	
			</table>
<center>
    第${pb.pc}页/共${pb.tp}页
    <a href="${pb.url}&pc=1">首页</a>
    <c:if test="${pb.pc>1}">
        <a href="${pb.url}&pc=${pb.pc-1}">上一页</a>
    </c:if>

    <c:choose>
        <c:when test="${pb.tp<=10}">
            <c:set var="begin" value="1"/>
            <c:set var="end" value="${pb.tp}"/>
        </c:when>
        <c:otherwise>
            <c:set var="begin" value="${pb.pc-5}"/>
            <c:set var="end" value="${pb.pc+4}"/>
            <%--头溢出--%>
            <c:if test="${begin<1}">
                <c:set var="begin" value="1"/>
                <c:set var="end" value="10"/>
            </c:if>
            <%--尾溢出--%>
            <c:if test="${end>pb.tp}">
                <c:set var="end" value="${pb.tp}"/>
                <c:set var="begin" value="${pb.tp-9}"/>
            </c:if>
        </c:otherwise>
    </c:choose>
    <%--循环遍历页码列表--%>
    <c:forEach var="i" begin="${begin}" end="${end}">
        <c:choose>
            <c:when test="${i eq pb.pc}">
                [${i}]
            </c:when>
            <c:otherwise>
                <a href="${pb.url}&pc=${i}">[${i}]</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:if test="${pb.pc<pb.tp}">
    <a href="${pb.url}&pc=${pb.pc+1}">下一页</a>
    </c:if>
    <a href="${pb.url}&pc=${pb.tp}">尾页</a>

</center>

<div id="footer" class="wrap">
	网上书城 &copy; 版权所有
</div>

</body>

</html>