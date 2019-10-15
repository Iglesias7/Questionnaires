/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModel;

import Model.Categorie;
import Model.Joueur;
import Model.Question;
import Model.QuestionsList;
import View.ViewQuestionJouer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ViewModelQuestion {
    private final QuestionsList questionsList;
    private final ViewModel viewModel;
    private final Stage stage ;
    
    private final IntegerProperty numLineSelectedQuestion = new SimpleIntegerProperty(-1);
    private final IntegerProperty numLineSelectedQuestionChoisie = new SimpleIntegerProperty(-1);
    
    private final SimpleListProperty<Question> lsQuestions = new SimpleListProperty<>();
    private final ObjectProperty<String> maQuestion = new SimpleObjectProperty<>();
    private final IntegerProperty pointQuestion = new SimpleIntegerProperty();
    private final ObjectProperty<Question> question = new SimpleObjectProperty<>();
    
    private final IntegerProperty somme = new SimpleIntegerProperty(0);
    private final IntegerProperty sommeChoisie = new SimpleIntegerProperty(0);
    
    private final BooleanProperty desableRadiobutton = new SimpleBooleanProperty(true);
    private final BooleanProperty visibleTableau = new SimpleBooleanProperty(false);
    private final BooleanProperty valueRadiobuttonq1 = new SimpleBooleanProperty(false);
    private final BooleanProperty valueRadiobuttonq2 = new SimpleBooleanProperty(false);
    private final BooleanProperty valueRadiobuttonq3 = new SimpleBooleanProperty(false);
    private final BooleanProperty valueRadiobuttonq4 = new SimpleBooleanProperty(false);
    
    private final StringProperty labelq1 = new SimpleStringProperty();
    private final StringProperty labelq2 = new SimpleStringProperty();
    private final StringProperty labelq3 = new SimpleStringProperty();
    private final StringProperty labelq4 = new SimpleStringProperty();
        
    private final BooleanProperty c_estUneQuestionChoisie = new SimpleBooleanProperty(false);
    
    
   
    
    public ViewModelQuestion(Stage stage, QuestionsList questions, ViewModel viewModel){
        this.questionsList = questions;
        this.viewModel = viewModel;
        this.stage = stage;
        
        sommePointQuestionsActuel();
        configApplicativeLogic();
    }

    private void configApplicativeLogic() {
        numLineSelectedQuestion.addListener((o, old, newValue) -> {
            int index = newValue.intValue();
            if(index >= 0 && index < questionsList.questionListSize()){
                c_estUneQuestionChoisie.set(false);
                getTAbleauDeBord(index);
                selectBonneResponse();
            }else{
                visibleTableau.set(false);
            }
        });
        
        numLineSelectedQuestionChoisie.addListener((o, old, newValue) -> {
            int index = newValue.intValue();
            if(index >= 0 && index < questionsList.questionChoisieListSize()){
                c_estUneQuestionChoisie.set(true);
                getTAbleauDeBord(index);
                selectBonneResponse();
            }else{
                visibleTableau.set(false);
            }
        });
    }
    
    private void getTAbleauDeBord(int index){
        if(c_estUneQuestionChoisie.get()){
            question.set(questionsList.getQuestionChoisieLine(index));
        }else{
            question.set(questionsList.getQuestionLine(index));
        }
        visibleTableau.set(true);
        maQuestion.set(question.get().getName());
        pointQuestion.set(question.get().getPoints());
        labelq1.set(question.get().getResponses().get(0));
        labelq2.set(question.get().getResponses().get(1));
        labelq3.set(question.get().getResponses().get(2));
        labelq4.set(question.get().getResponses().get(3));
    }
    
    //**********************************LES GETTERS******************************************************
    
    
    //----------------les listes de categories, de questions et de questionsChoisie-------------------
    public SimpleListProperty<Categorie> lsCategorieProperty() {
        return new SimpleListProperty<>(questionsList.getlsCategorie());
    }
    
    public SimpleListProperty<Question> lsQuestionChoisieProperty() {
        return new SimpleListProperty<>(questionsList.getlsQuestionsChoisie());
    }

    public SimpleListProperty<Question> lsQuestionsProperty() {
        return lsQuestions;
    }

    public BooleanProperty visibleTableauProperty() {
        return visibleTableau;
    }

    //-----------------Sont bindés avec les radioButon(q1, q2, q3, q4) de la vue: elles permettent de selectionner ou non un radioButton ------------------------
    public BooleanProperty ValueRadiobuttonQ1Property() {
        return valueRadiobuttonq1;
    }
    
    public BooleanProperty ValueRadiobuttonQ2Property() {
        return valueRadiobuttonq2;
    }
    
    public BooleanProperty ValueRadiobuttonQ3Property() {
        return valueRadiobuttonq3;
    }
    
    public BooleanProperty ValueRadiobuttonQ4Property() {
        return valueRadiobuttonq4;
    }
    
    //----------Est bindé avec les radioButton(q1, q2, q3, q4) de la vue : elle permet de les desactivés-----------------
    public BooleanProperty desableRadiobuttonProperty() {
        return desableRadiobutton;
    }
    
    //-----------------Est bindé avec le String(questionName) qui permet d'afficher la question dans le tableau de bord de la vue------------------------
    public ObjectProperty<String> maQuestionProperty() {
        return maQuestion;
    }

    //-----------------Est bindé avec le int(questionPoint) qui permet d'afficher les points dans le tableau de bord de la vue------------------------
    public IntegerProperty PointQuestionProperty() {
        return pointQuestion;
    }
    
    //---------------Est bindé avec la somme des points des Questions(pointDispo) de la vue--------------------------------
    public IntegerProperty getSomme() {
        return somme;
    }

    //----------Est bindé avec la somme des points des QuestionsChoisie(pointQuestionnaire) de la vue--------------
    public IntegerProperty getSommeChoisie() {
        return sommeChoisie;
    }
    
    //-----------------Sont bindés avec le String assosié à chaque radioButton dans la vue------------------------
    public StringProperty getLabelq1() {
        return labelq1;
    }

    public StringProperty getLabelq2() {
        return labelq2;
    }

    public StringProperty getLabelq3() {
        return labelq3;
    }

    public StringProperty getLabelq4() {
        return labelq4;
    }
    
    //-------------Est bindé avec le numéro de ligne de la liste de questions sélectionné(numLineSelectedQuestion) dans la vue---------------
    public IntegerProperty NumLineSelectedQuestionProperty() {
        return numLineSelectedQuestion;
    }
    
    //-------------Est bindé avec le numéro de ligne de la liste de questionsChoisie sélectionné(numLineSelectedQuestionChoisie) dans la vue---------------
    public IntegerProperty NumLineSelectedQuestionChoisieProperty() {
        return numLineSelectedQuestionChoisie;
    }
    
    
    //*************************FIN DES GETTERS***************************************************
    
    //--------------------------------------Sommes des points-----------------------------------------
    
    private void sommePointQuestionsActuel() {
        somme.set(questionsList.getSommeRest());
    }
    
    private void sommeTotalPointQuestionsChoisie(){
        sommeChoisie.set(questionsList.getSommeTotalQuestionsChoisie());
    }
    
    
    //------------------------------Nombre de questions choisies---------------------------------------
    public ReadOnlyIntegerProperty nombreQuestionsChoisie(){       
        return lsQuestionChoisieProperty().sizeProperty(); 
    }
    

    //------------------Ajout d'une question dans la liste de questionsChoisie------------------------------
    public void addQuestionChoisie(Question q) {
        if(lsQuestionsProperty().contains(q) && (sommeChoisie.get() + q.getPoints()) <= 10){
            questionsList.addQuestionToListQuestionsChoisie(q);//on ajoute la question dans la liste de questionsChoisie et on la supprime de la liste de questions
            sommePointQuestionsActuel();//on met la somme des points de la liste de questions à jours
            sommeTotalPointQuestionsChoisie();//on met la liste des points de la liste de questionsChoisie à jours
        }else if(lsQuestionsProperty().contains(q) && sommeChoisie.get() + q.getPoints() > 10){
            showMessageError("Le nombre de point des questions choisies ne dois pas etre supérieur à 10.");
        }
    }
    
    //------------------Retrait d'une question dans la liste de questionsChoisie------------------------------
    public void removeQuestionChoisie(Question q, Categorie c) {
        if(lsQuestionChoisieProperty().contains(q) && lsQuestionChoisieProperty().sizeProperty().get() > 0){
            questionsList.removeQuestionToListQuestionsChoisie(q);//on retire la question de la liste de questionsChoisie 
            lsQuestionCategorie(c);//On rafraichie la liste de questions de la categorie c:Retenons que dans cette liste figure toutes les questions qui ne sont pas dans la liste de questionsChoisie
            sommePointQuestionsActuel();//on met la somme des points de la liste de questions à jours
            sommeTotalPointQuestionsChoisie();//on met la liste des points de la liste de questionsChoisie à jours
        }
    }
    
    //------------------Charge la liste des questions d'une catégorie----------------------
    public void lsQuestionCategorie(Categorie c) {
        lsQuestions.clear();
        lsQuestions.set(questionsList.getlsQuestionCategorie(c));
        sommePointQuestionsActuel();
    }

    //---------------Bind le numéro de la ligne selectionné de la liste de question sur la vue au numéro de ligne selectionner de la liste de question dans le model------
    public void lineSelectionQuestion(ReadOnlyIntegerProperty numlineQuestion){
        numLineSelectedQuestion.bind(numlineQuestion);
    }
    
    //---------------Bind le numéro de la ligne selectionné de la liste de questionChoisie sur la vue au numéro de ligne selectionner de la liste de questionChoisie dans le model------
    public void lineSelectionQuestionChoisie(ReadOnlyIntegerProperty numlineQuestion){
        numLineSelectedQuestionChoisie.bind(numlineQuestion);
    }
    
    //-----------------------------------Renvoie une fenetre d'erreur-------------------------------------------------
    private void showMessageError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    //------------------------------show la viewQuestionJoueur-----------------------------------------------------
    public void showJouer(Joueur j1, Joueur j2) {
        if(lsQuestionChoisieProperty().sizeProperty().get() != 0){
            Stage primaryStage = new Stage();
            primaryStage.initOwner(stage);
            primaryStage.initModality(Modality.WINDOW_MODAL);
            SimpleListProperty<Question> maListeQuestion = lsQuestionChoisieProperty();
            ViewModelJouer viewModelJouer = new ViewModelJouer(primaryStage, viewModel, questionsList, maListeQuestion);
            ViewQuestionJouer view = new ViewQuestionJouer(primaryStage, viewModelJouer, j1, j2);
            primaryStage.show();
        }
    }
    
    //--------------Selectionne la bonne réponse-------------------------------
    private void selectBonneResponse(){
        switch(question.get().getNumCorrectResponse() - 1){//switch(numéro de la bonne réponse de la question actuelle)
            case 0:
                valueRadiobuttonq1.setValue(true);
                valueRadiobuttonq2.setValue(false);
                valueRadiobuttonq3.setValue(false);
                valueRadiobuttonq4.setValue(false);
                break;
            case 1:
                valueRadiobuttonq1.setValue(false);
                valueRadiobuttonq2.setValue(true);
                valueRadiobuttonq3.setValue(false);
                valueRadiobuttonq4.setValue(false);
                break;
            case 2:
                valueRadiobuttonq1.setValue(false);
                valueRadiobuttonq2.setValue(false);
                valueRadiobuttonq3.setValue(true);
                valueRadiobuttonq4.setValue(false);
                break;
            case 3:
                valueRadiobuttonq1.setValue(false);
                valueRadiobuttonq2.setValue(false);
                valueRadiobuttonq3.setValue(false);
                valueRadiobuttonq4.setValue(true);
                break;
        }
    } 
}