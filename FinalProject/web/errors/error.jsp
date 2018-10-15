<%-- 
    Document   : missed
    Created on : 30.09.2018, 13:33:10
    Author     : Администратор
--%>
<%@page isErrorPage="true" session = "false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${requestScope.locale}" scope="request" />
<fmt:setBundle basename="resources.pagecontent" var="rb"  />

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
            <fmt:message key = "label.error" bundle="${rb}"/>
        </title>
        <style>
            .image2 {
                position: relative;
                width: 960px;
                margin-left: 15px;
            }
            .notF {
                width: 75%;
                position: relative;
                z-index: 2;
                margin-left: 50px;
                
                font-size: 40px;
    font-family: Geneva, Arial, Helvetica, sans-serif;
            }
        </style>
    </head>
    <body>
        <jsp:include page = "../jsp/header.jsp"/>          
            
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
            
            </div>
                <div>
                    <br>
                    <img class = "image2" src ="${pageContext.servletContext.contextPath}/images/success.jpg" alt = "success picture"/>
                    <h1 class = "notF">
                        <c:choose >
                            <c:when test = "${role == 'user'}">
                                <fmt:message key = "error.textUser" bundle="${rb}"/>
                            </c:when>
                            <c:when test = "${role == 'admin'}">
                                <fmt:message key = "error.textAdmin" bundle="${rb}"/>
                            </c:when>
                        </c:choose>
                        
                    </h1>
                </div>
        </div>
        <jsp:include page = "../jsp/footer.jsp"/>
    </body>
</html>
