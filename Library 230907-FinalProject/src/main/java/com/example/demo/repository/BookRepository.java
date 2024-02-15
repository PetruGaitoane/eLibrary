package com.example.demo.repository;

import com.example.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBookByTitleIgnoreCase(String title);
    List<Book> findBookByGenreIgnoreCase(String genre);



}
