package Constants;

import java.util.Arrays;
import java.util.stream.IntStream;

public interface IComboConstants {

    String[] distance = {"Kilometer", "Mile"};
    String[] time = {"Hour", "Minute"};
    String[] road = {"Good Road with Good Surface", "Hilly Road with Twist", "Bad Road"};
    String[] ammunitionScale = {"RPG", "X 2nd Line"};
    String[] noOfFireUnit = Arrays.stream(IntStream.range(1, 11).toArray()).mapToObj(String::valueOf).toArray(String[]::new);
    String[] shortHaltDuration = Arrays.stream(IntStream.range(0, 61).toArray()).mapToObj(String::valueOf).toArray(String[]::new);
    String[] longHaltDuration = Arrays.stream(IntStream.range(0, 24).toArray()).mapToObj(String::valueOf).toArray(String[]::new);
    String[] shortHaltAfterHour = Arrays.stream(IntStream.range(1, 24).toArray()).mapToObj(String::valueOf).toArray(String[]::new);
    String[] longHaltAfterHour = Arrays.stream(IntStream.range(1, 24).toArray()).mapToObj(String::valueOf).toArray(String[]::new);
    String[] timeReqToLoad = Arrays.stream(IntStream.range(0, 24).toArray()).mapToObj(String::valueOf).toArray(String[]::new);
    String[] contingencyTime = Arrays.stream(IntStream.range(0, 24).toArray()).mapToObj(String::valueOf).toArray(String[]::new);

    String[] availableVehicleCount = Arrays.stream(IntStream.range(0, 301).toArray()).mapToObj(String::valueOf).toArray(String[]::new);
    String[] vehicleToVehicleDistance = Arrays.stream(IntStream.range(1, 1001).toArray()).mapToObj(String::valueOf).toArray(String[]::new);
    String[] density = Arrays.stream(IntStream.range(1, 21).toArray()).mapToObj(String::valueOf).toArray(String[]::new);

}
