package services;

import javafx.scene.control.Label;
import models.VehicleState;
import models.breakdown.VehicleGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.stream.Collectors;

public class VehicleService {


    public static ArrayList<VehicleGroup> getVehicleGroups(VehicleState vehicleState){

        ArrayList<VehicleGroup> sortedList = new ArrayList<>();
        sortedList.add(new VehicleGroup( vehicleState.getAvailableDate().get(0), vehicleState.getAvailableTime().get(0), vehicleState.getAvailableVehicle().get(0)));
        sortedList.add(new VehicleGroup( vehicleState.getAvailableDate().get(1), vehicleState.getAvailableTime().get(1), vehicleState.getAvailableVehicle().get(1)));
        sortedList.add(new VehicleGroup( vehicleState.getAvailableDate().get(2), vehicleState.getAvailableTime().get(2), vehicleState.getAvailableVehicle().get(2)));

        Collections.sort(sortedList);

        Stack<VehicleGroup> result = new Stack<>();
        result.add( sortedList.get(0));

        for(int i=1; i<sortedList.size(); i++) {

            if( sortedList.get(i).getDate().equals( result.peek().getDate()) ) {
                int count = Integer.parseInt(result.peek().getCount().getText()) + Integer.parseInt( sortedList.get(i).getCount().getText() );
                result.pop();
                sortedList.get(i).setCount( new Label( String.valueOf( count) ));
                result.add(sortedList.get(i));
            }
            else result.add( sortedList.get(i));
        }

        return new ArrayList<>(result);
    }
}
