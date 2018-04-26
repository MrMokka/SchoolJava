package Arbeidskrav2;

import javafx.scene.image.Image;

public class Question {
    
    
    private Image flag;
    private String country;
    private String capital;
    
    
    public Question(Image flag, String country, String capital){
        this.flag = flag;
        this.country = country;
        this.capital = capital;
    }
    
    
    
    //region Getters
    public Image getFlag(){
        return flag;
    }
    
    public String getCountry(){
        return country;
    }
    
    public String getCapital(){
        return capital;
    }
    //endregion
    
    
}
