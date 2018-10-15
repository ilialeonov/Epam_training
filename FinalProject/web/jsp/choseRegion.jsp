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
        <title><fmt:message key = "label.regionCh" bundle="${rb}"/></title>
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
            <div class="inline">
            <c:choose>
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
                
            <div class = "createMenu">
            
            
            
            <form class = "createData" name="editForm" method="GET" 
                    action="${pageContext.servletContext.contextPath}/controller" >
                    
                <c:if test="${not empty errorOccured}">
                     <h2 class = "title"><fmt:message key = "${errorOccured}" bundle="${rb}"/></h2>
                     <h2 class = "title"><fmt:message key = "${errorChoseRegionMessage}" bundle="${rb}"/></h2>
                </c:if>
                     
                
                <div class="title"><h2><fmt:message key = "region.choseRegion" bundle="${rb}"/></h2></div> <br/>
                  <input type="hidden" name="command" value="region" />
                  <fmt:message key = "region.region" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="region" value="${param.region}"
                         required pattern="[A-Z]([a-zA-Z]){1,10}"/>
                  <br/>
                  <input class ="button18" type="submit"
                         value = "<fmt:message key = "edit.label.find" bundle="${rb}"/>"/>
                </form>
        </div>
            </div>
        </div>
        <jsp:include page = "footer.jsp"/>
    </body>
</html>

