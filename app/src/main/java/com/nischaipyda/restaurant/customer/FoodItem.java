package com.nischaipyda.restaurant.customer;

public class FoodItem {

    private String Name;
    private int Price;

    public FoodItem(){}

    public FoodItem(String name, int price) {
        Name = name;
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
