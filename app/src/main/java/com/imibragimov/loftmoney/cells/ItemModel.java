package com.imibragimov.loftmoney.cells;

public class ItemModel {
    private String name;

    private String price;
    private int position;
    private boolean isSelected;

    public ItemModel(String name, String price, int position) {
        this.name = name;
        this.price = price;
        this.position = position;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }
    public String getPrice() { return price; }
    public int getPosition() { return position; }
    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}


