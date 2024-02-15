package com.example.demo.sevice;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.entity.Cart;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private CartRepository cartRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, CartRepository cartRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.cartRepository = cartRepository;
    }


    public Book getBookById(Long bookId){
        return bookRepository.findById(bookId).orElse(null);
    }
    public Boolean addBook(Book book){
        List<Book> bookList = bookRepository.findAll();
        if(bookList.stream().anyMatch(addbook -> addbook.getTitle().equals(book.getTitle()) &&
                addbook.getDescription().equals(book.getDescription()))) {
            return false;
        }
        bookRepository.saveAndFlush(book);
        return true;
    }

    public List<Book> getBookWithFilters(Map<String, Object> params) {
        if(params.size() == 0){
            return bookRepository.findAll();
        } else if(params.containsKey("title")){
            return bookRepository.findBookByTitleIgnoreCase((String) params.get("title"));
        } else if (params.containsKey("genre")) {
            return bookRepository.findBookByGenreIgnoreCase((String) params.get("genre"));
        } else {
            return null;
        }
    }
    public Book updateBook (Long bookId, Book updatedBook) {
        return bookRepository.findById(bookId).map( book -> { book.setTitle(updatedBook.getTitle());
                                                            book.setAuthors(updatedBook.getAuthors());
                                                            book.setGenre(updatedBook.getGenre());
                                                            book.setDescription(updatedBook.getDescription());
                                                            book.setSaleQuantity(updatedBook.getSaleQuantity());
                                                            book.setSalePrice(updatedBook.getSalePrice());
                                                            book.setLendQuantity(updatedBook.getLendQuantity());
                                                            return bookRepository.saveAndFlush(book);
        }).orElse(null);
    }

    public Boolean  deleteBook (Long bookId) {

        List<Book> bookList = bookRepository.findAll();

        if(bookList.stream().anyMatch(book -> book.getBookId().equals(bookId))){
            bookRepository.deleteById(bookId);
            return true;
        }
        return false;
    }

    public Book assignBookToAuthor(Long bookId, Long authorId) {
        Set<Author> authors = null;
        Book book = bookRepository.findById(bookId).orElse(null);
        Author author = authorRepository.findById(authorId).orElse(null);
        authors = book.getAuthors();
        authors.add(author);
        book.setAuthors(authors);
        return bookRepository.save(book);

    }


    public List<Book> getBookDetails (boolean bookCheckout, Long bookId){
        if(bookCheckout && bookId !=0){
            List<Book> list = new ArrayList<>();
            Book book = bookRepository.findById(bookId).orElse(null);
            list.add(book);
            return list;
        } else {
            return null;
        }
    }
    public List<Book> getCartBookList (Long cartId){
        Optional<Cart> cartList = cartRepository.findById(cartId);
        if(cartList != null){
            return cartList.stream().map(cart -> cart.getBook()).collect(Collectors.toList());
        } else {
            return null;
        }
    }

}
