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
        <script src="js/script.js"></script>
        <title>JSP Page</title>
        <style>
            li {
                list-style-type: none; 
                margin-top: 10px;
               }
        </style>
    </head>
    
    <body> 
        
    <div class = "rightMenu">
        <div class = "rightTitle">
            <fmt:message key = "name.wanted" bundle="${rb}"/>
        </div>
            <ul>
                
                <li>
                    <form class ="" name="addWForm" method="GET" 
                          action="${pageContext.servletContext.contextPath}/controller">
                        <input type="hidden" name="command" value="redirect" />
                        <input type ="hidden" name ="op" value ="create">
                        <input type ="hidden" name ="person" value ="isCriminal">
                        <input class ="button18" type="submit" 
                               value="<fmt:message key = "label.add"  bundle="${rb}"/>"/>
                    </form> 
                </li>
                
                <li>
                    <form class ="" name="editWForm" method="GET" 
                          action="${pageContext.servletContext.contextPath}/controller">
                        <input type="hidden" name="command" value="redirect" />
                        <input type ="hidden" name ="op" value ="edit">
                        <input type ="hidden" name ="person" value ="isCriminal">
                        <input class ="button18" type="submit" 
                               value="<fmt:message key = "label.edit" bundle="${rb}"/>"/>
                    </form>
                </li>
            </ul>
                    
        <div class = "rightTitle">
            <fmt:message key = "name.missed" bundle="${rb}"/>
        </div>
        
            <ul>
                <li>
                    <form class ="" name="addMForm" method="GET" 
                        action="${pageContext.servletContext.contextPath}/controller">
                        <input type ="hidden" name ="command" value ="redirect">
                        <input type ="hidden" name ="op" value ="create">
                        <input type ="hidden" name ="person" value ="isMissed">
                        <input class ="button18" type="submit" 
                               value="<fmt:message key = "label.add"  bundle="${rb}"/>"/>
                  </form>
                </li>
                <li>
                    <form class ="" name="editMForm" method="GET" 
                        action="${pageContext.servletContext.contextPath}/controller">
                      <input type="hidden" name="command" value="redirect" />
                      <input type ="hidden" name ="op" value ="edit">
                      <input type ="hidden" name ="person" value ="isMissed">
                      <input class ="button18" type="submit" 
                             value="<fmt:message key = "label.edit" bundle="${rb}"/>"/>
                  </form>
                </li>
            </ul>
                  
        <div class = "rightTitle">
            <fmt:message key = "name.info" bundle="${rb}"/>
        </div>

            <ul>
                <li>
                    <form class ="" name="infoWForm" method="GET" 
                        action="${pageContext.servletContext.contextPath}/controller">
                      <input type="hidden" name="command" value="infoWanted" />
                      <input class ="button18" type="submit" 
                             value="<fmt:message key = "label.wanted" bundle="${rb}"/>"/>
                  </form>
                </li>
                <li>
                    <form class ="" name="infoMForm" method="GET" 
                        action="${pageContext.servletContext.contextPath}/controller">
                      <input type="hidden" name="command" value="infoMissed" />
                      <input class ="button18" type="submit" 
                             value="<fmt:message key = "label.missed" bundle="${rb}"/>"/>
                  </form>
                </li>
            </ul>
                  
            <div class = "rightTitle" >
                <fmt:message key = "name.other" bundle="${rb}"/>
            </div>   
                <ul>
                    <li>
                        <form class ="" name="archiveForm" method="POST" 
                            action="${pageContext.servletContext.contextPath}/controller">
                          <input type="hidden" name="command" value="archive" />
                          <input class ="button18" type="submit" 
                                 value="<fmt:message key = "label.archive" bundle="${rb}"/>"/>
                      </form>
                    </li>
                </ul>
        </div>
        
    </body>
    
    
</html>
