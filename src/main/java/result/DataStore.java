package result;

import lombok.Data;
import models.GenInfo;

@Data
public class DataStore {

    private GenInfo genInfo;

    private static DataStore instance =null;

    private DataStore() { }

    public static DataStore getInstance() {
        if (instance == null) instance = new DataStore();
        return instance;
    }

}
