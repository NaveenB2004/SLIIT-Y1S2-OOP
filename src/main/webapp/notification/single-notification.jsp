<%@ page import = "java.util.List" %>
<%@ page import = "pgno130.obms.notification.Notification" %>
<%@ page contentType = "text/html;charset=UTF-8" language = "java" %>
<!doctype html>
<html lang = "en">
    <head>
        <meta charset = "utf-8">
        <meta name = "viewport" content = "width=device-width, initial-scale=1">
        <title>Bootstrap demo</title>
        <link href = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel = "stylesheet"
              integrity = "sha384-SgOJa3DmI69IUzQ2PVdRZhwQ+dy64/BUtbMJw1MZ8t5HZApcHrRKUc4W0kG879m7"
              crossorigin = "anonymous">
    </head>
    <body>
        <div class = "container text-center">
            <div class = "row">
                <div class = "col align-self-center">
                    <%
                        Notification notification = (Notification) request.getAttribute("notification");
                        out.println(notification.getId());
                    %>
                </div>
            </div>
        </div>

        <div class = "container">
            <div class = "row">
                <div class = "col align-self-end">
                    <%
                        boolean isAdmin = (boolean) request.getAttribute("isAdmin");
                        if (isAdmin) {
                            out.println("<a href=\"notification/update-notification/" + notification.getId() + "\">Edit</a>");
                            out.println("<a href=\"notification/delete-notification/" + notification.getId() + "\">Edit</a>");
                        }
                    %>
                </div>
            </div>
        </div>

        <script src = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
                integrity = "sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
                crossorigin = "anonymous"></script>
    </body>
</html>
