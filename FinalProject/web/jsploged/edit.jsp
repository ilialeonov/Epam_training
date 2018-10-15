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
        <title><fmt:message key = "label.edit" bundle="${rb}"/></title>
    </head>
    <body>
            <jsp:include page = "../jsp/header.jsp"/>
        <div class="field">
            <div class="inline">
            <div class = "rightMenu">
                <jsp:include page="/jsploged/rightPatternAdmin.jsp"/>
            </div>
            
            <c:if test="${not empty errorOccured}">
                     <h2 class = "title2"><fmt:message key = "${errorOccured}" bundle="${rb}"/></h2>
                     <h2 class = "title2"><fmt:message key = "${errorCreateMessage}" bundle="${rb}"/></h2>
                </c:if>
                <c:choose>
                    <c:when  test="${param.person == 'isCriminal'}">
                     <div class="title2"><h2><fmt:message key = "edit.editWTitle" bundle="${rb}"/></h2></div> <br/>
                     </c:when>
                     <c:otherwise>
                         <div class="title2"><h2><fmt:message key = "edit.editMTitle" bundle="${rb}"/></h2></div> <br/>
                     </c:otherwise>
                </c:choose>
            
            
            <div class = "createMenu">
                
                <form class = "createData" enctype="multipart/form-data" name="editForm" method="POST" 
                    action="${pageContext.servletContext.contextPath}/controller" >
                
                  <input type="hidden" name="command" value="edit" />
                  <input type="hidden" name="multimedia" value="" />
                  <input type="hidden" name ="id" value="${personToEdit.id}"/>
                  <input type="hidden" name ="person" value="${personToEdit}"/>

                <fmt:message key = "create.photo.enter" bundle="${rb}"/>:<br/>
                <input class = "button18" type="file" name="pic"><br/><br/>
                  
                  <fmt:message key = "create.name.enter" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="name" value="${personToEdit.name}"
                         required pattern="[A-Z]([a-zA-Z]){1,10}"/>
                  <br/>

                  <fmt:message key = "create.panname.enter" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="panname" value="${personToEdit.panname}"
                         required pattern="[A-Z]([a-zA-Z]){1,10}"/>
                  <br/> 
                  
                  <fmt:message key = "create.age.enter" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="age" value="${personToEdit.age}"
                         required pattern="[1-9][0-9]"/>
                  <br/> 

                  <fmt:message key = "create.birthReg.enter" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="birthReg" value="${personToEdit.birthPlace}"
                         required pattern="[A-Z]([a-zA-Z]){1,10}"/>
                  <br/> 

                  <fmt:message key = "create.lastSeenReg.enter" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="lastSeenReg" value="${personToEdit.lastPlace}"
                         required pattern="[A-Z]([a-zA-Z]){1,10}"/>
                  <br/> 

                  <fmt:message key = "create.award.enter" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="award" value="${personToEdit.award}"
                         required pattern="[1-9][0-9]{1,7}"/>
                  <br/> 

                  <fmt:message key = "create.information.enter" bundle="${rb}"/>:<br/>
                  <textarea class ="inputMe" rows="12" cols="120" name="information" />
                    ${personToEdit.information}
                  </textarea>
                  <br/> 
                  <br/> 
                  
                  <input class ="button18" type="submit"
                         value = "<fmt:message key = "edit.label" bundle="${rb}"/>"/>
                  
              </form>
            </div>
              </div>
                         
            <div class="title2"><h2><fmt:message key = "delete.deleteTitle" bundle="${rb}"/></h2></div> <br/>  
            <div class = "title3">
            <form  name="deleteForm" method="POST" 
                  action="${pageContext.servletContext.contextPath}/controller" >
                <input type="hidden" name ="id" value="${personToEdit.id}"/>
                <input type="hidden" name="person" value="${param.person}" />
                <input type="hidden" name="command" value="delete" />
                <input class ="button18" type="submit" 
                       value="<fmt:message key = "label.delete" bundle="${rb}"/>"/>
            </form>
            <br>
            <br>
            </div>
            
            <div class="title2"><h2><fmt:message key = "edit.foundTitle" bundle="${rb}"/></h2></div> <br/>  
            <div class = "title3">
            <form  name="foundForm" method="POST" 
                  action="${pageContext.servletContext.contextPath}/controller" >
                <input type="hidden" name ="id" value="${personToEdit.id}"/>
                <input type="hidden" name="person" value="${param.person}" />
                <input type="hidden" name="command" value="found" />
                <input class ="button18" type="submit" 
                       value="<fmt:message key = "label.wasFound" bundle="${rb}"/>"/>
            </form>
            <br>
            <br>
            </div>
            
        </div>
        <jsp:include page = "../jsp/footer.jsp"/>
    </body>
</html>