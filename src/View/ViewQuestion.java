/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Categorie;
import Model.Joueur;
import Model.Question;
import ViewModel.ViewModelQuestion;
import java.io.File;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewQuestion extends VBox{
    
    private final HBox entete = new HBox();
    private final HBox labelnameHaut = new HBox();
    private final HBox boxMilieu = new HBox();
    private final HBox labelnameBas = new HBox();
    private final HBox Button = new HBox();
    private final HBox milbas = new HBox();
    private final HBox myQuestion = new HBox();
    private final HBox myPoint = new HBox();
    private final HBox response = new HBox();
    
    
    private final HBox categorie = new HBox();
    private final ComboBox<Categorie> categ = new ComboBox<>();
    
    private final VBox disponible = new VBox();
    private final VBox milieu = new VBox();
    private final VBox choisie = new VBox();
    private final VBox myResponse = new VBox();
    
    private final Label titre = new Label("Construction Questionnaire");
    private final Label match = new Label();
    private final Label nbQuestions = new Label();
    
    private final Label questionName = new Label();
    private final Label questionPoint= new Label();
    private final Label reponseName= new Label("Résponses");
    
    private final Label questionNom = new Label("Questions Disponibles");
    private final Label questionChoix= new Label("Questions Choisies");
    
    private final Label pointDispo = new Label();
    private final Label pointQuestionnaire = new Label();
    
    private final Button btgauche = new Button(" <-  ");
    private final Button btdroite = new Button(" ->  ");
    
    private final ImageView btImgSon = new ImageView();
    private final ImageView btImgMute = new ImageView();
    private final ImageView btImgValider = new ImageView();
    private final ImageView btImgAnnuler = new ImageView();
    
    private final RadioButton q1 = new RadioButton();
    private final RadioButton q2 = new RadioButton();
    private final RadioButton q3 = new RadioButton();
    private final RadioButton q4 = new RadioButton();
    
    private final ToggleGroup group = new ToggleGroup();
    
    private final StringProperty lq1 = new SimpleStringProperty();
    private final StringProperty lq2 = new SimpleStringProperty();
    private final StringProperty lq3 = new SimpleStringProperty();
    private final StringProperty lq4 = new SimpleStringProperty();
    
    private final ListView<Question> lsQuestion = new ListView<>();
    private final ListView<Question> lsQuestionChoisie = new ListView<>();
    
    private final IntegerProperty numLineSelectedQuestion = new SimpleIntegerProperty(-1);
    private final IntegerProperty numLineSelectedQuestionChoisie = new SimpleIntegerProperty(-1);
    
    private final String musicURL = "f.mp3";     
    private final Media media = new Media(new File(musicURL).toURI().toString()); 
    private final MediaPlayer mediaPlayer = new MediaPlayer(media); 
         
    private final ViewModelQuestion viewModelQuestion;
    
    private final Joueur j1;
    private final Joueur j2;
    Stage primaryStage ;
    
    public ViewQuestion(Stage primaryStage,ViewModelQuestion viewModelQuestion, Joueur j1, Joueur j2){
        this.viewModelQuestion = viewModelQuestion;
        this.primaryStage = primaryStage;
        this.j1 = j1;
        this.j2 = j2;
        
        configQuestionnaire();
        ConfigListener();
        configBinding();
        
        Scene scene = new Scene(this, 900, 400);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("League des Champions de Questions");
        primaryStage.setScene(scene);
    }

    //***********************CONFIGURATIONS D'INTERFACES****************************************
    private void configQuestionnaire() {
        mediaPlayer.play();
        
        configEntete();
        configCategorie();
        configMileu();
        configButtonGaucheDroite();
        congigPoints();
        configButton();
        configThis();
    }
    
    private void configEntete(){
        entete.setSpacing(170);
        entete.setAlignment(Pos.CENTER);
        entete.getChildren().add(titre);
        
        match.setTextFill(Color.WHITE);
        match.setText(" Match  " + j1 + " - " + j2);
        
        titre.setTextFill(Color.WHITE);
        
        nbQuestions.setTextFill(Color.WHITE);
        
        labelnameHaut.setSpacing(580);
        labelnameHaut.getChildren().addAll(match, nbQuestions);
    }
    
    private void configCategorie(){
        categorie.setPadding(new Insets(10));
        categorie.getChildren().add(categ);
        categ.setPromptText("Toutes les Categories");
        categ.setCursor(Cursor.HAND);
        categ.setMinWidth(125);
        categ.setId("Tous");
    }
    
    private void configMileu(){
        boxMilieu.setSpacing(20);
        configMileuGauche();
        configMileuCentre();
        configMileuDroite();
        boxMilieu.getChildren().addAll(disponible, milieu, choisie);    
    }
    
    private void configMileuGauche(){
        disponible.setStyle("-fx-background-color: cornflowerblue;-fx-border-color: white");
        disponible.getChildren().addAll(questionNom, lsQuestion);
        disponible.setPrefWidth(250);
        disponible.setPrefHeight(300);
        
        questionNom.setPadding(new Insets(10));
        questionNom.setAlignment(Pos.CENTER);
        questionNom.setTextFill(Color.WHITE);
    }
    
    private void configMileuCentre(){
        configMilieuCentreHaut();
        configRadioButton();
        milieu.getChildren().addAll(myQuestion, myPoint, response, myResponse, milbas);
    }
    
    private void configMilieuCentreHaut(){
        milieu.setStyle("-fx-background-color: cornflowerblue;-fx-border-color: white");
        milieu.setPrefWidth(360);
        milieu.setPadding(new Insets(10));
        
        myQuestion.setPrefHeight(80);
        myQuestion.setMaxWidth(260);
        myQuestion.getChildren().add(questionName);
        
        questionName.setAlignment(Pos.TOP_CENTER);
        questionName.setWrapText(true);
        questionName.setTextFill(Color.WHITE);
        
        questionPoint.setTextFill(Color.WHITE);
        
        myPoint.getChildren().add(questionPoint);
        myPoint.setAlignment(Pos.CENTER);
        myPoint.setPrefHeight(70);
    }
    
    private void configRadioButton(){
        reponseName.setTextFill(Color.WHITE);
        response.getChildren().addAll(reponseName);
        response.setAlignment(Pos.CENTER);
        
        q1.setToggleGroup(group);
        q2.setToggleGroup(group);
        q3.setToggleGroup(group);
        q4.setToggleGroup(group);
        
        q1.setTextFill(Color.WHITE);
        q2.setTextFill(Color.WHITE);
        q3.setTextFill(Color.WHITE);
        q4.setTextFill(Color.WHITE);
        
        myResponse.getChildren().addAll(q1, q2, q3, q4);
        myResponse.setAlignment(Pos.CENTER_LEFT);
        myResponse.setStyle("-fx-border-color: white");
        myResponse.setPadding(new Insets(10));
        myResponse.setPrefHeight(50);
    }
    
    private void configMileuDroite(){
        choisie.setStyle("-fx-background-color: cornflowerblue;-fx-border-color: white");
        choisie.getChildren().addAll(questionChoix, lsQuestionChoisie);
        choisie.setPrefWidth(250);
        choisie.setPrefHeight(300);
        
        questionChoix.setPadding(new Insets(10));
        questionChoix.setAlignment(Pos.CENTER);
        questionChoix.setTextFill(Color.WHITE);
    }
    
    private void congigPoints(){
        pointDispo.setTextFill(Color.WHITE);
        pointQuestionnaire.setTextFill(Color.WHITE);
        labelnameBas.setSpacing(580);
        labelnameBas.getChildren().addAll(pointDispo, pointQuestionnaire);
    }
    
    private void configButtonGaucheDroite(){
        btdroite.setCursor(Cursor.HAND);
        btdroite.setCursor(Cursor.HAND);
        btdroite.setTextFill(Color.CORNFLOWERBLUE);
        btdroite.setStyle("-fx-background-color: whitesmoke;");
        
        btgauche.setStyle("-fx-background-color: whitesmoke;");
        btgauche.setTextFill(Color.CORNFLOWERBLUE);
        btgauche.setCursor(Cursor.HAND);
        
        milbas.setAlignment(Pos.CENTER);
        milbas.setPadding(new Insets(10));
        milbas.setSpacing(10);
        milbas.getChildren().addAll(btgauche, btdroite);
    }
    
    private void configButton(){
        btImgValider.setImage(new Image("images/Circled Play_30px.png"));
        btImgAnnuler.setImage(new Image("images/Cancel_30px_1.png"));
        btImgSon.setImage(new Image("images/Audio_30px.png"));
        btImgMute.setImage(new Image("images/No Audio_30px.png"));
        
        btImgSon.setCursor(Cursor.HAND);
        btImgMute.setCursor(Cursor.HAND);
        btImgValider.setCursor(Cursor.HAND);
        btImgAnnuler.setCursor(Cursor.HAND);
        
        Button.setSpacing(20);
        Button.setAlignment(Pos.CENTER);
        Button.getChildren().addAll(btImgSon, btImgMute,btImgValider, btImgAnnuler);
    }
    
    private void configThis(){
        this.setStyle("-fx-background-image: url(\"images/giphy.gif\"); -fx-background-size:910px");
        this.getChildren().addAll(entete, labelnameHaut, categorie, boxMilieu, labelnameBas, Button);
        this.setPadding(new Insets(10));
    }

    //************************Fin des configurations d'interfaces********************************
    
    private SelectionModel<Question> getListViewQuestionModel() {
        return lsQuestion.getSelectionModel();
    }
    
    private SelectionModel<Question> getListViewQuestionChoisieModel() {
        return lsQuestionChoisie.getSelectionModel();
    }
    
    private SelectionModel<Categorie> getListViewCategorie(){
        return categ.getSelectionModel();
    }

    private void configBinding() {
        configDataBindings();
        configActionsBindings();
        configViewModelBindings();
    }

    private void configDataBindings() {
        lsQuestion.itemsProperty().bind(viewModelQuestion.lsQuestionsProperty());
        categ.itemsProperty().bind(viewModelQuestion.lsCategorieProperty());
        categ.getSelectionModel().selectFirst();
        lsQuestionChoisie.itemsProperty().bind(viewModelQuestion.lsQuestionChoisieProperty());
        questionName.textProperty().bindBidirectional(viewModelQuestion.maQuestionProperty());

        questionPoint.textProperty().bind(viewModelQuestion.PointQuestionProperty().asString("%d Points"));

        pointDispo.textProperty().bind(viewModelQuestion.getSomme().asString("point disponible: %d"));
        nbQuestions.textProperty().bind(viewModelQuestion.nombreQuestionsChoisie().asString("Nombre de questions: %d"));
        pointQuestionnaire.textProperty().bind(viewModelQuestion.getSommeChoisie().asString("Points questionnaires: %d/10"));    
        bindRadioButton();
        bindVisibleTableau();
    }
    
    private void bindRadioButton(){
        lq1.bindBidirectional(viewModelQuestion.getLabelq1());
        lq2.bindBidirectional(viewModelQuestion.getLabelq2());
        lq3.bindBidirectional(viewModelQuestion.getLabelq3());
        lq4.bindBidirectional(viewModelQuestion.getLabelq4());
        
        q1.textProperty().bind(lq1);
        q2.textProperty().bind(lq2);
        q3.textProperty().bind(lq3);
        q4.textProperty().bind(lq4);
        
        q1.selectedProperty().bind(viewModelQuestion.ValueRadiobuttonQ1Property());
        q2.selectedProperty().bind(viewModelQuestion.ValueRadiobuttonQ2Property());
        q3.selectedProperty().bind(viewModelQuestion.ValueRadiobuttonQ3Property());
        q4.selectedProperty().bind(viewModelQuestion.ValueRadiobuttonQ4Property());
        
        q1.disableProperty().bind(viewModelQuestion.desableRadiobuttonProperty());
        q2.disableProperty().bind(viewModelQuestion.desableRadiobuttonProperty());
        q3.disableProperty().bind(viewModelQuestion.desableRadiobuttonProperty());
        q4.disableProperty().bind(viewModelQuestion.desableRadiobuttonProperty());
    }
    
    private void bindVisibleTableau(){
        questionName.visibleProperty().bind(viewModelQuestion.visibleTableauProperty());
        questionPoint.visibleProperty().bind(viewModelQuestion.visibleTableauProperty());
        reponseName.visibleProperty().bind(viewModelQuestion.visibleTableauProperty());
        milbas.visibleProperty().bind(viewModelQuestion.visibleTableauProperty());
        myResponse.visibleProperty().bind(viewModelQuestion.visibleTableauProperty());
    }

    private void configActionsBindings() {
        numLineSelectedQuestion.bind(viewModelQuestion.NumLineSelectedQuestionProperty());
        numLineSelectedQuestionChoisie.bind(viewModelQuestion.NumLineSelectedQuestionChoisieProperty());
    }

    private void configViewModelBindings() {
        viewModelQuestion.lineSelectionQuestion(getListViewQuestionModel().selectedIndexProperty());
        viewModelQuestion.lineSelectionQuestionChoisie(getListViewQuestionChoisieModel().selectedIndexProperty());
    }

    private void ConfigListener() {
        configSelect();
        configbtn();
    }
    
    private void configSelect(){
        categ.valueProperty().addListener((observable, oldValue, newValue) -> {
            viewModelQuestion.lsQuestionCategorie(newValue);
        });
        
        numLineSelectedQuestion.addListener((o, old, newV) -> {
            selectLineQuestion(newV.intValue());
        });
    }
    
    private void configbtn() {
        btnGauche();
        btnDroite();
        btnSon();
        btnValider();
        btnAnnuler();
    } 
    
    private void btnGauche(){
        btgauche.setOnAction((ActionEvent event) ->{
            Question q = getListViewQuestionChoisieModel().getSelectedItem();
            Categorie c = getListViewCategorie().getSelectedItem();
            viewModelQuestion.removeQuestionChoisie(q, c);
        });
    }
    
    private void btnDroite(){
        btdroite.setOnAction((ActionEvent event) ->{
            Question q = getListViewQuestionModel().getSelectedItem();
            viewModelQuestion.addQuestionChoisie(q);
        });
    }
    
    private void btnSon(){
        btImgSon.setOnMouseClicked(e -> {
            mediaPlayer.play();
        });
        
        btImgMute.setOnMouseClicked(e -> {
            mediaPlayer.pause();
        });
    }
    
    private void btnValider(){
        btImgValider.setOnMouseClicked(e -> {
            if(viewModelQuestion.lsQuestionChoisieProperty().sizeProperty().get() != 0){
                viewModelQuestion.showJouer(j1, j2);
                mediaPlayer.stop();
                primaryStage.close();
            }
        });
    }
    
    private void btnAnnuler(){
        btImgAnnuler.setOnMouseClicked(e -> {
            mediaPlayer.stop();
            primaryStage.close();
        });
    }
    
    private void selectLineQuestion(int index) {
        getListViewQuestionModel().select(index);
    }
}
