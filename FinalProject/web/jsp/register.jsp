<%-- 
    Document   : register
    Created on : 17.09.2018, 17:37:43
    Author     : Администратор
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${requestScope.locale}" scope="request" />
<fmt:setBundle basename="resources.pagecontent" var="rb"  />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.servletContext.contextPath}/css/style.css" rel="stylesheet" type="text/css">
        <title><fmt:message key = "label.RegisterTitle" bundle="${rb}"/></title>
        <style>
            .yet {
                position: relative;
                top: 50px;
                margin-left: 100px;
            }
        </style>
    </head>
   
    <body> 
        <jsp:include page = "header.jsp"/>
        <div class="field">
            <c:choose>
                <c:when test="${empty role}">
                    <c:if test="${not empty errorOccured}">
                        <h1 class="title"> <fmt:message key = "${errorOccured}" bundle="${rb}"/></h1>
                    </c:if>
                    <c:if test="${not empty errorRegisterMessage}">
                        <h2 class="title"><fmt:message key = "${errorRegisterMessage}" bundle="${rb}"/></h2>
                    </c:if>
                    
                    <h2 class="title"><fmt:message key = "label.registerPlease" bundle="${rb}"/></h2>
                    
                    <form class = "register" name="registerForm" method="POST" 
                          action="${pageContext.servletContext.contextPath}/controller" >
                        
                        <input  type="hidden" name="command" value="register" />

                        <fmt:message key = "label.name" bundle="${rb}"/>:<br/>
                        <input class ="inputCl" type="text" name="name" value="${param.name}"/>
                        <br/>

                        <fmt:message key = "label.mail" bundle="${rb}"/>:<br/>
                        <input class ="inputCl" type="text" name="mail" value="${param.mail}"/>
                        <br/>

                        <fmt:message key = "label.Login" bundle="${rb}"/>:<br/>
                        <input class ="inputCl" type="text" name="login" value="${param.login}"/>
                        <br/>

                        <fmt:message key = "label.Password" bundle="${rb}"/>:<br/>
                        <input class ="inputCl" type="password" name="password" value=""/>
                        <br/> 

                        <fmt:message key = "label.RepeatPassword" bundle="${rb}"/>:<br/>
                        <input class ="inputCl" type="password" name="passwordRepeat" value=""/> <br/>
                        <input class ="button18" type="submit" 
                               value="<fmt:message key = "label.register" bundle="${rb}"/>"/>
                    </form>
                    </c:when>
                    <c:when test = "${role == 'user'}">
                        <div class ="rightMenu">
                            <jsp:include page="../jsploged/rightPatternUser.jsp"/>
                        </div>
                        <div class="yet"><h2><fmt:message key = "label.alreadyRegistered" bundle="${rb}"/></h2></div>
                    </c:when>
                    <c:when test = "${role == 'admin'}">
                        <div class ="rightMenu">
                            <jsp:include page="../jsploged/rightPatternAdmin.jsp"/>
                        </div>
                        <div class="yet"><h2><fmt:message key = "label.alreadyRegistered" bundle="${rb}"/></h2></div>
                    </c:when>
                        
            </c:choose>
        </div>
        <jsp:include page = "footer.jsp"/>
    </body>
</html>

