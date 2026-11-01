package com.mycompany.kushopsystem;

public class Product {

    private final String productId;
    private final String productName;
    private final String type;
    private final int stock;
    private final double price;
    private final String status;
    private final String date;
    private final String image;

    public Product(String productId, String productName, String type, int stock,
                   double price, String status, String date, String image) {
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.date = date;
        this.image = image;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getType() { return type; }
    public int getStock() { return stock; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }
    public String getDate() { return date; }
    public String getImage() { return image; }
}