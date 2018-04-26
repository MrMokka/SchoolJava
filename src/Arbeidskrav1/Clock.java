package Arbeidskrav1;

public class Clock extends Meter {
    
    
    private String lowestInterval;
    
    //Create a new Clock
    public Clock(String lowestInterval, String regNum, String location, boolean inOrder){
        super(regNum, location, inOrder);
        this.lowestInterval = lowestInterval;
    }
    
    //Return a single string containing all information about this Clock
    public String toString(){
        return "Meter type: Clock\n" + "Lowest interval: " + lowestInterval + "\n" + super.toString();
    }
    
}
