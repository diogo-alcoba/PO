package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.UnknownTransactionKeyException;
//FIXME import classes
import ggc.exceptions.coreUnknownTransactionKeyException;

/**
 * Receive payment for sale transaction.
 */
public class DoReceivePayment extends Command<WarehouseManager> {

  public DoReceivePayment(WarehouseManager receiver) {
    super(Label.RECEIVE_PAYMENT, receiver);
    addIntegerField("transaction_key", Prompt.transactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    int transaction_key = integerField("transaction_key");
    try{
      _receiver.receivePayment(transaction_key);
    }
    catch (coreUnknownTransactionKeyException e){
      throw new UnknownTransactionKeyException(transaction_key);
    }
  }

}
