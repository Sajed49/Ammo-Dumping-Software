package controllers;

import Constants.IComboConstants;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import services.ComboService;
import services.FactoryService;

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

    @FXML
    JFXComboBox <Label> shortHaltTime = new JFXComboBox<>();
    @FXML
    JFXComboBox <Label> longHaltTime = new JFXComboBox<>();
    @FXML
    JFXComboBox <Label> shortHaltAfterHour = new JFXComboBox<>();
    @FXML
    JFXComboBox <Label> longHaltAfterHour = new JFXComboBox<>();
    @FXML
    JFXComboBox <Label> timeReqToLoad = new JFXComboBox<>();
    @FXML
    JFXComboBox <Label> contingencyTime = new JFXComboBox<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        intiAllDateAndTime();

        initComboBoxes();

    }

    private void initComboBoxes(){

        ComboService.initComboBox(shortHaltTime, IComboConstants.shortHaltDuration );
        ComboService.initComboBox(longHaltTime, IComboConstants.longHaltDuration );
        ComboService.initComboBox(shortHaltAfterHour, IComboConstants.shortHaltAfterHour );
        ComboService.initComboBox(longHaltAfterHour, IComboConstants.longHaltAfterHour );
        ComboService.initComboBox(timeReqToLoad, IComboConstants.timeReqToLoad );
        ComboService.initComboBox(contingencyTime, IComboConstants.contingencyTime );
    }

    private void intiAllDateAndTime() {

        FactoryService.initClock(firstLight);
        FactoryService.initClock(lastLight);
        FactoryService.initClock(dumpingStartTime);
        FactoryService.initClock(dumpingEndTime);
        FactoryService.initDate(dumpingStartDate);
        FactoryService.initDate(dumpingEndDate);
    }


}
