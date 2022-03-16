package com.example.client.beans;

import java.util.List;

public class OrderBean {
    private Long id;

    private Long cartId;
    private Float total;

    public OrderBean(){}

    public OrderBean(Long id, Long cartId, Float total) {
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

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public void calculeTotal(List<OrderItemBean> products) {
        Float total = new Float(0);
        for(OrderItemBean o : products){
            total+= o.getTotalPrice();
        }
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
