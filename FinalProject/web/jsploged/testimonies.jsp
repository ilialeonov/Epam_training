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
            <c:choose>
                <c:when  test="${person == 'isCriminal'}">
                 <fmt:message key = "label.wanted" bundle="${rb}"/>
                 </c:when>
                 <c:otherwise>
                     <fmt:message key = "label.missed" bundle="${rb}"/>
                 </c:otherwise>
            </c:choose>
        
        </title>
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
            
            </div>
            <div>
                <c:choose >
                <c:when test = "${person == 'isCriminal'}">
                    <div style="padding-bottom:5px;">
                        <h1 class = "title"><fmt:message key = "testimonies.wanted" bundle="${rb}"/> ${region}</h1>
                    </div>
                </c:when>
                <c:when test = "${person == 'isMissed'}">
                    <div style="padding-bottom:5px;">
                        <h1 class = "title"><fmt:message key = "testimonies.missed" bundle="${rb}"/> ${region}</h1>
                    </div>
                </c:when>
                    <c:otherwise>
                        <div style="padding-bottom:5px;">
                        <h1 class = "title"><fmt:message key = "testimonies.archive" bundle="${rb}"/> ${region}</h1>
                    </div>
                    </c:otherwise>
            </c:choose>
                
                
                <ul>
                    <c:forEach items="${list}" var="item">
                        <li>
                            <div style ="overflow:auto;">
                                <img class ="image" width="300px" alt="img" 
                                    src="data:image/jpeg;base64,${item.person.base64image}"/>
                                <div style = "left: 50px; bottom: 100px;">
                                    <fmt:message key = "create.id" bundle="${rb}"/>:${item.person.id}<br>
                                    <fmt:message key = "create.name" bundle="${rb}"/>:${item.person.name}<br>
                                    <fmt:message key = "create.panname" bundle="${rb}"/>:${item.person.panname}<br>
                                    <fmt:message key = "create.age" bundle="${rb}"/>:${item.person.age}<br>
                                    <fmt:message key = "create.birthReg" bundle="${rb}"/>:${item.person.birthPlace}<br>
                                    <fmt:message key = "create.lastSeenReg" bundle="${rb}"/>:${item.person.lastPlace}<br>
                                    <fmt:message key = "create.award" bundle="${rb}"/>:
                                    <fmt:formatNumber value="${item.person.award}" type = "number"/> $<br>
                                    
                                </div>
                            </div>
                        </li>
                        <li>
                            <div style = "left: 50px; bottom: 100px; padding: 3px;">
                                    <h3><fmt:message key = "testify.testimony" bundle="${rb}"/>:</h3>
                                    <div style="width:900px;">${item.testimony}</div>
                                </div>
                        </li>
                        <li>
                           <div style = "left: 50px; bottom: 100px; padding: 3px;">
                               <h3><fmt:message key = "testify.points" bundle="${rb}"/>:</h3>
                                    ${item.points}
                                </div> 
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class = "nextprev">
                <form  name="previousPageForm" method="GET" 
                      action="${pageContext.servletContext.contextPath}/controller" >

                    <input type="hidden" name="command" value="testimony" />
                    <input type="hidden" name="pageNumber" value="${pageNumber}" />
                    <input type="hidden" name="direction" value="previous" />
                    <input type="hidden" name="person" value="${person}" />

                    <input class ="button18" type="submit" 
                           value="<fmt:message key = "label.previous" bundle="${rb}"/>"/>
                </form>
                </div>
                <div class = "nextprev">
                    
                <form  name="nextPageForm" method="GET" 
                      action="${pageContext.servletContext.contextPath}/controller" >

                    <input type="hidden" name="command" value="testimony" />
                    <input type="hidden" name="pageNumber" value="${pageNumber}" />
                    <input type="hidden" name="direction" value="next" />
                    <input type="hidden" name="person" value="${person}" />
                    <input class ="button18" type="submit" 
                           value="<fmt:message key = "label.next" bundle="${rb}"/>"/>
                </form>
            </div>
            <br/>
            <br/>
            
        </div>
        <jsp:include page = "../jsp/footer.jsp"/>
    </body>
</html>

