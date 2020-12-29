package main;

import Constants.IConstants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static Scene scene;
    public static Stage stage;

    private static Parent loadFXML(Object obj, String name) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(obj.getClass().getResource(name + ".fxml"));
        return fxmlLoader.load();
    }

    public static void setRoot(Object obj, String fxml) throws IOException {
        scene.setRoot(loadFXML(obj, fxml));
    }


    public static void main(String[] args) {
        loadNecessaryStuffs();
        launch();

    }

    private static void loadNecessaryStuffs() {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
            getErrorPopUp();
        }
    }

    private static void getErrorPopUp() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("You have encountered an error");
        alert.showAndWait();
    }


    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        scene = new Scene(loadFXML(new App(), "App"), 1100, 800);
        App.stage.setScene(scene);
        App.stage.getIcons().add(new Image(IConstants.logo));
        App.stage.setTitle(IConstants.applicationTitle);
        App.stage.show();
    }
}