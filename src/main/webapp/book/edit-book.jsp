<%@ page import = "pgno130.obms.book.Book" %>
<%@ page contentType = "text/html;charset=UTF-8" %>
<!doctype html>
<html lang = "en">
    <head>
        <meta charset = "utf-8">
        <meta name = "viewport" content = "width=device-width, initial-scale=1">
        <link rel = "shortcut icon" type = "image/x-icon"
              href = "https://i.pinimg.com/736x/f9/90/35/f99035ed201d5cd6f927fcbba5102ad5.jpg">
        <title>Online Bookstore Management System</title>
        <link href = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel = "stylesheet"
              integrity = "sha384-SgOJa3DmI69IUzQ2PVdRZhwQ+dy64/BUtbMJw1MZ8t5HZApcHrRKUc4W0kG879m7"
              crossorigin = "anonymous">
    </head>
    <body>
        <%
            Book book = (Book) request.getAttribute("book");
        %>

        <div class = "container">
            <br><br>
            <div class = "text-center">
                <h1>Online Bookstore Management System</h1>
            </div>
            <br><br>
            <div class = "row">
                <form action = "${pageContext.request.contextPath}/book/update-book" method = "POST">
                    <%
                        if (book != null) {
                            out.println("<input type = \"hidden\" name=\"id\" value=\"" + book.getId() + "\">");
                        }
                    %>
                    <div class = "mb-3">
                        <label for = "title" class = "form-label">Book Name</label>
                        <input type = "text" class = "form-control" id = "title" name = "title"
                               value = "<%= book == null ? "" : book.getTitle() %>">
                    </div>
                    <div class = "mb-3">
                        <label for = "author" class = "form-label">Book Author</label>
                        <input type = "text" class = "form-control" id = "author" name = "author"
                               value = "<%= book == null ? "" : book.getAuthor() %>">
                    </div>
                    <div class = "mb-3">
                        <label for = "description" class = "form-label">Description</label>
                        <input type = "text" class = "form-control" id = "description" name = "description"
                               value = "<%= book == null ? "" : book.getDescription() %>">
                    </div>
                    <div class = "mb-3">
                        <label for = "image" class = "form-label">Image URL</label>
                        <input type = "text" class = "form-control" id = "image" name = "image"
                               value = "<%= book == null ? "" : book.getImage() %>">
                    </div>
                    <div class = "mb-3">
                        <label for = "price" class = "form-label">Book Price</label>
                        <input type = "number" class = "form-control" id = "price" name = "price"
                               value = "<%= book == null ? "" : book.getPrice() %>">
                    </div>
                    <div class = "mb-3">
                        <label for = "rating" class = "form-label">Book Rating (out of 10)</label>
                        <input type = "number" class = "form-control" id = "rating" name = "rating"
                               value = "<%= book == null ? "" : book.getRating() %>">
                    </div>
                    <div class = "mb-3">
                        <button type = "submit" class = "btn btn-primary mb-3">
                            <%=book == null ? "Add" : "Update"%> Book
                        </button>
                    </div>
                </form>

            </div>
        </div>

        <script src = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
                integrity = "sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
                crossorigin = "anonymous"></script>
    </body>
</html>
