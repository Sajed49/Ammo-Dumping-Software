package models.pos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArtiPos extends Pos{

    protected Double noOfAmmo;
    public ArtiPos(String posName, Integer priority, Double distance, Double noOfAmmo){
        super( posName, priority, distance);
        this.noOfAmmo = noOfAmmo;
    }
}
