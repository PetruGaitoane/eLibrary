package com.example.demo.repository;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByAuthorNameIgnoreCase(String authorName);

}
