package com.example.client.beans;

public class ProductBean {

    private Long id;

    private String name;
    private String description;
    private String illustration;

    private Float price;

    public ProductBean() {
    }

    public ProductBean(Long id, String name, String description, String illustration, Float price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.illustration = illustration;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", illustration='" + illustration + '\'' +
                ", price=" + price +
                '}';
    }
}
