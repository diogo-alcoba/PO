package ggc.exceptions;

public class coreUnknownTransactionKeyException extends BadEntryException{
    
    public coreUnknownTransactionKeyException(String entrySpecification){
        super(entrySpecification);
    }
}