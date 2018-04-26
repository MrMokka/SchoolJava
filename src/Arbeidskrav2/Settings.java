package Arbeidskrav2;

import java.util.ArrayList;
import java.util.HashMap;

public class Settings {
    
    
    private boolean findCapitals = false;
    private boolean findCountries = true;
    
    private boolean reuseQuestions = false;
    private int totalQuestNum = 0;
    
    private int imageSizeX = 280;
    private int imageSizeY = 160;
    
    private int sceneSizeX = 400;
    private int sceneSizeY = 350;
    
    private int fontSize = 18;
    private String fontFamily = "Areal";
    private String language = "norwegian";
    private ArrayList<String> possibleLanguages = new ArrayList<>();
    
    private HashMap<String, HashMap<String, String>> languageMap = new HashMap<>();
    
    
    
    //region Getters
    
    public boolean isFindCapitals(){
        return findCapitals;
    }
    
    public boolean isFindCountries(){
        return findCountries;
    }
    
    public int getImageSizeX(){
        return imageSizeX;
    }
    
    public int getImageSizeY(){
        return imageSizeY;
    }
    
    public int getFontSize(){
        return fontSize;
    }
    
    public String getFontFamily(){
        return fontFamily;
    }
    
    public int getSceneSizeX(){
        return sceneSizeX;
    }
    
    public int getSceneSizeY(){
        return sceneSizeY;
    }
    
    public boolean isReuseQuestions(){
        return reuseQuestions;
    }
    
    public int getTotalQuestNum(){
        return totalQuestNum;
    }
    
    public String getLanguage(){
        return language;
    }
    
    public HashMap<String, HashMap<String, String>> getLanguageMap(){
        return languageMap;
    }
    
    public ArrayList<String> getPossibleLanguages(){
        return possibleLanguages;
    }
    
    //endregion
    
    
    
    //region Setters
    
    public void setFindCapitals(boolean findCapitals){
        this.findCapitals = findCapitals;
    }
    
    public void setFindCountries(boolean findCountries){
        this.findCountries = findCountries;
    }
    
    public void setImageSizeX(int imageSizeX){
        this.imageSizeX = imageSizeX;
    }
    
    public void setImageSizeY(int imageSizeY){
        this.imageSizeY = imageSizeY;
    }
    
    public void setFontSize(int fontSize){
        this.fontSize = fontSize;
    }
    
    public void setFontFamily(String fontFamily){
        this.fontFamily = fontFamily;
    }
    
    public void setSceneSizeX(int sceneSizeX){
        this.sceneSizeX = sceneSizeX;
    }
    
    public void setSceneSizeY(int sceneSizeY){
        this.sceneSizeY = sceneSizeY;
    }
    
    public void setReuseQuestions(boolean reuseQuestions){
        this.reuseQuestions = reuseQuestions;
    }
    
    public void setTotalQuestNum(int totalQuestNum){
        this.totalQuestNum = totalQuestNum;
    }
    
    public void setLanguage(String language){
        this.language = language;
    }
    
    public void setLanguageMap(HashMap<String, HashMap<String, String>> languageMap){
        this.languageMap = languageMap;
    }
    
    public void setPossibleLanguages(ArrayList<String> possibleLanguages){
        this.possibleLanguages = possibleLanguages;
    }
    
    //endregion
    
    
}
