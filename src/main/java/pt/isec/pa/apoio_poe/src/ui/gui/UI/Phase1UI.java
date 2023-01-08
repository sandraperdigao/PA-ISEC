package pt.isec.pa.apoio_poe.src.ui.gui.UI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pt.isec.pa.apoio_poe.src.model.PoEManager;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

public class Phase1UI extends BorderPane {
    PoEManager poEManager;
    Button btnStudents,btnTeachers,btnProposals,btnClose,btnContinue,btnExit;
    TextArea textArea,textArea2,textarea3;
    Label label, label2, label3, label4;
    ButtonBar buttonBar;

    private static final int BUTTONSIZE = 250;
    private static final int SMALLBUTTONSIZE = 150;
    private static final int MINWIDTH = 200;
    private static final int SPACING = 10;

    public Phase1UI(PoEManager poEManager) {
        this.poEManager = poEManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnStudents = new Button("Gerir estudantes");
        btnStudents.setPrefWidth(BUTTONSIZE);
        btnTeachers = new Button("Gerir docentes");
        btnTeachers.setPrefWidth(BUTTONSIZE);
        btnProposals = new Button("Gerir propostas");
        btnProposals.setPrefWidth(BUTTONSIZE);
        btnClose = new Button("Fechar Fase");
        btnClose.setPrefWidth(SMALLBUTTONSIZE);
        btnContinue = new Button("Avançar Fase >>>");
        btnContinue.setPrefWidth(SMALLBUTTONSIZE);
        btnExit  = new Button("Sair do Programa");
        btnExit.setPrefWidth(SMALLBUTTONSIZE);

        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setVisible(true);
        textArea.setMinWidth(MINWIDTH);
        textArea2 = new TextArea();
        textArea2 .setEditable(false);
        textArea2 .setVisible(true);
        textArea2 .setMinWidth(MINWIDTH);
        textarea3 = new TextArea();
        textarea3 .setEditable(false);
        textarea3 .setVisible(true);
        textarea3 .setMinWidth(MINWIDTH);

        label = new Label("Estudantes:");
        label2 = new Label("Professores:");
        label3 = new Label("Propostas:");
        label4 = new Label("FASE 1 - CONFIGURAÇÃO");
        label.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));
        label2.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));
        label3.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));
        label4.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));
        label4.setFont(new Font("Arial", 30));

        buttonBar= new ButtonBar();
        buttonBar.getButtons().addAll(btnContinue, new Separator(Orientation.VERTICAL), btnClose, new Separator(Orientation.VERTICAL), btnExit);
        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setBackground((new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY))));
        hBox1.setBorder(new Border(new BorderStroke(Color.LIGHTGREY,BorderStrokeStyle.SOLID, null , null)));
        hBox1.getChildren().add(buttonBar);
        hBox1.setPadding(Insets.EMPTY);
        HBox.setMargin(buttonBar,new Insets(SPACING/2));

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(SPACING);
        vBox.getChildren().addAll(btnStudents,btnTeachers,btnProposals);

        VBox vBox1 = new VBox();
        vBox1.setAlignment(Pos.CENTER);
        vBox1.getChildren().addAll(label,textArea,label2,textArea2,label3,textarea3);
        VBox.setMargin(label,new Insets(SPACING/2,SPACING/2,SPACING/2,SPACING));
        VBox.setMargin(textArea,new Insets(SPACING/2,SPACING/2,SPACING/2,SPACING));
        VBox.setMargin(label2,new Insets(SPACING/2,SPACING/2,SPACING/2,SPACING));
        VBox.setMargin(textArea2,new Insets(SPACING/2,SPACING/2,SPACING/2,SPACING));
        VBox.setMargin(label3,new Insets(SPACING/2,SPACING/2,SPACING/2,SPACING));
        VBox.setMargin(textarea3,new Insets(SPACING/2,SPACING/2,SPACING/2,SPACING));

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(SPACING);
        hBox.getChildren().addAll(vBox,vBox1);

        this.setCenter(hBox);
        this.setBottom(hBox1);
        this.setTop(label4);
    }

    private void registerHandlers() {
        poEManager.addPropertyChangeListener(evt -> { update(); });

        btnStudents.setOnAction( event -> {
            poEManager.manageStudents();
            update();
        });

        btnTeachers.setOnAction( event -> {
            poEManager.manageTeachers();
            update();
        });

        btnProposals.setOnAction( event -> {
            poEManager.manageProposals();
            update();
        });

        btnClose.setOnAction( event -> {
            try {
                poEManager.closePhase();
            } catch (Exception e) {
                e.printStackTrace();
            }
            update();
        });

        btnContinue.setOnAction( event -> {
            poEManager.forward();
            update();
        });

        btnExit.setOnAction( event -> {
            Platform.exit();
        });
    }

    private void update() {
        textArea.setText(poEManager.getStudentsAsString());
        textArea2.setText(poEManager.getTeachersAsString());
        textarea3.setText(poEManager.getProposalsAsString());

        if(poEManager.isState1Closed()){
            btnStudents.setDisable(true);
            btnTeachers.setDisable(true);
            btnProposals.setDisable(true);
            btnClose.setDisable(true);
        }

        if (poEManager.getState() != StateEnum.PHASE1) {
            this.setVisible(false);
            return;
        }

        this.setVisible(true);
    }
}
