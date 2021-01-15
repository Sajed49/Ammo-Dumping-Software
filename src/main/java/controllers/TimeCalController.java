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

        long totalDayLightInADay = TimeService.findTimeDiffInMinutes(firstLight, lastLight);
        long days = DateService.findDays(dumpingStartDate, dumpingEndDate);

        dayTimeAvailable.setText(TimeService.formatToDisplay(totalDayLightInADay));
        nightTimeAvailable.setText(TimeService.formatToDisplay((24 * 60) - totalDayLightInADay));

        long dumpingDayTime = 0L, dumpingNightTime1 = 0L, dumpingNightTime2 = 0L;
        long totalDumpingTimeInADay = TimeService.findTimeDiffInMinutes(dumpingStartTime, dumpingEndTime);
        long darkPhase1 = TimeService.findTimeDiffInMinutes(firstLight, dumpingStartTime);
        long lightPhase1 = TimeService.findTimeDiffInMinutes(firstLight, dumpingEndTime);
        long darkPhase2 = TimeService.findTimeDiffInMinutes(lastLight, dumpingEndTime);
        long darkPhase2Reduction = TimeService.findTimeDiffInMinutes(lastLight, dumpingStartTime);

        if (darkPhase1 < 0) {

            if (lightPhase1 < 0) dumpingNightTime1 = totalDumpingTimeInADay;
            else if (darkPhase2 < 0) {
                dumpingDayTime = lightPhase1;
                dumpingNightTime1 = (-darkPhase1);
            } else {
                dumpingDayTime = totalDayLightInADay;
                dumpingNightTime1 = (-darkPhase1);
                dumpingNightTime2 = darkPhase2;
            }
        } else if (darkPhase2Reduction < 0) {
            if (darkPhase2 < 0) dumpingDayTime += totalDumpingTimeInADay;
            else {
                dumpingDayTime = (-darkPhase2Reduction);
                dumpingNightTime2 = darkPhase2;
            }
        } else {
            dumpingNightTime2 = totalDumpingTimeInADay;
        }

        long totalDumpingDayTime = days * dumpingDayTime;
        long totalDumpingNightTime = days * (dumpingNightTime1 + dumpingNightTime2);

        totalDumpingNightTime -= deductContingencyTimeInNight(dumpingNightTime1, dumpingDayTime, dumpingNightTime2);
        totalDumpingDayTime -= deductContingencyTimeInDay(dumpingNightTime1, dumpingDayTime, dumpingNightTime2);

        totalDayTimeAvailable.setText(TimeService.formatToDisplay(totalDumpingDayTime));
        totalNightTimeAvailable.setText(TimeService.formatToDisplay(totalDumpingNightTime));

        totalTimeAvailable.setText(TimeService.formatToDisplay(totalDumpingDayTime + totalDumpingNightTime));
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
        ComboService.autoSelectComboBoxValue(contingencyTime, DataStore.getInstance().getTimeCal().getContingencyTime());
    }


    private Long deductContingencyTimeInNight(long nightTime1, long dayLightTime, long nightTime2) {

        long contingency = Integer.parseInt(contingencyTime.getValue().getText()) * 60L;
        long result = 0;

        if (contingency > 0) {
            result += Math.min(contingency, nightTime2);
            contingency -= Math.min(contingency, nightTime2);
        }

        if (contingency > 0) {
            contingency -= Math.min(contingency, dayLightTime);
        }

        if (contingency > 0) {
            result += Math.min(contingency, nightTime1);
        }

        return result;
    }


    private Long deductContingencyTimeInDay(long nightTime1, long dayLightTime, long nightTime2) {

        long contingency = Integer.parseInt(contingencyTime.getValue().getText()) * 60L;
        long result = 0;

        if (contingency > 0) {
            contingency -= Math.min(contingency, nightTime2);
        }

        if (contingency > 0) {
            result += Math.min(contingency, dayLightTime);
        }

        return result;
    }


}

