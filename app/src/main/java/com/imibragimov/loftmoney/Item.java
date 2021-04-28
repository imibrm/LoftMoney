package com.imibragimov.loftmoney;

public class Item {

    private String name;
    private String price;
    private int position;

    public Item(String name, String price, int position) {
        this.name = name;
        this.price = price;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
}