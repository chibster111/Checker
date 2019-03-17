package sample;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.awt.*;
import java.util.HashMap;
import java.util.List;


public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}
