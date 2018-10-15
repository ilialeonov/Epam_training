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
        <title><fmt:message key = "label.create" bundle="${rb}"/></title>
    </head>
    <body>
            <jsp:include page = "../jsp/header.jsp"/>
        <div class="field">
            <div class="inline">
            <div class = "rightMenu">
                <jsp:include page="/jsploged/rightPatternAdmin.jsp"/>
            </div>
            <div class = "createMenu">
                
            
                <form class = "createData" enctype="multipart/form-data" name="createForm" method="POST" 
                    action="${pageContext.servletContext.contextPath}/controller" >
                    
                    <c:if test="${not empty errorOccured}">
                     <h2 class = "title"><fmt:message key = "${errorOccured}" bundle="${rb}"/></h2>
                     <h2 class = "title"><fmt:message key = "${errorCreateMessage}" bundle="${rb}"/></h2>
                     
                </c:if>
                <c:choose>
                    <c:when  test="${param.person == 'isCriminal'}">
                     <div class="title"><h2><fmt:message key = "create.creationWTitle" bundle="${rb}"/></h2></div> <br/>
                     </c:when>
                     <c:otherwise>
                         <div class="title"><h2><fmt:message key = "create.creationMTitle" bundle="${rb}"/></h2></div> <br/>
                     </c:otherwise>
                </c:choose>
                    
                  <input type="hidden" name="command" value="create" />
                  <input type="hidden" name="multimedia" value="" />
                  <input type="hidden" name ="person" value="${param.person}"/>

                <fmt:message key = "create.photo.enter" bundle="${rb}"/>:<br/>
                <input class = "button18" type="file" name="pic" required><br/><br/>
                  
                  <fmt:message key = "create.name.enter" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="name" value="${param.name}"
                         required pattern="[A-Z]([a-zA-Z]){1,10}"/>
                  <br/>

                  <fmt:message key = "create.panname.enter" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="panname" value="${param.panname}"
                         required pattern="[A-Z]([a-zA-Z]){1,10}"/>
                  <br/> 

                  <fmt:message key = "create.age.enter" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="age" value="${param.age}"
                         required pattern="[1-9][0-9]"/>
                  <br/> 

                  <fmt:message key = "create.birthReg.enter" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="birthReg" value="${param.birthReg}"
                         required pattern="[A-Z]([a-zA-Z]){1,10}"/>
                  <br/> 

                  <fmt:message key = "create.lastSeenReg.enter" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="lastSeenReg" value="${param.lastSeenReg}"
                         required pattern="[A-Z]([a-zA-Z]){1,10}"/>
                  <br/> 

                  <fmt:message key = "create.award.enter" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="award" value="${param.award}"
                         required pattern="[1-9][0-9]{1,7}"/>
                  <br/> 

                  <fmt:message key = "create.information.enter" bundle="${rb}"/>:<br/>
                  <textarea class ="inputMe" rows="12" cols="120" name="information"/></textarea>
                  <br/> 
                  
                  <input class ="button18" type="submit"
                         value = "<fmt:message key = "create.label" bundle="${rb}"/>"/>
                  <br/><br/>
              </form>
                  
            </div>
              </div>
        </div>
        <jsp:include page = "../jsp/footer.jsp"/>
    </body>
</html>