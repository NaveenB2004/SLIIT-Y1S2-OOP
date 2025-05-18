<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>LIA BOOKS</title>
    <style>
        body {
            margin: 0;
            font-family: Times New Roman;
            background: url('https://th.bing.com/th/id/OIP.LlZt_wpT08TqW_wMCW6-4wHaHa?cb=iwc1&pid=ImgDet&w=182&h=182&c=7');
            color: white;
        }

        header {
            position: fixed;
            top: 0;
            width: 100%;
            height: 80px;
            background: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)),
            url('https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?auto=format&fit=crop&w=1500&q=80') center/cover no-repeat;
            color: #ffffff;
            text-align: center;
            padding: 30px 20px 20px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.7);
            z-index: 1000;
        }

        .logo {
            font-size: 45px;
            font-weight: bold;
            color: #00ffcc;
            text-shadow: 2px 2px 6px rgba(0, 255, 255, 0.8);
            margin-bottom: 10px;
        }

        .nav-links {
            margin-bottom: 10px;
        }

        .nav-links a {
            color: #fff;
            margin: 0 20px;
            text-decoration: none;
            font-size: 20px;
            font-weight: bold;
        }

        .nav-links a:hover {
            color: #00ffcc;
        }

        .top-right {
            position: absolute;
            top: 20px;
            right: 20px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .search-bar {
            background: #222;
            border: none;
            padding: 8px;
            width: 200px;
            color: white;
            border-radius: 5px;
        }

        .top-right a {
            color: #e5e4e2;
            text-decoration: none;
            font-size: 14px;
        }

        .main {
            display: flex;
            padding: 20px;
            gap: 20px;
            flex-wrap: wrap;
        }

        .sidebar {
            position: fixed;
            width: 180px;
            height: 510px;
            background-color: #1f1f1f;
            padding: 15px;
            border-radius: 10px;
            font-size: 14px;
            margin-top: 130px;
        }

        .sidebar h3 {
            margin-top: 0;
            font-size: 16px;
        }

        .sidebar label,
        select,
        input {
            display: block;
            margin-bottom: 10px;
            width: 100%;
            font-size: 14px;
        }

        .sidebar button {
            background-color: #8a2be2;
            border: none;
            padding: 8px;
            width: 100%;
            color: white;
            border-radius: 5px;
            margin-bottom: 10px;
            font-size: 14px;
            cursor: pointer;
        }

        .books {
            margin-top: 150px;
            margin-left: 250px;
            flex: 1;
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
        }

        .book-card {
            background-color: #1f1f1f;
            width: 220px;
            height: 520px;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 0 10px rgba(0, 0, 0, 1);
        }

        .book-card img {
            width: 100%;
            height: 300px;
            object-fit: cover;
        }

        .book-info {
            padding: 10px;
            font-size: 16px;
        }

        .book-info h4 {
            margin: 0;
        }

        .price {
            color: #00ff99;
            font-weight: bold;
        }

        .rating {
            color: gold;
        }

        .stock {
            color: #00cc66;
            font-weight: bold;
        }

        .view-btn {
            display: block;
            background-color: #8a2be2;
            color: white;
            text-align: center;
            padding: 10px;
            text-decoration: none;
            border-radius: 5px;
            margin: 10px;
        }

        /* Add Book Form Styling */
        #addBookForm {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: #2b2b2b;
            border-radius: 15px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.7);
            padding: 25px;
            width: 320px;
            z-index: 2000;
            display: none;
            color: white;
            font-family: 'Segoe UI', sans-serif;
        }

        #addBookForm h3 {
            text-align: center;
            color: #00ffcc;
            margin-bottom: 20px;
        }

        #addBookForm label {
            margin-top: 10px;
            font-size: 14px;
            color: #ccc;
        }

        #addBookForm input, #addBookForm select {
            width: 100%;
            padding: 10px;
            margin-top: 4px;
            border: none;
            border-radius: 8px;
            background-color: #444;
            color: white;
            font-size: 14px;
        }

        #addBookForm input:focus, #addBookForm select:focus {
            outline: 2px solid #00ffcc;
        }

        #addBookForm button {
            width: 48%;
            padding: 10px;
            border: none;
            margin-top: 15px;
            border-radius: 8px;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
        }

        #addBookForm button:first-of-type {
            background-color: #00b386;
            color: white;
            margin-right: 4%;
        }

        #addBookForm button:last-of-type {
            background-color: #cc0000;
            color: white;
        }

        @media (max-width: 768px) {
            .main {
                flex-direction: column;
            }

            .books {
                margin-left: 0;
            }

            .sidebar {
                width: 100%;
            }
        }
    </style>
</head>
<body>

<header>
    <div class="logo">LIA BOOKS</div>
    <div class="nav-links">
        <a href="#">Home</a>
        <a href="#">Categories</a>
        <a href="#">Contact Us</a>
    </div>
    <div class="top-right">
        <input class="search-bar" type="text" id="searchInput" placeholder="Search for books..." oninput="searchBooks()">
        <a href="#">Login</a>
        <a href="#">Register</a>
    </div>
</header>

<div class="main">
    <div class="sidebar">
        <h3>Filters</h3>
        <label>Genre</label>
        <select id="genreFilter">
            <option>All Genres</option>
            <option>Fiction</option>
            <option>Biography</option>
            <option>Non-Fiction</option>
            <option>Self-Help</option>
        </select>

        <label>Book Type</label>
        <select id="typeFilter">
            <option>All Types</option>
            <option>Physical</option>
            <option>E-Book</option>
        </select>

        <label>Price Range</label>
        <input type="number" id="minPrice" placeholder="Min Price">
        <input type="number" id="maxPrice" placeholder="Max Price">

        <label>Sort By</label>
        <select id="sortBy">
            <option value="title-asc">Title (A-Z)</option>
            <option value="title-desc">Title (Z-A)</option>
            <option value="price-asc">Price (Low-High)</option>
            <option value="price-desc">Price (High-Low)</option>
        </select>

        <button onclick="applyFilters()">Apply Filters</button>
        <button onclick="resetFilters()">Reset Filters</button>
        <button style="background-color: maroon;" onclick="openAddBookForm()">Add Book</button>
        <button style="background-color: maroon;" onclick="removeBooks()">Remove Book</button>
    </div>

    <div class="books" id="bookContainer"></div>

    <!-- Add Book Form -->
    <div id="addBookForm">
        <h3>Add a New Book</h3>

        <label>Title</label>
        <input type="text" id="bookTitle" required>

        <label>Category</label>
        <input type="text" id="bookCategory" required>

        <label>Price</label>
        <input type="number" id="bookPrice" required>

        <label>Type</label>
        <select id="bookType" required>
            <option value="">-- Select --</option>
            <option value="Physical">Physical</option>
            <option value="E-Book">E-Book</option>
        </select>

        <label>Rating (1-5 stars)</label>
        <input type="number" id="bookRating" min="1" max="5" required>

        <label>In Stock?</label>
        <select id="bookStock" required>
            <option value="true">Yes</option>
            <option value="false">No</option>
        </select>

        <label>Image URL</label>
        <input type="url" id="bookImg" required>

        <div style="display: flex; justify-content: space-between;">
            <button onclick="saveBook()">Save</button>
            <button onclick="closeAddBookForm()">Cancel</button>
        </div>
    </div>
</div>

<script>
    const books = [
        { title: "Dune", category: "Fiction", img: "https://m.media-amazon.com/images/I/91zbi9M+mKL.jpg", rating: "⭐⭐⭐⭐⭐", price: 9.00, type: "Physical", inStock: true },
        { title: "Educated", category: "Biography", img: "https://m.media-amazon.com/images/I/81WojUxbbFL.jpg", rating: "⭐⭐⭐⭐⭐", price: 10.20, type: "E-Book", inStock: true },
        { title: "Steve Jobs", category: "Biography", img: "https://m.media-amazon.com/images/I/81VStYnDGrL.jpg", rating: "⭐⭐⭐⭐", price: 18.00, type: "Physical", inStock: true }
    ];

    function renderBooks(bookList) {
        const container = document.getElementById("bookContainer");
        container.innerHTML = "";
        bookList.forEach(book => {
            const div = document.createElement("div");
            div.className = "book-card";
            div.innerHTML = `
        <img src="${book.img}" alt="${book.title}">
        <div class="book-info">
          <h4>${book.title}</h4>
          <p>Category: ${book.category}</p>
          <p class="price">$${book.price}</p>
          <p class="rating">${book.rating}</p>
          <p class="stock">${book.inStock ? "In Stock" : "Out of Stock"}</p>
          <a href="#" class="view-btn">View Details</a>
        </div>
      `;
            container.appendChild(div);
        });
    }

    function searchBooks() {
        const query = document.getElementById("searchInput").value.toLowerCase();
        const filtered = books.filter(book => book.title.toLowerCase().includes(query));
        renderBooks(filtered);
    }

    function openAddBookForm() {
        document.getElementById("addBookForm").style.display = "block";
    }

    function closeAddBookForm() {
        document.getElementById("addBookForm").style.display = "none";
    }

    function saveBook() {
        const title = document.getElementById("bookTitle").value.trim();
        const category = document.getElementById("bookCategory").value.trim();
        const price = parseFloat(document.getElementById("bookPrice").value);
        const type = document.getElementById("bookType").value;
        const ratingStars = parseInt(document.getElementById("bookRating").value);
        const stock = document.getElementById("bookStock").value === "true";
        const img = document.getElementById("bookImg").value.trim();

        if (!title || !category || !price || !type || !ratingStars || !img) {
            alert("⚠️ Please fill out all fields correctly.");
            return;
        }

        const rating = "⭐".repeat(Math.min(ratingStars, 5));
        books.push({ title, category, price, type, img, rating, inStock: stock });
        renderBooks(books);
        closeAddBookForm();

        // Clear form
        document.getElementById("bookTitle").value = "";
        document.getElementById("bookCategory").value = "";
        document.getElementById("bookPrice").value = "";
        document.getElementById("bookType").value = "";
        document.getElementById("bookRating").value = "";
        document.getElementById("bookStock").value = "true";
        document.getElementById("bookImg").value = "";
    }

    function removeBooks() {
        const title = prompt("Enter the title of the book to remove:");
        const index = books.findIndex(book => book.title.toLowerCase() === title.toLowerCase());
        if (index !== -1) {
            books.splice(index, 1);
            alert("✅ Book removed successfully.");
        } else {
            alert("❌ Book not found.");
        }
        renderBooks(books);
    }

    function applyFilters() {
        const genre = document.getElementById("genreFilter").value;
        const type = document.getElementById("typeFilter").value;
        const minPrice = parseFloat(document.getElementById("minPrice").value) || 0;
        const maxPrice = parseFloat(document.getElementById("maxPrice").value) || Infinity;
        const filtered = books.filter(book =>
            (genre === "All Genres" || book.category === genre) &&
            (type === "All Types" || book.type === type) &&
            book.price >= minPrice &&
            book.price <= maxPrice
        );
        renderBooks(filtered);
    }

    function resetFilters() {
        document.getElementById("genreFilter").value = "All Genres";
        document.getElementById("typeFilter").value = "All Types";
        document.getElementById("minPrice").value = "";
        document.getElementById("maxPrice").value = "";
        document.getElementById("sortBy").value = "title-asc";
        renderBooks(books);
    }

    window.onload = () => renderBooks(books);
</script>
</body>
</html>
