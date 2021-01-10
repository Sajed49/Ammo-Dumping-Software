package controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import services.Factory;

import java.net.URL;
import java.util.ResourceBundle;

public class TimeCalController implements Initializable {
    @FXML
    JFXTimePicker firstLight = new JFXTimePicker();
    @FXML
    JFXTimePicker lastLight = new JFXTimePicker();
    @FXML
    JFXTimePicker dumpingStartTime = new JFXTimePicker();
    @FXML
    JFXTimePicker dumpingEndTime = new JFXTimePicker();

    @FXML
    JFXDatePicker dumpingStartDate = new JFXDatePicker();
    @FXML
    JFXDatePicker dumpingEndDate = new JFXDatePicker();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        intiAllDateAndTime();

    }

    private void intiAllDateAndTime() {

        Factory.initClock(firstLight);
        Factory.initClock(lastLight);
        Factory.initClock(dumpingStartTime);
        Factory.initClock(dumpingEndTime);

        Factory.initDate(dumpingStartDate);
        Factory.initDate(dumpingEndDate);
    }


}
