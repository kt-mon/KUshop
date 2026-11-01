package com.mycompany.kushopsystem;

public class CartItem {

    private final Product product;
    private final int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public String getProductId() { return product.getProductId(); }
    public String getProductName() { return product.getProductName(); }
    public int getQuantity() { return quantity; }

    public double getPrice() { return product.getPrice() * quantity; }
}