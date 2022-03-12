package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.UnavailableProductException;
import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.app.exceptions.UnknownProductKeyException;
import ggc.exceptions.coreUnavailableProductException;
//FIXME import classes
import ggc.exceptions.coreUnknownPartnerKeyException;
import ggc.exceptions.coreUnknownProductKeyException;

/**
 * Register order.
 */
public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {

  public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
    addStringField("partner_key", Prompt.partnerKey());
    addStringField("product_key", Prompt.productKey());
    addIntegerField("amount", Prompt.amount());
  }

  @Override
  public final void execute() throws CommandException {
    String partner_key = stringField("partner_key");
    String product_key = stringField("product_key");
    int amount = integerField("amount");

    try{
      _receiver.registerBreakdown(partner_key, product_key, amount);
    }
    catch (coreUnknownPartnerKeyException e){
      throw new UnknownPartnerKeyException(partner_key);
    }
    catch (coreUnknownProductKeyException e){
      throw new UnknownProductKeyException(product_key);
    }
    catch (coreUnavailableProductException e){
      throw new UnavailableProductException(product_key, amount, e.getAvailable());
    }
  }
  

}
