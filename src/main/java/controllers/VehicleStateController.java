package controllers;

import Constants.IComboConstants;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.Data;
import main.App;
import models.VehicleState;
import result.DataStore;
import services.ComboService;
import services.DateService;
import services.TimeService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class VehicleStateController implements Initializable {

    @FXML
    JFXDatePicker availableDateLine1 = new JFXDatePicker();
    @FXML
    JFXDatePicker availableDateLine2 = new JFXDatePicker();
    @FXML
    JFXDatePicker availableDateLine3 = new JFXDatePicker();
    @FXML
    JFXTimePicker availableTimeLine1 = new JFXTimePicker();
    @FXML
    JFXTimePicker availableTimeLine2 = new JFXTimePicker();
    @FXML
    JFXTimePicker availableTimeLine3 = new JFXTimePicker();
    @FXML
    JFXComboBox<Label> availableVehicleLine1 = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> availableVehicleLine2 = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> availableVehicleLine3 = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> vehicleToVehicleDistance = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> density = new JFXComboBox<>();


    @FXML
    Label time1 = new Label();
    @FXML
    Label time2 = new Label();
    @FXML
    Label time3 = new Label();
    @FXML
    Label totalAvailableVehicles1 = new Label();
    @FXML
    Label totalAvailableVehicles2 = new Label();
    @FXML
    Label totalAvailableVehicles3 = new Label();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        intiAllDateAndTime();
        initComboBoxes();

        if (DataStore.getInstance().getVehicleState() != null) {
            loadFromDataStore();
        }


    }

    private void intiAllDateAndTime() {

        TimeService.initClock(availableTimeLine1);
        TimeService.initClock(availableTimeLine2);
        TimeService.initClock(availableTimeLine3);
        DateService.initDate(availableDateLine1);
        DateService.initDate(availableDateLine2);
        DateService.initDate(availableDateLine3);
    }


    private void initComboBoxes() {

        ComboService.initComboBox(availableVehicleLine1, IComboConstants.availableVehicleCount);
        ComboService.initComboBox(availableVehicleLine2, IComboConstants.availableVehicleCount);
        ComboService.initComboBox(availableVehicleLine3, IComboConstants.availableVehicleCount);
        ComboService.initComboBox(vehicleToVehicleDistance, IComboConstants.vehicleToVehicleDistance);
        ComboService.initComboBox(density, IComboConstants.density);

    }


    @FXML
    private void calculate() {

        ArrayList<VehicleGroup> sortedList = new ArrayList<>();
        sortedList.add(new VehicleGroup(availableDateLine1, availableTimeLine1, availableVehicleLine1.getValue().getText()));
        sortedList.add(new VehicleGroup(availableDateLine2, availableTimeLine2, availableVehicleLine2.getValue().getText()));
        sortedList.add(new VehicleGroup(availableDateLine3, availableTimeLine3, availableVehicleLine3.getValue().getText()));

        Collections.sort(sortedList);

        time1.setText(DateService.display(sortedList.get(0).getDatePicker()) + " - " + TimeService.display(sortedList.get(0).getTimePicker()));
        time2.setText(DateService.display(sortedList.get(1).getDatePicker()) + " - " + TimeService.display(sortedList.get(1).getTimePicker()));
        time3.setText(DateService.display(sortedList.get(2).getDatePicker()) + " - " + TimeService.display(sortedList.get(2).getTimePicker()));

        totalAvailableVehicles1.setText(sortedList.get(0).getCount().getText());
        totalAvailableVehicles2.setText(String.valueOf(
                Integer.parseInt(sortedList.get(0).getCount().getText())
                        + Integer.parseInt(sortedList.get(1).getCount().getText())));
        totalAvailableVehicles3.setText(String.valueOf(
                Integer.parseInt(sortedList.get(0).getCount().getText())
                        + Integer.parseInt(sortedList.get(1).getCount().getText())
                        + Integer.parseInt(sortedList.get(2).getCount().getText())));
    }


    @FXML
    private void next() throws IOException {

        calculate();

        VehicleState vehicleState = new VehicleState();
        vehicleState.setAvailableDate(availableDateLine1, availableDateLine2, availableDateLine3);
        vehicleState.setAvailableTime(availableTimeLine1, availableTimeLine2, availableTimeLine3);
        vehicleState.setAvailableVehicle(
                totalAvailableVehicles1, totalAvailableVehicles2, totalAvailableVehicles3);
        vehicleState.setVehicleToVehicleDistance(vehicleToVehicleDistance.getValue().getText());
        vehicleState.setDensity(density.getValue().getText());

        DataStore.getInstance().setVehicleState(vehicleState);

        App.setRoot(new UnitInfoController(), "UnitInfo");
    }


    private void loadFromDataStore() {

        DataStore.getInstance().getVehicleState().loadAvailableDate(availableDateLine1, availableDateLine2, availableDateLine3);
        DataStore.getInstance().getVehicleState().loadAvailableTime(availableTimeLine1, availableTimeLine2, availableTimeLine3);
        DataStore.getInstance().getVehicleState().loadTotalAvailableVehicles(
                availableVehicleLine1, availableVehicleLine2, availableVehicleLine3);

        ComboService.autoSelectComboBoxValue(
                vehicleToVehicleDistance, DataStore.getInstance().getVehicleState().getVehicleToVehicleDistance());
        ComboService.autoSelectComboBoxValue(density, DataStore.getInstance().getVehicleState().getDensity());
    }
}


@Data
class VehicleGroup implements Comparable<VehicleGroup> {

    JFXDatePicker datePicker;
    JFXTimePicker timePicker;
    Label count;

    LocalDateTime date;

    public VehicleGroup(JFXDatePicker datePicker, JFXTimePicker timePicker, String count) {
        this.datePicker = datePicker;
        this.timePicker = timePicker;
        this.count = new Label(count);

        date = LocalDateTime.of(datePicker.getValue(), timePicker.getValue());

    }

    @Override
    public int compareTo(VehicleGroup o) {
        boolean isBefore = this.date.isBefore(o.date);
        if (isBefore) return -1;
        else return 0;
    }
}
