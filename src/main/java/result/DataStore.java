package result;

import lombok.Data;
import models.GenInfo;
import models.TimeCal;
import models.Unit;
import models.VehicleState;

import java.util.ArrayList;

@Data
public class DataStore {

    private static DataStore instance = null;
    private GenInfo genInfo;
    private TimeCal timeCal;
    private VehicleState vehicleState;
    private ArrayList<Unit> unitInfo = new ArrayList<>();

    private DataStore() {
    }

    public static DataStore getInstance() {
        if (instance == null) instance = new DataStore();
        return instance;
    }

}
