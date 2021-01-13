package result;

import lombok.Data;
import models.GenInfo;
import models.TimeCal;
import models.VehicleState;

@Data
public class DataStore {

    private static DataStore instance = null;
    private GenInfo genInfo;
    private TimeCal timeCal;
    private VehicleState vehicleState;

    private DataStore() {
    }

    public static DataStore getInstance() {
        if (instance == null) instance = new DataStore();
        return instance;
    }

}
