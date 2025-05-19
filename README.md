# Library Book Management System

A simple library book management system built with Spring Boot and file-based storage. This project is designed for SLIIT Year 1 Semester 2 students, focusing on the Author Detail Management component.

## Features

- View book details including summaries and author information
- Admin mode for managing books (add, edit, delete)
- File-based storage using JSON
- Responsive Bootstrap UI
- RESTful API with Spring Boot

## Prerequisites

- Java 11 or higher
- Maven
- Web browser (Chrome/Firefox recommended)

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd library-management
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Open your browser and navigate to:
   ```
   http://localhost:8080
   ```

## Usage

### User Mode
- View the list of available books
- Click on a book to view its details
- Read book summaries and author information

### Admin Mode
1. Click "Admin Mode" in the navigation bar
2. Add new books using the form at the bottom
3. Edit existing books by clicking a book and using the "Edit" button
4. Delete books using the "Delete" button in the book details modal

## File Storage

The application uses `books.json` in the project root directory to store book and author data. The file is automatically created if it doesn't exist.

## Project Structure

```
library-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── librarymanagement/
│   │   │           ├── LibraryManagementApplication.java
│   │   │           ├── Book.java
│   │   │           ├── Author.java
│   │   │           ├── BookController.java
│   │   │           └── BookService.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html
│   │       │   └── js/
│   │       │       └── main.js
│   │       └── application.properties
├── books.json
├── pom.xml
└── README.md
```

## API Endpoints

- `GET /api/books` - Get all books
- `GET /api/books/{id}` - Get a specific book
- `POST /api/books` - Add a new book
- `PUT /api/books/{id}` - Update a book
- `DELETE /api/books/{id}` - Delete a book

## Contributing

This is a student project for SLIIT Year 1 Semester 2. Feel free to fork and modify for your own use.

## License

This project is open source and available under the [MIT License](LICENSE). 