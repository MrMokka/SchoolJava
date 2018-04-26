package Arbeidskrav1;

public class Weight extends Meter {
    
    
    private String weightInterval;
    
    //Create a new Weight
    public Weight(String weightInterval, String regNum, String location, boolean inOrder){
        super(regNum, location, inOrder);
        this.weightInterval = weightInterval;
    }
    
    //Return a single string containing all information about this Weight
    public String toString(){
        return "Meter type: Weight\n" + "Lowest weight: " + weightInterval + "\n" + super.toString();
    }
    
}
