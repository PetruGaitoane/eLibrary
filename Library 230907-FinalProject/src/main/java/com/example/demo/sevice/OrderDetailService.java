package com.example.demo.sevice;

import com.example.demo.entity.*;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderDetailService {
    private static final String ORDER_PLACED = "Placed";
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public OrderDetail getOrderDetailById(Long orderId){
        return orderDetailRepository.findById(orderId).orElse(null);
    }



    public List<OrderDetail> getOrderDetailsWithFilters(Map<String, Object> params) {
        if(params.size() == 0){
            return orderDetailRepository.findAll();
        } else {
            return null;
        }
    }


    public OrderInput placeOrder(OrderInput orderInput){
        List<OrderBookQuantity> bookQuantityList =  orderInput.getOrderBookQuantityList();
        for(OrderBookQuantity bookOrder: bookQuantityList){
            Book book = bookRepository.findById(bookOrder.getBookId()).get();

            OrderDetail orderDetail = new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    ORDER_PLACED,
                    bookOrder.getQuantity(),
                    book.getSalePrice(),
                    book.getSalePrice() * bookOrder.getQuantity(),
                    book
            );
            orderDetailRepository.save(orderDetail);
        }
        return orderInput;
    }

    public Boolean  deleteOrder(Long orderId) {

        List<OrderDetail> orderDetailList = orderDetailRepository.findAll();
        if(orderDetailList.stream().anyMatch(order -> order.getOrderId().equals(orderId))){
            orderDetailRepository.deleteById(orderId);
            return true;
        }
        return false;
    }


}
