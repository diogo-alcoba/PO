package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.UnknownPartnerKeyException;
//FIXME import classes
import ggc.exceptions.coreUnknownPartnerKeyException;

/**
 * Lookup payments by given partner.
 */
public class DoLookupPaymentsByPartner extends Command<WarehouseManager> {

  public DoLookupPaymentsByPartner(WarehouseManager receiver) {
    super(Label.PAID_BY_PARTNER, receiver);
    addStringField("partner_key", Prompt.partnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String partner_key = stringField("partner_key");
    try{
      _display.popup(_receiver.LookupPaymentsByPartner(partner_key));
    }
    catch (coreUnknownPartnerKeyException e){
      throw new UnknownPartnerKeyException(partner_key);
    }
  }

}
