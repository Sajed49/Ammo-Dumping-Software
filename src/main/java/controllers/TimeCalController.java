package controllers;

import Constants.IComboConstants;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.App;
import models.Day;
import models.TimeCal;
import result.DataStore;
import services.ComboService;
import services.DateService;
import services.TimeService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
    JFXComboBox<Label> timeReqToUnload = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> dumpingExecPeriod = new JFXComboBox<>();
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
        ComboService.initComboBox(timeReqToUnload, IComboConstants.timeReqToLoad);
        ComboService.initComboBox(dumpingExecPeriod, IComboConstants.dumpingExecPeriod);
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

        LocalDateTime reducedForContingency = TimeService.reduceTimeByHours(
                dumpingEndDate, dumpingEndTime, Integer.parseInt(contingencyTime.getValue().getText()));

        if (dumpingExecPeriod.getValue().getText().equals(IComboConstants.dumpingExecPeriod[1])) // only night
            reducedForContingency = LocalDateTime.of(dumpingEndDate.getValue(), dumpingEndTime.getValue());

        long days = DateService.findDays(dumpingStartDate.getValue(), reducedForContingency.toLocalDate());

        System.out.println(reducedForContingency);
        ArrayList<Day> dayInfo = new ArrayList<>();

        if (days == 1) {
            dayInfo.add(new Day(firstLight.getValue(), lastLight.getValue(), dumpingStartTime.getValue(), reducedForContingency.toLocalTime()));
        } else {
            dayInfo.add(new Day(firstLight.getValue(), lastLight.getValue(), dumpingStartTime.getValue(), LocalTime.MAX));
            for (int i = 2; i < days; i++)
                dayInfo.add(new Day(firstLight.getValue(), lastLight.getValue(), LocalTime.MIN, LocalTime.MAX));
            dayInfo.add(new Day(firstLight.getValue(), lastLight.getValue(), LocalTime.MIN, reducedForContingency.toLocalTime()));
        }

        long totalDayTime = 0L, totalNightTime = 0L;
        for (Day day : dayInfo) {
            totalDayTime += day.getDumpingDayTime();
            totalNightTime += (day.getDumpingNightTime1() + day.getDumpingNightTime2());
        }

        long dayTime = TimeService.findTimeDiffInMinutes(firstLight, lastLight);

        dayTimeAvailable.setText(TimeService.formatToDisplay(dayTime));
        nightTimeAvailable.setText(TimeService.formatToDisplay(24 * 60 - dayTime));

        if (dumpingExecPeriod.getValue().getText().equals(IComboConstants.dumpingExecPeriod[1])) {
            totalDayTime = 0L;
            totalNightTime -= (60L * Integer.parseInt(contingencyTime.getValue().getText()));
        }

        totalDayTimeAvailable.setText(TimeService.formatToDisplay(totalDayTime));
        totalNightTimeAvailable.setText(TimeService.formatToDisplay(totalNightTime));
        totalTimeAvailable.setText(TimeService.formatToDisplay(totalDayTime + totalNightTime));


    }

    @FXML
    private void next() throws IOException {

        calculate();

        TimeCal timeCal = new TimeCal(TimeService.display(firstLight), TimeService.display(lastLight), TimeService.display(dumpingStartTime),
                TimeService.display(dumpingEndTime), DateService.display(dumpingStartDate), DateService.display(dumpingEndDate),
                shortHaltTime.getValue().getText(), longHaltTime.getValue().getText(), shortHaltAfterHour.getValue().getText(),
                longHaltAfterHour.getValue().getText(), timeReqToLoad.getValue().getText(), timeReqToUnload.getValue().getText(),
                dumpingExecPeriod.getValue().getText(), contingencyTime.getValue().getText(),
                dayTimeAvailable.getText(), nightTimeAvailable.getText(), totalDayTimeAvailable.getText(),
                totalNightTimeAvailable.getText(), totalTimeAvailable.getText());

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

        ComboService.autoSelectComboBoxValue(shortHaltTime, DataStore.getInstance().getTimeCal().getShortHaltTime());
        ComboService.autoSelectComboBoxValue(longHaltTime, DataStore.getInstance().getTimeCal().getLongHaltTime());
        ComboService.autoSelectComboBoxValue(shortHaltAfterHour, DataStore.getInstance().getTimeCal().getShortHaltAfterHour());
        ComboService.autoSelectComboBoxValue(longHaltAfterHour, DataStore.getInstance().getTimeCal().getLongHaltAfterHour());
        ComboService.autoSelectComboBoxValue(timeReqToLoad, DataStore.getInstance().getTimeCal().getTimeReqToLoad());
        ComboService.autoSelectComboBoxValue(timeReqToUnload, DataStore.getInstance().getTimeCal().getTimeReqToUnload());
        ComboService.autoSelectComboBoxValue(dumpingExecPeriod, DataStore.getInstance().getTimeCal().getDumpingExecPeriod());
        ComboService.autoSelectComboBoxValue(contingencyTime, DataStore.getInstance().getTimeCal().getContingencyTime());
    }


}

