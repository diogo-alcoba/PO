package ggc.exceptions;

public class coreUnknownPartnerKeyException extends BadEntryException{
    /**
     * 
     * @param entrySpecification
     */
    public coreUnknownPartnerKeyException(String entrySpecification){
        super(entrySpecification);
    }
}