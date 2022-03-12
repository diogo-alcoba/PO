package ggc;

public class Normal extends Status{


    public double payP1(double base_value){
        return 0.9 * base_value;
    }

    public double payP2(double base_value, int current_date, int deadline){
        return base_value;
    }

    public double payP3(double base_value, int current_date, int deadline){
        int days_passed = current_date - deadline;
        return (1 + 0.05 * days_passed) * base_value;
    }

    public double payP4(double base_value, int current_date, int deadline){
        int days_passed = current_date - deadline;
        return (1 + 0.1 * days_passed) * base_value;
    }

    public double getPointLossPercentage(){
        return 1;
    }

    public Status checkIfUpgrade(int points){
        if(points > 2000){
            return new Selection();
        }
        return this;
    }

    public String checkIfDowngrade(int days_passed){
        return "";
    }

    
    /** 
     * @return String
     */
    public String toString(){
        return "NORMAL";
    }
}