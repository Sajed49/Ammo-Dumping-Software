package Constants;

import java.util.Arrays;
import java.util.stream.IntStream;

public interface IComboConstants {

    String[] distance = {"Kilometer", "Mile"};
    String[] time = {"Hour", "Minute"};
    String[] road = {"Good Road with Good Surface", "Hilly Road with Twist", "Bad Road"};
    String[] ammunitionScale = {"RPG", "X 2nd Line"};
    String[] noOfFireUnit = Arrays.stream( IntStream.range(1, 11).toArray() ).mapToObj(String::valueOf).toArray(String[]::new);
}
