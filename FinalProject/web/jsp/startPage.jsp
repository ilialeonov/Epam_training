<%-- 
    Document   : startPage
    Created on : 18.09.2018, 13:12:30
    Author     : Администратор
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.servletContext.contextPath}/css/style.css" rel="stylesheet" type="text/css">
        <title>JSP Page</title>
    </head>
   
    <body> 
        <jsp:include page = "header.jsp"/>
        <c:set var="current" value="122000"/>
        default: <fmt:formatNumber value="${current}"/> <br/>
        percent: <fmt:formatNumber value="${current}" type="percent" /> <br/>
        bel rub: <fmt:formatNumber value="${current}" type="currency" /> <br/>
    </body>
</html>
