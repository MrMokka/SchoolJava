package Arbeidskrav1;

public abstract class Meter {
    
    
    private String regNum, location;
    private boolean inOrder;
    
    //Constructor for all Meters, here the regNumber, location and
    //inOrder status stored. All Meters has these things in common
    //so it makes sense to store these things here.
    public Meter(String regNum, String location, boolean inOrder){
        this.regNum = regNum;
        this.location = location;
        this.inOrder = inOrder;
    }
    
    //Return a single string containing the regNum, location and inOrder status
    public String toString(){
        return "Reg number: " + regNum + "\nLocation: " + location + "\nIn order: " + inOrder;
    }
    
    //Returns the regNumber
    public String getRegNum(){
        return regNum;
    }
    
    //Set a new location
    protected void setLocation(String newLocation){
        this.location = newLocation;
    }
    
    //Set a new inOrder status
    protected void setInOrder(boolean newInOrder){
        this.inOrder = newInOrder;
    }
    
}
