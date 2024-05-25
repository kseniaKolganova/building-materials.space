package com.example.administratorpanel.model;// Product.java

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_Product")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Articul", unique = true)
    private String articul;

    @Column(name = "Avaibale_Quantity")
    private int availableQuantity;

    @Column(name = "Price")
    private double price;

    @Column(name = "Description")
    private String description;

    @Column(name = "Colors")
    private String colors;

    @Column(name = "Country")
    private String country;

    @Column(name = "Models")
    private String models;

    @Column(name = "Marka")
    private String marka;

    @Column(name = "Weight")
    private String weight;

    @ManyToOne
    @JoinColumn(name = "ID_Categories")
    private Categories category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Photo> photos;

    // Getters and setters

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArticul() {
        return articul;
    }

    public void setArticul(String articul) {
        this.articul = articul;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public List<Photo> getPhotos() {
        return photos;
    }
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ShoppingCart> cartItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    // Геттеры и сеттеры для reviews
    public List<Review> getReviews() {
        return reviews;
    }
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
