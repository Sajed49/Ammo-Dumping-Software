package models;

import lombok.Data;
import services.TimeService;

import java.time.LocalTime;

@Data
public class Day {

    long dumpingNightTime1 = 0L;
    long dumpingDayTime = 0L;
    long dumpingNightTime2 = 0L;

    LocalTime firstLight;
    LocalTime lastLight;

    LocalTime dumpingStartTime;
    LocalTime dumpingEndTime;

    public Day(LocalTime firstLight, LocalTime lastLight, LocalTime dumpingStartTime, LocalTime dumpingEndTime) {
        this.firstLight = firstLight;
        this.lastLight = lastLight;
        this.dumpingStartTime = dumpingStartTime;
        this.dumpingEndTime = dumpingEndTime;

        calculateBreakdown();
    }


    private void calculateBreakdown() {

        long totalDayLightInADay = TimeService.findTimeDiffInMinutes(firstLight, lastLight);

        long totalDumpingTimeInADay = TimeService.findTimeDiffInMinutes(dumpingStartTime, dumpingEndTime);
        long darkPhase1 = TimeService.findTimeDiffInMinutes(firstLight, dumpingStartTime);
        long lightPhase1 = TimeService.findTimeDiffInMinutes(firstLight, dumpingEndTime);
        long darkPhase2 = TimeService.findTimeDiffInMinutes(lastLight, dumpingEndTime);
        long darkPhase2Reduction = TimeService.findTimeDiffInMinutes(lastLight, dumpingStartTime);

        if (darkPhase1 < 0) {

            if (lightPhase1 < 0) dumpingNightTime1 = totalDumpingTimeInADay;
            else if (darkPhase2 < 0) {
                dumpingDayTime = lightPhase1;
                dumpingNightTime1 = (-darkPhase1);
            } else {
                dumpingDayTime = totalDayLightInADay;
                dumpingNightTime1 = (-darkPhase1);
                dumpingNightTime2 = darkPhase2;
            }
        } else if (darkPhase2Reduction < 0) {
            if (darkPhase2 < 0) dumpingDayTime += totalDumpingTimeInADay;
            else {
                dumpingDayTime = (-darkPhase2Reduction);
                dumpingNightTime2 = darkPhase2;
            }
        } else {
            dumpingNightTime2 = totalDumpingTimeInADay;
        }

    }
}
