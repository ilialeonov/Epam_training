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
        <title>edit</title>
    </head>
    <body>
            <jsp:include page = "../jsp/header.jsp"/>
        <div class="field">
            <div class="inline">
            <div class = "rightMenu">
                <jsp:include page="/jsploged/rightPatternAdmin.jsp"/>
            </div>
            <div class = "createMenu">
                
                <form class = "createData" enctype="multipart/form-data" name="editForm" method="POST" 
                    action="${pageContext.servletContext.contextPath}/controller" >
                <c:if test="${not empty errorOccured}">
                     <h2 class = "title"><fmt:message key = "${errorOccured}" bundle="${rb}"/></h2>
                     <h2 class = "title"><fmt:message key = "${errorCreateMessage}" bundle="${rb}"/></h2>
                </c:if>
                <c:choose>
                    <c:when  test="${param.person == 'isCriminal'}">
                     <div class="title"><h2><fmt:message key = "edit.editWTitle" bundle="${rb}"/></h2></div> <br/>
                     </c:when>
                     <c:otherwise>
                         <div class="title"><h2><fmt:message key = "edit.editMTitle" bundle="${rb}"/></h2></div> <br/>
                     </c:otherwise>
                </c:choose>
                  <input type="hidden" name="command" value="edit" />
                  <input type="hidden" name="multimedia" value="" />
                  <input type="hidden" name ="id" value="${personToEdit.id}"/>
                  <input type="hidden" name ="person" value="${personToEdit}"/>

                <fmt:message key = "create.photo" bundle="${rb}"/>:<br/>
                <input class = "button18" type="file" name="pic"><br/><br/>
                  
                  <fmt:message key = "create.name" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="name" value="${personToEdit.name}"/>
                  <br/>

                  <fmt:message key = "create.panname" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="panname" value="${personToEdit.panname}"/>
                  <br/> 
                  
                  <fmt:message key = "create.age" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="age" value="${personToEdit.age}"/>
                  <br/> 

                  <fmt:message key = "create.birthReg" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="birthReg" value="${personToEdit.birthPlace}"/>
                  <br/> 

                  <fmt:message key = "create.lastSeenReg" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="lastSeenReg" value="${personToEdit.lastPlace}"/>
                  <br/> 

                  <fmt:message key = "create.award" bundle="${rb}"/>:<br/>
                  <input class ="inputCl" type="text" name="award" value="${personToEdit.award}"/>
                  <br/> 

                  <fmt:message key = "create.information" bundle="${rb}"/>:<br/>
                  <textarea class ="inputMe" rows="12" cols="120" name="information" />
                    ${personToEdit.information}
                  </textarea>
                  <br/> 
                  <fmt:message key = "create.status" bundle="${rb}"/><br/>
                  
                <c:choose>
                    <c:when  test="${personToEdit.status == 'false'}">
                     <input class ="inputCl" type="text" name="status" value="No"/>
                     </c:when>
                     <c:otherwise>
                         <input class ="inputCl" type="text" name="status" value="Yes"/>
                     </c:otherwise>
                </c:choose>
                  
                  <br/> 
                  
                  <input class ="button18" type="submit"
                         value = "<fmt:message key = "edit.label" bundle="${rb}"/>"/>
                  <br/><br/>
              </form>
                  
            </div>
              </div>
        </div>
        <jsp:include page = "../jsp/footer.jsp"/>
    </body>
</html>