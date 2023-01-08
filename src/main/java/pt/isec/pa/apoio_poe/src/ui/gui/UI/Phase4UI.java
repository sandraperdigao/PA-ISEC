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

public class Phase4UI extends BorderPane {
    PoEManager poEManager;
    Button btnAutoAssignProposalsTeachers,btnManualAssignTeacher, btnExportCSV, btnClose, btnGoBack,btnExit, btnUndo, btnRedo, btnRemoveManual, btnRemoveAll;
    TextArea textArea,textArea2,textArea3,textArea4;
    Label label, label2, label3, label4, label5, label6, label7;
    ButtonBar buttonBar;

    private static final int BUTTONSIZE = 450;
    public static final int BUTTON_HEIGHT = 40;
    private static final int SMALLBUTTONSIZE = 150;
    private static final int MINWIDTH = 150;
    private static final int SPACING = 10;

    public Phase4UI(PoEManager poEManager) throws CloneNotSupportedException {
        this.poEManager = poEManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() throws CloneNotSupportedException {
        btnAutoAssignProposalsTeachers = new Button("Orientadores-Autopropostas");
        btnAutoAssignProposalsTeachers.setPrefWidth(BUTTONSIZE);
        btnManualAssignTeacher = new Button("Orientadores-Estudantes c/propostas atribuídas");
        btnManualAssignTeacher.setPrefWidth(BUTTONSIZE);
        btnExportCSV = new Button("Exportar para ficheiro CSV");
        btnExportCSV.setPrefWidth(BUTTONSIZE);
        btnClose = new Button("Fechar Fase");
        btnClose.setPrefWidth(SMALLBUTTONSIZE);
        btnGoBack = new Button("Recuar Fase <<<");
        btnGoBack.setPrefWidth(SMALLBUTTONSIZE);
        btnExit  = new Button("Sair do Programa");
        btnExit.setPrefWidth(SMALLBUTTONSIZE);
        btnRemoveManual = new Button("Remover Manualmente");
        btnRemoveManual.setPrefWidth(BUTTONSIZE);
        btnRemoveAll = new Button("Remover Todos");
        btnRemoveAll.setPrefWidth(BUTTONSIZE);

        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setVisible(true);
        textArea.setMinWidth(MINWIDTH);
        textArea2 = new TextArea();
        textArea2 .setEditable(false);
        textArea2 .setVisible(true);
        textArea2 .setMinWidth(MINWIDTH);
        textArea3 = new TextArea();
        textArea3 .setEditable(false);
        textArea3 .setVisible(true);
        textArea3 .setMinWidth(MINWIDTH);
        textArea4 = new TextArea();
        textArea4 .setEditable(false);
        textArea4 .setVisible(true);
        textArea4 .setMinWidth(MINWIDTH);

        label = new Label("Orientadores");
        label2 = new Label("Estudantes com proposta atribuída com orientador associado");
        label3 = new Label("Estudantes com proposta atribuída sem orientador associado");
        label4 = new Label("Estatísticas dos Docentes");
        label5 = new Label("Associar Automaticamente:");
        label6 = new Label("Associar Manualmente:");

        label.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));
        label2.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));
        label3.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));
        label4.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));
        label5.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));
        label6.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));

        buttonBar= new ButtonBar();
        buttonBar.getButtons().addAll(btnGoBack,new Separator(Orientation.VERTICAL), btnClose, new Separator(Orientation.VERTICAL), btnExit);
        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setBackground((new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY))));
        hBox1.setBorder(new Border(new BorderStroke(Color.LIGHTGREY,BorderStrokeStyle.SOLID, null , null)));
        hBox1.getChildren().add(buttonBar);
        hBox1.setPadding(Insets.EMPTY);
        HBox.setMargin(buttonBar,new Insets(SPACING/2));

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
        vBox.getChildren().addAll(label5,btnAutoAssignProposalsTeachers,label6, btnManualAssignTeacher,btnRemoveManual,btnRemoveAll,hBox2,btnExportCSV);

        VBox vBox1 = new VBox();
        vBox1.setAlignment(Pos.CENTER);
        vBox1.getChildren().addAll(label, textArea,label4,textArea4, label3,textArea3,label2,textArea2);
        VBox.setMargin(label,new Insets(10));
        VBox.setMargin(textArea,new Insets(10));
        VBox.setMargin(label2,new Insets(10));
        VBox.setMargin(textArea2,new Insets(10));
        VBox.setMargin(label3,new Insets(10));
        VBox.setMargin(textArea3,new Insets(10));
        VBox.setMargin(label4,new Insets(10));
        VBox.setMargin(textArea4,new Insets(10));

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(SPACING);
        hBox.getChildren().addAll(vBox,vBox1);

        label7 = new Label("FASE 4 - ATRIBUIÇÃO DE ORIENTADORES");
        label7.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));
        label7.setFont(new Font("Arial", 30));

        this.setCenter(hBox);
        this.setBottom(hBox1);
        this.setTop(label7);

        textArea.setText(poEManager.listSupervisors()); //Listar Orientadores
        textArea4.setText(poEManager.listSupervisorStats()); //Número de orientações por docente, em média, mínimo, máximo, e por docente especificado
        textArea2.setText(poEManager.listStudentsAssignedWithSupervisor()); //Apresentar estudantes com proposta atribuída e com orientador associado
        textArea3.setText(poEManager.listStudentsAssignedWithoutSupervisor()); //Apresentar estudantes com proposta atribuída mas sem orientador associado

    }

    private void registerHandlers() {
        poEManager.addPropertyChangeListener(evt -> {
            try {
                update();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        });

        btnAutoAssignProposalsTeachers.setOnAction( event -> {
            poEManager.autoAssignProposalsTeachers();
            try {
                update();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        });

        btnManualAssignTeacher.setOnAction( event -> {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Atribuição Manual de Orientadores a Estudantes com propostas atribuídas");
            dialog.setHeaderText("Insira o número de estudante e email de orientador a atribuir");
            dialog.setResizable(true);

            Label label1 = new Label("Numero de estudante: ");
            Label label2 = new Label("Email do Orientador: ");
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
            String teacherEmail = text2.getText();
            try {
                if (!Objects.equals(nrStudentAsString, "") && !Objects.equals(teacherEmail, ""))
                    poEManager.manualAssignTeacher(nrStudentAsString, teacherEmail);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                update();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }

        });

        btnRemoveManual.setOnAction( event -> {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Remoção Manual de Orientador a Proposta com Estudante atribuído");
            dialog.setHeaderText("Insira o número de estudante e o email do orientador");
            dialog.setResizable(true);

            Label label1 = new Label("Numero de estudante: ");
            Label label2 = new Label("Email do orientador: ");
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
            String teacherEmail = text2.getText();
            try {
                if (!Objects.equals(nrStudentAsString, "") && !Objects.equals(teacherEmail, ""))
                    poEManager.manualUnassignSupervisorAttribution(nrStudentAsString, teacherEmail);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                update();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });

        btnRemoveAll.setOnAction( event -> {
            try {
                poEManager.removeAllSupervisorAttributions();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                update();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });

        btnExportCSV.setOnAction( event -> {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Exportar para Ficheiro CSV");
            tid.setContentText("Ficheiro de Atribuições de Docentes a exportar");
            tid.setHeaderText("Atribuições");
            tid.showAndWait().ifPresent(response ->{
                poEManager.exportSupervisorAttributionsToFile(response);
            });
        });

        btnClose.setOnAction( event -> {
            try {
                poEManager.closePhase();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                update();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        });

        btnGoBack.setOnAction( event -> {
            try {
                poEManager.backward();
                update();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        });

        btnUndo.setOnAction( event -> {
            try {
                poEManager.undoPhase4();
                update();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnRedo.setOnAction( event -> {
            try {
                poEManager.redoPhase4();
                update();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnExit.setOnAction( event -> {
            Platform.exit();
        });
    }

    private void update() throws CloneNotSupportedException {
        textArea.setText(poEManager.listSupervisors()); //Listar Orientadores
        textArea2.setText(poEManager.listStudentsAssignedWithSupervisor()); //Apresentar estudantes com proposta atribuída e com orientador associado
        textArea3.setText(poEManager.listStudentsAssignedWithoutSupervisor()); //Apresentar estudantes com proposta atribuída mas sem orientador associado
        textArea4.setText(poEManager.listSupervisorStats()); //Número de orientações por docente, em média, mínimo, máximo, e por docente especificado

        if(poEManager.isState4Closed()){
            btnAutoAssignProposalsTeachers.setDisable(true);
            btnManualAssignTeacher.setDisable(true);
            btnRemoveManual.setDisable(true);
            btnRemoveAll.setDisable(true);
            btnClose.setDisable(true);
        }

        if (poEManager.getState() != StateEnum.PHASE4) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
