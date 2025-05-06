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
        <br><br>
        <h1 class="container col assign-self-center text-center">Online Bookstore Management System</h1>
        <br><br>
        <div class = "container">
            <div class = "col align-self-center">
                <%
                    Notification notification = request.getAttribute("notification") == null ?
                            null : (Notification) request.getAttribute("notification");
                %>

                <form action = "${pageContext.request.contextPath}/notification/update-notification"
                      method = "post">
                    <%
                        if (notification != null) {
                            out.println("<input type = \"hidden\" name = \"id\" value = \"" + notification.getId() + "\">");
                            out.println("<input type = \"hidden\" name = \"timestamp\" value = \"" + notification.getTimestamp() + "\">");
                        }
                    %>
                    <div class = "mb-3">
                        <label for = "title" class = "form-label">Notification Title</label>
                        <input type = "text" class = "form-control" id = "title" name = "title"
                               value = "<%= notification == null ? "" : notification.getTitle() %>">
                    </div>
                    <div class = "mb-3">
                        <label for = "message" class = "form-label">Description</label>
                        <textarea class = "form-control" id = "message" name = "message"
                                  rows = "5"><%= notification == null ? "" : notification.getMessage() %></textarea>
                    </div>
                    <button type = "submit" class = "btn btn-primary d-grid gap-2 col-6 mx-auto">Submit</button>
                </form>
            </div>
        </div>

        <script src = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
                integrity = "sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
                crossorigin = "anonymous"></script>
    </body>
</html>
