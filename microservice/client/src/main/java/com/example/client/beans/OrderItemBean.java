package com.example.client.beans;

public class OrderItemBean {

    private Long id;

    private int quantity;
    private double price;

    public OrderItemBean(Long id, int quantity, double price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderItemBean(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
