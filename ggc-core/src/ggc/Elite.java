package ggc;

public class Elite extends Status{


    public double payP1(double base_value){
        return 0.9 * base_value;
    }

    public double payP2(double base_value, int current_date, int deadline){
        return 0.9 * base_value;
    }

    public double payP3(double base_value, int current_date, int deadline){
        return 0.95 * base_value;
    }

    public double payP4(double base_value, int current_date, int deadline){
        return base_value;
    }


    public Status checkIfUpgrade(int points){
        return this;
    }

    public String checkIfDowngrade(int days_passed){
        if (days_passed > 15){
            return "SELECTION";
        }
        return "";
    }

}