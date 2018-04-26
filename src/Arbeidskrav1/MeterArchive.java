package Arbeidskrav1;

import java.util.ArrayList;
import java.util.List;

public class MeterArchive {
    
    
    private List<Meter> meters = new ArrayList<>();
    
    public MeterArchive(){
    
    }
    
    //Add a Meter to the meters list
    public void addMeter(Meter meter){
        meters.add(meter);
    }
    
    //Print a single Meter to console
    public void printMeter(String regNum){
        Meter meter = getMeter(regNum);
        if(meter != null){
            System.out.println(meter.toString());
        }
    }
    
    //Print all Meters in the meters list
    public void printAllMeters(){
        for(int i = 0; i < meters.size(); i++){
            System.out.println(meters.get(i).toString());
            System.out.println();
        }
    }
    
    //Return a single Meter based on the regNumber
    public Meter getMeter(String regNum){
        for(int i = 0; i < meters.size(); i++){
            if(meters.get(i).getRegNum().equals(regNum)){
                return meters.get(i);
            }
        }
        return null;
    }
    
    //Remove a Meter from the meters list
    public boolean removeMeter(String regNum){
        Meter meter = getMeter(regNum);
        if(meter != null){
            meters.remove(meter);
            return true;
        }
        return false;
    }
    
    //Edit a single Meter, giving it a new location
    public boolean editMeter(String regNum, String newLocation){
        Meter meter = getMeter(regNum);
        if(meter != null){
            meter.setLocation(newLocation);
            return true;
        }
        return false;
    }
    
    //Edit a single Meter, giving it a new inOrder status
    public boolean editMeter(String regNum, boolean newInOrder){
        Meter meter = getMeter(regNum);
        if(meter != null){
            meter.setInOrder(newInOrder);
            return true;
        }
        return false;
    }
    
    //Return all Meters, only returns the meterss list
    public List<Meter> getMeters(){
        return meters;
    }
    
    
}
