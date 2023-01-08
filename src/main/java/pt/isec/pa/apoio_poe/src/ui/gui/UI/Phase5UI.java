package pt.isec.pa.apoio_poe.src.ui.gui.UI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pt.isec.pa.apoio_poe.src.model.PoEManager;
import pt.isec.pa.apoio_poe.src.model.fsm.StateEnum;

import java.util.LinkedHashMap;
import java.util.Map;

public class Phase5UI extends BorderPane {
    PoEManager poEManager;
    Button btnExportCSV,btnExit;
    TextArea textArea, textArea2;
    Label label, label1, label2, label5, label6, label7;
    MenuButton menuButton1, menuButton2;
    MenuItem menuItem1, menuItem2, menuItem5, menuItem6;
    PieChart pieChart, pieChart1;
    BarChart<Integer,String> barChart1, barChart2;

    private static final int BUTTONSIZE = 450;
    private static final int MINWIDTH = 150;
    private static final int SPACING = 10;

    public Phase5UI(PoEManager poEManager) throws CloneNotSupportedException {
        this.poEManager = poEManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        btnExportCSV = new Button("Exportar para ficheiro CSV");
        btnExportCSV.setPrefWidth(BUTTONSIZE);
        btnExit  = new Button("Sair do Programa");
        btnExit.setPrefWidth(BUTTONSIZE);
        menuButton1 = new MenuButton("Mostrar Propostas");
        menuButton1.setMnemonicParsing(true);
        menuButton1.setPrefWidth(BUTTONSIZE);
        menuButton1.setAlignment(Pos.CENTER);
        label1 = new Label("Propostas disponíveis");
        label1.prefWidthProperty().bind(menuButton1.widthProperty());
        label1.setAlignment(Pos.TOP_RIGHT);
        label2 = new Label("Propostas atribuídas");
        label2.prefWidthProperty().bind(menuButton1.widthProperty());
        label2.setAlignment(Pos.TOP_RIGHT);

        menuItem1 = new MenuItem("",label1);
        menuItem2 = new MenuItem("",label2);

        menuButton2 = new MenuButton("Mostrar Estudantes");
        menuButton2.setMnemonicParsing(true);
        menuButton2.setPrefWidth(BUTTONSIZE);
        menuButton2.setAlignment(Pos.CENTER);
        label5 = new Label("Estudantes com propostas atribuídas");
        label5.prefWidthProperty().bind(menuButton2.widthProperty());
        label5.setAlignment(Pos.TOP_RIGHT);
        label6 = new Label("Estudantes sem propostas atribuídas e com opções de candidatura");
        label6.prefWidthProperty().bind(menuButton2.widthProperty());
        label6.setAlignment(Pos.TOP_RIGHT);
        menuItem5 = new MenuItem("",label5);
        menuItem6 = new MenuItem("",label6);

        menuButton1.getItems().addAll(menuItem1,menuItem2);
        menuButton2.getItems().addAll(menuItem5,menuItem6);

        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setVisible(true);
        textArea.setMinWidth(MINWIDTH);
        textArea2 = new TextArea();
        textArea2.setEditable(false);
        textArea2.setVisible(true);
        textArea2.setMinWidth(MINWIDTH);
        label = new Label("Estatísticas dos Docentes");
        label.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));

        pieChart = new PieChart();
        pieChart.setLabelLineLength(10);
        pieChart.setLegendSide(Side.LEFT);

        pieChart1 = new PieChart();
        pieChart1.setLabelLineLength(10);
        pieChart1.setLegendSide(Side.LEFT);

        final CategoryAxis yAxis = new CategoryAxis();
        final NumberAxis xAxis = new NumberAxis(0,5,1);
        barChart1 = new BarChart(xAxis, yAxis);
        xAxis.setLabel("Valor");
        yAxis.setLabel("Docente");
        xAxis.setTickLabelRotation(90);

        final CategoryAxis yAxis1 = new CategoryAxis();
        final NumberAxis xAxis1 = new NumberAxis(0,5,1);
        barChart2 = new BarChart(xAxis1, yAxis1);
        xAxis1.setLabel("Valor");
        yAxis1.setLabel("Empresa");
        xAxis1.setTickLabelRotation(90);

        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getChildren().addAll(label,textArea,textArea2);
        VBox.setMargin(label,new Insets(10));
        VBox.setMargin(textArea,new Insets(10));
        VBox.setMargin(textArea2,new Insets(10));

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(SPACING);
        vBox.getChildren().addAll(btnExportCSV,menuButton1, menuButton2,btnExit,vBox2);

        VBox vBox3 = new VBox();
        vBox3.setAlignment(Pos.CENTER);
        vBox3.getChildren().addAll(pieChart,pieChart1, barChart1, barChart2);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(SPACING);
        hBox.getChildren().addAll(vBox,vBox3);

        label7 = new Label("FASE 5 - CONSULTA");
        label7.setBackground(new Background(new BackgroundFill(Color.MINTCREAM,CornerRadii.EMPTY, Insets.EMPTY)));
        label7.setFont(new Font("Arial", 30));

        this.setCenter(hBox);
        this.setTop(label7);
    }

    private void registerHandlers() {

        poEManager.addPropertyChangeListener(evt -> {
            try {
                update();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });

        menuItem1.setOnAction(actionEvent -> {
            textArea2.setText("Propostas disponíveis:\n"+poEManager.listProposalsFilteredPhase3and5(new boolean[]{false,false,true,false}));
        });

        menuItem2.setOnAction(actionEvent -> {
            textArea2.setText("Propostas atribuídas:\n"+poEManager.listProposalsFilteredPhase3and5(new boolean[]{false,false,false,true}));
        });

        menuItem5.setOnAction(actionEvent -> {
            textArea2.setText("Estudantes com propostas atribuídas:\n"+poEManager.listStudentsAttributed());
        });

        menuItem6.setOnAction(actionEvent -> {
            textArea2.setText("Estudantes sem propostas atribuídas e com opções de candidatura:\n"+poEManager.listStudentsNotAttributedWithApplications());
        });

        btnExportCSV.setOnAction( event -> {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle("Exportar para Ficheiro CSV");
            tid.setContentText("Ficheiro de Atribuições de Docentes a exportar");
            tid.setHeaderText("Atribuições");
            tid.showAndWait().ifPresent(response ->{
                try {
                    poEManager.exportSupervisorAttributionsToFile(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });

        btnExit.setOnAction( event -> {
            Platform.exit();
        });
    }

    private void update() throws CloneNotSupportedException {
        textArea.setText(poEManager.listSupervisorStats());
        textArea2.clear();

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("RAS",poEManager.getNumberProposalAttributionPerAbbField("RAS")),
                        new PieChart.Data("SI",poEManager.getNumberProposalAttributionPerAbbField("SI")),
                        new PieChart.Data("DA",poEManager.getNumberProposalAttributionPerAbbField("DA"))
                );
        pieChart.setData(pieChartData);

        ObservableList<PieChart.Data> pieChartData1 =
                FXCollections.observableArrayList(
                        new PieChart.Data("Propostas\nAtribuídas",poEManager.getNumberProposalsAttributed()),
                        new PieChart.Data("Propostas não Atribuídas",poEManager.getNumberProposalsAvailable())
                );
        pieChart1.setData(pieChartData1);

        XYChart.Series<Integer,String> series1 = new XYChart.Series<>();
        LinkedHashMap<String,Integer> linkedMap= poEManager.getTeachersOrderedByNumberOfProposalsAttributed();
        int count = 0;
        if(linkedMap!=null){
            for(Map.Entry<String,Integer> entry: linkedMap.entrySet()){
                String v = entry.getKey();
                Integer k = (entry.getValue());

                series1.getData().add(new XYChart.Data<>(k,v));

                count++;
                if(count ==5)
                    break;
            }
        }
        barChart1.getData().clear();
        barChart1.getData().add(series1);

        XYChart.Series<Integer,String> series2 = new XYChart.Series<>();
        LinkedHashMap<String,Integer> linkedMap1= poEManager.getCompaniesOrderedByNumberOfInternshipsAttributed();
        int count1 = 0;
        if(linkedMap1!=null){
            for(Map.Entry<String,Integer> entry: linkedMap1.entrySet()){
                String v = entry.getKey();
                Integer k = (entry.getValue());

                series2.getData().add(new XYChart.Data<>(k,v));

                count1++;
                if(count1 ==5)
                    break;
            }
        }
        barChart2.getData().clear();
        barChart2.getData().add(series2);

        if (poEManager.getState() != StateEnum.PHASE5) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);

        if(linkedMap == null)
            return;

        if(linkedMap1==null)
            return;
    }
}
