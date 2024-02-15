package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.Cart;
import com.example.demo.helpers.CustomErrorResponse;
import com.example.demo.sevice.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/library") //http://localhost:8080/api/library
public class CartController {
    @Autowired
    private CartService cartService;


//    @GetMapping("/books/addToCart/{bookId}") //http://localhost:8080/api/library/books/addToCart/{bookId}
//    public Cart addToCart(@PathVariable Long bookId){
//        return cartService.addToCart(bookId);
//    }

    @GetMapping("/books/addToCart/{bookId}") //http://localhost:8080/api/library/books/addToCart/{bookId}
    public ResponseEntity<Object> addToCart(@PathVariable Long bookId){
        ResponseEntity<Object> response = null;
        try{
            Cart cart = cartService.addToCart(bookId);
            if(cart != null){
                response = new ResponseEntity<>("Book added successfully to cart!", HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Book id: "+ bookId +" not found!"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping("/books/getCartDetails/{cartId}") //localhost:8080/api/library/books/getCartDetails/{cartId}
    public ResponseEntity<Object> getCartDetails(@PathVariable Long cartId){
        ResponseEntity<Object> response = null;
        try{
            Cart cart = cartService.getCartDetails(cartId);
            if(cart != null){
                response = new ResponseEntity<>(cart, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Cart id: "+ cartId +" not found!"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping("/books/deleteCart/{cartId}")  //http://localhost:8080/api/library/books/deleteCart/{cartId}
    public ResponseEntity<Object> deleteCart(@PathVariable Long cartId) {
        ResponseEntity<Object> response = null;
        try {
            boolean deleted = cartService.deleteCart(cartId);
            if(!deleted) {
                response =  new ResponseEntity<>(new CustomErrorResponse(" 404", "Delete failed! Cart ID: " + cartId + " not found."), HttpStatus.NOT_FOUND);
            } else {
                response =  new ResponseEntity<>("Cart with ID: " + cartId + " was deleted successfully!", HttpStatus.OK);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }








}
