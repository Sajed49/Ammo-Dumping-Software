package controllers.calculation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.breakdown.TimeTable;
import services.DateTimeService;

import java.net.URL;
import java.util.ResourceBundle;

public class TimeTableRowController implements Initializable {

    @FXML
    private Label ser;
    @FXML
    private Label dateAndTime;
    @FXML
    private Label event;
    @FXML
    private Label noOfVehicles;
    @FXML
    private Label duration;
    @FXML
    private Label distanceCovered;
    @FXML
    private Label distanceTravelled;
    @FXML
    private Label unitName;

    TimeTable timeTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;

        this.ser.setText( timeTable.getSer() );
        this.dateAndTime.setText(DateTimeService.display( timeTable.getDateAndTime() ) );
        this.noOfVehicles.setText( timeTable.getNoOfVehicles() );
        this.unitName.setText( timeTable.getUnitName() );

        this.event.setText( timeTable.getEvent());
        this.duration.setText( timeTable.getDuration());
        this.distanceCovered.setText( timeTable.getDistanceCovered() );
    }


}
