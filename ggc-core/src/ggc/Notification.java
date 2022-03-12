package ggc;

import java.io.Serializable;

public class Notification implements Serializable {

    private String delivery;
    private String product_key;
    private double price;


    public Notification(String delivery, String product_key, double price) {
        this.delivery = delivery;
        this.product_key = product_key;
        this.price = price;
    }


    public String getDelivery() {
        return delivery;
    }


    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }


    public String getProduct_key() {
        return product_key;
    }


    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }


    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return delivery + "|" + product_key + "|" + Math.round(price);
    }

}
