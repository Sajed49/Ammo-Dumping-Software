package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class UnitInfoController  implements Initializable {

    @FXML
    VBox vBox = new VBox();

    ArrayList<UnitController> unitControllers = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            addUnit();
            addUnit();
            addUnit();

            for (int i = 0; i < unitControllers.size(); i++) {
                unitControllers.get(i).setSer(i+1);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addUnit() throws IOException {

        FXMLLoader loader =  new FXMLLoader( getClass().getResource("Unit.fxml"));
        GridPane pane = loader.load();
        unitControllers.add( loader.getController() );
        vBox.getChildren().add( pane );
//        GridPane newLoadedPane = FXMLLoader.load( getClass().getResource("Unit.fxml"));
//        vBox.getChildren().add( newLoadedPane );


    }
}
