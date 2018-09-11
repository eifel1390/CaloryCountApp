package com.example.calorycountapp.Model;

public class Active extends Entity {

    private String nameActive;
    private String categoryActive;
    private int caloricityCost;

    public Active(){}

    public Active(String nameActive, String categoryActive, int caloricityCost) {
        this.nameActive = nameActive;
        this.categoryActive = categoryActive;
        this.caloricityCost = caloricityCost;
    }

    public String getNameActive() {
        return nameActive;
    }

    public void setNameActive(String nameActive) {
        this.nameActive = nameActive;
    }

    public String getCategoryActive() {
        return categoryActive;
    }

    public void setCategoryActive(String categoryActive) {
        this.categoryActive = categoryActive;
    }

    public int getCaloricityCost() {
        return caloricityCost;
    }

    public void setCaloricityCost(int caloricityCost) {
        this.caloricityCost = caloricityCost;
    }
}