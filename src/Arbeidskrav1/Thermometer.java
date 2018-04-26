package Arbeidskrav1;

public class Thermometer extends Meter {
    
    
    private String tempScale;
    
    //Create a new Thermometer
    public Thermometer(String tempScale, String regNum, String location, boolean inOrder){
        super(regNum, location, inOrder);
        this.tempScale = tempScale;
    }
    
    //Return a single string containing all information about this Thermometer
    public String toString(){
        return "Meter type: Thermomoter\n" + "Temp scale: " + tempScale + "\n" + super.toString();
    }
    
    
}
