package pt.isec.pa.apoio_poe.src.ui.gui;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import pt.isec.pa.apoio_poe.src.model.PoEManager;

import java.io.IOException;

public class AppMenu extends MenuBar {
    PoEManager poEManager;
    Menu mnFile;
    MenuItem mnExit, mnSave;

    public AppMenu(PoEManager poEManager){
        this.poEManager = poEManager;
        this.createView();
        this.registerHandlers();
        this.update();
    }

    private void createView() {
        mnFile = new Menu("File");
        mnExit = new MenuItem("_Exit");
        mnSave = new MenuItem("_Save");
        mnFile.getItems().addAll(mnSave,new SeparatorMenuItem(), mnExit);

        this.getMenus().add(mnFile); //depois colocar addAll
        this.setUseSystemMenuBar(true);
    }

    private void registerHandlers() {
        mnSave.setOnAction(actionEvent -> {
            try {
                poEManager.saveState(poEManager.getContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mnExit.setOnAction(actionEvent -> {
            Platform.exit();
        });
    }

    private void update() {

    }

}
