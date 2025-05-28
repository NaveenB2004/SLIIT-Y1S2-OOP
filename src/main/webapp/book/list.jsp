<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Book List</title>
    <meta charset="UTF-8">
</head>
<body>
<h2>Books</h2>
<a href="/add">Add New Book</a>
<table border="1">
    <tr>
        <th>Title</th>
        <th>Category</th>
        <th>Price</th>
        <th>Rating</th>
        <th>Quantity</th>
        <th>Actions</th>
    </tr>
    <c:forEach items="${books}" var="book">
        <tr>
            <td>${book.title}</td>
            <td>${book.category}</td>
            <td>${book.price}</td>
            <td>${book.rating}</td>
            <td>${book.quantity}</td>
            <td>
                <a href="/edit/${book.id}">Edit</a> |
                <a href="/delete/${book.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
