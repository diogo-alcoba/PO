package ggc;

import java.io.Serializable;

public abstract class Status implements Serializable{

    public abstract double payP1(double base_value);
    
    public abstract double payP2(double base_value, int current_date, int deadline);

    public abstract double payP3(double base_value, int current_date, int deadline);

    public abstract double payP4(double base_value, int current_date, int deadline);


    public abstract Status checkIfUpgrade(int points);

    public abstract String checkIfDowngrade(int days_passed);


}