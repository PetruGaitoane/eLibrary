package com.example.demo.sevice;


import com.example.demo.entity.Book;
import com.example.demo.entity.Cart;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private CartRepository cartRepository;
    private BookRepository bookRepository;



    @Autowired
    public CartService(CartRepository cartRepository, BookRepository bookRepository){
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
    }

    public Cart addToCart(Long bookId){
        Book book = bookRepository.findById(bookId).orElse(null);
        if(book != null){
            Cart cart = new Cart(book);
            return cartRepository.saveAndFlush(cart);
        }
        return null;
    }

    public Cart getCartDetails(Long cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    public Boolean  deleteCart (Long cartId) {

        List<Cart> cartList = cartRepository.findAll();
        // !!!!change to find by id;
        if(cartList.stream().anyMatch(cart -> cart.getCartId().equals(cartId))){
            cartRepository.deleteById(cartId);
            return true;
        }
        return false;
    }
}
