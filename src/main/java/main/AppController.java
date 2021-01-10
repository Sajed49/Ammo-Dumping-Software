package main;

import com.jfoenix.controls.JFXButton;
import controllers.GenInfoController;
import controllers.TimeCalController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AppController implements Initializable {

    @FXML
    JFXButton genInfo = new JFXButton();



    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    @FXML
    private void showGenInfo() throws IOException {
        App.setRoot( new GenInfoController(), "GenInfo");
    }

    @FXML
    private void showTimeCal() throws IOException {
        App.setRoot( new TimeCalController(), "TimeCal");
    }




    @FXML
    private void saveGenInfo() {

    }
}
