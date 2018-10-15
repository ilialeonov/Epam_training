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
                    <form class ="" name="testifyWForm" method="GET" 
                          action="${pageContext.servletContext.contextPath}/controller">
                        <input type="hidden" name="command" value="redirect" />
                        <input type ="hidden" name ="person" value ="isCriminal">
                        <input type ="hidden" name ="op" value ="testify">
                        <input class ="button18" type="submit" 
                               value="<fmt:message key = "label.testify"  bundle="${rb}"/>"/>
                    </form> 
                </li>
                
                <li>
                    <form class ="" name="testimoniesWForm" method="GET" 
                          action="${pageContext.servletContext.contextPath}/controller">
                        <input type="hidden" name="command" value="testimony" />
                        <input type ="hidden" name ="person" value ="isCriminal">
                        <input class ="button18" type="submit" 
                               value="<fmt:message key = "label.testimonies" bundle="${rb}"/>"/>
                    </form>
                </li>
            </ul>
                    
        <div class = "rightTitle">
            <fmt:message key = "name.missed" bundle="${rb}"/>
        </div>
        
            <ul>
                <li>
                    <form class ="" name="testifyMForm" method="GET" 
                        action="${pageContext.servletContext.contextPath}/controller">
                        <input type ="hidden" name ="command" value ="redirect">
                        <input type ="hidden" name ="op" value ="testify">
                        <input type ="hidden" name ="person" value ="isMissed">
                        <input class ="button18" type="submit" 
                               value="<fmt:message key = "label.testify"  bundle="${rb}"/>"/>
                  </form>
                </li>
                <li>
                    <form class ="" name="testimoniesMForm" method="GET" 
                        action="${pageContext.servletContext.contextPath}/controller">
                      <input type="hidden" name="command" value="testimony" />
                      <input type ="hidden" name ="person" value ="isMissed">
                      <input class ="button18" type="submit" 
                             value="<fmt:message key = "label.testimonies" bundle="${rb}"/>"/>
                  </form>
                </li>
            </ul>
                  
            <div class = "rightTitle">
            <fmt:message key = "name.other" bundle="${rb}"/>
        </div>

            <ul>
                <li>
                    <form class ="" name="archiveForm" method="POST" 
                        action="${pageContext.servletContext.contextPath}/controller">
                      <input type="hidden" name="command" value="testimonyArchive" />
                      <input class ="button18" type="submit" 
                             value="<fmt:message key = "label.archiveMessage" bundle="${rb}"/>"/>
                  </form>
                </li>
                <li>
                    <form class ="" name="purseForm" method="POST" 
                        action="${pageContext.servletContext.contextPath}/controller">
                      <input type="hidden" name="command" value="purse" />
                      <input class ="button18" type="submit" 
                             value="<fmt:message key = "label.purse" bundle="${rb}"/>"/>
                  </form>
                </li>
            </ul>
                  
            <div class = "rightTitle" >
                <fmt:message key = "name.changeLang" bundle="${rb}"/>
            </div>   
            
            
                <form class ="lang" name="changeLangForm" method="POST" 
                        action="${pageContext.servletContext.contextPath}/controller">
                      <input type="hidden" name="command" value="changeLanguage" />
                      <input type="hidden" name="chosenLocale" value="ru_RU" />
                      <input class ="button18" type="submit" 
                             value="<fmt:message key = "label.changeLangRu" bundle="${rb}"/>"/>
                </form>
                <form class ="lang" name="changeLangForm" method="POST" 
                        action="${pageContext.servletContext.contextPath}/controller">
                      <input type="hidden" name="command" value="changeLanguage" />
                      <input type="hidden" name="chosenLocale" value="en_EN" />
                      <input class ="button18" type="submit" 
                             value="<fmt:message key = "label.changeLangEng" bundle="${rb}"/>"/>
                </form>
        </div>
        
    </body>
    
    
</html>
