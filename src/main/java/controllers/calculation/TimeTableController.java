package controllers.calculation;

import Constants.IComboConstants;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import models.breakdown.Breakdown;
import models.breakdown.BreakdownPos;
import models.breakdown.TimeTable;
import models.breakdown.VehicleGroup;
import result.DataStore;
import services.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class TimeTableController implements Initializable {

    @FXML
    VBox vBox = new VBox();
    @FXML
    JFXButton next = new JFXButton("Next");
    @FXML
    JFXButton print = new JFXButton("Print");

    ArrayList<Breakdown> breakdowns = new ArrayList<>();
    ArrayList<TimeTableRowController> rowControllers = new ArrayList<>();


    LocalDateTime currentTime;
    ArrayList<VehicleGroup> vehicleGroups = new ArrayList<>();
    ArrayList<TravelGroup> travelGroups = new ArrayList<>();
    ArrayList<TimeTable> timeTableList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadSavedData();

        if( DataStore.getInstance().getGenInfo().getLimitations().equals( IComboConstants.limitations[0] )) vehicleConstraintCalculation();
        else timeConstraintCalculation();
        //addBreakdown();
//        addBreakdown();
//        addBreakdown();
    }


    @SneakyThrows(IOException.class)
    private void addBreakdown() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("TimeTableRow.fxml"));
        GridPane pane = loader.load();
        rowControllers.add(loader.getController());
        vBox.getChildren().add(pane);
    }

    private void loadSavedData() {

        breakdowns = DataStore.getInstance().getBreakdowns();
        if( DataStore.getInstance().getVehicleState() != null ) vehicleGroups = VehicleService.getVehicleGroups(DataStore.getInstance().getVehicleState());

        for (Breakdown breakdown : breakdowns) {
            if( breakdown.getPosArrayList().size() > 0 ) travelGroups.add( TravelGroup.builder().breakdown( breakdown).pos( breakdown.getPosArrayList().get(0)).build() );
        }
        for (Breakdown breakdown : breakdowns) {
            if( breakdown.getPosArrayList().size() > 1 ) travelGroups.add( TravelGroup.builder().breakdown( breakdown).pos( breakdown.getPosArrayList().get(1)).build() );
        }
        for (Breakdown breakdown : breakdowns) {
            if( breakdown.getPosArrayList().size() > 2 ) travelGroups.add( TravelGroup.builder().breakdown( breakdown).pos( breakdown.getPosArrayList().get(2)).build() );
        }

//         travelGroups.forEach(System.out::println);
    }

    private void timeConstraintCalculation(){

        int high = 1000;
        int low = 0;

        if( !isPossible(high) ) {
            System.out.println("Not possible.");
            return;
        }
        while (low < high) {
            int mid = low + (high - low) / 2;

            if (isPossible(mid)) {
                high = mid;
            }
            else {
                low = mid + 1;
            }
        }

        System.out.println( low );
        timeConstraintCalculation(low);
    }


    private Boolean isPossible(int vehicleCount ){

        PriorityQueue<TripReturn> myQueue = new PriorityQueue<>();
        int idleVehicle = vehicleCount;
        Long loadingTime = Integer.parseInt(DataStore.getInstance().getTimeCal().getTimeReqToLoad()) * 60L;
        Long unLoadingTime = Integer.parseInt(DataStore.getInstance().getTimeCal().getTimeReqToUnload()) * 60L;

        currentTime =  LocalDateTime.of(LocalDate.parse(DataStore.getInstance().getTimeCal().getDumpingStartDate(), DateService.dateFormatter),
                LocalTime.parse( DataStore.getInstance().getTimeCal().getDumpingStartTime(), TimeService.formatter) );

        ArrayList<TimeTable> results = new ArrayList<>();

        for (TravelGroup travelGroup : travelGroups) {

            int requiredVehicle = travelGroup.getPos().getVehicles().intValue();

            if( requiredVehicle == 0) continue; // no vehicles required
            else if( requiredVehicle > idleVehicle && myQueue.isEmpty() ){
                return false; // not possible case vehicle shortage
            }
            else {
                //System.out.println( travelGroup.getPos().getPosName() );
                //System.out.println( requiredVehicle+" "+idleVehicle+" "+myQueue.size());
                Long oneWayTripTime = getOneWayTripTime( travelGroup.getPos() );
                Long duration = loadingTime + (oneWayTripTime*2) + unLoadingTime;

                while(  requiredVehicle > idleVehicle ) {

                    if( myQueue.isEmpty()) break;
                    idleVehicle += myQueue.peek().getVehicleCount();
                    currentTime = myQueue.peek().getDateTime();
                    myQueue.poll();
                }

                if( requiredVehicle > idleVehicle && myQueue.isEmpty() ){
                    return false; // not possible case vehicle shortage
                }

                myQueue.add( TripReturn.builder().dateTime( DateTimeService.addMinutesToDateTime(currentTime, duration) )
                        .vehicleCount( requiredVehicle ).build() ); // comeback time
                idleVehicle = idleVehicle - requiredVehicle;
                results.addAll( getRoundTripInfo(travelGroup, loadingTime, unLoadingTime));
            }


        }

        Collections.sort( results, Comparator.comparing(TimeTable::getDateAndTime));

        LocalDateTime finishTime = LocalDateTime.of(LocalDate.parse(DataStore.getInstance().getTimeCal().getDumpingEndDate(), DateService.dateFormatter),
                LocalTime.parse( DataStore.getInstance().getTimeCal().getDumpingEndTime(), TimeService.formatter) );

        if( results.size() == 0 ) return true;
        if( results.get( results.size() - 1).getDateAndTime().isAfter(finishTime ) ) return false;
        else return true;

    }


    private void timeConstraintCalculation(int vehicleCount){

        PriorityQueue<TripReturn> myQueue = new PriorityQueue<>();
        int idleVehicle = vehicleCount;
        Long loadingTime = Integer.parseInt(DataStore.getInstance().getTimeCal().getTimeReqToLoad()) * 60L;
        Long unLoadingTime = Integer.parseInt(DataStore.getInstance().getTimeCal().getTimeReqToUnload()) * 60L;

        currentTime =  LocalDateTime.of(LocalDate.parse(DataStore.getInstance().getTimeCal().getDumpingStartDate(), DateService.dateFormatter),
                LocalTime.parse( DataStore.getInstance().getTimeCal().getDumpingStartTime(), TimeService.formatter) );

        ArrayList<TimeTable> results = new ArrayList<>();

        for (TravelGroup travelGroup : travelGroups) {

            int requiredVehicle = travelGroup.getPos().getVehicles().intValue();

            if( requiredVehicle == 0) continue; // no vehicles required
            else if( requiredVehicle > idleVehicle && myQueue.isEmpty() ){
                return;// not possible case vehicle shortage
            }
            else {
                //System.out.println( travelGroup.getPos().getPosName() );
                //System.out.println( requiredVehicle+" "+idleVehicle+" "+myQueue.size());
                Long oneWayTripTime = getOneWayTripTime( travelGroup.getPos() );
                Long duration = loadingTime + (oneWayTripTime*2) + unLoadingTime;

                while(  requiredVehicle > idleVehicle ) {

                    if( myQueue.isEmpty()) break;
                    idleVehicle += myQueue.peek().getVehicleCount();
                    currentTime = myQueue.peek().getDateTime();
                    myQueue.poll();
                }

                if( requiredVehicle > idleVehicle && myQueue.isEmpty() ){
                    return; // not possible case vehicle shortage
                }

                myQueue.add( TripReturn.builder().dateTime( DateTimeService.addMinutesToDateTime(currentTime, duration) )
                        .vehicleCount( requiredVehicle ).build() ); // comeback time
                idleVehicle = idleVehicle - requiredVehicle;
                results.addAll( getRoundTripInfo(travelGroup, loadingTime, unLoadingTime));
            }


        }

        Collections.sort( results, Comparator.comparing(TimeTable::getDateAndTime));

        LocalDateTime finishTime = LocalDateTime.of(LocalDate.parse(DataStore.getInstance().getTimeCal().getDumpingEndDate(), DateService.dateFormatter),
                LocalTime.parse( DataStore.getInstance().getTimeCal().getDumpingEndTime(), TimeService.formatter) );

        if( results.size() == 0 ) return;
        else if( results.get( results.size() - 1).getDateAndTime().isAfter(finishTime ) ) return;
        else {
            for( TimeTable timeTable: results ) {
//            System.out.println( timeTable );
                addBreakdown();
                int i = rowControllers.size() - 1;
                rowControllers.get(i).setTimeTable( timeTable );
            }

        }

    }

    private void vehicleConstraintCalculation(){

        PriorityQueue<TripReturn> myQueue = new PriorityQueue<>();
        int vehicleGroupIndex = 0;
        int idleVehicle = 0;
        Long loadingTime = Integer.parseInt(DataStore.getInstance().getTimeCal().getTimeReqToLoad()) * 60L;
        Long unLoadingTime = Integer.parseInt(DataStore.getInstance().getTimeCal().getTimeReqToUnload()) * 60L;

        if( vehicleGroups.size() > 0 ) {
            idleVehicle += Integer.parseInt( vehicleGroups.get(vehicleGroupIndex).getCount().getText() );
            currentTime = vehicleGroups.get(vehicleGroupIndex).getDate();
            vehicleGroupIndex++;
        }

        ArrayList<TimeTable> results = new ArrayList<>();

        for (TravelGroup travelGroup : travelGroups) {

            int requiredVehicle = travelGroup.getPos().getVehicles().intValue();

            for ( ; requiredVehicle > idleVehicle && vehicleGroups.size() > vehicleGroupIndex; vehicleGroupIndex++) {
                idleVehicle += Integer.parseInt( vehicleGroups.get(vehicleGroupIndex).getCount().getText() );
                currentTime = vehicleGroups.get(vehicleGroupIndex).getDate();
            }


            if( requiredVehicle == 0) continue; // no vehicles required
            else if( requiredVehicle > idleVehicle && myQueue.isEmpty() ){
                break; // not possible case vehicle shortage
            }
            else {
                //System.out.println( travelGroup.getPos().getPosName() );
                //System.out.println( requiredVehicle+" "+idleVehicle+" "+myQueue.size());
                Long oneWayTripTime = getOneWayTripTime( travelGroup.getPos() );
                Long duration = loadingTime + (oneWayTripTime*2) + unLoadingTime;

                while(  requiredVehicle > idleVehicle ) {

                    if( myQueue.isEmpty()) break;
                    idleVehicle += myQueue.peek().getVehicleCount();
                    currentTime = myQueue.peek().getDateTime();
                    myQueue.poll();
                }

                if( requiredVehicle > idleVehicle && myQueue.isEmpty() ){
                    break; // not possible case vehicle shortage
                }

                myQueue.add( TripReturn.builder().dateTime( DateTimeService.addMinutesToDateTime(currentTime, duration) )
                        .vehicleCount( requiredVehicle ).build() ); // comeback time
                idleVehicle = idleVehicle - requiredVehicle;
                results.addAll( getRoundTripInfo(travelGroup, loadingTime, unLoadingTime));
            }


        }

        Collections.sort( results, Comparator.comparing(TimeTable::getDateAndTime));
        for( TimeTable timeTable: results ) {
//            System.out.println( timeTable );
            addBreakdown();
            int i = rowControllers.size() - 1;
            rowControllers.get(i).setTimeTable( timeTable );
        }


    }


    private ArrayList<TimeTable> getRoundTripInfo( TravelGroup travelGroup, Long loadingTime, Long unloadingTime ){
        BreakdownPos pos = travelGroup.getPos();
        String unitName = travelGroup.getBreakdown().getUnitName();

        Long oneTripRunTime = getOneWayTripTime( pos );

        ArrayList<TimeTable> results = new ArrayList<>();

        results.add( TimeTable.builder().dateAndTime( currentTime ).event("Loading").noOfVehicles( String.valueOf(pos.getVehicles()) ).
                duration( TimeService.formatToDisplay(loadingTime) ).unitName( unitName ).build() );

        results.add( TimeTable.builder().dateAndTime( DateTimeService.addMinutesToDateTime(currentTime, loadingTime) ).event("From "+ pos.getPosName() +" to Dumping")
                .noOfVehicles( String.valueOf(pos.getVehicles()) ).
                duration( TimeService.formatToDisplay(oneTripRunTime)).distanceCovered( String.valueOf(pos.getDistance()) ).unitName( unitName ).build() );

        results.add( TimeTable.builder().dateAndTime( DateTimeService.addMinutesToDateTime(currentTime, loadingTime + oneTripRunTime) ).event("Unloading").noOfVehicles( String.valueOf(pos.getVehicles()) ).
                duration( TimeService.formatToDisplay(loadingTime) ).unitName( unitName ).build() );

        results.add( TimeTable.builder().dateAndTime( DateTimeService.addMinutesToDateTime(currentTime, loadingTime + oneTripRunTime + unloadingTime) ).event("Returning From Dumping")
                .noOfVehicles( String.valueOf(pos.getVehicles()) ).
                duration( TimeService.formatToDisplay(oneTripRunTime)).distanceCovered( String.valueOf(pos.getDistance()) ).unitName( unitName ).build() );

        results.add( TimeTable.builder().dateAndTime( DateTimeService.addMinutesToDateTime(currentTime, loadingTime + (oneTripRunTime*2) + unloadingTime) ).event("Returned From Dumping")
                .noOfVehicles( String.valueOf(pos.getVehicles()) ).
                duration(TimeService.formatToDisplay(oneTripRunTime)).unitName( unitName ).build() );

        return results;
    }



    private Long getOneWayTripTime( BreakdownPos pos){

        return TimeService.getMinutesFromStringTime( pos.getRunTime() ) +
                TimeService.getMinutesFromStringTime( pos.getPassTime() ) +
                TimeService.getMinutesFromStringTime( pos.getHaltTime() );
    }
}

@Data
@Builder
class TravelGroup{

    private Breakdown breakdown;
    private BreakdownPos pos;

}


@Data
@Builder
class TripReturn implements Comparable<TripReturn> {

    private LocalDateTime dateTime;
    private Integer vehicleCount;

    @Override
    public int compareTo(TripReturn tripReturn) {
        if( this.dateTime.isAfter( tripReturn.getDateTime()) ) {
            return 1;
        } else if ( this.dateTime.isBefore( tripReturn.getDateTime())) {
            return -1;
        } else {
            return 0;
        }
    }

}