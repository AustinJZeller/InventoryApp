package com.austinzeller.inventoryapp;

class Product {

    private String mName;
    private Integer mPrice;
    private Integer mQuantity;
    private String mPicture;

    Product(String name, Integer price, Integer quantity, String picture) {
        mName = name;
        mPrice = price;
        mQuantity = quantity;
        mPicture = picture;
    }

    public String getName() {
        return mName;
    }

    Integer getPrice() {
        return mPrice;
    }

    Integer getQuantity() {
        return mQuantity;
    }

    String getPicture() {
        return mPicture;
    }
}
