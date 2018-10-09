<%-- 
    Document   : footer
    Created on : 21.09.2018, 13:35:10
    Author     : Администратор
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session = "false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${requestScope.locale}" scope="request" />
<fmt:setBundle basename="resources.pagecontent" var="rb"  />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.servletContext.contextPath}/css/style.css" rel="stylesheet" type="text/css">
        <title>JSP Page</title>
        <style>
            p {
                font-family: Geneva, Arial, Helvetica, sans-serif;
                font-size:15px;
                text-indent: 1.5em;
            }
        </style>
    </head>
    <body>
        <div class="underfield">
            <p><fmt:message key = "footer.first" bundle="${rb}"/><p/>
<p><fmt:message key = "footer.second" bundle="${rb}"/> <p/>
<p><fmt:message key = "footer.third" bundle="${rb}"/><p/>
        </div>
    </body>
</html>
