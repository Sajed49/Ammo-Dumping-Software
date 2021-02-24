package models.breakdown;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeTable {

    private String ser;
    private LocalDateTime dateAndTime;
    private String event;
    private String noOfVehicles;
    private String duration;
    private String distanceCovered;
    private String distanceTravelled;
    private String unitName;

}
