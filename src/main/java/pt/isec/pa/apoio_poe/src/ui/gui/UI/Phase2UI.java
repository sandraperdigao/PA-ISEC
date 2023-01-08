package pt.isec.pa.apoio_poe.src.ui.gui.UI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import pt.isec.pa.apoio_poe.src.model.PoEManager;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

import java.io.File;

public class Phase2UI extends BorderPane {
    PoEManager poEManager;
    Button btnImportCSV,btnExportCSV,btnInsertManual, btnEditManual, btnRemoveManual, btnRemoveAll,btnClose,btnContinue,btnGoBack,btnExit;
    TextArea textArea, textArea2;
    Label label, label1, label2, label3, label4, label5, label6, label7, label8;
    MenuButton menuButton1, menuButton2;
    MenuItem menuItem1, menuItem2, menuItem3, menuItem4, menuItem5, menuItem6, menuItem7;
    ButtonBar buttonBar;

    private static final int BUTTONSIZE = 450;
    private static final int SMALLBUTTONSIZE = 150;
    private static final int MINWIDTH = 150;
    private static final int SPACING = 10;

    public Phase2UI(PoEManager poEManager) {
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
        btnClose = new Button("Fechar Fase");
        btnClose.setPrefWidth(SMALLBUTTONSIZE);
        btnContinue = new Button("Avançar Fase >>>");
        btnContinue.setPrefWidth(SMALLBUTTONSIZE);
        btnGoBack = new Button("Recuar Fase <<<");
        btnGoBack.setPrefWidth(SMALLBUTTONSIZE);
        btnExit  = new Button("Sair do Programa");
        btnExit.setPrefWidth(SMALLBUTTONSIZE);
        menuButton1 = new MenuButton("Mostrar Propostas");
        menuButton1.setMnemonicParsing(true);
        menuButton1.setPrefWidth(BUTTONSIZE);
        menuButton1.setAlignment(Pos.CENTER);
        label1 = new Label("Autopropostas de alunos");
        label1.prefWidthProperty().bind(menuButton1.widthProperty());
        label1.setAlignment(Pos.TOP_RIGHT);
        label2 = new Label("Propostas de docentes");
        label2.prefWidthProperty().bind(menuButton1.widthProperty());
        label2.setAlignment(Pos.TOP_RIGHT);
        label3 = new Label("Propostas com candidaturas");
        label3.prefWidthProperty().bind(menuButton1.widthProperty());
        label3.setAlignment(Pos.TOP_RIGHT);
        label4 = new Label("Propostas sem candidatura");
        label4.prefWidthProperty().bind(menuButton1.widthProperty());
        label4.setAlignment(Pos.TOP_RIGHT);
        menuItem1 = new MenuItem("",label1);
        menuItem2 = new MenuItem("",label2);
        menuItem3 = new MenuItem("",label3);
        menuItem4 = new MenuItem("",label4);

        menuButton2 = new MenuButton("Mostrar Estudantes");
        menuButton2.setMnemonicParsing(true);
        menuButton2.setPrefWidth(BUTTONSIZE);
        menuButton2.setAlignment(Pos.CENTER);
        label5 = new Label("Estudantes com AutoProposta");
        label5.prefWidthProperty().bind(menuButton2.widthProperty());
        label5.setAlignment(Pos.TOP_RIGHT);
        label6 = new Label("Estudantes com Candidatura Registada");
        label6.prefWidthProperty().bind(menuButton2.widthProperty());
        label6.setAlignment(Pos.TOP_RIGHT);
        label7 = new Label("Estudantes sem Candidatura Registada");
        label7.prefWidthProperty().bind(menuButton2.widthProperty());
        label7.setAlignment(Pos.TOP_RIGHT);
        menuItem5 = new MenuItem("",label5);
        menuItem6 = new MenuItem("",label6);
        menuItem7 = new MenuItem("",label7);

        menuButton1.getItems().addAll(menuItem1,menuItem2,menuItem3,menuItem4);
        menuButton2.getItems().addAll(menuItem5,menuItem6,menuItem7);

        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setVisible(true);
        textArea.setMinWidth(MINWIDTH);
        textArea2 = new TextArea();
        textArea2.setEditable(false);
        textArea2.setVisible(true);
        textArea2.setMinWidth(MINWIDTH);
        label = new Label("Candidaturas");
        label.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(SPACING);
        vBox.getChildren().addAll(btnImportCSV,btnExportCSV,btnInsertManual,btnRemoveManual,btnRemoveAll,menuButton1, menuButton2);

        buttonBar= new ButtonBar();
        buttonBar.getButtons().addAll(btnGoBack, new Separator(Orientation.VERTICAL), btnContinue, new Separator(Orientation.VERTICAL), btnClose, new Separator(Orientation.VERTICAL), btnExit);
        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setBackground((new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY))));
        hBox1.setBorder(new Border(new BorderStroke(Color.LIGHTGREY,BorderStrokeStyle.SOLID, null , null)));
        hBox1.getChildren().add(buttonBar);
        hBox1.setPadding(Insets.EMPTY);
        HBox.setMargin(buttonBar,new Insets(SPACING/2));

        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getChildren().addAll(label,textArea,textArea2);
        VBox.setMargin(label,new Insets(10));
        VBox.setMargin(textArea,new Insets(10));
        VBox.setMargin(textArea2,new Insets(10));

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(SPACING);
        hBox.getChildren().addAll(vBox,vBox2);

        label8 = new Label("FASE 2 - CANDIDATURAS");
        label8.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));
        label8.setFont(new Font("Arial", 30));

        this.setCenter(hBox);
        this.setBottom(hBox1);
        this.setTop(label8);
    }

    private void registerHandlers() {

        poEManager.addPropertyChangeListener(evt -> { update(); });

        menuItem1.setOnAction(actionEvent -> {
            textArea2.setText("Autopropostas de alunos:\n"+poEManager.listProposalsFilteredPhase2(new boolean[]{true,false,false,false})); //Autopropostas de alunos
        });

        menuItem2.setOnAction(actionEvent -> {
            textArea2.setText("Propostas de docentes:\n"+poEManager.listProposalsFilteredPhase2(new boolean[]{false,true,false,false}));
        });

        menuItem3.setOnAction(actionEvent -> {
            textArea2.setText("Propostas com candidatura:\n"+poEManager.listProposalsFilteredPhase2(new boolean[]{false,false,true,false}));
        });

        menuItem4.setOnAction(actionEvent -> {
            textArea2.setText("Propostas sem candidatura:\n"+poEManager.listProposalsFilteredPhase2(new boolean[]{false,false,false,true}));
        });

        menuItem5.setOnAction(actionEvent -> {
            textArea2.setText("Estudantes com autoproposta:\n"+poEManager.listStudentsAuto());
        });

        menuItem6.setOnAction(actionEvent -> {
            textArea2.setText("Estudantes com candidatura registada:\n"+poEManager.listStudentsApplied());
        });

        menuItem7.setOnAction(actionEvent -> {
            textArea2.setText("Estudantes sem candidatura registada:\n"+poEManager.listStudentsNotApplied());
        });

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
                    poEManager.insertApplicationsFromFile(hFile.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            update();
        });

        btnExportCSV.setOnAction( event -> {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Exportar para Ficheiro CSV");
            tid.setContentText("Ficheiro de Candidaturas a exportar");
            tid.setHeaderText("Candidaturas");
            tid.showAndWait().ifPresent(response ->{
                try {
                    poEManager.exportApplicationsToFile(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });

        btnInsertManual.setOnAction( event -> {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Inserir Candidatura Manualmente");
            tid.setContentText("Inserção Manual");
            tid.setHeaderText("Candidatura no formato:\n"+"Número de Estudante,Código da Proposta 1, Código da Proposta 2, etc.");
            tid.showAndWait().ifPresent(response ->{
                try {
                    poEManager.manualInsertApplication(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            update();
        });

        btnRemoveManual.setOnAction( event -> {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Remover Candidatura Manualmente");
            tid.setContentText("Remoção Manual");
            tid.setHeaderText("Numero de aluno");
            tid.showAndWait().ifPresent(response ->{
                try {
                    poEManager.manualRemoveApplication(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            update();
        });

        btnRemoveAll.setOnAction( event -> {
            try {
                poEManager.removeAllApplications();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

        btnGoBack.setOnAction( event -> {
            poEManager.backward();
            update();
        });

        btnExit.setOnAction( event -> {
            Platform.exit();
        });
    }

    private void update() {

        textArea.setText(poEManager.listApplications()); //candidaturas
        textArea2.clear();

        if(poEManager.isState2Closed()){
            btnImportCSV.setDisable(true);
            btnClose.setDisable(true);
            btnInsertManual.setDisable(true);
            btnEditManual.setDisable(true);
            btnRemoveManual.setDisable(true);
            btnRemoveAll.setDisable(true);
        }

        if (poEManager.getState() != StateEnum.PHASE2) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
