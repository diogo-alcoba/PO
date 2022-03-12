package ggc;

import java.io.Serializable;

public interface Subject extends Serializable {
  
    public void registerObserver(String observer_key, Observer observer);

    public void removeObserver(String observer_key);
    
    public boolean hasObserver(String observer_key);
    
    public void notifyObservers(String delivery, double price);

}
