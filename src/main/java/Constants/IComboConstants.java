package Constants;

import java.util.Arrays;
import java.util.stream.IntStream;

public interface IComboConstants {

    String[] loadingPoint = {"Log Base", "RP", "BAA", "DAA", "FLB"};
    String[] road = {"Good Road with Good Surface", "Hilly Road with Twist", "Bad Road"};
    Double[] speedNight = { 19.3121, 16.0934, 14.4841}; // 12, 10, 9 mile
    Double[] speedDayAndNight = { 28.9682, 24.94483, 21.72614}; // 18 , 15.5, 13.5

    String[] typeOfStore = {"Arty Ammo", "Engr Store", "SAA", "Ration", "POL", "Misc Store"};
    String[] ammunitionScale = {"RPG", "X 2nd Line"};
    String[] noOfUnit = Arrays.stream(IntStream.range(1, 11).toArray()).mapToObj(String::valueOf).toArray(String[]::new);
    String[] limitations = {"No of Vehicle is Limited", "Time Constraints Imposed"};


    String[] shortHaltDuration = Arrays.stream(IntStream.range(0, 61).toArray()).mapToObj(String::valueOf).toArray(String[]::new);
    String[] longHaltDuration = Arrays.stream(IntStream.range(0, 24).toArray()).mapToObj(String::valueOf).toArray(String[]::new);
    String[] shortHaltAfterHour = Arrays.stream(IntStream.range(1, 24).toArray()).mapToObj(String::valueOf).toArray(String[]::new);
    String[] longHaltAfterHour = Arrays.stream(IntStream.range(1, 24).toArray()).mapToObj(String::valueOf).toArray(String[]::new);
    String[] timeReqToLoad = Arrays.stream(IntStream.range(0, 24).toArray()).mapToObj(String::valueOf).toArray(String[]::new);
    String[] dumpingExecPeriod = {"Both Day & Ni", " Only at Ni"};
    String[] contingencyTime = Arrays.stream(IntStream.range(0, 49).toArray()).mapToObj(String::valueOf).toArray(String[]::new);

    String[] availableVehicleCount = Arrays.stream(IntStream.range(0, 301).toArray()).mapToObj(String::valueOf).toArray(String[]::new);
    String[] vehicleToVehicleDistance = Arrays.stream(IntStream.range(1, 1001).toArray()).mapToObj(String::valueOf).toArray(String[]::new);
    String[] density = Arrays.stream(IntStream.range(1, 51).toArray()).mapToObj(String::valueOf).toArray(String[]::new);

    String[] unitName = {"1 FD Regiment", "2 FD Regiment", "3 FD Regiment",
            "4 FD Regiment", "5 FD Regiment", "6 FD Regiment",
            "7 FD Regiment", "8 FD Regiment", "9 FD Regiment"};
    String[] gunType = {"105 mm Pack How/M-56", "122 mm How D 30/T-96/T-83/T-54", "130 mm Gun", "155 mm GH Nora B-52", "120 mm Mor"};
    Integer[] truckCapacity = { 100, 80, 40, 30, 100};
    Integer[] secondLineCount = { 50, 50, 50, 36, 50};
    String[] noOfGuns = {"6", "12", "18"};
    String[] posCount = Arrays.stream(IntStream.range(1, 4).map(i -> 1 + (4 - 1 - i)).toArray()).mapToObj(String::valueOf).toArray(String[]::new);

    String[] posnName = {"Main Posn", "Contg posn 1", "Contg posn 2"};
    String[] priority = Arrays.stream(IntStream.range(1, 4).toArray()).mapToObj(String::valueOf).toArray(String[]::new);

}
