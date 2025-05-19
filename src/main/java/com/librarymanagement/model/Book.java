package com.librarymanagement.model;

public class Book {
    private Long id;
    private String title;
    private String summary;
    private String imageUrl;
    private Author author;

    // Default constructor
    public Book() {}

    // Constructor with parameters
    public Book(Long id, String title, String summary, String imageUrl, Author author) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.imageUrl = imageUrl;
        this.author = author;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
} 