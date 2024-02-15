package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")


public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authorId")
    private Long authorId;
    @Column(name = "Author")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]{2,}$")
    private String authorName;
    @JsonIgnore
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books  = new HashSet<>();

    public Author() {
    }

    public Author(String authorName, Set<Book> books) {
        this.authorName = authorName;
        this.books = books;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
