package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.exceptions.coreUnknownPartnerKeyException;

/**
 * Show batches supplied by partner.
 */
class DoShowBatchesByPartner extends Command<WarehouseManager> {

  DoShowBatchesByPartner(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_SUPPLIED_BY_PARTNER, receiver);
    addStringField("partner_key", Prompt.partnerKey());
  }

  @Override
  public final void execute() throws CommandException {
    String partner_key = stringField("partner_key");
    try{
      _display.popup(_receiver.showBatchesByPartner(partner_key));
    }
    catch (coreUnknownPartnerKeyException e){
      throw new UnknownPartnerKeyException(partner_key);
    }
  }

}
