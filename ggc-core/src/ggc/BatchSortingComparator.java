package ggc;

import java.util.Comparator;

public class BatchSortingComparator implements Comparator<Batch>{
    
    @Override
    public int compare(Batch batch1, Batch batch2){
        int partner_name_compare = batch1.getPartner_key().compareTo(batch2.getPartner_key());
        int price_compare = (int) (Math.round(batch1.getPrice()) - Math.round(batch2.getPrice()));
        int current_stock_compare = batch1.getCurrent_stock() - batch2.getCurrent_stock();
        if (partner_name_compare == 0){
            if (price_compare == 0){
                return current_stock_compare;
            }
            return price_compare;
        }
        return partner_name_compare;
    }
}