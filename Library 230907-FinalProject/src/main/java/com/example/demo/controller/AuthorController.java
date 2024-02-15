package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.helpers.CustomErrorResponse;
import com.example.demo.sevice.AuthorService;
import com.example.demo.sevice.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/library") //http://localhost:8080/api/library

public class AuthorController {

    private BookService bookService;
    private AuthorService authorService;


    @Autowired
    private AuthorController(BookService bookService, AuthorService authorService){
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/authors/{authorId}") //http://localhost:8080/api/library/authors/{authorId}
    public ResponseEntity<Object> getAuthorsById(@PathVariable Long authorId){
        ResponseEntity<Object> response = null;
        try{
            Author author = authorService.getAuthorById(authorId);
            if(author != null){
                response = new ResponseEntity<>(author, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Author id: "+ authorId +"not found!"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }
    @GetMapping("/authors") //http://localhost:8080/api/library/authors
    public ResponseEntity<Object> getBookAuthorWithFilter(@RequestParam Map<String, Object> params){
        ResponseEntity<Object> response = null;
        try{
            List<Author> authorList = authorService.getAuthorWithFilters(params);
            if(authorList != null && authorList.size() > 0){
                response = new ResponseEntity<>(authorService.getAuthorWithFilters(params), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Author not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PostMapping("/authors") //http://localhost:8080/api/library/authors
    public ResponseEntity<Object> addAuthor(@Valid @RequestBody Author author){
        ResponseEntity<Object> response = null;
        try{
            boolean addedAuthor = authorService.addAuthor(author);
            if(!addedAuthor){
                response = new ResponseEntity<>(new CustomErrorResponse("404","Author already in library"), HttpStatus.BAD_REQUEST);
            }else {
                response = new ResponseEntity<>("Author added successfully", HttpStatus.CREATED);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PutMapping("/authors/{authorId}")  //http://localhost:8080/api/library/authors/{authorId}
    public ResponseEntity<Object> updateAuthor(@PathVariable Long authorId,
                                             @Valid @RequestBody Author author){
        ResponseEntity<Object> response = null;
        try{
            Author updatedAuthor = authorService.updateAuthor(authorId, author);
            if(updatedAuthor != null){
                response = new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
            }else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Author id: " + authorId + " not found to update!") , HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }


    @DeleteMapping("/authors/{authorId}")  //http://localhost:8080/api/library/authors/{authorId}
    public ResponseEntity<Object> deleteBook(@PathVariable Long authorId) {
        ResponseEntity<Object> response = null;
        try {
            boolean deleted = authorService.deleteAuthor(authorId);
            if(!deleted) {
                response =  new ResponseEntity<>(new CustomErrorResponse(" 404", "Delete failed! Book ID: " + authorId + " not found."), HttpStatus.NOT_FOUND);
            } else {
                response =  new ResponseEntity<>("Book with ID: " + authorId + " was deleted successfully!", HttpStatus.OK);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }


}
