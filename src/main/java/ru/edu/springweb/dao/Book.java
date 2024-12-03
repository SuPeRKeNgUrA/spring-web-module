package ru.edu.springweb.dao;

public class Book {

    private Long id;
    private String name;
    private String authorName;
    private int numberOfPages;

    public Book() {
    }

    public Book(Long id, String name, String authorName, int numberOfPages) {
        this.id = id;
        this.name = name;
        this.authorName = authorName;
        this.numberOfPages = numberOfPages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
