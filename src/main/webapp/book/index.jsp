<%@ page contentType = "text/html;charset=UTF-8" language = "java" %>
<html>
    <head>
        <title>Hello JSP</title>
    </head>
    <body>
        <h1>Hello from JSP!</h1>
        <%
            int[] x = {1, 2, 3, 4, 5};
            for (int i : x) {
                out.print(i + "");
            }
        %>
    </body>
</html>
