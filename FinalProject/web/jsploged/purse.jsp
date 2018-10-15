<%-- 
    Document   : main
    Created on : 16.09.2018, 21:26:15
    Author     : Администратор
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${requestScope.locale}" scope="request" />
<fmt:setBundle basename="resources.pagecontent" var="rb"  />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.servletContext.contextPath}/css/style.css" rel="stylesheet" type="text/css">
        <script src="js/script.js"></script>
        <title><fmt:message key = "label.purse" bundle="${rb}"/></title>
    </head>
    <body>
            <jsp:include page = "../jsp/header.jsp"/>
        <div class="field">
            <div class="inline">
            <div class = "rightMenu">
                <jsp:include page="/jsploged/rightPatternUser.jsp"/>
            </div>  
                <c:if test="${not empty errorOccured}">
                     <h2 class = "title"><fmt:message key = "${errorOccured}" bundle="${rb}"/></h2>
                     <h2 class = "title"><fmt:message key = "${errorMessage}" bundle="${rb}"/></h2>
                     <br>
                </c:if>
                <c:set var="money">
                    
                </c:set>
                <div class = "title">
                     <h1 ><fmt:message key = "purse.response" bundle="${rb}"/> ${user.name}</h1>
                     <h2 ><fmt:message key = "purse.thank" bundle="${rb}"/> 
                     <fmt:formatNumber type = "currency" >
                            <ctg:convert>
                            ${user.money}
                        </ctg:convert>
                    </fmt:formatNumber></h2>
                </div>
        </div>
        </div>
        <jsp:include page = "../jsp/footer.jsp"/>
    </body>
</html>