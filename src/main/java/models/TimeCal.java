package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeCal {

    String firstLight;
    String lastLight;
    String dumpingStartTime;
    String dumpingEndTime;
    String dumpingStartDate;
    String dumpingEndDate;

    String shortHaltTime;
    String longHaltTime;
    String shortHaltAfterHour;
    String longHaltAfterHour;
    String timeReqToLoad;
    String timeReqToUnload;
    String dumpingExecPeriod;
    String contingencyTime;

    String dayTimeAvailable;
    String nightTimeAvailable;
    String totalDayTimeAvailable;
    String totalNightTimeAvailable;
    String totalTimeAvailable;

}
