package models.breakdown;

import Constants.IComboConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.Unit;
import models.pos.ArtiPos;
import models.pos.OtherPos;
import models.pos.Pos;
import result.DataStore;
import services.TimeService;

import java.util.ArrayList;
import java.util.Arrays;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Breakdown {

    private String labelSer;
    private String labelEqptName;

    private String unitName;
    private String gunType;
    private Integer noOfGuns;
    private Integer posCount;

    private ArrayList<BreakdownPos> posArrayList = new ArrayList<>();

    private int index;

    public Breakdown(Unit unit, int index) {

        this.labelSer = unit.getLabelSer();
        this.labelEqptName = unit.getLabelEqptName();
        this.unitName = unit.getUnitName();
        this.gunType = unit.getGunType();
        this.noOfGuns = unit.getNoOfGuns();
        this.posCount = unit.getPosCount();
        this.index = index;

        processPos( unit.getPositions() );
    }


    private void processPos( ArrayList<Pos> pos ) {

        for (Pos po : pos) {

            BreakdownPos breakdownPos = new BreakdownPos();

            breakdownPos.setPosName( po.getPosName() );

            if ( po instanceof ArtiPos) {
                ArtiPos temp = (ArtiPos) po;
                breakdownPos.setVehicles( calculateThreeTonsArtillery(temp) );
            } else {
                OtherPos temp = (OtherPos) po;
                breakdownPos.setVehicles( temp.getNoOfThreeTon() );
            }

            Double runTime = findRunTime(po);
            breakdownPos.setRunTime( TimeService.getStringTimeFromDouble(runTime) );
            breakdownPos.setHaltTime( TimeService.getStringTimeFromDouble( findHaltTime(runTime)));
            breakdownPos.setPassTime( TimeService.getStringTimeFromDouble( findPassTime(breakdownPos.getVehicles()) ));
            breakdownPos.setDistance( po.getDistance() );

            posArrayList.add( breakdownPos );
        }
    }


    private Double calculateThreeTonsArtillery(ArtiPos temp){


        int index = Arrays.asList( IComboConstants.gunType ).indexOf( gunType );
        double result = 0.0;
        //rpg
        if(DataStore.getInstance().getGenInfo().getAmmoScale().equalsIgnoreCase(IComboConstants.ammunitionScale[0])) {

            result = temp.getNoOfAmmo() * noOfGuns / IComboConstants.truckCapacity[index] ;
        }// 2nd line
        else result = (temp.getNoOfAmmo() * noOfGuns * IComboConstants.secondLineCount[index] ) / IComboConstants.truckCapacity[index];

        return Math.ceil( result );
    }


    private Double findRunTime(Pos temp) {

        int index = Arrays.asList( IComboConstants.road ).indexOf( DataStore.getInstance().getGenInfo().getRoadCondition() );
        Double distance = temp.getDistance();
        Double speed = findAverageSpeed(index);

        return distance / speed;
    }

    private Double findAverageSpeed(int index) {

        Double speed;
        if( DataStore.getInstance().getTimeCal().getDumpingExecPeriod().equals( IComboConstants.dumpingExecPeriod[1])){
            speed = IComboConstants.speedNight[index];
        }
        else {
            speed = IComboConstants.speedDayAndNight[index];
        }
        return speed;
    }


    private Double findHaltTime( Double runTime ) {

        Integer shortHalt = Integer.valueOf(DataStore.getInstance().getTimeCal().getShortHaltTime());
        Integer shortHaltAfter = Integer.parseInt(DataStore.getInstance().getTimeCal().getShortHaltAfterHour()) * 60;

        Integer longHalt = Integer.parseInt(DataStore.getInstance().getTimeCal().getLongHaltTime()) * 60;
        Integer longHaltAfter = Integer.parseInt(DataStore.getInstance().getTimeCal().getLongHaltAfterHour()) * 60;

        Integer runtimeInMiuntes = runTime.intValue() * 60;
        Integer haltTimeInMinutes = 0;

        while ( runtimeInMiuntes >= 60 ) {

            if( runtimeInMiuntes % longHaltAfter == 0 ) {
                haltTimeInMinutes += longHalt;
            }
            else if( runtimeInMiuntes % shortHaltAfter == 0 ) {
                haltTimeInMinutes += shortHalt;
            }

            runtimeInMiuntes -= 60;
        }

        return Double.valueOf( haltTimeInMinutes ) / 60;
    }


    private Double findPassTime( Double vehicle ) {
        int index = Arrays.asList( IComboConstants.road ).indexOf( DataStore.getInstance().getGenInfo().getRoadCondition() );

        Double denominator = findAverageSpeed(index) * Integer.parseInt( DataStore.getInstance().getVehicleState().getDensity());
        if( denominator == 0 ) denominator = 1.0;

        Double numerator = (vehicle * 60);

        //System.out.println( denominator +" "+numerator +" "+vehicle);
        return (( numerator / denominator ) + ( vehicle / 25 ) ) / 60;
    }



}
