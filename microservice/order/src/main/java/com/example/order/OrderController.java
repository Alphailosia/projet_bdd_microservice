package com.example.order;

import com.example.order.model.Order;
import com.example.order.repository.OrderItemRepository;
import com.example.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
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

    @PostMapping("/order/{id}")
    @Transactional
    public Optional<Order> saveOrder(@PathVariable Long id, @RequestBody Order order){
        Order resOrder = orderRepository.getById(id);

        resOrder.setCartId(order.getCartId());

        orderRepository.save(resOrder);

        Optional<Order> res = orderRepository.findById(id);

        return res;
    }
}
