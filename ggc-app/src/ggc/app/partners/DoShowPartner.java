package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.exceptions.coreUnknownPartnerKeyException;

/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
    addStringField("key", Prompt.partnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String key = stringField("key");
    try{
      _display.popup(_receiver.showPartner(key));
    }

    catch (coreUnknownPartnerKeyException e){
      throw new UnknownPartnerKeyException(key);
    }
  }

}
