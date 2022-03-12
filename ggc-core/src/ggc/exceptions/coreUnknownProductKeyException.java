package ggc.exceptions;

public class coreUnknownProductKeyException extends BadEntryException{

    public coreUnknownProductKeyException(String entrySpecification){
        super(entrySpecification);
    }
}