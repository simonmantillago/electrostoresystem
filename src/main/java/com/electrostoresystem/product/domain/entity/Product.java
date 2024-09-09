package com.electrostoresystem.product.domain.entity;

public class Product {
    private int id;
    private String name;
    private String description;
    private float salePrice;
    private int stock;
    private int minStock;
    private int categoryId;
    private int brandId;

    public Product() {
    }

    public Product(int id, String name, String description, float salePrice, int stock, int minStock, int categoryId,
            int brandId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.salePrice = salePrice;
        this.stock = stock;
        this.minStock = minStock;
        this.categoryId = categoryId;
        this.brandId = brandId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMinStock() {
        return minStock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }
    

}
