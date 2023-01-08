package pt.isec.pa.apoio_poe.src.ui.gui.UI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import pt.isec.pa.apoio_poe.src.model.PoEManager;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

import java.io.File;


public class Phase1TeachersUI extends BorderPane {
    PoEManager poEManager;
    Button btnImportCSV,btnExportCSV,btnInsertManual, btnEditManual, btnRemoveManual, btnRemoveAll, btnContinue,btnExit;
    TextArea textArea;
    Label label;

    private static final int BUTTONSIZE = 350;
    private static final int SPACING = 10;

    public Phase1TeachersUI(PoEManager poEManager) {
        this.poEManager = poEManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnImportCSV = new Button("Importar de ficheiro CSV");
        btnImportCSV.setPrefWidth(BUTTONSIZE);
        btnExportCSV = new Button("Exportar para ficheiro CSV");
        btnExportCSV.setPrefWidth(BUTTONSIZE);
        btnInsertManual = new Button("Inserir Manualmente");
        btnInsertManual.setPrefWidth(BUTTONSIZE);
        btnEditManual = new Button("Editar Manualmente");
        btnEditManual.setPrefWidth(BUTTONSIZE);
        btnRemoveManual = new Button("Remover Manualmente");
        btnRemoveManual.setPrefWidth(BUTTONSIZE);
        btnRemoveAll = new Button("Remover Todos");
        btnRemoveAll.setPrefWidth(BUTTONSIZE);
        btnContinue = new Button("Terminar edição");
        btnContinue.setPrefWidth(BUTTONSIZE);
        btnExit = new Button("Sair do Programa");
        btnExit.setPrefWidth(BUTTONSIZE);

        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setVisible(true);

        label = new Label("Professores:");
        label.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(SPACING);
        vBox.getChildren().addAll(btnImportCSV,btnExportCSV,btnInsertManual,btnRemoveManual,btnRemoveAll,btnContinue,btnExit);

        VBox vBox1 = new VBox();
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(SPACING);
        vBox1.getChildren().addAll(label,textArea);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(SPACING);
        hBox.getChildren().addAll(vBox,vBox1);

        this.setCenter(hBox);
    }

    private void registerHandlers() {
        poEManager.addPropertyChangeListener(evt -> { update(); });

        btnImportCSV.setOnAction( event -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("File open...");
            fileChooser.setInitialDirectory(new File(".")); //diretoria atual
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"),
                    new FileChooser.ExtensionFilter("All", "*.*")
            );

            File hFile = fileChooser.showOpenDialog(this.getScene().getWindow()); //devolve o ficheiro escolhido

            if(hFile != null) {
                try {
                    poEManager.insertTeachersFromFile(hFile.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            update();

        });

        btnExportCSV.setOnAction( event -> {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Exportar para Ficheiro CSV");
            tid.setContentText("Ficheiro de Professores a exportar");
            tid.setHeaderText("Professores");
            tid.showAndWait().ifPresent(response ->{
                try {
                    poEManager.exportTeachersToFile(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });

        btnInsertManual.setOnAction( event -> {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Inserir Docente Manualmente");
            tid.setContentText("Inserção Manual");
            tid.setHeaderText("Docente no formato:\n"+"Nome,Email");
            tid.showAndWait().ifPresent(response ->{
                try {
                    poEManager.manualInsertTeacher(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            update();
        });

        btnRemoveManual.setOnAction( event -> {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Remover Docente Manualmente");
            tid.setContentText("Remoção Manual");
            tid.setHeaderText("Email do Docente");
            tid.showAndWait().ifPresent(response ->{
                try {
                    poEManager.manualRemoveTeacher(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            update();
        });

        btnRemoveAll.setOnAction( event -> {
            try {
                poEManager.removeAllTeachers();
            } catch (Exception e) {
                e.printStackTrace();
            }
            update();
        });

        btnContinue.setOnAction( event -> {
            poEManager.forward();
        });

        btnExit.setOnAction( event -> {
            Platform.exit();
        });

    }

    private void update() {
        textArea.setText(poEManager.getTeachersAsString());
        if (poEManager.getState() != StateEnum.PHASE1_TEACHERS) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
