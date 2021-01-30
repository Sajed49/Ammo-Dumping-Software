package models.breakdown;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BreakdownPos {

    private String posName;
    private Double vehicles;
    private String runTime;
    private String haltTime;
    private String passTime;
}
