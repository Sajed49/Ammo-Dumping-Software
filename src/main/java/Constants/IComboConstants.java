package Constants;

import java.util.Arrays;
import java.util.stream.IntStream;

public interface IComboConstants {

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

    String[] unitName = { "1 FD Regiment", "2 FD Regiment", "3 FD Regiment",
            "4 FD Regiment", "5 FD Regiment", "6 FD Regiment",
            "7 FD Regiment", "8 FD Regiment", "9 FD Regiment" };
    String[] gunType = { "105 mm How M-56", "122 mm How T-54" };
    String[] noOfGuns = { "6", "12", "18" };
}
