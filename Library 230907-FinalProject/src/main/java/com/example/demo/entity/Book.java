package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookId")
    private Long bookId;
    @Column(name = "Title")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z_0-9 ]{2,}$")
    private String title;
    @Column(name = "Genre")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]{2,}$")
    private String genre;
    @Column(name = "Description")
    @NotNull
    private String description;
    @Column(name = "ForSale")
    @NotNull
//    @Pattern(regexp = "^[0-9]*$")
    private int saleQuantity;
    @Column(name = "SalePrice")
    @NotNull
//    @Pattern(regexp = "^(\\+?\\d*\\.?\\d+)$")
    private double salePrice;
    @Column(name = "ForLend")
    @NotNull
//    @Pattern(regexp = "^[0-9]*$")
    private int lendQuantity;

    @ManyToMany
    @JoinTable (name = "books_authors",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")}
    )
    private Set<Author> authors = new HashSet<>();

    public Book() {
    }

    public Book(String title, String genre, String description, int saleQuantity, double salePrice,int lendQuantity,Set<Author> authors) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.saleQuantity = saleQuantity;
        this.salePrice = salePrice;
        this.lendQuantity = lendQuantity;
        this.authors = authors;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSaleQuantity() {
        return saleQuantity;
    }

    public void setSaleQuantity(int saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getLendQuantity() {
        return lendQuantity;
    }

    public void setLendQuantity(int lendQuantity) {
        this.lendQuantity = lendQuantity;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }


}
