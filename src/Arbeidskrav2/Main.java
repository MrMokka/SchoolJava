package Arbeidskrav2;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main extends Application {
    
    //region Game Scene Variables
    
    private Label topText = new Label("Top Text");
    private Label bottomText = new Label("Bottom Text");
    private Label scoreText = new Label("Poeng: 0 / 0");
    private Label questCounterText = new Label("1 / 1");
    private Label correctText = new Label("");
    
    private Button submitButton = new Button("Svar");
    private Button nextQuestBtn = new Button("Neste");
    
    private ImageView flagImage = new ImageView();
    private TextField answerField = new TextField();
    
    //endregion
    
    //region Start Scene Variables
    
    private Label questCountText = new Label("Antall spørsmål:");
    private Label fontSizeText = new Label("Font størrelse:");
    private Label fontTypeText = new Label("Font type:");
    private Label langTypeText = new Label("Språk:");
    private Label errorQuestionType = new Label();
    private Label errorQuestionCount = new Label();
    
    private CheckBox findCapital = new CheckBox("Finn hovedstader");
    private CheckBox findCountries = new CheckBox("Finn land");
    private CheckBox reuseQuestions = new CheckBox("Gjenbruke spørsmål");
    
    
    private ChoiceBox<Integer> fontSizeVar = new ChoiceBox<>();
    private ChoiceBox<String> fontTypeVar = new ChoiceBox<>();
    private ChoiceBox<String> langTypeVar = new ChoiceBox<>();
    
    private Button startQuizButton = new Button("Start Quiz");
    private TextField questCountField = new TextField("15");
    
    //endregion
    
    //region End Scene Variables
    
    private Label gameOverText = new Label("Spill avsluttet");
    private Label infoText1 = new Label("Du fikk:");
    private Label infoText2 = new Label("Poeng!");
    private Label infoText3 = new Label("Skriv navn for hightscore:");
    private Label scoreInfoText = new Label("0 / 0");
    
    private Button restartQuizButton = new Button("Restart Quiz");
    private TextField inputName = new TextField();
    
    //endregion
    
    
    //region General Variables
    
    private int score;
    private int maxScore;
    private Random random = new Random();
    private String correctAnswer;
    private int questionCounter = 0;
    
    private Settings settings = new Settings();
    private HashMap<String, String> languageMap = new HashMap<>();
    
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<Question> usedQuestions = new ArrayList<>();
    private ArrayList<Scene> scenes = new ArrayList<>();
    
    private StartScene startScene;
    private GameScene gameScene;
    private EndScene endScene;
    private int sceneCounter = -1;
    
    private Stage activeStage;
    
    //endregion
    
    @Override
    public void start(Stage stage) throws Exception{
    
        /* // All fonts in javafx
        for(String font : Font.getFontNames()){
            System.out.println(font);
        }
        */
        
        activeStage = stage;
        activeStage.setTitle("Quiz");
        activeStage.setResizable(false);
    
        setup();
        
        activeStage.show();
        
    }
    
    
    private void setup(){
        
        readLanguagesFromFile();
        readQuestionsFromFile();
        changeLanguage();
        
        startScene = new StartScene();
        gameScene = new GameScene();
        endScene = new EndScene();
        
        
        nextScene(0);
        
    }
    
    /**
     * Change the displayed scene. Pick a scene using index. Lower than 0 or higher than 2 is next scene
     *
     * @param index The index of the scene you want (0 = start scene, 1 = game scene, 2 = end scene).
     *              If the index is lower than 0 or higher than 2 the next scene is picked.
     */
    private void nextScene(int index){
    
        sceneCounter++;
    
        sceneCounter = index;
        activeStage.setScene(scenes.get(sceneCounter));
        
        if(sceneCounter == 0){
            startScene.refreshFontSettings();
        }else if(sceneCounter == 1){
            nextQuestion();
            gameScene.refreshFontSettings();
        }else if(sceneCounter == 2){
            endScene.refreshFontSettings();
        }
        
    }
    
    /**
     * Function to pick a new question at random from ArrayList.
     * 'Next' button is hidden and 'Submit' button is shown.
     * Also picks a question type, might happen random if user picks more
     * than one type of question.
     */
    private void nextQuestion(){
        
        if(questionCounter == settings.getTotalQuestNum()){
            scoreInfoText.setText(score + " / " + maxScore);
            nextScene(2);
            return;
        }
        questionCounter++;
    
        //Reset submit and next button
        nextQuestBtn.setDefaultButton(false);
        submitButton.setDefaultButton(true);
        nextQuestBtn.setVisible(false);
        submitButton.setVisible(true);
        
        questCounterText.setText(questionCounter + " / " + settings.getTotalQuestNum());
        answerField.setText("");
        correctText.setText("");
        
        //Find random question
        int temp = random.nextInt(questions.size());
        Question quest = questions.get(temp);
        
        //If user dont want to reuse questions, remove from question list
        if(!settings.isReuseQuestions()){
            usedQuestions.add(quest);
            questions.remove(temp);
        }
        
        //Select question type
        if(settings.isFindCountries() && settings.isFindCapitals()){
            //Pick a random question type
            int r = random.nextInt(2);
            if(r == 0){
                findCapital(quest);
            } else {
                findCountry(quest);
            }
        }else if(settings.isFindCapitals()){
            findCapital(quest);
        }else if(settings.isFindCountries()){
            findCountry(quest);
        }
        
    }
    
    /**
     * Question to find the capital of the displayed flag.
     * @param quest A question to display flag image and get answer from
     */
    private void findCapital(Question quest){
        topText.setText(languageMap.get("qCapital") + "?");
        bottomText.setText("");
        flagImage.setImage(quest.getFlag());
        correctAnswer = quest.getCapital();
    }
    
    /**
     * Question to find the country of the displayed flag.
     * @param quest A question to display flag image and get answer from
     */
    private void findCountry(Question quest){
        topText.setText(languageMap.get("qCountry") + "?");
        bottomText.setText("");
        flagImage.setImage(quest.getFlag());
        correctAnswer = quest.getCountry();
    }
    
    /**
     * Function called when 'Submit' button is pressed to submit the answer.
     * The answer is checked for the right answer, points calculated and the
     * user is informed if he got it right or not.
     * Submit button is hidden, and next button is activated.
     */
    private void submitButtonAction(ActionEvent event){
        submitButton.setDefaultButton(false);
        nextQuestBtn.setDefaultButton(true);
        submitButton.setVisible(false);
        nextQuestBtn.setVisible(true);
        
        maxScore++;
        if(answerField.getText().toLowerCase().equals(correctAnswer)){
            bottomText.setText(languageMap.get("correct") + "!");
            score++;
        }else{
            String str = correctAnswer.substring(0, 1).toUpperCase() + correctAnswer.substring(1);
            correctText.setText(languageMap.get("correctAnswer") + ": " + str);
            bottomText.setText(languageMap.get("wrong") + "!");
        }
        scoreText.setText(languageMap.get("points") + ": " + score + " / " + maxScore);
        nextQuestBtn.setVisible(true);
    }
    
    /**
     * Short funtion called by the 'Next' button to get the next question
     */
    private void nextQuestionAction(ActionEvent event){
        nextQuestion();
    }
    
    /**
     * Function to be called when the user presses the 'Start Quiz' button on the start screen.
     * Here the settings will be checked and set, if the user has any invalid settings an error
     * will be displayed to the user and the program will not continue to the quiz.
     */
    private void startQuizAction(ActionEvent event){
        //Reset visual error messages
        errorQuestionType.setText("");
        errorQuestionCount.setText("");
    
        //Local variables
        int temp = 0;
        boolean error = false;
        
        //Get the number of questions. If user types a non number character, create error
        try{
            temp = Integer.parseInt(questCountField.getText());
        } catch(NumberFormatException e){
            errorQuestionCount.setText("Please only type numbers");
            error = true;
        }
        
        //Check if the user has selected one or more of the question types, else create error
        if(!findCountries.isSelected() && !findCapital.isSelected()){
            errorQuestionType.setText("You need to pick a question type");
            error = true;
        }
        
        //Dont switch scene if there is an error
        if(error)
            return;
        
        resetSettings();
        
        //Check if the user wants to reuse the questions
        if(!reuseQuestions.isSelected()){
            //Save how many questions to display, but not more than the number of questions
            if(temp > questions.size()){
                temp = questions.size();
            }
        }
        
        //Setup some settings, then call for the next scene
        settings.setTotalQuestNum(temp);
        settings.setFindCapitals(findCapital.isSelected());
        settings.setFindCountries(findCountries.isSelected());
        settings.setReuseQuestions(reuseQuestions.isSelected());
        scoreText.setText(languageMap.get("points") + ": " + score + " / " + maxScore);
        questCounterText.setText(questionCounter + " / " + settings.getTotalQuestNum());
        nextScene(1);
    }
    
    /**
     * Function to becalled when the user presses the 'Restart Quiz' button on the end scene.
     * The start scene will then be activated.
     */
    private void restartQuizAction(ActionEvent event){
        nextScene(0);
    }
    
    /**
     * Read from questions.json file and create objects of the
     * Question class, then add the object to a ArrayList
     */
    private void readQuestionsFromFile(){
        
        JSONParser parser = new JSONParser();
        
        try{
            Object obj = parser.parse(new FileReader("Arbeidskrav2/questions.json"));
            
            JSONObject jsonObj = (JSONObject) obj;
            JSONArray quests = (JSONArray) jsonObj.get("questions");
            for(int i = 0; i < quests.size(); i++){
                JSONObject tempObj = (JSONObject) quests.get(i);
                String country = (String) tempObj.get("country");
                String flagPath = (String) tempObj.get("flag");
                String capital = (String) tempObj.get("capital");
                
                FileInputStream sourceImage = new FileInputStream(flagPath);
                Image flagImage = new Image(sourceImage, settings.getImageSizeX(), settings.getImageSizeY(), false, true);
                
                questions.add(new Question(flagImage, country, capital));
                
            }
            
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(ParseException e){
            e.printStackTrace();
        }
    }
    
    private void readLanguagesFromFile(){
        JSONParser parser = new JSONParser();
        HashMap<String, HashMap<String, String>> map = settings.getLanguageMap();
        try{
            Object obj = parser.parse(new FileReader("Arbeidskrav2/language.json"));
        
            JSONObject jsonObj = (JSONObject) obj;
            JSONObject languagesRaw = (JSONObject) jsonObj.get("languages");
            
            Set languageKeys = languagesRaw.keySet();
//            System.out.println(languagesRaw);
//            System.out.println(languageKeys);
            
            ArrayList<String> langList = settings.getPossibleLanguages();
            Set keys = languagesRaw.keySet();
            Iterator it = keys.iterator();
            
            while(it.hasNext()){
                HashMap<String, String> langMap = new HashMap<>();
                String languageName = (String) it.next();
                langList.add(languageName);
                JSONObject lang = (JSONObject) languagesRaw.get(languageName);
                
                langMap.put("qCountry", (String) lang.get("qCountry"));
                langMap.put("qCapital", (String) lang.get("qCapital"));
                langMap.put("correct", (String) lang.get("correct"));
                langMap.put("wrong", (String) lang.get("wrong"));
                langMap.put("correctAnswer", (String) lang.get("correctAnswer"));
                langMap.put("points", (String) lang.get("points"));
                langMap.put("questionCount", (String) lang.get("questionCount"));
                langMap.put("answer", (String) lang.get("answer"));
                langMap.put("next", (String) lang.get("next"));
                langMap.put("fontSize", (String) lang.get("fontSize"));
                langMap.put("fontType", (String) lang.get("fontType"));
                langMap.put("findCapital", (String) lang.get("findCapital"));
                langMap.put("findCountries", (String) lang.get("findCountries"));
                langMap.put("reuseQuestions", (String) lang.get("reuseQuestions"));
                langMap.put("startQuiz", (String) lang.get("startQuiz"));
                langMap.put("gameOver", (String) lang.get("gameOver"));
                langMap.put("youGot", (String) lang.get("youGot"));
                langMap.put("typeName", (String) lang.get("typeName"));
                langMap.put("restartQuiz", (String) lang.get("restartQuiz"));
                langMap.put("langType", (String) lang.get("langType"));
                
                map.put(languageName, langMap);
                
            }
            settings.setLanguageMap(map);
            settings.setPossibleLanguages(langList);
        
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(ParseException e){
            e.printStackTrace();
        }
    }
    
    
    /**
     * Simple reset function to reset some score and counter values.
     * This function is most important if the user want to restart the quiz.
     */
    private void resetSettings(){
        maxScore = 0;
        score = 0;
        questionCounter = 0;
        questions.addAll(usedQuestions);
    }
    
    private void changeLanguage(){
    
        languageMap = settings.getLanguageMap().get(settings.getLanguage());
        
        //Start scene texts
        findCapital.setText(languageMap.get("findCapital"));
        findCountries.setText(languageMap.get("findCountries"));
        reuseQuestions.setText(languageMap.get("reuseQuestions"));
        questCountText.setText(languageMap.get("questionCount"));
        startQuizButton.setText(languageMap.get("startQuiz"));
        fontSizeText.setText(languageMap.get("fontSize"));
        fontTypeText.setText(languageMap.get("fontType"));
        startQuizButton.setText(languageMap.get("startQuiz"));
        langTypeText.setText(languageMap.get("langType"));
        
        //Game scene texts
        submitButton.setText(languageMap.get("answer"));
        nextQuestBtn.setText(languageMap.get("next"));
        
        //End scene texts
        infoText1.setText(languageMap.get("youGot"));
        infoText2.setText(languageMap.get("points") + "!");
        infoText3.setText(languageMap.get("typeName"));
        gameOverText.setText(languageMap.get("gameOver"));
        restartQuizButton.setText(languageMap.get("restartQuiz"));
        
    }
    
    //Game scene inner class, here is all the settings for the game scene itself
    class GameScene {
        
        private GridPane gridPane = new GridPane();
        
        public GameScene(){
            addEmptyFlag();
            setup();
        }
    
        private void addEmptyFlag(){
            try{
                FileInputStream sourceImage = new FileInputStream("Arbeidskrav2/empty.png");
                Image flag = new Image(sourceImage, settings.getImageSizeX(), settings.getImageSizeY(), false, false);
                flagImage.setImage(flag);
                flagImage.setPreserveRatio(true);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
        
        }
        
        private void setup(){
            
            settings.setSceneSizeX(450);
            settings.setSceneSizeY(350);
    
            refreshFontSettings();
            
            submitButton.setOnAction(Main.this::submitButtonAction);
            nextQuestBtn.setOnAction(Main.this::nextQuestionAction);
            
            gridPane.setPadding(new Insets(10, 10, 10, 10));
            gridPane.setMinSize(400, 350);
            gridPane.setVgap(10);
            gridPane.setHgap(10);
            
            gridPane.add(topText, 0, 0, 2, 1);
            gridPane.add(correctText, 1, 2);
            gridPane.add(bottomText, 0, 2);
            gridPane.add(scoreText, 0, 4);
            gridPane.add(questCounterText, 2, 4);
            gridPane.add(answerField, 0, 3, 2, 1);
            gridPane.add(nextQuestBtn, 2, 3, 2, 1);
            gridPane.add(submitButton, 2, 3, 2, 1);
            gridPane.add(flagImage, 0, 1, 2, 1);
            
            scenes.add(new Scene(gridPane, settings.getSceneSizeX(), settings.getSceneSizeY()));
        }
        
        private void refreshFontSettings(){
    
            questCounterText.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            topText.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            bottomText.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            scoreText.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            correctText.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            answerField.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            submitButton.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            nextQuestBtn.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            
        }
        
    }
    
    //Start scene inner class, here is all settings for the start scene
    class StartScene {
        
        //Default font settings
        private int startFontSize = settings.getFontSize();
        private String startFontFamily = settings.getFontFamily();
        private GridPane gridPane = new GridPane();
        
        //Constructor
        public StartScene(){
            setup();
        }
        
        private void setup(){
            
            //Set scene size for starting scene
            settings.setSceneSizeX(450);
            settings.setSceneSizeY(550);
    
            //Settings for the font family selector
            fontTypeVar.setItems(FXCollections.observableArrayList(
                    "Areal", "Calibri", "Comic Sans MS Bold", "Corbel", "Marlett", "MS Gothic", "SansSerif Regular", "Segoe UI Black", "Trebuchet MS", "Times New Roman", "Verdana"));
            fontTypeVar.setValue("Areal");
            fontTypeVar.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                startFontFamily = fontTypeVar.getItems().get(newValue.intValue());
                refreshFontSettings();
            });
            
            //Settings for the font size selector
            fontSizeVar.setItems(FXCollections.observableArrayList(10, 12, 14, 16, 18, 20, 22));
            fontSizeVar.setValue(18);
            fontSizeVar.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                startFontSize = fontSizeVar.getItems().get(newValue.intValue());
                refreshFontSettings();
            });
            
            //Settings for language change box
    
            ArrayList<String> langList = settings.getPossibleLanguages();
            for(int i = 0; i < langList.size(); i++){
                langTypeVar.getItems().add(langList.get(i));
            }
            langTypeVar.setValue(settings.getLanguage());
            langTypeVar.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                settings.setLanguage(langTypeVar.getItems().get(newValue.intValue()));
                changeLanguage();
            });
            
            //Refresh font settings for components on the start screen
            refreshFontSettings();
            
            //General settings for the startscreen components
            reuseQuestions.setSelected(false);
            findCapital.setSelected(false);
            findCountries.setSelected(true);
            startQuizButton.setOnAction(Main.this::startQuizAction);
            
            //Settings for the gridpane
            gridPane.setPadding(new Insets(20, 20, 20, 20));
            gridPane.setMinSize(400, 350);
            gridPane.setVgap(10);
            
            
            //Add everything to the gridpane
            gridPane.add(findCapital, 0, 0);
            gridPane.add(findCountries, 0, 1);
            gridPane.add(reuseQuestions, 0, 2);
            gridPane.add(questCountText, 0, 3);
            gridPane.add(questCountField, 0, 4);
            gridPane.add(fontSizeText, 0, 5);
            gridPane.add(fontSizeVar, 0, 6);
            gridPane.add(fontTypeText, 0, 7);
            gridPane.add(fontTypeVar, 0, 8);
            gridPane.add(langTypeText, 0, 9);
            gridPane.add(langTypeVar, 0, 10);
            gridPane.add(errorQuestionType, 1, 0);
            gridPane.add(errorQuestionCount, 1, 4);
            gridPane.add(startQuizButton, 1, 12);
            
            //Create a new scene and save it as a variable in outer class
            scenes.add(new Scene(gridPane, settings.getSceneSizeX(), settings.getSceneSizeY()));
            
        }
        
        //Set font for all components on the start screen
        private void refreshFontSettings(){
            
            settings.setFontFamily(startFontFamily);
            settings.setFontSize(startFontSize);
            reuseQuestions.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            questCountField.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            questCountText.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            findCountries.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            findCapital.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            startQuizButton.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            fontSizeText.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            fontTypeText.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            langTypeText.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            fontSizeVar.setStyle("-fx-font: " + settings.getFontSize() + " '" + settings.getFontFamily() + "'");
            fontTypeVar.setStyle("-fx-font: " + settings.getFontSize() + " '" + settings.getFontFamily() + "'");
            langTypeVar.setStyle("-fx-font: " + settings.getFontSize() + " '" + settings.getFontFamily() + "'");
            
            //Update field sizes
            fontSizeVar.setPrefWidth(startFontSize * 3);
            startQuizButton.setPrefWidth(startFontSize * 10);
        }
        
    }
    
    //End scene inner class, here is all settings for the end scene
    class EndScene {
        
        private GridPane gridPane = new GridPane();
        private BorderPane borderPane = new BorderPane();
        private VBox vBox = new VBox();
        
        //Constructor
        public EndScene(){
            setup();
        }
    
        private void setup(){
        
            //Set scene size for starting scene
            settings.setSceneSizeX(350);
            settings.setSceneSizeY(400);
        
            //Refresh font settings for components on the start screen
            refreshFontSettings();
        
            //General settings for the startscreen components
            restartQuizButton.setOnAction(Main.this::restartQuizAction);
            restartQuizButton.setMinWidth(100f);
            
            vBox.setSpacing(10f);
            vBox.getChildren().addAll(gameOverText, infoText1, scoreInfoText, infoText2, infoText3, inputName, restartQuizButton);
            
            borderPane.setPadding(new Insets(50));
            borderPane.setCenter(vBox);
            borderPane.setMinSize(400, 350);
            
            
        
            //Create a new scene and save it as a variable in outer class
            scenes.add(new Scene(borderPane, settings.getSceneSizeX(), settings.getSceneSizeY()));
        
        }
    
        //Set font for all components on the start screen
        private void refreshFontSettings(){
            
            infoText1.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            infoText2.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            infoText3.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            gameOverText.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            scoreInfoText.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            inputName.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            restartQuizButton.setFont(new Font(settings.getFontFamily(), settings.getFontSize()));
            
        }
    
    }
    
}


