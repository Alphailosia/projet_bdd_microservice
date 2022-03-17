package com.example.cart;

import com.example.cart.model.Cart;
import com.example.cart.model.CartItem;
import com.example.cart.repository.CartItemRepository;
import com.example.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
public class CartController {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @PostMapping("/cart")
    public ResponseEntity<Cart> createNewCart(){
        Cart cart = cartRepository.save(new Cart());
        if (cart == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new cart");

        return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
    }

    @GetMapping("/cart/{id}")
    public Optional<Cart> getCart(@PathVariable Long id){
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart");
        return cart;
    }

    @PostMapping("/cart/{id}")
    @Transactional
    public ResponseEntity<CartItem> addProductToCart(@PathVariable Long id, @RequestBody CartItem cartItem){
        Cart cart = cartRepository.getById(id);

        if (cart == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart");

        cart.addProduct(cartItem);
        cartRepository.save(cart);
        return new ResponseEntity<CartItem>(cartItem, HttpStatus.CREATED);
    }

    @PostMapping("/cartd/{id}")
    @Transactional
    public ResponseEntity<Cart> deleteProductsFromCart(@PathVariable Long id, @RequestBody Cart cart){
        Cart cart1 = cartRepository.getById(id);

        cart1.setProducts(cart.getProducts());

        cartRepository.save(cart1);

        return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
    }

}
