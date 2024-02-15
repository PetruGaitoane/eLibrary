package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.OrderInput;
import com.example.demo.entity.UserEntity;
import com.example.demo.helpers.CustomErrorResponse;
import com.example.demo.sevice.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/library")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/orderDetails/{orderId}") //http://localhost:8080/api/library/orderDetails/{orderId}
    public ResponseEntity<Object> getOrderDetailsById(@PathVariable Long orderId){
        ResponseEntity<Object> response = null;
        try{
            OrderDetail order = orderDetailService.getOrderDetailById(orderId);
            if(order != null){
                response = new ResponseEntity<>(order, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Order id: "+ orderId +" not found!"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping("/orderDetails") //http://localhost:8080/api/library/orderDetails
    public ResponseEntity<Object> getOrderDetailsWithFilter(@RequestParam Map<String, Object> params){
        ResponseEntity<Object> response = null;
        try{
            List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailsWithFilters(params);
            if(orderDetailList != null && orderDetailList.size() > 0){
                response = new ResponseEntity<>(orderDetailService.getOrderDetailsWithFilters(params), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Orders not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }


    @PostMapping("/placeOrder") //http://localhost:8080/api/library/placeOrder
    public ResponseEntity<Object> placeOrder(@RequestBody OrderInput orderInput){
        ResponseEntity<Object> response = null;
//        orderDetailService.placeOrder(orderInput);
        try{
            OrderInput newOrder = orderDetailService.placeOrder(orderInput);
            if(newOrder != null){
                response = new ResponseEntity<>("Order placed successfully!", HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Can't place order!"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;

    }



    @DeleteMapping("/deleteOrder/{orderId}") //http://localhost:8080/api/library/deleteOrder/{orderId}
    public ResponseEntity<Object> placeOrder(@PathVariable Long orderId){
        ResponseEntity<Object> response = null;
        try {
            boolean deletedOrder = orderDetailService.deleteOrder(orderId);
            if(!deletedOrder) {
                response =  new ResponseEntity<>(new CustomErrorResponse(" 404", "Unable to delete order! Id: " + orderId + " not found!"), HttpStatus.NOT_FOUND);
            } else {
                response =  new ResponseEntity<>("Order deleted successfully!", HttpStatus.OK);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }




}

