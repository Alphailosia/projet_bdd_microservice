package com.example.client.beans;

public class OrderItemBean {

    private Long id;

    private Integer quantity;
    private Float price;
    private Float totalPrice;

    public OrderItemBean(Long id, Integer quantity, Float price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = this.quantity*this.price;
    }

    public OrderItemBean(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
