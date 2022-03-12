package ggc;

public class Sale extends Transaction{

    private double base_value;
    private double value_to_pay;
    private int deadline;
    private int payment_date;

    public Sale(int id, String partner_key, String product_key, int amount, double base_value, double value_to_pay,
            int deadline, int payment_date) {
        super(id, partner_key, product_key, amount);
        this.base_value = base_value;
        this.value_to_pay = value_to_pay;
        this.deadline = deadline;
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

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(int payment_date) {
        this.payment_date = payment_date;
    }

    @Override
    public String toString() {
        String res = "VENDA|" + super.toString() + "|" + Math.round(base_value) + "|" + Math.round(value_to_pay) + "|" + deadline;
        if (payment_date > -1){
            res += "|" + payment_date;
        }
        return res;
    }


}