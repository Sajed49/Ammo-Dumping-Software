package models;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import lombok.Data;
import models.breakdown.VehicleGroup;
import services.ComboService;
import services.DateService;
import services.TimeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class VehicleState {

    private List<String> availableDate = new ArrayList<>();
    private List<String> availableTime = new ArrayList<>();
    private List<String> availableVehicle = new ArrayList<>();
    private String vehicleToVehicleDistance;
    private String density;

    public void setAvailableDate(JFXDatePicker... dates) {
        availableDate = Arrays.stream(dates).map(DateService::display).collect(Collectors.toList());
    }

    public void setAvailableTime(JFXTimePicker... times) {
        availableTime = Arrays.stream(times).map(TimeService::display).collect(Collectors.toList());
    }

    public void setAvailableVehicle(Label... labels) {
        availableVehicle = Arrays.stream(labels).map(Labeled::getText).collect(Collectors.toList());
    }


    public void loadAvailableDate(JFXDatePicker... dates) {

        for (int i = 0; i < dates.length; i++) {
            DateService.setDate(dates[i], availableDate.get(i));
        }
    }

    public void loadAvailableTime(JFXTimePicker... times) {
        for (int i = 0; i < times.length; i++) {
            TimeService.setTime(times[i], availableTime.get(i));
        }
    }

    @SafeVarargs
    public final void loadTotalAvailableVehicles(JFXComboBox<Label>... totalAvailableVehicles) {
        for (int i = 0; i < totalAvailableVehicles.length; i++) {
            ComboService.autoSelectComboBoxValue(totalAvailableVehicles[i], availableVehicle.get(i));
        }
    }
}
