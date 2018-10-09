<%-- 
    Document   : startPage
    Created on : 18.09.2018, 13:12:30
    Author     : Администратор
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="false" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${requestScope.locale}" scope="request" />
<fmt:setBundle basename="resources.pagecontent" var="rb"  />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.servletContext.contextPath}/css/style.css" rel="stylesheet" type="text/css">
        <title>JSP Page</title>
    </head>
    
    <body> 
        <img class = "imgT" src ="${pageContext.servletContext.contextPath}/images/maiak_med.jpeg" alt = "maiak picture"/>
        <form class = "login" name="loginForm" method="POST" 
              action="${pageContext.servletContext.contextPath}/controller" >
            <input type="hidden" name="command" value="login" />
            <fmt:message key = "label.Login" bundle="${rb}"/>:<br/>
            <input type="text" name="login" value=""/>
            <br/>
            <fmt:message key = "label.Password" bundle="${rb}"/>:<br/>
            <input type="password" name="password" value=""/>
            <br/> 
            <input class ="button18" type="submit" 
                   value="<fmt:message key = "label.login" bundle="${rb}"/>"/>
        </form>
        

    <div class = "menu">
        
        <form class ="menuch" name="mainForm" method="POST" 
              action="${pageContext.servletContext.contextPath}/controller">
            <input type="hidden" name="command" value="main" />
            <input class ="button18" type="submit" 
                   value="<fmt:message key = "label.main" bundle="${rb}"/>"/>
        </form>
        
        <form class ="menuch" name="missedForm" method="POST" 
              action="${pageContext.servletContext.contextPath}/controller">
            <input type="hidden" name="command" value="show" />
            <input type="hidden" name="person" value="isMissed" />
            <input class ="button18" type="submit" 
                   value="<fmt:message key = "label.missed" bundle="${rb}"/>"/>
        </form>
    
        <form class ="menuch" name="wantedForm" method="POST" 
              action="${pageContext.servletContext.contextPath}/controller">
            <input type="hidden" name="command" value="show" />
            <input type="hidden" name="person" value="isCriminal" />
            <input class ="button18" type="submit" 
                   value="<fmt:message key = "label.wanted" bundle="${rb}"/>"/>
        </form>
        <form class ="menuch" name="regionForm" method="POST" 
              action="${pageContext.servletContext.contextPath}/controller">
            <input type="hidden" name="command" value="redirect" />
            <input type ="hidden" name ="op" value ="region">
            <input class ="button18" type="submit" 
                   value="<fmt:message key = "label.region" bundle="${rb}"/>"/>
        </form>
        <form class ="menuch" name="testForm" method="POST" 
              action="${pageContext.servletContext.contextPath}/controller">
            <input type="hidden" name="command" value="redirect" />
            <input type ="hidden" name ="op" value ="test">
            <input class ="button18" type="submit" 
                   value="<fmt:message key = "label.test" bundle="${rb}"/>"/>
        </form>
        
        
        
        <form class="logregCh" name="logoutForm" method="POST" 
              action="${pageContext.servletContext.contextPath}/controller">
            <input type="hidden" name="command" value="logout" />
            <input class ="button18" type="submit" 
                   value="<fmt:message key = "label.logout" bundle="${rb}"/>"/> 
        </form>
        <a href="${pageContext.servletContext.contextPath}/jsp/register.jsp">
            <fmt:message key = "label.register" bundle="${rb}"/>
        </a>
        </div>
        
    </body>
    
    
</html>
