package ggc.exceptions;

public class coreInvalidDateException extends BadEntryException{
    /**
     * 
     * @param entrySpecification
     */
    public coreInvalidDateException(String entrySpecification){
        super(entrySpecification);
    }
}