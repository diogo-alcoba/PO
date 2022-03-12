package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.exceptions.coreUnknownPartnerKeyException;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerAcquisitions extends Command<WarehouseManager> {

  DoShowPartnerAcquisitions(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_ACQUISITIONS, receiver);
    addStringField("partner_key", Prompt.partnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String partner_key = stringField("partner_key");
    try{
      _display.popup(_receiver.showPartnerAcquisitions(partner_key));
    }
    catch (coreUnknownPartnerKeyException e){
      throw new UnknownPartnerKeyException(partner_key);
    }
  }
}
