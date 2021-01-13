package result;

import lombok.Data;
import models.GenInfo;
import models.TimeCal;

@Data
public class DataStore {

    private static DataStore instance = null;
    private GenInfo genInfo;
    private TimeCal timeCal;

    private DataStore() {
    }

    public static DataStore getInstance() {
        if (instance == null) instance = new DataStore();
        return instance;
    }

}
