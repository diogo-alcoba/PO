package ggc;

import java.util.Comparator;

public class BatchPriceComparator implements Comparator<Batch>{
    
    @Override
    public int compare(Batch batch1, Batch batch2){
        int price_compare = (int) (Math.round(batch1.getPrice()) - Math.round(batch2.getPrice()));
        return price_compare;
    }       
}
