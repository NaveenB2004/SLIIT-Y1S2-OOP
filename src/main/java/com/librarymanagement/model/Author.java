package com.librarymanagement.model;

import java.util.List;

public class Author {
    private String name;
    private String bio;
    private List<String> otherBooks;

    // Default constructor
    public Author() {}

    // Constructor with parameters
    public Author(String name, String bio, List<String> otherBooks) {
        this.name = name;
        this.bio = bio;
        this.otherBooks = otherBooks;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public List<String> getOtherBooks() { return otherBooks; }
    public void setOtherBooks(List<String> otherBooks) { this.otherBooks = otherBooks; }
} 