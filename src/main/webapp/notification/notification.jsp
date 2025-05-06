<%@ page import = "java.util.List" %>
<%@ page import = "pgno130.obms.notification.Notification" %>
<%@ page contentType = "text/html;charset=UTF-8" language = "java" %>
<!doctype html>
<html lang = "en">
    <head>
        <meta charset = "utf-8">
        <meta name = "viewport" content = "width=device-width, initial-scale=1">
        <link rel="shortcut icon" type="image/x-icon" href="https://i.pinimg.com/736x/f9/90/35/f99035ed201d5cd6f927fcbba5102ad5.jpg">
        <title>Online Bookstore Management System</title>
        <link href = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel = "stylesheet"
              integrity = "sha384-SgOJa3DmI69IUzQ2PVdRZhwQ+dy64/BUtbMJw1MZ8t5HZApcHrRKUc4W0kG879m7"
              crossorigin = "anonymous">
    </head>
    <body>
        <%
            Boolean isSuccess = request.getAttribute("isSuccess") == null ? null : (Boolean) request.getAttribute("isSuccess");
            if (isSuccess != null) {
                out.println(isSuccess);
            }
        %>
        <br><br>
        <h1 class="container col assign-self-center text-center">Online Bookstore Management System</h1>
        <br><br>
        <div class = "container text-center">
            <div class = "row">
                <div class = "col align-self-center">
                    <table class = "table table-hover">
                        <thead>
                        <tr>
                            <th scope = "col">#</th>
                            <th scope = "col">Title</th>
                            <th scope = "col">Timestamp</th>
                            <th scope = "col">Operations</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            List<Notification> notifications = (List<Notification>) request.getAttribute("notifications");
                            for (Notification notification : notifications) {
                                out.println("<tr>");
                                out.println("<th scope = \"row\">" + notification.getId() + "</th>");
                                out.println("<td>" + notification.getTitle() + "</td>");
                                out.println("<td>" + notification.getTimestamp() + "</td>");
                                out.println("<td><a href=\"" + request.getContextPath() + "/notification/" + notification.getId() + "\">" +
                                        "<button type=\"button\" class=\"btn btn-primary btn-sm d-grid gap-2 col-6 mx-auto\">View</button>" +
                                        "</a></td>");
                                out.println("</tr>");
                            }
                        %>
                        </tbody>
                    </table>
                    <br>
                    <%
                        Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");
                        if (isAdmin) {
                            out.println("<a href=\"" + request.getContextPath() + "/notification/update-notification\">" +
                                    "<button type=\"button\" class=\"btn btn-primary\">Add New Notification</button></a>");
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
