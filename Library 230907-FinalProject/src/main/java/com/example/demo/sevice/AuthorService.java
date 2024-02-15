package com.example.demo.sevice;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    @Autowired
    public AuthorService (AuthorRepository authorRepository, BookRepository bookRepository){
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }
    public Author getAuthorById(Long authorId){
        return authorRepository.findById(authorId).orElse(null);
    }

    public Boolean addAuthor(Author author){
        List<Author> authorList = authorRepository.findAll();
        if(authorList.stream().anyMatch(addauthor -> addauthor.getAuthorName().equals(author.getAuthorName()))) {
            return false;
        }
        authorRepository.saveAndFlush(author);
        return true;
    }

    public List<Author> getAuthorWithFilters(Map<String, Object> params) {
        if(params.size() == 0){
            return authorRepository.findAll();
        } else if (params.containsKey("author")) {
            return authorRepository.findByAuthorNameIgnoreCase((String) params.get("author"));
        }  else {
            return null;
        }
    }

    public Author updateAuthor (Long authorId, Author updatedAuthor) {
        return authorRepository.findById(authorId).map( author -> { author.setAuthorName(updatedAuthor.getAuthorName());
            author.setBooks(updatedAuthor.getBooks());
            return authorRepository.saveAndFlush(author);
        }).orElse(null);
    }

    public Boolean  deleteAuthor (Long authorId) {

        List<Author> authorList = authorRepository.findAll();
        // !!!!change to find by id;
        if(authorList.stream().anyMatch(book -> book.getAuthorId().equals(authorId))){
            bookRepository.deleteById(authorId);
            return true;
        }
        return false;
    }

}
