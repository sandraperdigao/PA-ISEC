package pt.isec.pa.apoio_poe.src.ui.gui.UI;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.src.model.PoEManager;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

public class InitialUI extends BorderPane {
    PoEManager poEManager;
    Button btnStart,btnContinue,btnExit;
    private static final int BUTTONSIZE = 350;

    public InitialUI(PoEManager poEManager) {
        this.poEManager = poEManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnStart = new Button("Nova Sessão");
        btnStart.setPrefWidth(BUTTONSIZE);
        btnContinue = new Button("Retomar sessão anterior...");
        btnContinue.setPrefWidth(BUTTONSIZE);
        btnExit  = new Button("Terminar");
        btnExit.setPrefWidth(BUTTONSIZE);
        VBox vBox = new VBox(btnStart,btnContinue,btnExit);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        this.setCenter(vBox);
    }

    private void registerHandlers() {
        poEManager.addPropertyChangeListener(evt -> { update(); });
        btnStart.setOnAction( event -> {
            poEManager.forward();
        });
        btnContinue.setOnAction( event -> {
            try {
                poEManager.loadLastSession();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnExit.setOnAction( event -> {
            Platform.exit();
        });
    }

    private void update() {
        if (poEManager.getState() != StateEnum.INITIAL) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
