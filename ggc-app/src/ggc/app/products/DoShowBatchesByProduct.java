package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.UnknownProductKeyException;
import ggc.exceptions.coreUnknownProductKeyException;

/**
 * Show all products.
 */
class DoShowBatchesByProduct extends Command<WarehouseManager> {

  DoShowBatchesByProduct(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_BY_PRODUCT, receiver);
    addStringField("product_key", Prompt.productKey());
  }

  @Override
  public final void execute() throws CommandException {
    String product_key = stringField("product_key");
    try{
      _display.popup(_receiver.showBatchesByProduct(product_key));
    }
    catch (coreUnknownProductKeyException e){
      throw new UnknownProductKeyException(product_key);
    }
  }

}
