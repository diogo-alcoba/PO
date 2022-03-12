package ggc;

import java.util.ArrayList;

public class Partner implements Observer{

    private String key;
    private String name;
    private String address;
    Status status;
    private int points;
    private double orders_value;
    private double performed_sales_value;
    private double paid_sales_value;
    private ArrayList<Batch> batches_by_partner;
    private ArrayList<Transaction> transaction_history;
    private ArrayList<Notification> notification_list;

    /**
     * 
     * @param key
     * @param name
     * @param address
     */
    public Partner(String key, String name, String address) {
        this.key = key;
        this.name = name;
        this.address = address;
        status = new Normal();
        points = 0;
        orders_value = 0;
        performed_sales_value = 0;
        paid_sales_value = 0;
        batches_by_partner = new ArrayList<Batch>();
        transaction_history = new ArrayList<Transaction>();
        notification_list = new ArrayList<Notification>();
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
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return String
     */
    public String getAddress() {
        return address;
    }

    
    /** 
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }
    
    /** 
     * @return int
     */
    public int getPoints() {
        return points;
    }

    
    /** 
     * @param points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    
    /** 
     * @return int
     */
    public double getOrders_value() {
        return orders_value;
    }

    
    /** 
     * @param orders_value
     */
    public void setOrders_value(double orders_value) {
        this.orders_value = orders_value;
    }

    
    /** 
     * @return int
     */
    public double getPerformed_sales_value() {
        return performed_sales_value;
    }

    
    /** 
     * @param performed_sales_value
     */
    public void setPerformed_sales_value(double performed_sales_value) {
        this.performed_sales_value = performed_sales_value;
    }

    
    /** 
     * @return int
     */
    public double getPaid_sales_value() {
        return paid_sales_value;
    }

    
    /** 
     * @param paid_sales_value
     */
    public void setPaid_sales_value(double paid_sales_value) {
        this.paid_sales_value = paid_sales_value;
    }

    public ArrayList<Batch> getBatchesByPartner(){
        return batches_by_partner;
    }
    
    /** 
     * @param product_key
     * @param batch
     */
    public void addBatch(Batch batch){
        batches_by_partner.add(batch);
    }

    public ArrayList<Transaction> getTransactionHistory(){
        return transaction_history;
    }

    public void addTransaction(Transaction transaction){
        transaction_history.add(transaction);
    }

    @Override
    public void update(String delivery, String product_key, double price){
        Notification notification = new Notification(delivery, product_key, price);
        notification_list.add(notification);
    }

    @Override
    public ArrayList<Notification> getNotifications() {
        return notification_list;
    }


    /** 
     * @return String
     */
    @Override
    public String toString() {
        return key + "|" + name + "|" + address + "|" + status.toString() + "|" + points + "|" + Math.round(orders_value) + "|" + Math.round(performed_sales_value) + "|" + Math.round(paid_sales_value);
    }

    

}