package models.pos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OtherPos extends Pos{

    protected Double noOfThreeTon;
    public OtherPos(String posName, Integer priority, Double distance, Double noOfThreeTon){
        super( posName, priority, distance);
        this.noOfThreeTon = noOfThreeTon;
    }
}
