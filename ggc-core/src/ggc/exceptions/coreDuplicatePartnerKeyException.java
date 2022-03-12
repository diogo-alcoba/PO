package ggc.exceptions;

public class coreDuplicatePartnerKeyException extends BadEntryException{
    /**
     * 
     * @param entrySpecification
     */
    public coreDuplicatePartnerKeyException(String entrySpecification){
        super(entrySpecification);
    }
}
