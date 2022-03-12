package ggc;

import java.io.Serializable;

public class Transaction implements Serializable{

    private int id;
    private String partner_key;
    private String product_key;
    private int amount;

    public Transaction(int id, String partner_key, String product_key, int amount) {
        this.id = id;
        this.partner_key = partner_key;
        this.product_key = product_key;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartner_key() {
        return partner_key;
    }

    public void setPartner_key(String partner_key) {
        this.partner_key = partner_key;
    }

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return id + "|" + partner_key + "|" + product_key + "|" + amount;
    }

    
    

}