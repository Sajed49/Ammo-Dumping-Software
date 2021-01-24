package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pos {

    private String posName;
    private Integer priority;
    private Double distance;
    private Integer noOfThreeTonLoad;
    private Double noOfAmmo;
}
