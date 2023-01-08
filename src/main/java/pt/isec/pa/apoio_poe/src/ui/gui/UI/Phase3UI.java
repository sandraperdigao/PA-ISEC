package pt.isec.pa.apoio_poe.src.ui.gui.UI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pt.isec.pa.apoio_poe.src.model.PoEManager;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;
import pt.isec.pa.apoio_poe.src.ui.gui.resources.ImageManager;

import java.util.Objects;

public class Phase3UI extends BorderPane {
    PoEManager poEManager;
    Button btnAutoAssignProposalsStudents,btnAutoAttributionStudent,btnManualAttributionStudents, btnExportCSV, btnClose,btnContinue,btnGoBack, btnExit, btnUndo, btnRedo,  btnRemoveManual, btnRemoveAll;
    TextArea textArea, textArea1;
    Label label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11;
    MenuButton menuButton1, menuButton2;
    MenuItem menuItem1, menuItem2, menuItem3, menuItem4, menuItem5, menuItem6, menuItem7, menuItem8;
    ButtonBar buttonBar;

    private static final int BUTTONSIZE = 450;
    public static final int BUTTON_HEIGHT = 40;
    private static final int SMALLBUTTONSIZE = 150;
    private static final int MINWIDTH = 150;
    private static final int SPACING = 10;

    public Phase3UI(PoEManager poEManager) {
        this.poEManager = poEManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnAutoAssignProposalsStudents = new Button("Alunos-Autopropostas");
        btnAutoAssignProposalsStudents.setPrefWidth(BUTTONSIZE);
        btnAutoAttributionStudent = new Button("Alunos-Propostas sem orientador associado");
        btnAutoAttributionStudent.setPrefWidth(BUTTONSIZE);
        btnManualAttributionStudents = new Button("Proposta-Aluno");
        btnManualAttributionStudents.setPrefWidth(BUTTONSIZE);
        btnExportCSV = new Button("Exportar para ficheiro CSV");
        btnExportCSV.setPrefWidth(BUTTONSIZE);
        btnClose = new Button("Fechar Fase");
        btnClose.setPrefWidth(SMALLBUTTONSIZE);
        btnContinue = new Button("Avançar Fase >>>");
        btnContinue.setPrefWidth(SMALLBUTTONSIZE);
        btnGoBack = new Button("Recuar Fase <<<");
        btnGoBack.setPrefWidth(SMALLBUTTONSIZE);
        btnExit  = new Button("Sair do Programa");
        btnExit.setPrefWidth(SMALLBUTTONSIZE);
        btnRemoveManual = new Button("Remover Manualmente");
        btnRemoveManual.setPrefWidth(BUTTONSIZE);
        btnRemoveAll = new Button("Remover Todos");
        btnRemoveAll.setPrefWidth(BUTTONSIZE);
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
        label3 = new Label("Propostas atribuídas");
        label3.prefWidthProperty().bind(menuButton1.widthProperty());
        label3.setAlignment(Pos.TOP_RIGHT);
        label4 = new Label("Propostas disponíveis");
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
        label5 = new Label("Estudantes com proposta atribuída");
        label5.prefWidthProperty().bind(menuButton2.widthProperty());
        label5.setAlignment(Pos.TOP_RIGHT);
        label6 = new Label("Estudantes sem proposta atribuída");
        label6.prefWidthProperty().bind(menuButton2.widthProperty());
        label6.setAlignment(Pos.TOP_RIGHT);
        label7 = new Label("Estudantes com AutoProposta");
        label7.prefWidthProperty().bind(menuButton2.widthProperty());
        label7.setAlignment(Pos.TOP_RIGHT);
        label8 = new Label("Estudantes com Candidatura Registada");
        label8.prefWidthProperty().bind(menuButton2.widthProperty());
        label8.setAlignment(Pos.TOP_RIGHT);
        menuItem5 = new MenuItem("",label5);
        menuItem6 = new MenuItem("",label6);
        menuItem7 = new MenuItem("",label7);
        menuItem8 = new MenuItem("",label8);

        menuButton1.getItems().addAll(menuItem1,menuItem2,menuItem3,menuItem4);
        menuButton2.getItems().addAll(menuItem5,menuItem6,menuItem7,menuItem8);

        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setVisible(true);
        textArea.setMinWidth(MINWIDTH);
        textArea1 = new TextArea();
        textArea1.setEditable(false);
        textArea1.setVisible(true);
        textArea1.setMinWidth(MINWIDTH);
        label9 = new Label("Associar Automaticamente:");
        label10 = new Label("Associar Manualmente:");
        label9.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));
        label10.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));

        Image image = ImageManager.getImage("undo.png");
        ImageView imgView1 = new ImageView(image);
        imgView1.setPreserveRatio(true);
        imgView1.setFitHeight(BUTTON_HEIGHT - 5);
        btnUndo = new Button("Undo",imgView1);
        btnUndo.setPrefHeight(BUTTON_HEIGHT);
        Image image2 = ImageManager.getImage("redo.png");
        ImageView imgView2 = new ImageView(image2);
        imgView2.setPreserveRatio(true);
        imgView2.setFitHeight(BUTTON_HEIGHT - 5);
        btnRedo = new Button("Redo",imgView2);
        btnRedo.setPrefHeight(BUTTON_HEIGHT);

        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        hBox2.getChildren().addAll(btnUndo,btnRedo);
        HBox.setMargin(btnUndo,new Insets(SPACING/2));
        HBox.setMargin(btnRedo,new Insets(SPACING/2));

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(SPACING);
        vBox.getChildren().addAll(label9,btnAutoAssignProposalsStudents,btnAutoAttributionStudent,label10, btnManualAttributionStudents,btnRemoveManual,btnRemoveAll,hBox2,btnExportCSV);

        buttonBar= new ButtonBar();
        buttonBar.getButtons().addAll(btnGoBack, new Separator(Orientation.VERTICAL), btnContinue, new Separator(Orientation.VERTICAL), btnClose, new Separator(Orientation.VERTICAL), btnExit);
        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setBackground((new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY))));
        hBox1.setBorder(new Border(new BorderStroke(Color.LIGHTGREY,BorderStrokeStyle.SOLID, null , null)));
        hBox1.getChildren().add(buttonBar);
        hBox1.setPadding(Insets.EMPTY);
        HBox.setMargin(buttonBar,new Insets(SPACING/2));

        VBox vBox1 = new VBox();
        vBox1.setAlignment(Pos.CENTER);
        vBox1.getChildren().addAll(menuButton1, textArea,  menuButton2, textArea1);
        VBox.setMargin(textArea,new Insets(10));
        VBox.setMargin(textArea1,new Insets(10));

        VBox.setMargin(label9,new Insets(10));
        VBox.setMargin(label10,new Insets(10));

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(SPACING);
        hBox.getChildren().addAll(vBox,vBox1);

        label11 = new Label("FASE 3 - ATRIBUIÇÃO DE PROPOSTAS");
        label11.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));
        label11.setFont(new Font("Arial", 30));

        this.setCenter(hBox);
        this.setBottom(hBox1);
        this.setTop(label11);
    }

    private void registerHandlers() {
        poEManager.addPropertyChangeListener(evt -> { update(); });

        menuItem1.setOnAction(actionEvent -> {
            textArea.setText("Autopropostas de alunos:\n"+poEManager.listProposalsFilteredPhase3and5(new boolean[]{true,false,false,false})); //Autopropostas de alunos
        });

        menuItem2.setOnAction(actionEvent -> {
            textArea.setText("Propostas de docentes:\n"+poEManager.listProposalsFilteredPhase3and5(new boolean[]{false,true,false,false})); //propostas de docentes
        });

        menuItem3.setOnAction(actionEvent -> {
            textArea.setText("Propostas atribuídas:\n"+poEManager.listProposalsFilteredPhase3and5(new boolean[]{false,false,false,true})); //propostas atribuidas
        });

        menuItem4.setOnAction(actionEvent -> {
            textArea.setText("Propostas disponíveis:\n"+poEManager.listProposalsFilteredPhase3and5(new boolean[]{false,false,true,false})); //propostas disponiveis
        });

        menuItem5.setOnAction(actionEvent -> {
            textArea1.setText("Estudantes com proposta atribuída:\n"+poEManager.listStudentsAttributed()); //estudantes com proposta atribuida
        });

        menuItem6.setOnAction(actionEvent -> {
            textArea1.setText("Estudantes sem proposta atribuída:\n"+poEManager.listStudentsNotAttributed()); //estudantes sem proposta atribuida
        });

        menuItem7.setOnAction(actionEvent -> {
            textArea1.setText("Estudantes com autoproposta:\n"+poEManager.listStudentsAuto()); //estudantes com autoproposta
        });

        menuItem8.setOnAction(actionEvent -> {
            textArea1.setText("Estudantes com candidatura registada:\n"+poEManager.listStudentsApplied()); //estudantes com candidatura registada
        });

        btnAutoAssignProposalsStudents.setOnAction( event -> {
            try {
                poEManager.autoAssignProposalsStudents();
            } catch (Exception e) {
                e.printStackTrace();
            }
            update();
        });

        btnAutoAttributionStudent.setOnAction( event -> {
            try {
                poEManager.autoAttributionStudent();
            } catch (Exception e) {
                e.printStackTrace();
            }
            update();
        });

        btnManualAttributionStudents.setOnAction( event -> {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Atribuição Manual de Estudante a Proposta");
            dialog.setHeaderText("Insira o código da proposta e o número de estudante a atribuir");
            dialog.setResizable(true);

            Label label1 = new Label("Numero de estudante: ");
            Label label2 = new Label("Código da proposta: ");
            TextField text1 = new TextField();
            TextField text2 = new TextField();

            GridPane grid = new GridPane();
            grid.add(label1, 1, 1);
            grid.add(text1, 2, 1);
            grid.add(label2, 1, 2);
            grid.add(text2, 2, 2);
            dialog.getDialogPane().setContent(grid);

            ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

            dialog.showAndWait();

                String nrStudentAsString = text1.getText();
                String codeProposal = text2.getText();
                try {
                    if (!Objects.equals(nrStudentAsString, "") && !Objects.equals(codeProposal, ""))
                        poEManager.manualAssignAttribution(nrStudentAsString, codeProposal);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            update();
        });

        btnRemoveManual.setOnAction( event -> {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Remoção Manual de Estudante a Proposta");
            dialog.setHeaderText("Insira o número de estudante a remover da proposta e o código da proposta");
            dialog.setResizable(true);

            Label label1 = new Label("Numero de estudante: ");
            Label label2 = new Label("Código da proposta: ");
            TextField text1 = new TextField();
            TextField text2 = new TextField();

            GridPane grid = new GridPane();
            grid.add(label1, 1, 1);
            grid.add(text1, 2, 1);
            grid.add(label2, 1, 2);
            grid.add(text2, 2, 2);
            dialog.getDialogPane().setContent(grid);

            ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

            dialog.showAndWait();

            String nrStudentAsString = text1.getText();
            String codeProposal = text2.getText();
            try {
                if (!Objects.equals(nrStudentAsString, "") && !Objects.equals(codeProposal, ""))
                    poEManager.manualUnassignAttribution(nrStudentAsString, codeProposal);
            } catch (Exception e) {
                e.printStackTrace();
            }

            update();
        });

        btnRemoveAll.setOnAction( event -> {
            try {
                poEManager.removeAllAttributions();
            } catch (Exception e) {
                e.printStackTrace();
            }
            update();
        });

        btnExportCSV.setOnAction( event -> {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Exportar para Ficheiro CSV");
            tid.setContentText("Ficheiro de Atribuições a exportar");
            tid.setHeaderText("Atribuições");
            tid.showAndWait().ifPresent(response ->{
                try {
                    poEManager.exportAttributionsToFile(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
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

        btnUndo.setOnAction( event -> {
            try {
                poEManager.undoPhase3();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnRedo.setOnAction( event -> {
            try {
                poEManager.redoPhase3();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnExit.setOnAction( event -> {
            Platform.exit();
        });
    }

    private void update() {
        textArea.clear();
        textArea1.clear();
        if(!poEManager.isState2Closed() && !poEManager.isState3Closed()){
            btnAutoAttributionStudent.setDisable(true);
            btnManualAttributionStudents.setDisable(true);
        } else if((!poEManager.isState2Closed() && poEManager.isState3Closed()) || (poEManager.isState2Closed() && poEManager.isState3Closed())){
            btnAutoAttributionStudent.setDisable(true);
            btnAutoAssignProposalsStudents.setDisable(true);
            btnManualAttributionStudents.setDisable(true);
            btnRemoveAll.setDisable(true);
            btnRemoveManual.setDisable(true);
            btnUndo.setDisable(true);
            btnRedo.setDisable(true);
            btnClose.setDisable(true);
        }

        if (poEManager.getState() != StateEnum.PHASE3) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }


}
