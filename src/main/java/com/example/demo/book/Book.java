package com.example.demo.book;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Boolean isAvailable;

    @Column(nullable = false)
    private Date updatedAt;

    @Column(nullable = false)
    private Date createdAt;

    public Book(Long id, String title, String author, String category, Boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isAvailable = isAvailable;
        this.updatedAt = new Date();
        this.createdAt = new Date();
    }

    public Book(String title, String author, String category) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.isAvailable = true;
        this.updatedAt = new Date();
        this.createdAt = new Date();
    }

    public Book() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(category, book.category) && Objects.equals(isAvailable, book.isAvailable) && Objects.equals(updatedAt, book.updatedAt) && Objects.equals(createdAt, book.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, category, isAvailable, updatedAt, createdAt);
    }

    @Override
    public String toString() {
        return "Book{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", author='" + author + '\'' +
               ", category='" + category + '\'' +
               ", isAvailable=" + isAvailable +
               ", updatedAt=" + updatedAt +
               ", createdAt=" + createdAt +
               '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean available) {
        isAvailable = available;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
