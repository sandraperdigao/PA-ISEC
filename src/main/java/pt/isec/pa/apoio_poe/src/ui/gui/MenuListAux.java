package pt.isec.pa.apoio_poe.src.ui.gui;

import javafx.scene.control.ListView;
import pt.isec.pa.apoio_poe.src.model.PoEManager;

public class MenuListAux extends ListView<String> {

    PoEManager poEManager;

    public MenuListAux(PoEManager poEManager) throws Exception {
        this.poEManager = poEManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
    }

    private void registerHandlers() {
        poEManager.addPropertyChangeListener(evt -> {
            try {
                update();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void update() throws Exception {
        this.getItems().clear();
        this.getItems().addAll(poEManager.getList());

    }
}
