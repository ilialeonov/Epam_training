<%-- 
    Document   : main
    Created on : 16.09.2018, 21:26:15
    Author     : Администратор
--%>

<%@page session = "false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${requestScope.locale}" scope="request" />
<fmt:setBundle basename="resources.pagecontent" var="rb"  />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.servletContext.contextPath}/css/style.css" rel="stylesheet" type="text/css">
        <title>main</title>
        <style>
            rMenu {
                position: absolute;
                right: 500px;
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
                <c:if test="${not empty errorOccured}">
                <h1 class = "title"><fmt:message key = "${errorOccured}" bundle="${rb}"/></h1>
                 <h2 class = "title"><fmt:message key = "${errorTestifyMessage}" bundle="${rb}"/></h2>
            </c:if>
           
            
            <div class ="person">
            <div class="title"><h1><fmt:message key = "main.title" bundle="${rb}"/></h1></div> <br/>
            
            <fmt:setLocale value="${locale}"/>
            
            <img width="600px" alt="img" src="data:image/jpeg;base64,${person.base64image}"/>
            <h2><fmt:message key = "create.id" bundle="${rb}"/>: ${person.id} </h2>
            <h2><fmt:message key = "create.name" bundle="${rb}"/>: ${person.name} </h2>
            <h2><fmt:message key = "create.panname" bundle="${rb}"/>: ${person.panname} </h2>
            
            <h2><fmt:message key = "create.age" bundle="${rb}"/>: ${person.age} <fmt:message key = "create.years" bundle="${rb}"/></h2>
            <h2><fmt:message key = "create.birthReg" bundle="${rb}"/>: ${person.birthPlace} </h2>
            <h2><fmt:message key = "create.lastSeenReg" bundle="${rb}"/>: ${person.lastPlace} </h2>
            <h2><fmt:message key = "create.award" bundle="${rb}"/>: 
            <fmt:formatNumber type = "currency">
                    <ctg:convert>
                        ${person.award}
                    </ctg:convert>
                    
                </fmt:formatNumber>
            </h2>
            
            <h2><fmt:message key = "create.information" bundle="${rb}"/>: </h2>
            <p class="personInf">
                ${person.information}
            </p>
            </div>
            <div>
                <h1 style = "position:relative; margin-left: 100px; padding: 10px;">
                    <fmt:message key = "testify.informUs" bundle="${rb}"/><br/></h1>
                    
            <form class = "informData" enctype="multipart/form-data" name="createForm" method="POST" 
                  action="${pageContext.servletContext.contextPath}/controller" >
                  <input type="hidden" name="command" value="testify" />
                  <input type="hidden" name ="id" value="${param.id}"/>
                  <input type="hidden" name ="name" value="${param.name}"/>
                  <input type="hidden" name ="panname" value="${param.panname}"/>
                  <input type="hidden" name ="person" value="${param.person}"/>
                  
                  <!--<input type="hidden" name ="userId" value="${sessionScope.id}"/>-->
                  
                  <fmt:message key = "create.information" bundle="${rb}"/>:<br/>
                  <textarea class ="inputMe" rows="12" cols="120" name="testimony"/></textarea>
                  <br/> 
                  
                  <input class ="button18" type="submit"
                         value = "<fmt:message key = "testify.testify" bundle="${rb}"/>"/>
                  <br/><br/>
            </form>
            </div>
            
            </div>
            
        </div>
        <jsp:include page = "../jsp/footer.jsp"/>
    </body>
</html>
