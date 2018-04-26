package dag6;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    
    private int count = 0;
    private Label myLabel = new Label("0");
    
    @Override
    public void start(Stage stage) throws Exception{
        
        Button myButton = new Button("_Count");
        
    
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setMinSize(300, 300);
        pane.setVgap(10);
        pane.setHgap(10);
        
        
        myButton.setOnAction(this::buttonClick);
        
        pane.add(myLabel, 1, 0);
        pane.add(myButton, 0, 0);
        
        Scene scene = new Scene(pane, 300, 300);
        stage.setTitle("Click count");
        stage.setScene(scene);
        
        stage.show();
        
    }
    
    
    
    
    
    private void buttonClick(ActionEvent event){
        
        count += 1;
        
        myLabel.setText("" + count);
        
    }
    
    
}