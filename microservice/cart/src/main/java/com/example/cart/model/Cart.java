package com.example.cart.model;


import javax.persistence.*;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    List<CartItem> products;

    public Cart(){}

    public Cart(List<CartItem> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItem> getProducts() {
        return products;
    }

    public void setProducts(List<CartItem> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", products=" + products +
                '}';
    }

    public void addProduct(CartItem cartItem) {
        boolean isInCart = inCart(cartItem);
        if(isInCart){
            addQuantity(cartItem);
        }
        else{
            this.products.add(cartItem);
        }
    }

    private void addQuantity(CartItem cartItem) {
        for(int i=0;i<this.products.size();i++){
            if(this.products.get(i).getProductId().equals(cartItem.getProductId())){
                this.products.get(i).setQuantity(this.products.get(i).getQuantity()+cartItem.getQuantity());
            }
            if(this.products.get(i).getQuantity()==0){
                this.products.remove(this.products.get(i));
            }
        }
    }

    private boolean inCart(CartItem cartItem) {
        for(int i=0;i<this.products.size();i++){
            if(this.products.get(i).getProductId().equals(cartItem.getProductId())){
                return true;
            }
        }
        return false;
    }
}
