package pt.isec.pa.apoio_poe.src;

import javafx.application.Application;
import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.PoEManager;
import pt.isec.pa.apoio_poe.src.ui.gui.MainJFX;
import pt.isec.pa.apoio_poe.src.ui.text.TextInterface;

import static pt.isec.pa.apoio_poe.src.Configs.START_UI;
import static pt.isec.pa.apoio_poe.src.Configs.TEST_ENVIRONMENT;

public class Main {
    public static void main(String[] args) throws Exception {

        if(START_UI){
            Application.launch(MainJFX.class,args);
        }
        else{
            Logger.log("PoE Management started.");
            PoEManager poeManager = PoEManager.getInstance(TEST_ENVIRONMENT);
            TextInterface text = new TextInterface(poeManager.getContext());
            text.start();
            poeManager.saveState(poeManager.getContext());
            Logger.log("PoE Management terminating...");
        }

    }
}
