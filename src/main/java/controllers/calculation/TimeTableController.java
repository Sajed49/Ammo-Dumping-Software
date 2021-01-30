package controllers.calculation;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import models.Unit;
import models.breakdown.Breakdown;
import result.DataStore;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TimeTableController implements Initializable {

    @FXML
    VBox vBox = new VBox();
    @FXML
    JFXButton next = new JFXButton("Next");
    @FXML
    JFXButton print = new JFXButton("Print");


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



}
