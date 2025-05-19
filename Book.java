package com.librarymanagement;

public class Book {
    private Long id;
    private String title;
    private String summary;
    private Author author;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
}