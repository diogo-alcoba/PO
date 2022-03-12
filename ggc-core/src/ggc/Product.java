package ggc;

import java.util.ArrayList;
import java.util.HashMap;

public class Product implements Subject{


    private String key;
    private double max_price;
    private double min_price;
    private int total_current_stock;
    private ArrayList<Batch> batches_by_product;
    private HashMap<String, Observer> observer_list;
    
    /**
     * 
     * @param key
     * @param max_price
     * @param total_current_stock
     */
    public Product(String key, double max_price, double min_price, int total_current_stock) {
        this.key = key;
        this.max_price = max_price;
        this.min_price = min_price;
        this.total_current_stock = total_current_stock;
        batches_by_product = new ArrayList<Batch>();
        observer_list = new HashMap<String, Observer>();
    }

    
    /** 
     * @return double
     */
    public double getMax_price() {
        return max_price;
    }

    
    /** 
     * @param max_price
     */
    public void setMax_price(double max_price) {
        this.max_price = max_price;
    }

    
    /** 
     * @return String
     */
    public String getKey() {
        return key;
    }

    
    /** 
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    
    /** 
     * @return int
     */
    public int getTotal_current_stock() {
        return total_current_stock;
    }

    
    /** 
     * @param total_current_stock
     */
    public void setTotal_current_stock(int total_current_stock) {
        this.total_current_stock = total_current_stock;
    }

    
    public double getMin_price() {
        return min_price;
    }


    public void setMin_price(double min_price) {
        this.min_price = min_price;
    }


    /** 
     * @return TreeMap<String, Batch>
     */
    public ArrayList<Batch> getBatchesByProduct() {
        return batches_by_product;
    }

    
    /** 
     * @param batch
     */
    public void addBatch(Batch batch){
        batches_by_product.add(batch);
    }
    
    

    @Override
    public void registerObserver(String observer_key, Observer observer) {
        observer_list.put(observer_key, observer);
    }

    @Override
    public void removeObserver(String observer_key) {
        observer_list.remove(observer_key);
    }


    @Override
    public boolean hasObserver(String observer_key) {
        return observer_list.containsKey(observer_key);
    }

    @Override
    public void notifyObservers(String delivery , double price) {
        for (Observer observer : observer_list.values())
            observer.update(delivery, key, price);
    }
    
    @Override
    public String toString() {
        return key + "|" + Math.round(max_price) + "|" + total_current_stock;
    }

   

}