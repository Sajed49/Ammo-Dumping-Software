package controllers;

import Constants.IComboConstants;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.App;
import models.VehicleState;
import result.DataStore;
import services.ComboService;
import services.DateService;
import services.FactoryService;
import services.TimeService;

import java.io.IOException;
import java.net.URL;
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
    JFXComboBox <Label> availableVehicleLine1 = new JFXComboBox<>();
    @FXML
    JFXComboBox <Label> availableVehicleLine2 = new JFXComboBox<>();
    @FXML
    JFXComboBox <Label> availableVehicleLine3 = new JFXComboBox<>();
    @FXML
    JFXComboBox <Label> vehicleToVehicleDistance = new JFXComboBox<>();
    @FXML
    JFXComboBox <Label> density = new JFXComboBox<>();


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

        time1.setText( DateService.display( availableDateLine1 ) + " - " + TimeService.display( availableTimeLine1 ) );
        time2.setText( DateService.display( availableDateLine2 ) + " - " + TimeService.display( availableTimeLine2 ) );
        time3.setText( DateService.display( availableDateLine3 ) + " - " + TimeService.display( availableTimeLine3 ) );

        totalAvailableVehicles1.setText( availableVehicleLine1.getValue().getText());
        totalAvailableVehicles2.setText( String.valueOf(
                Integer.parseInt( availableVehicleLine1.getValue().getText())
                + Integer.parseInt( availableVehicleLine2.getValue().getText()) ) );
        totalAvailableVehicles3.setText( String.valueOf(
                Integer.parseInt( availableVehicleLine1.getValue().getText())
                + Integer.parseInt( availableVehicleLine2.getValue().getText())
                + Integer.parseInt( availableVehicleLine3.getValue().getText())));
    }


    @FXML
    private void next() throws IOException {

        calculate();

        VehicleState vehicleState = new VehicleState();
        vehicleState.setAvailableDate( availableDateLine1, availableDateLine2, availableDateLine3);
        vehicleState.setAvailableTime( availableTimeLine1, availableTimeLine2, availableTimeLine3);
        vehicleState.setAvailableVehicle(
                totalAvailableVehicles1, totalAvailableVehicles2, totalAvailableVehicles3 );
        vehicleState.setVehicleToVehicleDistance( vehicleToVehicleDistance.getValue().getText() );
        vehicleState.setDensity( density.getValue().getText() );

        DataStore.getInstance().setVehicleState( vehicleState );

        App.setRoot( new UnitInfoController(), "UnitInfo");
    }


    private void loadFromDataStore() {

        DataStore.getInstance().getVehicleState().loadAvailableDate( availableDateLine1, availableDateLine2, availableDateLine3);
        DataStore.getInstance().getVehicleState().loadAvailableTime( availableTimeLine1, availableTimeLine2, availableTimeLine3);
        DataStore.getInstance().getVehicleState().loadTotalAvailableVehicles(
                availableVehicleLine1, availableVehicleLine2, availableVehicleLine3);

        FactoryService.autoSelectComboBoxValue(
                vehicleToVehicleDistance, DataStore.getInstance().getVehicleState().getVehicleToVehicleDistance() );
        FactoryService.autoSelectComboBoxValue( density, DataStore.getInstance().getVehicleState().getDensity() );
    }
}
