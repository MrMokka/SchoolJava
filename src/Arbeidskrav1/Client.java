package Arbeidskrav1;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Client {
    
    
    private MeterArchive ma;
    
    
    public Client(){
        mainMethod();
    }
    
    //Two ways of adding data, dummydata has all information in the code
    //and readFromFile, that will read information from a .json file.
    private void mainMethod(){
        ma = new MeterArchive();
        //addDummyData();
        readDataFromFile();
        ma.printAllMeters();
        System.out.println("---------------------");
        System.out.println("Remove: " + ma.removeMeter("T2001"));
        ma.printAllMeters();
        System.out.println("---------------------");
        System.out.println("Edit in order: " + ma.editMeter("T2000", false));;
        ma.printMeter("T2000");
        System.out.println("---------------------");
        System.out.println("Edit location: " +  ma.editMeter("T2000", "R101H10"));
        ma.printMeter("T2000");
    }
    
    //Add data from cod
    private void addDummyData(){
    
        ma.addMeter(new Clock("1.0E - 4s", "K3000", "R101H6", true));
        ma.addMeter(new Clock("0.01s", "K3001", "R101H7", true));
        ma.addMeter(new Thermometer("0.01C - 100C", "T2000", "R101H4", true));
        ma.addMeter(new Thermometer("1.0C - 200C", "T2001", "R101H5", true));
        ma.addMeter(new Weight("0.01g - 10g", "V1000", "R101H1", true));
        ma.addMeter(new Weight("0.1g - 100g", "V1001", "R101H2", true));
    }
    
    //Read from .json file
    private void readDataFromFile(){
    
        JSONParser parser = new JSONParser();
        
        try{
            Object obj = parser.parse(new FileReader("Arbeidskrav1/metersFile.json"));
    
            JSONObject jsonObj = (JSONObject) obj;
    
            JSONArray meters = (JSONArray) jsonObj.get("meters");
            for(int i = 0; i < meters.size(); i++){
                JSONObject tempObj = (JSONObject) meters.get(i);
                String type = (String) tempObj.get("type");
                String interval = (String) tempObj.get("interval");
                String regNum = (String) tempObj.get("regNum");
                String location = (String) tempObj.get("location");
                boolean inOrder = (boolean) tempObj.get("inOrder");
                
                if(type.equals("clock")){
                    ma.addMeter(new Clock(interval, regNum, location, inOrder));
                } else if(type.equals("thermometer")){
                    ma.addMeter(new Thermometer(interval, regNum, location, inOrder));
                } else if(type.equals("weight")){
                    ma.addMeter(new Weight(interval, regNum, location, inOrder));
                } else {
                    System.out.println("Type is not valid");
                }
                
            }
            
            
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        
    }
    
    
    
    
    
    
    
}
