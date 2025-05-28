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
            Book[] books = (Book[]) request.getAttribute("books");
        %>

        <div class = "container">
            <br><br>
            <div class = "text-center">
                <h1>Online Bookstore Management System</h1>
            </div>
            <br><br>
            <div class = "d-grid gap-2">
                <a href = "${pageContext.request.contextPath}/book?sortBy=price"
                   class = "btn btn-primary">Sort By Price</a>
                <a href = "${pageContext.request.contextPath}/book?sortBy=rating"
                   class = "btn btn-primary">Sort By Rating</a>
            </div>
            <br><br>
            <div class = "row row-cols-4 justify-content-around">
                <%
                    for (Book book : books) {
                        out.println("<div class = \"card\" style = \"width: 18rem;\">");
                        out.println("<img src = \"" + book.getImage() + "\" class = \"card-img-top\" alt=\"book-image\">");
                        out.println("<div class = \"card-body\">");
                        out.println("<h5 class = \"card-title\">" + book.getTitle() + "</h5>");
                        out.println("<p>Author: " + book.getAuthor() + "</p>");
                        out.println("<p>Description: " + book.getDescription() + "</p>");
                        out.println("<hr>");
                        out.println("<p>Price: $" + book.getPrice() + "</p>");
                        out.println("<p>Rating: " + book.getRating() + "/10</p>");
                        out.println("<a href = \"" + request.getContextPath() + "/book/get-book?id=" + book.getId() +
                                "&forEdit=false\" class = \"btn btn-primary\">View</a>");
                        out.println("</div>");
                        out.println("</div>");
                    }
                %>
            </div>
            <br><br><br>
            <div class = "d-grid gap-2">
                <a href = "${pageContext.request.contextPath}/book/get-book?id=0&forEdit=true"
                   class = "btn btn-primary">Add New Book</a>
            </div>
            <br><br>
        </div>

        <script src = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"
                integrity = "sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq"
                crossorigin = "anonymous"></script>
    </body>
</html>
