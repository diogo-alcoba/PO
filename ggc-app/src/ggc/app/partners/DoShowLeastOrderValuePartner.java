package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;

class DoShowLeastOrderValuePartner extends Command<WarehouseManager>{

    DoShowLeastOrderValuePartner(WarehouseManager receiver) {
        super(Label.SHOW_LEAST_ORDER_VALUE_PARTNER, receiver);
    }

    @Override
    public void execute() throws CommandException {
        _display.popup(_receiver.showLeastOrderValuePartner());
    }
    
}
