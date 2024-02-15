package com.example.demo.controller;


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
public class BookController {
    private BookService bookService;
    private AuthorService authorService;


    @Autowired
    private BookController(BookService bookService, AuthorService authorService){
        this.bookService = bookService;
        this.authorService = authorService;
    }


    @GetMapping("/info") //http://localhost:8080/api/library/info
    public ResponseEntity<?> info(){
        return new ResponseEntity<>("eLibrary API is up and running!", HttpStatus.OK);
    }

    @GetMapping("/books/{bookId}") //http://localhost:8080/api/library/books/{bookId}
    public ResponseEntity<Object> getBookById(@PathVariable Long bookId){
        ResponseEntity<Object> response = null;
        try{
            Book book = bookService.getBookById(bookId);
            if(book != null){
                response = new ResponseEntity<>(book, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Book id: "+ bookId +" not found!"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping("/books") //http://localhost:8080/api/library/books
    public ResponseEntity<Object> getBookWithFilter(@RequestParam Map<String, Object> params){
        ResponseEntity<Object> response = null;
        try{
            List<Book> bookList = bookService.getBookWithFilters(params);
            if(bookList != null && bookList.size() > 0){
                response = new ResponseEntity<>(bookService.getBookWithFilters(params), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Book not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PostMapping("/books") //http://localhost:8080/api/library/books
    public ResponseEntity<Object> addBook(@Valid @RequestBody Book book){
        ResponseEntity<Object> response = null;
        try{
            boolean addedBook = bookService.addBook(book);
            if(!addedBook){
                response = new ResponseEntity<>(new CustomErrorResponse("404","Book already in library"), HttpStatus.BAD_REQUEST);
            }else {
                response = new ResponseEntity<>("Book added successfully", HttpStatus.CREATED);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PutMapping("/books/{bookId}")  //http://localhost:8080/api/library/books/{bookId}
    public ResponseEntity<Object> updateBook(@PathVariable Long bookId,
                                                @Valid @RequestBody Book book){
        ResponseEntity<Object> response = null;
        try{
            Book updatedBook = bookService.updateBook(bookId, book);
            if(updatedBook != null){
                response = new ResponseEntity<>(updatedBook, HttpStatus.OK);
            }else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Book id: " + bookId + " not found to update!") , HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
            }
        return response;
    }

    @PutMapping("/books/{bookId}/author/{authorId}")  //http://localhost:8080/api/library/books/{bookId}/author/{authorId}
    public ResponseEntity<Object> assignBookToAuthor(@PathVariable Long bookId,
                                                     @PathVariable Long authorId){
        ResponseEntity<Object> response = null;
        try{
            Book assignedBookToAuthor = bookService.assignBookToAuthor(bookId, authorId);
            if(assignedBookToAuthor != null){
                response = new ResponseEntity<>(assignedBookToAuthor, HttpStatus.OK);
            }else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Book id: " + bookId + ", or author id: " + authorId + " not found!") , HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping("/books/{bookId}")  //http://localhost:8080/api/library/books/{bookId}
    public ResponseEntity<Object> deleteBook(@PathVariable Long bookId) {
        ResponseEntity<Object> response = null;
        try {
            boolean deleted = bookService.deleteBook(bookId);
            if(!deleted) {
                response =  new ResponseEntity<>(new CustomErrorResponse(" 404", "Delete failed! Book ID: " + bookId + " not found."), HttpStatus.NOT_FOUND);
            } else {
                response =  new ResponseEntity<>("Book with ID: " + bookId + " was deleted successfully!", HttpStatus.OK);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }





//    @GetMapping("/books/bookCheckout/{bookId}") //http://localhost:8080/api/library/books/bookCheckout/{bookId}
//    public List<Book> getBookDetails(@PathVariable boolean bookCheckout,
//                               @PathVariable Long bookId){
//
//        return bookService.getBookDetails(bookCheckout, bookId);
//    }


    @GetMapping("/books/bookCheckout/{bookId}") //http://localhost:8080/api/library/books/bookCheckout/{bookId}
    public ResponseEntity<Object> getBookDetails(@PathVariable boolean bookCheckout,
                                     @PathVariable Long bookId){
        ResponseEntity<Object> response = null;
        try{
            List<Book> bookList = bookService.getBookDetails(bookCheckout, bookId);
            if(bookList != null && bookList.size() > 0){
                response = new ResponseEntity<>(bookService.getBookDetails(bookCheckout, bookId), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Book not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping("/books/cartCheckout/{cartId}") //http://localhost:8080/api/library/books/cartCheckout/{cartId}
    public ResponseEntity<Object> getBookDetails(@PathVariable Long cartId){
        ResponseEntity<Object> response = null;
        try{
            List<Book> bookList = bookService.getCartBookList(cartId);
            if(bookList != null && bookList.size() > 0){
                response = new ResponseEntity<>(bookService.getCartBookList(cartId), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Cart id " + cartId + " not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
