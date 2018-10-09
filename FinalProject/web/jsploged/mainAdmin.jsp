<%-- 
    Document   : main
    Created on : 16.09.2018, 21:26:15
    Author     : Администратор
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <div class="title"><h1>This is admin main page</h1></div>
            <div class = "rightMenu">
            <jsp:include page="/jsploged/rightPatternAdmin.jsp"/>
            </div>
        </div>
        <jsp:include page = "../jsp/footer.jsp"/>
    </body>
</html>