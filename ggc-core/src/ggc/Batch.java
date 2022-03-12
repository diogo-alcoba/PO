package ggc;

import java.io.Serializable;

public class Batch implements Serializable{

    private String product_key;
    private String partner_key;
    private double price;
    private int current_stock;

    /**
     * 
     * @param product_key
     * @param partner_key
     * @param price
     * @param current_stock
     */
    public Batch(String product_key, String partner_key, double price, int current_stock) {
        this.product_key = product_key;
        this.partner_key = partner_key;
        this.price = price;
        this.current_stock = current_stock;
    }

    
    /** 
     * @return String
     */
    public String getProduct_key() {
        return product_key;
    }

    
    /** 
     * @param product_key
     */
    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    
    /** 
     * @return String
     */
    public String getPartner_key() {
        return partner_key;
    }

    
    /** 
     * @param partner_key
     */
    public void setPartner_key(String partner_key) {
        this.partner_key = partner_key;
    }

    
    /** 
     * @return double
     */
    public double getPrice() {
        return price;
    }

    
    /** 
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    
    /** 
     * @return int
     */
    public int getCurrent_stock() {
        return current_stock;
    }

    
    /** 
     * @param current_stock
     */
    public void setCurrent_stock(int current_stock) {
        this.current_stock = current_stock;
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return product_key + "|" + partner_key + "|" + Math.round(price) + "|" + current_stock;
    }

    
    
}
