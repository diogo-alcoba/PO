package ggc;

public class Acquisition extends Transaction{

    private double payed_value;
    private int payment_date;

    public Acquisition(int id, String partner_key, String product_key, int amount, double payed_value, int payment_date) {
        super(id, partner_key, product_key, amount);
        this.payed_value = payed_value;
        this.payment_date = payment_date;
    }

    public double getPayed_value() {
        return payed_value;
    }

    public void setPayed_value(double payed_value) {
        this.payed_value = payed_value;
    }

    public int getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(int payment_date) {
        this.payment_date = payment_date;
    }

    @Override
    public String toString() {
        return "COMPRA|" + super.toString() + "|" + Math.round(payed_value) + "|" + payment_date;
    }
    
}