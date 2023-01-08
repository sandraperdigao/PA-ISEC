package pt.isec.pa.apoio_poe.src.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import pt.isec.pa.apoio_poe.src.model.PoEManager;


public class BottomBox extends HBox {
    PoEManager poEManager;

    public BottomBox(PoEManager poEManager) {
        this.poEManager = poEManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));
        setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));

        Label lbl = new Label("Gestão de projetos e estágios do DEIS-ISEC");
        lbl.setAlignment(Pos.CENTER);
        lbl.setMinWidth(100);

        this.getChildren().add(lbl);
    }

    private void registerHandlers() {}

    private void update() {}
}
