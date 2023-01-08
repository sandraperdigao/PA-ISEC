package pt.isec.pa.apoio_poe.src.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.src.log.Logger;
import pt.isec.pa.apoio_poe.src.model.PoEManager;

import static pt.isec.pa.apoio_poe.src.Configs.TEST_ENVIRONMENT;

public class MainJFX extends Application {
    PoEManager poEManager;

    @Override
    public void init() throws Exception {
        super.init();
        //poEManager = new PoEManager(); // here or in the constructor
        Logger.getInstance();
        poEManager = PoEManager.getInstance(TEST_ENVIRONMENT);
    }

    @Override
    public void start(Stage stage) throws Exception {
        configureStage(stage, -1, 1);
        configureStage1(new Stage(), stage.getX()+stage.getWidth(),stage.getY());
    }

    public void configureStage(Stage stage, double x, double y) throws CloneNotSupportedException {
        RootPane root = new RootPane(poEManager);
        Scene scene = new Scene(root,1024,768);
        stage.setScene(scene);
        stage.setTitle("Gestão de projetos e estágios do Departamento de Engenharia Informática e de Sistemas do ISEC");
        stage.setMinWidth(700);
        stage.setMinHeight(400);
        if( x>=0 && y>=0){
            stage.setX(x);
            stage.setY(y);
        }
        stage.show();
    }

    public void configureStage1(Stage stage, double x, double y) throws Exception {
        MenuListAux root = new MenuListAux(poEManager);
        Scene scene = new Scene(root,300,400);
        stage.setScene(scene);
        stage.setTitle("Gestão de projetos e estágios do Departamento de Engenharia Informática e de Sistemas do ISEC");
        stage.setMinWidth(400);
        stage.setMinHeight(400);
        if( x>=0 && y>=0){
            stage.setX(x);
            stage.setY(y);
        }
        stage.show();
    }

}
