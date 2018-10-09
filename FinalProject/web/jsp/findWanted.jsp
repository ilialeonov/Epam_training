<%-- 
    Document   : main
    Created on : 16.09.2018, 21:26:15
    Author     : Администратор
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${requestScope.locale}" scope="request" />
<fmt:setBundle basename="resources.pagecontent" var="rb"  />

<!DOCTYPE html>
<html>
    <head>
        <script src="/js/jquery.js" type="text/javascript"></script>
        <script src="/js/jquery.min.js" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.servletContext.contextPath}/css/style.css" rel="stylesheet" type="text/css">
        <script src="js/script.js"></script>
        <title>main</title>
    </head>
    <body>
        <jsp:include page = "../jsp/header.jsp"/>
        <div class="field">
            <div class="title"><h2><fmt:message key = "find.title" bundle="${rb}"/></h2></div>
            <c:if test="${not empty role}">
                <div class = "rightMenu">
                    <jsp:include page="/jsploged/rightPatternAdmin.jsp"/>
                </div>
            </c:if>
            <div>
                
            </div>
        </div>
        <jsp:include page = "../jsp/footer.jsp"/>
    </body>
</html>