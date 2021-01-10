package Constants;

import java.io.File;

public interface IConstants {

    String currentDirectory = System.getProperty("user.dir") + File.separator;
    String dbURL = currentDirectory + "data";
    String dbName = "data.db";
    String applicationTitle = "Ammo Dumping Software";

    String logo = "file:" + "img" + File.separator + "logo.png";

    String missingDataError = "Please fill up all the fields.";
}
