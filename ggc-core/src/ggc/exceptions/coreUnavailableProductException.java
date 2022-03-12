package ggc.exceptions;

public class coreUnavailableProductException extends BadEntryException{

    private int requested;
    private int available;

    public coreUnavailableProductException(String entrySpecification, int requested, int available){
        super(entrySpecification);
        this.requested = requested;
        this.available = available;
    }

    public int getRequested(){
        return requested;
    }

    public int getAvailable(){
        return available;
    }
}