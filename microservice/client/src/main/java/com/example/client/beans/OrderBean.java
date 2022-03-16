package com.example.client.beans;

public class OrderBean {
    private Long id;

    private Long cartId;
    private double total;

    public OrderBean(){}

    public OrderBean(Long id, Long cartId, double total) {
        this.id = id;
        this.cartId = cartId;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cartId=" + cartId +
                ", total=" + total +
                '}';
    }
}
