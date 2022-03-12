package ggc;

public class Selection extends Status{


    public double payP1(double base_value){
        return 0.9 * base_value;
    }

    public double payP2(double base_value, int current_date, int deadline){
        int days_before = deadline - current_date;
        if (days_before >= 2){
            return 0.95 * base_value;
        }
        return base_value;
    }

    public double payP3(double base_value, int current_date, int deadline){
        int days_passed = current_date - deadline;
        if (days_passed > 1){
            return (1 + 0.02 * (days_passed - 1)) * base_value;
        }
        return base_value;
    }

    public double payP4(double base_value, int current_date, int deadline){
        int days_passed = current_date - deadline;
        return (1 + 0.05 * days_passed) * base_value;
    }


    public Status checkIfUpgrade(int points){
        if (points > 25000){
            return new Elite();
        }
        return this;
    }

    public String checkIfDowngrade(int days_passed){
        if (days_passed > 2){
            return "NORMAL";
        }
        return "";
    }
}