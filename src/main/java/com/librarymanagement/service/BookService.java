package com.librarymanagement.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagement.model.Book;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private static final String FILE_PATH = "books.json";
    private final ObjectMapper objectMapper;

    public BookService() {
        this.objectMapper = new ObjectMapper();
    }

    public List<Book> getAllBooks() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, new TypeReference<List<Book>>(){});
        } catch (IOException e) {
            throw new RuntimeException("Error reading books file", e);
        }
    }

    public Book getBookById(Long id) {
        List<Book> books = getAllBooks();
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book addBook(Book book) {
        List<Book> books = getAllBooks();
        book.setId(generateId(books));
        if (book.getImageUrl() == null || book.getImageUrl().trim().isEmpty()) {
            book.setImageUrl(null);
        }
        books.add(book);
        saveBooks(books);
        return book;
    }

    public Book updateBook(Long id, Book updatedBook) {
        List<Book> books = getAllBooks();
        Optional<Book> existingBook = books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setTitle(updatedBook.getTitle());
            book.setSummary(updatedBook.getSummary());
            book.setAuthor(updatedBook.getAuthor());
            if (updatedBook.getImageUrl() == null || updatedBook.getImageUrl().trim().isEmpty()) {
                book.setImageUrl(null);
            } else {
                book.setImageUrl(updatedBook.getImageUrl().trim());
            }
            saveBooks(books);
            return book;
        }
        throw new RuntimeException("Book not found");
    }

    public void deleteBook(Long id) {
        List<Book> books = getAllBooks();
        books.removeIf(book -> book.getId().equals(id));
        saveBooks(books);
    }

    private void saveBooks(List<Book> books) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), books);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to books file", e);
        }
    }

    private Long generateId(List<Book> books) {
        return books.stream()
                .map(Book::getId)
                .max(Long::compareTo)
                .map(id -> id + 1)
                .orElse(1L);
    }
} 