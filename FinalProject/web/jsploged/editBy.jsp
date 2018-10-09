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
            <div class = "rightMenu">
                <jsp:include page="/jsploged/rightPatternAdmin.jsp"/>
            </div>
            <div class = "createMenu">
                <form class = "createData" name="editForm" method="GET" 
                    action="${pageContext.servletContext.contextPath}/controller" >
                    
                <c:if test="${not empty errorOccured}">
                     <h2 class = "title"><fmt:message key = "${errorOccured}" bundle="${rb}"/></h2>
                     <h2 class = "title"><fmt:message key = "${errorEditByMessage}" bundle="${rb}"/></h2>
                     <br>
                </c:if>
                     
                <c:choose>
                    <c:when  test="${param.person == 'isCriminal'}">
                        <div class="title"><h2><fmt:message key = "edit.editByWTitle" bundle="${rb}"/></h2></div> <br/>
                     </c:when>
                     <c:otherwise>
                         <div class="title"><h2><fmt:message key = "edit.editByMTitle" bundle="${rb}"/></h2></div> <br/>
                     </c:otherwise>
                </c:choose>
                  <input type="hidden" name="person" value="${param.person}" />
                  <input type="hidden" name="command" value="find" />
                  <fmt:message key = "create.id" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="id" value="${param.id}"/>
                  <br/>
                  <input class ="button18" type="submit"
                         value = "<fmt:message key = "edit.label.find" bundle="${rb}"/>"/>
                </form>
                
                  <form class = "createData" name="createForm" method="GET" 
                    action="${pageContext.servletContext.contextPath}/controller" >
                    <div class="title"><h2><fmt:message key = "edit.editByContinue" bundle="${rb}"/></h2></div> <br/>
                  <input type="hidden" name="person" value="${param.person}" />
                  <input type="hidden" name="command" value="find" />
                  
                  <fmt:message key = "create.name" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="name" value="${param.name}"/>
                  <br/>

                  <fmt:message key = "create.panname" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="panname" value="${param.panname}"/>
                  <br/> 

                  <input class ="button18" type="submit"
                         value = "<fmt:message key = "edit.label.find" bundle="${rb}"/>"/>
                  <br/><br/>
              </form>
                  
            </div>
              </div>
        </div>
        <jsp:include page = "../jsp/footer.jsp"/>
    </body>
</html>