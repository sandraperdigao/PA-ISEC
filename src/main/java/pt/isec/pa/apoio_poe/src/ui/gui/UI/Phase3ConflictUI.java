package pt.isec.pa.apoio_poe.src.ui.gui.UI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.apoio_poe.src.model.PoEManager;
import pt.isec.pa.apoio_poe.src.model.data.proposal.ProposalAdapter;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

public class Phase3ConflictUI extends BorderPane {
    PoEManager poEManager;
    Button btnChoice,btnExit;
    TextArea textArea;
    TextField textField;
    Label label;
    private static final int BUTTONSIZE = 300;

    public Phase3ConflictUI(PoEManager poEManager) {
        this.poEManager = poEManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnChoice = new Button("OK");
        btnChoice.setPrefWidth(BUTTONSIZE);
        btnExit  = new Button("Sair do Programa");
        btnExit.setPrefWidth(BUTTONSIZE);

        textArea = new TextArea();
        textArea.setEditable(false);
        label = new Label("Por favor selecione um dos alunos abaixo para atribuir a proposta (1 ou 2):");
        textField = new TextField();
        label.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(textField,btnChoice);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label,textArea,hBox,btnExit);

        this.setCenter(vBox);

    }

    private void registerHandlers() {
        poEManager.addPropertyChangeListener(evt -> { update(); });

        btnChoice.setOnAction( event -> {
            String option = textField.getText();
            int opt = Integer.parseInt(option);
            try {
                poEManager.resolveConflict(opt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnExit.setOnAction( event -> {
            Platform.exit();
        });
    }

    private void update() {

        try {

            ProposalAdapter proposalConflict = poEManager.getConflictProposal();
            String[] listStudents = poEManager.getConflictStudents();

            if(listStudents == null){
                textArea.setText("");
            }
            else{
                StringBuilder sb = new StringBuilder();
                for(String element: listStudents){
                    sb.append(element).append("\n");
                }

                textArea.setText("Proposta:\n"+poEManager.getConflictProposal().toString()+"Alunos:\n"+sb);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        if (poEManager.getState() != StateEnum.PHASE3_CONFLICT) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
