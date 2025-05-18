<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Book Form</title>
    <meta charset="UTF-8">
</head>
<body>
<h2>Book Form</h2>
<form action="/save" method="post">
    <input type="hidden" name="id" value="${book.id}" />

    Title: <input type="text" name="title" value="${book.title}" required /><br/>
    Author: <input type="text" name="author" value="${book.author}" required /><br/>

    <input type="submit" value="Save" />
</form>
<br/>
<a href="/">Back to list</a>
</body>
</html>
