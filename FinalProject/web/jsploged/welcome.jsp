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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.servletContext.contextPath}/css/style.css" rel="stylesheet" type="text/css">
        <script src="js/script.js"></script>
        <title>main</title>
    </head>
    <body>
        <jsp:include page = "../jsp/header.jsp"/>
        <div class="field">
            <div class="inline">
            
            <c:choose>
                    <c:when  test="${requestScope.role == 'user'}">
                        <div class = "rightMenu">
                            <jsp:include page="/jsploged/rightPatternUser.jsp"/>
                        </div>
                     </c:when>
                    <c:when  test="${requestScope.role == 'admin'}">
                        <div class = "rightMenu">
                            <jsp:include page="/jsploged/rightPatternAdmin.jsp"/>
                        </div>
                     </c:when>
                </c:choose>

            <div class = "createMenu">
                     <h1 class = "titleW"><fmt:message key = "welcome.welcome" bundle="${rb}"/></h1>
                     <h2 class = "titleW"><fmt:message key = "welcome.help" bundle="${rb}"/></h2>
                     <h2 class = "titleW"><fmt:message key = "welcome.watch" bundle="${rb}"/></h2>
            </div>
              </div>
        </div>
        <jsp:include page = "../jsp/footer.jsp"/>
    </body>
</html>