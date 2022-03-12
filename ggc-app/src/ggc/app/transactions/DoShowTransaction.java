package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.UnknownTransactionKeyException;
import ggc.exceptions.coreUnknownTransactionKeyException;

/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<WarehouseManager> {

  public DoShowTransaction(WarehouseManager receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    addIntegerField("key", Prompt.transactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    int key = integerField("key");

    try{
      _display.popup(_receiver.showTransaction(key));
    }

    catch (coreUnknownTransactionKeyException e){
      throw new UnknownTransactionKeyException(key);
    }
  }

}
