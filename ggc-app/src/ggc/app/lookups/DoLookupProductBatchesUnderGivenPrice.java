package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
//FIXME import classes

/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductBatchesUnderGivenPrice extends Command<WarehouseManager> {

  public DoLookupProductBatchesUnderGivenPrice(WarehouseManager receiver) {
    super(Label.PRODUCTS_UNDER_PRICE, receiver);
    addRealField("price_limit", Prompt.priceLimit());
  }

  @Override
  public void execute() throws CommandException {
    double price_limit = realField("price_limit");
    _display.popup(_receiver.LookupProductBatchesUnderGivenPrice(price_limit));
  }

}
