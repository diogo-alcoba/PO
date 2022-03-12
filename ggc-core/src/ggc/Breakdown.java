package ggc;

public class Breakdown extends Transaction{
    
    private double base_value;
    private double value_to_pay;
    private int payment_date;
    private String components_price;

    public Breakdown(int id, String partner_key, String product_key, int amount, double base_value, double value_to_pay,
            int payment_date) {
        super(id, partner_key, product_key, amount);
        this.base_value = base_value;
        this.value_to_pay = value_to_pay;
        this.payment_date = payment_date;
    }

    public double getBase_value() {
        return base_value;
    }

    public void setBase_value(double base_value) {
        this.base_value = base_value;
    }

    public double getValue_to_pay() {
        return value_to_pay;
    }

    public void setValue_to_pay(double value_to_pay) {
        this.value_to_pay = value_to_pay;
    }

    public int getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(int payment_date) {
        this.payment_date = payment_date;
    }

    public String getComponents_price() {
        return components_price;
    }

    public void setComponents_price(String components_price) {
        this.components_price = components_price;
    }

    @Override
    public String toString() {
        return "DESAGREGAÇÃO|" + super.toString() + "|" + Math.round(base_value) + "|" + Math.round(value_to_pay) + "|" + payment_date + "|" + components_price;
    }

    
    
    

    
}
