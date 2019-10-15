/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Joueur;
import Model.Question;
import ViewModel.ViewModelJouer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewQuestionJouer extends VBox{
    
    private final HBox entete = new HBox();
    private final HBox sousEntete = new HBox();
    private final HBox sousEntete2 = new HBox();
    private final VBox milieu = new VBox();
    private final HBox bas = new HBox();
    private final HBox sousBas = new HBox();
    private final HBox mPoints = new HBox();
    private final HBox boxHint = new HBox();
    private final HBox boxHintLabel = new HBox();
    private final HBox progressBar = new HBox();
    private final HBox ancienneResponse = new HBox();
    
    private final HBox milhaut = new HBox();
    private final HBox milMil = new HBox();
    private final HBox milBas = new HBox();
    
    private final VBox milSousBas = new VBox();
    
    private final Label titre = new Label("Réponses au questionnaire");
    private final Label match = new Label();
    private final Label totalQuestion = new Label();
    private final Label nbQuestions = new Label();
    private final Label mesPoints = new Label();
    private final Label labelHint = new Label();
    
    private final Label questionName = new Label();
    private final Label questionPoint= new Label();
    
    private final Label reponse = new Label("Réponses");
    
    private final Label labelfausseResponse= new Label();
    
    private final Label totalPoints= new Label();
    
    private final Button hint = new Button("Hint");
    private final ImageView btImghint = new ImageView("images/Lion Statue_50px.png");
    
    private final Button valider = new Button("Valider");
    private final Button Annuler = new Button("Annuler");
    
    private final RadioButton q1 = new RadioButton();
    private final RadioButton q2 = new RadioButton();
    private final RadioButton q3 = new RadioButton();
    private final RadioButton q4 = new RadioButton();
    
    private final ToggleGroup group = new ToggleGroup();
    
    private final ProgressBar progress = new ProgressBar();
    private final ProgressIndicator progressIndicator= new ProgressIndicator();
    
    private final StringProperty lq1 = new SimpleStringProperty();
    private final StringProperty lq2 = new SimpleStringProperty();
    private final StringProperty lq3 = new SimpleStringProperty();
    private final StringProperty lq4 = new SimpleStringProperty();
    

    private final ListView<Question> lsQuestionChoisie = new ListView<>();
    
    private final ViewModelJouer viewModelJouer;
    private final Joueur j1;
    private final Joueur j2;
    private final Stage primaryStage ;
    
    public ViewQuestionJouer(Stage primaryStage,ViewModelJouer viewModelJouer, Joueur j1, Joueur j2){
        this.viewModelJouer = viewModelJouer;
        this.primaryStage = primaryStage;

        this.j1 = j1;
        this.j2 = j2;
        configQuestionnaire();
        ConfigListener();
        configBinding();
        
        Scene scene = new Scene(this, 350, 600);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Question Game");
        primaryStage.setScene(scene);
    }

    private void configQuestionnaire() {
        configEntete();
        configMileu();
        configRadioButton();
        congigPoints();
        configButton();
        configThis();
    }
    
    private void configEntete(){
        entete.setSpacing(170);
        entete.setAlignment(Pos.CENTER);
        entete.getChildren().add(titre);
        entete.setPrefHeight(50);
        titre.setTextFill(Color.WHITE);
        
        match.setTextFill(Color.WHITE);
        totalQuestion.setTextFill(Color.WHITE);
        nbQuestions.setTextFill(Color.WHITE);
        match.setText(" Match  " + j1 + " - " + j2);
        
        sousEntete.setSpacing(150);
        sousEntete.getChildren().addAll(match, sousEntete2);
        sousEntete2.getChildren().addAll(nbQuestions, totalQuestion);
    }
    
    private void configMileu(){
        milieu.setStyle("-fx-border-color: white");
        milieu.setPrefHeight(450);
        milieu.setPadding(new Insets(10));
        milieu.setPrefWidth(300);
        milieu.setAlignment(Pos.CENTER);
        
        questionName.setAlignment(Pos.TOP_CENTER);
        questionName.setWrapText(true);
        questionName.setTextFill(Color.WHITE);
        milhaut.setPrefHeight(180);
        milhaut.getChildren().add(questionName);
        milhaut.setPadding(new Insets(10));
        
        questionPoint.setTextFill(Color.WHITE);
        questionPoint.setPrefHeight(180);
        
        milMil.setAlignment(Pos.CENTER);
        milMil.getChildren().add(questionPoint);   
        
        boxHint.getChildren().addAll(btImghint);
        boxHint.setPadding(new Insets(10));
        boxHint.setAlignment(Pos.CENTER);
        
        boxHintLabel.getChildren().add(labelHint);
        boxHintLabel.setPadding(new Insets(10));
        boxHintLabel.setAlignment(Pos.CENTER);
        
        hint.setStyle("-fx-background-color: whitesmoke; -fx-border-radius: 25%;");
        hint.setTextFill(Color.DARKBLUE);
        hint.setAlignment(Pos.CENTER);
        btImghint.setCursor(Cursor.HAND);
        hint.setMinWidth(130);
        
        labelHint.setStyle("-fx-background-color: white");
        labelHint.setTextFill(Color.DARKBLUE);
        labelHint.setMinWidth(130);
        labelHint.setAlignment(Pos.CENTER);
        labelHint.setPadding(new Insets(10));
        
        milieu.getChildren().addAll(milhaut, milMil, boxHint, boxHintLabel, milBas, milSousBas, mPoints);   
    }
    
    private void congigPoints(){
        mPoints.setAlignment(Pos.CENTER);
        mesPoints.setTextFill(Color.WHITE);
        totalPoints.setTextFill(Color.WHITE);
        
        progress.setMinWidth(90);
        
        progressBar.setPrefHeight(50);
        progressIndicator.setPrefHeight(8);
        progressBar.setPadding(new Insets(10));
        progressBar.getChildren().addAll(progress,progressIndicator);
        
        mPoints.setPrefHeight(50);
        mPoints.getChildren().addAll(mesPoints, totalPoints, progressBar);
        
        bas.setPrefHeight(30);
    }
    
    private void configRadioButton(){
        reponse.setTextFill(Color.WHITE);
        milBas.setAlignment(Pos.CENTER);
        milBas.getChildren().add(reponse);
        
        q1.setToggleGroup(group);
        q2.setToggleGroup(group);
        q3.setToggleGroup(group);
        q4.setToggleGroup(group);
        
        q1.setTextFill(Color.WHITE);
        q2.setTextFill(Color.WHITE);
        q3.setTextFill(Color.WHITE);
        q4.setTextFill(Color.WHITE);
        
        q1.setCursor(Cursor.HAND);
        q2.setCursor(Cursor.HAND);
        q3.setCursor(Cursor.HAND);
        q4.setCursor(Cursor.HAND);
        
        ancienneResponse.getChildren().add(labelfausseResponse);
        ancienneResponse.setPadding(new Insets(10));
        ancienneResponse.setAlignment(Pos.CENTER);
        
        labelfausseResponse.setStyle("-fx-background-color: white");
        labelfausseResponse.setTextFill(Color.DARKRED);
        labelfausseResponse.setMinWidth(130);
        labelfausseResponse.setAlignment(Pos.CENTER);
        labelfausseResponse.setPadding(new Insets(10));
        milSousBas.setPadding(new Insets(10));
        milSousBas.setPrefHeight(30);
        milSousBas.setPrefWidth(300);
        milSousBas.setStyle("-fx-border-color: white");
        milSousBas.getChildren().addAll(q1, q2, q3, q4, ancienneResponse);
    }
    
    private void configButton(){
        valider.setStyle("-fx-background-color: darkblue;");
        valider.setTextFill(Color.WHITE);
        Annuler.setStyle("-fx-background-color: white;");
        Annuler.setTextFill(Color.DARKRED);
        sousBas.setAlignment(Pos.CENTER);
        valider.setCursor(Cursor.HAND);
        Annuler.setCursor(Cursor.HAND);
        sousBas.setSpacing(10);
        sousBas.getChildren().addAll(valider, Annuler);
    }
    
    private void configThis(){
        this.setStyle("-fx-background-color: cornflowerblue;");
        this.getChildren().addAll(entete, sousEntete, milieu, bas, sousBas);
        this.setPadding(new Insets(10));
    }
    
    public SelectionModel<Question> getListViewResponseChoisieModel() {
        return lsQuestionChoisie.getSelectionModel();
    }

    private void configBinding() {
        configDataBindings();
    }

    private void configDataBindings() {
        progressIndicator.progressProperty().bind(viewModelJouer.PointGagnerProperty().divide(viewModelJouer.getTotalPoint().multiply(1.0)));
        progress.progressProperty().bind(viewModelJouer.PointGagnerProperty().divide(viewModelJouer.getTotalPoint().multiply(1.0)));

        questionName.textProperty().bindBidirectional(viewModelJouer.maQuestionProperty());
        
        questionPoint.textProperty().bind(viewModelJouer.pointQuestionProperty().asString("%d Points"));
        
        nbQuestions.textProperty().bind(viewModelJouer.nbQuestionProperty().asString("%d"));
        totalQuestion.textProperty().bind(viewModelJouer.nombreQuestionsChoisie().asString("/%d"));
        
        mesPoints.textProperty().bind(viewModelJouer.PointGagnerProperty().asString("Point gagnés: %d"));
        totalPoints.textProperty().bind(viewModelJouer.getTotalPoint().asString("/%d"));
        
        btImghint.visibleProperty().bind(viewModelJouer.hintVisibleTextProperty());
        
        labelHint.visibleProperty().bind(viewModelJouer.labelHintVisibleTextProperty());
        labelHint.textProperty().bind(viewModelJouer.hintTextProperty());
        
        labelfausseResponse.visibleProperty().bind(viewModelJouer.labelfausseResponseVisibleTextProperty());
        labelfausseResponse.textProperty().bind(viewModelJouer.labelfausseResponseTextProperty());
        
        configDataBindingRadioButton();
    }
    
    private void configDataBindingRadioButton() {
        lq1.bindBidirectional(viewModelJouer.getLabelq1());
        lq2.bindBidirectional(viewModelJouer.getLabelq2());
        lq3.bindBidirectional(viewModelJouer.getLabelq3());
        lq4.bindBidirectional(viewModelJouer.getLabelq4());

        q1.selectedProperty().bindBidirectional(viewModelJouer.q1Property());
        q2.selectedProperty().bindBidirectional(viewModelJouer.q2Property());
        q3.selectedProperty().bindBidirectional(viewModelJouer.q3Property());
        q4.selectedProperty().bindBidirectional(viewModelJouer.q4Property());
        
        q1.textProperty().bind(lq1);
        q2.textProperty().bind(lq2);
        q3.textProperty().bind(lq3);
        q4.textProperty().bind(lq4);
    }
    
    private void ConfigListener() {
        
        valider.setOnAction((ActionEvent event) -> {
            RadioButton radiobutton = (RadioButton) group.getSelectedToggle();         
            viewModelJouer.valider(radiobutton, j1, j2);
            if(radiobutton != null){
                radiobutton.setSelected(false);
            }        
        });
        
        Annuler.setOnAction((ActionEvent event) -> {
            viewModelJouer.showStopMatch();
        });
        
        btImghint.setOnMouseClicked(e -> {
            viewModelJouer.showHint();
        });
    }
}
