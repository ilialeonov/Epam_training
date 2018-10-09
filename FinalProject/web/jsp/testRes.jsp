<%-- 
    Document   : missed
    Created on : 30.09.2018, 13:33:10
    Author     : Администратор
--%>
<%@page session = "false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${requestScope.locale}" scope="request" />
<fmt:setBundle basename="resources.pagecontent" var="rb"  />
<fmt:setBundle basename="resources.test" var="test"  />


<!DOCTYPE html>
<html>
    <head>
        <style>
            li {
             list-style-type: none; /* Убираем маркеры */
            }
            .image {
                padding-right:  20px;
                float:left;
            }
        </style>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            <fmt:message key = "label.region" bundle="${rb}"/>
        </title>
        <style>
            rMenu {
                position: absolute;
                right: 500px;
            }
        </style>
    </head>
    <body>
        <jsp:include page = "header.jsp"/>
        <div class="field">
            <div class="inline">
            <c:choose >
                <c:when test = "${role == 'user'}">
                    <div class ="rightMenu">
                        <jsp:include page="../jsploged/rightPatternUser.jsp"/>
                    </div>
                </c:when>
                <c:when test = "${role == 'admin'}">
                    <div class ="rightMenu">
                        <jsp:include page="../jsploged/rightPatternAdmin.jsp"/>
                    </div>
                </c:when>
            </c:choose>
            
            </div >
            <h2 style = "margin-left: 50px; padding-top: 10px;"><fmt:message key = "test.result" bundle="${test}"/>${term} <fmt:message key = "test.years" bundle="${test}"/></h2>
        </div>
        <jsp:include page = "footer.jsp"/>
    </body>
</html>
