package gce.module02;

import gce.module02.model.Data;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainStage.fxml"));
        primaryStage.setTitle("Castaneda - CEN4025C - Module 02");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        try {
            Data.getInstance().loadItems();
        } catch (Exception e) {
            System.out.println("");
        }
    }

    @Override
    public void stop() {
        try {
            Data.getInstance().saveItems();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
