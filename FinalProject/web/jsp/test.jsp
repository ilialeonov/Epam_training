<%-- 
    Document   : missed
    Created on : 30.09.2018, 13:33:10
    Author     : Администратор
--%>
<%@page session = "false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${requestScope.locale}" scope="request" />
<fmt:setBundle basename="resources.pagecontent" var="rb"  />
<fmt:setBundle basename="resources.test" var="test"  />


<!DOCTYPE html>
<html>
    <head>
        <style>
            li {
             list-style-type: none; /* Убираем маркеры */
            }
            .image {
                padding-right:  20px;
                float:left;
            }
        </style>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            <fmt:message key = "label.region" bundle="${rb}"/>
        </title>
        <style>
            rMenu {
                position: absolute;
                right: 500px;
            }
        </style>
    </head>
    <body>
        <jsp:include page = "header.jsp"/>
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
            
            </div>
            
            
                <div style = "margin-left: 50px; padding-bottom: 5px;">
                    <h2 style = "margin-left: 50px; padding-bottom: 5px;"><fmt:message key = "test.start" bundle="${test}"/>  </h2> 
                    <c:if test="${testError} not empty">
                    <h2 style = "margin-left: 50px; padding-bottom: 5px;"><fmt:message key = "${testError}" bundle="${test}"/>  </h2>
                    </c:if>
                <form method="post" action="${pageContext.servletContext.contextPath}/controller">
                    <input type="hidden" name="command" value="testRes" />
                    <h2><fmt:message key = "test.q1" bundle="${test}"/></h2>
                    <ul>
                        <li>
                        <input name="q1" value="1" type="radio"><fmt:message key = "test.q1a1" bundle="${test}"/>
                        </li>
                        <li>
                        <input name="q1" value="2" type="radio"><fmt:message key = "test.q1a2" bundle="${test}"/>
                        </li>
                        <li>
                        <input name="q1" value="3" type="radio"><fmt:message key = "test.q1a3" bundle="${test}"/>
                        </li>
                    </ul>
                        
                    <h2><fmt:message key = "test.q2" bundle="${test}"/></h2>
                    <ul>
                        <li>
                        <input name="q2" value="1" type="radio"><fmt:message key = "test.q2a1" bundle="${test}"/>
                        </li>
                        <li>
                        <input name="q2" value="2" type="radio"><fmt:message key = "test.q2a2" bundle="${test}"/>
                        </li>
                        <li>
                        <input name="q2" value="3" type="radio"><fmt:message key = "test.q2a3" bundle="${test}"/>
                        </li>
                    </ul>
                        
                    <h2><fmt:message key = "test.q3" bundle="${test}"/></h2>
                    <ul>
                        <li>
                        <input name="q3" value="1" type="radio"><fmt:message key = "test.q3a1" bundle="${test}"/>
                        </li>
                        <li>
                        <input name="q3" value="2" type="radio"><fmt:message key = "test.q3a2" bundle="${test}"/>
                        </li>
                        <li>
                        <input name="q3" value="3" type="radio"><fmt:message key = "test.q3a3" bundle="${test}"/>
                        </li>
                    </ul>
                        
                    <h2><fmt:message key = "test.q4" bundle="${test}"/></h2>
                    <ul>
                        <li>
                        <input name="q4" value="1" type="radio"><fmt:message key = "test.q4a1" bundle="${test}"/>
                        </li>
                        <li>
                        <input name="q4" value="2" type="radio"><fmt:message key = "test.q4a2" bundle="${test}"/>
                        </li>
                        <li>
                        <input name="q4" value="3" type="radio"><fmt:message key = "test.q4a3" bundle="${test}"/>
                        </li>
                    </ul>
                        
                     <h2><fmt:message key = "test.q5" bundle="${test}"/></h2>
                    <ul>
                        <li>
                        <input name="q5" value="1" type="radio"><fmt:message key = "test.q5a1" bundle="${test}"/>
                        </li>
                        <li>
                        <input name="q5" value="2" type="radio"><fmt:message key = "test.q5a2" bundle="${test}"/>
                        </li>
                        <li>
                        <input name="q5" value="3" type="radio"><fmt:message key = "test.q5a3" bundle="${test}"/>
                        </li>
                    </ul>   
                        
                    <h2><fmt:message key = "test.q6" bundle="${test}"/></h2>
                    <ul>
                        <li>
                        <input name="q6" value="1" type="radio"><fmt:message key = "test.q6a1" bundle="${test}"/>
                        </li>
                        <li>
                        <input name="q6" value="2" type="radio"><fmt:message key = "test.q6a2" bundle="${test}"/>
                        </li>
                        <li>
                        <input name="q6" value="3" type="radio"><fmt:message key = "test.q6a3" bundle="${test}"/>
                        </li>
                    </ul>
                        
                    <h2><fmt:message key = "test.q7" bundle="${test}"/></h2>
                    <ul>
                        <li>
                        <input name="q7" value="1" type="radio"><fmt:message key = "test.q7a1" bundle="${test}"/>
                        </li>
                        <li>
                        <input name="q7" value="2" type="radio"><fmt:message key = "test.q7a2" bundle="${test}"/>
                        </li>
                        <li>
                        <input name="q7" value="3" type="radio"><fmt:message key = "test.q7a3" bundle="${test}"/>
                        </li>
                    </ul>
                    <p><input class ="button18" type="submit" value="<fmt:message key = "label.send" bundle="${rb}"/>"></p>
                </form>
            <br/>
            <br/>
            
        </div>
        </div>
        <jsp:include page = "footer.jsp"/>
    </body>
</html>
