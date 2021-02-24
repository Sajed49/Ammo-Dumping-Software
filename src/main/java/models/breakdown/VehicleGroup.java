package models.breakdown;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.scene.control.Label;
import lombok.Data;
import services.DateService;
import services.TimeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

@Data
public
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

    public VehicleGroup(String datePicker, String timePicker, String count) {
        this.datePicker = new JFXDatePicker(LocalDate.parse( datePicker, DateService.dateFormatter));
        this.timePicker = new JFXTimePicker(LocalTime.parse( timePicker, TimeService.formatter));
        this.count = new Label(count);

        date = LocalDateTime.of( this.datePicker.getValue(),  this.timePicker.getValue());

    }


    @Override
    public int compareTo(VehicleGroup o) {
        boolean isBefore = this.date.isBefore(o.date);
        if (isBefore) return -1;
        else return 0;
    }

    public static int getTotalCount(ArrayList <VehicleGroup> vehicleGroups, int lastIndex){

        int count = 0;
        for(int i=0; i<vehicleGroups.size() && i<= lastIndex; i++) {
            count += Integer.parseInt(vehicleGroups.get(i).getCount().getText());
        }
        return count;
    }
}
