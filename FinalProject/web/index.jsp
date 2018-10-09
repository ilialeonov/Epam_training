<%@page session="false" contentType="text/html; charset=UTF-8"%>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <input type="hidden" name="command" value="main"/>
        <jsp:forward page="/controller?command=main"/>    
    </body>
</html>
