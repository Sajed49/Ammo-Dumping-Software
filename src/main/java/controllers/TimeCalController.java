package controllers;

import Constants.IComboConstants;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.App;
import models.TimeCal;
import result.DataStore;
import services.ComboService;
import services.DateService;
import services.FactoryService;
import services.TimeService;

import java.io.IOException;
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
    JFXComboBox<Label> shortHaltTime = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> longHaltTime = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> shortHaltAfterHour = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> longHaltAfterHour = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> timeReqToLoad = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> contingencyTime = new JFXComboBox<>();


    @FXML
    Label dayTimeAvailable = new Label("");
    @FXML
    Label nightTimeAvailable = new Label("");
    @FXML
    Label totalDayTimeAvailable = new Label("");
    @FXML
    Label totalNightTimeAvailable = new Label("");
    @FXML
    Label totalTimeAvailable = new Label("");


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        intiAllDateAndTime();
        initComboBoxes();

        if (DataStore.getInstance().getTimeCal() != null) loadFromDataStore();

    }

    private void initComboBoxes() {

        ComboService.initComboBox(shortHaltTime, IComboConstants.shortHaltDuration);
        ComboService.initComboBox(longHaltTime, IComboConstants.longHaltDuration);
        ComboService.initComboBox(shortHaltAfterHour, IComboConstants.shortHaltAfterHour);
        ComboService.initComboBox(longHaltAfterHour, IComboConstants.longHaltAfterHour);
        ComboService.initComboBox(timeReqToLoad, IComboConstants.timeReqToLoad);
        ComboService.initComboBox(contingencyTime, IComboConstants.contingencyTime);
    }

    private void intiAllDateAndTime() {

        TimeService.initClock(firstLight);
        TimeService.initClock(lastLight);
        TimeService.initClock(dumpingStartTime);
        TimeService.initClock(dumpingEndTime);
        DateService.initDate(dumpingStartDate);
        DateService.initDate(dumpingEndDate);
    }


    @FXML
    private void calculate() {

        long dayTime = TimeService.findTimeDiffInMinutes(firstLight, lastLight);
        dayTimeAvailable.setText(TimeService.formatToDisplay(dayTime));
        nightTimeAvailable.setText(TimeService.formatToDisplay((24 * 60) - dayTime));

        long dumpingDayTime = 0, dumpingNightTime = 0;
        long totalDumpingTimeInADay = TimeService.findTimeDiffInMinutes(dumpingStartTime, dumpingEndTime);

        long temp1 = TimeService.findTimeDiffInMinutes(firstLight, dumpingStartTime);
        long temp2 = TimeService.findTimeDiffInMinutes(firstLight, dumpingEndTime);
        long temp3 = TimeService.findTimeDiffInMinutes(lastLight, dumpingEndTime);
        long temp4 = TimeService.findTimeDiffInMinutes(lastLight, dumpingStartTime);
        if (temp1 < 0) {

            if (temp2 < 0) dumpingNightTime += totalDumpingTimeInADay;
            else if (temp3 < 0) {
                dumpingDayTime += temp2;
                dumpingNightTime += (-temp1);
            } else {
                dumpingDayTime += dayTime;
                dumpingNightTime += ((-temp1) + temp3);
            }
        } else if (temp4 < 0) {

            if (temp3 < 0) {
                dumpingDayTime += totalDumpingTimeInADay;
            } else {
                dumpingDayTime += (-temp4);
                dumpingNightTime += temp3;
            }
        } else {
            dumpingNightTime += totalDumpingTimeInADay;
        }

        long days = DateService.findDays(dumpingStartDate, dumpingEndDate);


        totalDayTimeAvailable.setText(TimeService.formatToDisplay(days * dumpingDayTime));
        totalNightTimeAvailable.setText(TimeService.formatToDisplay(days * dumpingNightTime));

        totalTimeAvailable.setText(TimeService.formatToDisplay(days * totalDumpingTimeInADay));
    }


    @FXML
    private void next() throws IOException {

        calculate();

        TimeCal timeCal = new TimeCal(TimeService.display(firstLight), TimeService.display(lastLight), TimeService.display(dumpingStartTime),
                TimeService.display(dumpingEndTime), DateService.display(dumpingStartDate), DateService.display(dumpingEndDate),
                shortHaltTime.getValue().getText(), longHaltTime.getValue().getText(), shortHaltAfterHour.getValue().getText(),
                longHaltAfterHour.getValue().getText(), timeReqToLoad.getValue().getText(), contingencyTime.getValue().getText(),
                dayTimeAvailable.getText(), nightTimeAvailable.getText(), totalDayTimeAvailable.getText(),
                totalNightTimeAvailable.getText(), totalTimeAvailable.getText());

        // Singleton class
        DataStore.getInstance().setTimeCal(timeCal);

        App.setRoot(new VehicleStateController(), "VehicleState");
    }


    private void loadFromDataStore() {

        TimeService.setTime(firstLight, DataStore.getInstance().getTimeCal().getFirstLight());
        TimeService.setTime(lastLight, DataStore.getInstance().getTimeCal().getLastLight());
        TimeService.setTime(dumpingStartTime, DataStore.getInstance().getTimeCal().getDumpingStartTime());
        TimeService.setTime(dumpingEndTime, DataStore.getInstance().getTimeCal().getDumpingEndTime());

        DateService.setDate(dumpingStartDate, DataStore.getInstance().getTimeCal().getDumpingStartDate());
        DateService.setDate(dumpingEndDate, DataStore.getInstance().getTimeCal().getDumpingEndDate());

        FactoryService.autoSelectComboBoxValue(shortHaltTime, DataStore.getInstance().getTimeCal().getShortHaltTime());
        FactoryService.autoSelectComboBoxValue(longHaltTime, DataStore.getInstance().getTimeCal().getLongHaltTime());
        FactoryService.autoSelectComboBoxValue(shortHaltAfterHour, DataStore.getInstance().getTimeCal().getShortHaltAfterHour());
        FactoryService.autoSelectComboBoxValue(longHaltAfterHour, DataStore.getInstance().getTimeCal().getLongHaltAfterHour());
        FactoryService.autoSelectComboBoxValue(timeReqToLoad, DataStore.getInstance().getTimeCal().getTimeReqToLoad());
        FactoryService.autoSelectComboBoxValue(contingencyTime, DataStore.getInstance().getTimeCal().getContingencyTime());
    }

}

