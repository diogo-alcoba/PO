package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.app.exceptions.UnknownProductKeyException;
import ggc.exceptions.coreUnknownPartnerKeyException;
//FIXME import classes
import ggc.exceptions.coreUnknownProductKeyException;

/**
 * Toggle product-related notifications.
 */
class DoToggleProductNotifications extends Command<WarehouseManager> {

  DoToggleProductNotifications(WarehouseManager receiver) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);
    addStringField("partner_key", Prompt.partnerKey());
    addStringField("product_key", Prompt.productKey());
  }

  @Override
  public void execute() throws CommandException {
    String partner_key = stringField("partner_key");
    String product_key = stringField("product_key");
    try{
    _receiver.toggleProductNotification(partner_key, product_key);
    }
    catch (coreUnknownProductKeyException e){
      throw new UnknownProductKeyException(product_key);
    }
    catch (coreUnknownPartnerKeyException e){
      throw new UnknownPartnerKeyException(partner_key);
    }
  }

}
