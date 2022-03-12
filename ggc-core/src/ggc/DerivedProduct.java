package ggc;

import java.util.HashMap;

public class DerivedProduct extends Product{

    private double multiplicate_aggravation;
    private String components;
    private HashMap<Product, Integer> component_list;

    /**
     * 
     * @param key
     * @param max_price
     * @param total_current_stock
     * @param multiplicate_aggravation
     * @param components
     */
    public DerivedProduct(String key, double max_price, double min_price, int total_current_stock, double multiplicate_aggravation, String components) {
        super(key, max_price, min_price, total_current_stock);
        this.multiplicate_aggravation = multiplicate_aggravation;
        this.components = components;
        component_list = new HashMap<Product, Integer>();
    }

    
    /** 
     * @return double
     */
    public double getMultiplicate_aggravation() {
        return multiplicate_aggravation;
    }

    
    /** 
     * @param multiplicate_aggravation
     */
    public void setMultiplicate_aggravation(double multiplicate_aggravation) {
        this.multiplicate_aggravation = multiplicate_aggravation;
    }

    
    /** 
     * @return HashMap<Product, Integer>
     */
    public HashMap<Product, Integer> getComponent_list() {
        return component_list;
    }

    
    /** 
     * @param component_list
     */
    public void setComponent_list(HashMap<Product, Integer> component_list) {
        this.component_list = component_list;
    }

    
    /** 
     * @param component
     * @param quantity
     */
    public void addComponent(Product component, int quantity){
        component_list.put(component, quantity);
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
       /*  String res = super.toString() + "|" + multiplicate_aggravation + "|";
        for (Product component : component_list.keySet()){
            res += component.getKey() + ":" + component_list.get(component) + "#";
        }
        res = res.substring(0, res.length() - 1);
        return res; */
        return super.toString() + "|" + multiplicate_aggravation + "|" + components;
    }

}