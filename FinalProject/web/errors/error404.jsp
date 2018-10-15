<%-- 
    Document   : missed
    Created on : 30.09.2018, 13:33:10
    Author     : Администратор
--%>
<%@page isErrorPage="true" session = "false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${requestScope.locale}" scope="request" />
<fmt:setBundle basename="resources.pagecontent" var="rb"  />

<!DOCTYPE html>
<html>
    <head>
        <link href="${pageContext.servletContext.contextPath}/css/style.css" rel="stylesheet" type="text/css">
        <style>
            li {
             list-style-type: none; /* Убираем маркеры */
            }
            .image {
                width: 100%;
                padding-right:  0px;
                padding-top: 100px;
            }
            .notF {
                color: white;
                position: relative;
                z-index: 2;
                margin-left: 500px;
                margin-top:-350px;
                font-size: 30px;
    font-family: Geneva, Arial, Helvetica, sans-serif;
            }
        </style>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            <fmt:message key = "label.error" bundle="${rb}"/>
        </title>
    </head>
    <body>
        <img class = "image" src ="${pageContext.servletContext.contextPath}/images/maiak_med.jpeg" alt = "maiak picture"/>
        <div class = "notF">
            <h1>
                <fmt:message key = "error404.first" bundle="${rb}"/> <br>
                <fmt:message key = "error404.second" bundle="${rb}"/>
                <a href="http://localhost:8080/FinalProject/">
                    <fmt:message key = "error404.third" bundle="${rb}"/></a> 
                    <fmt:message key = "error404.fourth" bundle="${rb}"/>
            </h1>
            
        </div>
    </body>
</html>
