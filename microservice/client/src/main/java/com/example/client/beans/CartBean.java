package com.example.client.beans;

import java.util.List;

public class CartBean {

    private Long id;

    List<CartItemBean> products;

    public CartBean(){}

    public CartBean(Long id, List<CartItemBean> products) {
        this.id = id;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItemBean> getProducts() {
        return products;
    }

    public void setProducts(List<CartItemBean> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", products=" + products +
                '}';
    }

    public void addProduct(CartItemBean cartItem) {
        this.products.add(cartItem);
    }
}
