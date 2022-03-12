package ggc;

import java.io.Serializable;
import java.util.ArrayList;

public interface Observer extends Serializable {

    public void update(String delivery, String product_key, double price);
    
    public ArrayList<Notification> getNotifications();
}
