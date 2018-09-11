package com.example.calorycountapp.Model;

public class Product extends Entity {

    private String name;
    private String categoryProduct;
    private int caloricity;

    public Product(){}

    public Product(String name,String categoryProduct,int caloricity){
        this.name = name;
        this.categoryProduct = categoryProduct;
        this.caloricity = caloricity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryProduct() {
        return categoryProduct;
    }

    public void setCategoryProduct(String categoryProduct) {
        this.categoryProduct = categoryProduct;
    }

    public int getCaloricity() {
        return caloricity;
    }

    public void setCaloricity(int caloricity) {
        this.caloricity = caloricity;
    }
}

