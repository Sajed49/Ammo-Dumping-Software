package models.pos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Pos {

    protected String posName;
    protected Integer priority;
    protected Double distance;

}
