package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenInfo {
    private String loadingPoint;
    private String roadCondition;
    private String typeOfStore;
    private String AmmoScale;
    private String noOfFireUnits;
    private String limitations;
}
