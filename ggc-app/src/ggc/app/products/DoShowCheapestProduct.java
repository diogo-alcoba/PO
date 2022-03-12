package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;

class DoShowCheapestProduct extends Command<WarehouseManager> {

    DoShowCheapestProduct(WarehouseManager receiver) {
      super(Label.SHOW_CHEAPEST_PRODUCT, receiver);
    }

    @Override
    public final void execute() throws CommandException {
      _display.popup(_receiver.showCheapestProduct());
    }
}