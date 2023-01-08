package pt.isec.pa.apoio_poe.src.ui.gui;

import javafx.scene.layout.*;
import pt.isec.pa.apoio_poe.src.model.PoEManager;
import pt.isec.pa.apoio_poe.src.ui.gui.UI.*;
import pt.isec.pa.apoio_poe.src.ui.gui.resources.CSSManager;
import pt.isec.pa.apoio_poe.src.ui.gui.resources.ImageManager;


public class RootPane extends BorderPane {

    PoEManager poeManager;

    public RootPane(PoEManager poeManager) throws CloneNotSupportedException {

        this.poeManager = poeManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() throws CloneNotSupportedException {
        CSSManager.applyCSS(this,"styles.css");
        StackPane stackPane = new StackPane(
                new InitialUI(poeManager),
                new Phase1UI(poeManager),
                new Phase1ProposalsUI(poeManager),
                new Phase1TeachersUI(poeManager),
                new Phase1StudentsUI(poeManager),
                new Phase2UI(poeManager),
                new Phase3UI(poeManager),
                new Phase3ConflictUI(poeManager),
                new Phase4UI(poeManager),
                new Phase5UI(poeManager)
        );

        stackPane.setBackground(new Background(new BackgroundImage(
                ImageManager.getImage("background3.jpg"),
                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1,1,true,true,false,false)
        )));

        this.setTop(new AppMenu(poeManager));
        this.setCenter(stackPane);
        this.setBottom(new BottomBox(poeManager));
    }

    private void registerHandlers() {}

    private void update() {}
}
