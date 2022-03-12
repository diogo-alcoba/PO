package ggc.app.transactions;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.exceptions.coreUnknownPartnerKeyException;
import ggc.exceptions.coreUnknownProductKeyException;

/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
    addStringField("partner_key", Prompt.partnerKey());
    addStringField("product_key", Prompt.productKey());
    addRealField("price", Prompt.price());
    addIntegerField("amount", Prompt.amount());
  }

  @Override
  public final void execute() throws CommandException {
    String partner_key = stringField("partner_key");
    String product_key = stringField("product_key");
    Double price = realField("price");
    int amount = integerField("amount");

    try{
      _receiver.registerAcquisition(partner_key, product_key, amount, price);
    }

    catch (coreUnknownPartnerKeyException e){
      throw new UnknownPartnerKeyException(partner_key);
    }

    catch (coreUnknownProductKeyException e1){
      boolean answer = Form.confirm(Prompt.addRecipe());
      if (!answer){
        _receiver.registerProduct(product_key, price, 0, 0, null);
      }
    

      if (answer){
        int component_number = Form.requestInteger(Prompt.numberOfComponents());
        double multiplicate_aggravation = Form.requestReal(Prompt.alpha());
        String components = "";
        for (int i = 0; i < component_number; i++){
          String component_key = Form.requestString(Prompt.productKey());
          int component_amount = Form.requestInteger(Prompt.amount());
          components += _receiver.getProduct_list().get(component_key).getKey() + ":" + component_amount + "#"; 
        }
        components = components.substring(0, components.length() - 1);
        _receiver.registerProduct(product_key, price, 0, multiplicate_aggravation, components);
      }
      try{
        _receiver.registerAcquisition(partner_key, product_key, amount, price);
      }
      catch (coreUnknownPartnerKeyException e2){}
      catch (coreUnknownProductKeyException e3){}
    }
  }

}
