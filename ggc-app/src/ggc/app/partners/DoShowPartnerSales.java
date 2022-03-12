package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.exceptions.coreUnknownPartnerKeyException;
import ggc.app.exceptions.UnknownPartnerKeyException;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerSales extends Command<WarehouseManager> {

  DoShowPartnerSales(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_SALES, receiver);
    addStringField("partner_key", Prompt.partnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String partner_key = stringField("partner_key");
    try{
      _display.popup(_receiver.showPartnerSales(partner_key));
    }
    catch (coreUnknownPartnerKeyException e){
      throw new UnknownPartnerKeyException(partner_key);
    }
  }

}
