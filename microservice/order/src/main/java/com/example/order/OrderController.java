package com.example.order;

import com.example.order.model.Order;
import com.example.order.repository.OrderItemRepository;
import com.example.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @PostMapping("/order")
    public ResponseEntity<Order> createNewOrder(){
        Order order = orderRepository.save(new Order());
        if (order == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new order");

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }

    @GetMapping("/order/{id}")
    public Optional<Order> getOrder(@PathVariable Long id){
        Optional<Order> order = orderRepository.findById(id);

        if (order == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart");

        return order;
    }
}
