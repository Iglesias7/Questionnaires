/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModel;

import Model.Joueur;
import Model.Match;
import Model.QuestionsList;
import Model.Tournoi;
import javafx.stage.Stage;
import Model.TournoiList;
import Model.TournoiList.Result;
import View.ViewQuestion;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

public class ViewModel{
    
    private final TournoiList tournoiList;
    private final Stage stage ;
    
    private final IntegerProperty numLineSelectedTournoi = new SimpleIntegerProperty(-1);
    private final IntegerProperty numLineSelectedMatch = new SimpleIntegerProperty(-1);
    private final IntegerProperty numLineSelectedJoueur = new SimpleIntegerProperty(-1);
    
    private final BooleanProperty btnadddesable = new SimpleBooleanProperty(false);
    private final BooleanProperty btnjouerdesable = new SimpleBooleanProperty(false);
    private final BooleanProperty btndeldesable = new SimpleBooleanProperty(false);
    private final BooleanProperty btnmoddesable = new SimpleBooleanProperty(false);
    private final BooleanProperty btnanndesable = new SimpleBooleanProperty(false);
    
    private final BooleanProperty visibleMatch = new SimpleBooleanProperty(false);

    
    private final IntegerProperty numLineSelectedToJ1 = new SimpleIntegerProperty(-1);
    private final IntegerProperty numLineSelectedToJ2 = new SimpleIntegerProperty(-1);
    private final IntegerProperty numLineSelectedToRes = new SimpleIntegerProperty(-1);
    
    private final SimpleListProperty<Joueur> lsJoueur = new SimpleListProperty<>();
    private final SimpleListProperty<Match> lsMatch = new SimpleListProperty<>();

    
    
    public ViewModel(Stage stage, TournoiList tournois) {
        this.tournoiList = tournois;
        this.stage = stage;
        configApplicativeLogic();
    }
    
    
    private void configApplicativeLogic() {
        numLineSelectedTournoi.addListener((o -> {
            if (numLineSelectedTournoi.intValue() != -1) {
                lsJoueur.setValue(tournoiList.getLsJoueurs(numLineSelectedTournoi.intValue()));
                lsMatch.setValue(tournoiList.getLsMatch(numLineSelectedTournoi.intValue()));
                btnadddesable.set(false);
                btnjouerdesable.set(false);
                btndeldesable.set(true);
                btnmoddesable.set(true);
                btnanndesable.set(true);
                visibleMatch.set(true);
            }else{
                btnadddesable.set(true);
                btnjouerdesable.set(true);
                btndeldesable.set(false);
                btnmoddesable.set(false);
                btnanndesable.set(false);
                visibleMatch.set(false);
            }
        }));
        
        numLineSelectedMatch.addListener((o -> {
            if (numLineSelectedMatch.intValue() != -1) {
                btnadddesable.set(true);
                btnjouerdesable.set(true);
                btndeldesable.set(false); 
                btnanndesable.set(false);
                btnmoddesable.set(false);
            }else{
                btnadddesable.set(false);
                btnjouerdesable.set(false);
                btndeldesable.set(true); 
                btnanndesable.set(true);
                btnmoddesable.set(true);
            }
        })); 
    }
    
    
    //----------------------les getters--------------------------------------
    
    public SimpleListProperty<Joueur> lsJoueurProperty() {
        return lsJoueur;
    }

    public SimpleListProperty<Match> lsMatchProperty() {
        return lsMatch;
    }
    
    public IntegerProperty numLineSelectedTournoiProperty() {
        return numLineSelectedTournoi;
    }
     
    public IntegerProperty numLineSelectedMatchProperty() {
        return numLineSelectedMatch;
    }
    
    public IntegerProperty numLineSelectedJoueurProperty() {
        return numLineSelectedMatch;
    }
    
    public BooleanProperty BtnadddesableProperty() {
        return btnadddesable;
    }
    
    public BooleanProperty BtnjouerdesableProperty() {
        return btnjouerdesable;
    }
    
    public BooleanProperty BtndeldesableProperty() {
        return btndeldesable;
    }
    
    public BooleanProperty BtnmoddesableProperty() {
        return btnmoddesable;
    }
    
    public BooleanProperty BtnanndesableProperty() {
        return btnanndesable;
    }

    public BooleanProperty getVisibleMatch() {
        return visibleMatch;
    }
    
    
    //Renvoie la liste des tournois
    public SimpleListProperty<Tournoi> lsTournoiProperty() {
        return new SimpleListProperty<>(tournoiList.getLsTournoi());
    }
    

    //----------------------Bind des numéro d'index selectionner dans les combobox-------------------------------------
    
    //utiliser dans ViewMatch(configJoueuREsBindings())
    public void lineSelectionJ1(ReadOnlyIntegerProperty numlinejoueur){
        numLineSelectedToJ1.bind(numlinejoueur);
    }
    
    //utiliser dans ViewMatch(configJoueuREsBindings())
    public void lineSelectionJ2(ReadOnlyIntegerProperty numlinejoueur){
        numLineSelectedToJ2.bind(numlinejoueur);
    }
    
    //utiliser dans ViewMatch(configJoueuREsBindings())
    public void lineSelectionRes(ReadOnlyIntegerProperty numlinejoueur){
        numLineSelectedToRes.bind(numlinejoueur);
    }
    
    //utiliser dans ViewGlobale(configViewModelBindings())
    public void lineSelectionTournoi(ReadOnlyIntegerProperty numlinetournoi) {
        numLineSelectedTournoi.bind(numlinetournoi);
    }
    
    //utiliser dans ViewGlobale(configViewModelBindings())
    public void lineSelectionMatch(ReadOnlyIntegerProperty numlinematch){
        numLineSelectedMatch.bind(numlinematch);
    }
    
    //utiliser dans ViewGlobale(configViewModelBindings())
    public void lineSelectionJoueur(ReadOnlyIntegerProperty numlineJoueur){
        numLineSelectedJoueur.bind(numlineJoueur);
    }

    //Ajout d'un match par la viewMatch
    public void addMatch(){
        if (numLineSelectedToJ1.intValue() != -1 && numLineSelectedToJ2.intValue() != -1 && numLineSelectedToRes.intValue() != -1) {       
            Match match = new Match(j1Select(), j2Select(), resSelect());
            tournoiList.addMatch(numLineSelectedTournoi.intValue(), match);
        }else{
            showMsg("Vous devez choisir deux joueurs différents et vous assuré que tous les champs sont remplis.");
        }
    }
    
    //Ajout d'un match par la viewQuestionJouer
    public void addMatchJouer(Joueur j1, Joueur j2, Result res){   
        Match match = new Match(j1, j2, res);
        tournoiList.addMatch(numLineSelectedTournoi.intValue(), match);
    }
    
    //suppression d'un match
    public void deleteMatch(){
        if(this.numLineSelectedMatch.intValue() >= 0)
            showDeleteDialog(tournoiList.SelectedLineMatch(this.numLineSelectedTournoi.intValue(), this.numLineSelectedMatch.intValue()));
        btnadddesable.set(false);
        btnjouerdesable.set(false);
        btndeldesable.set(true);
        btnmoddesable.set(true);
    }
    
    //Lancer le jeux d'un match
    public void PlayMatch(){
        if (numLineSelectedToJ1.intValue() != -1 && numLineSelectedToJ2.intValue() != -1 && numLineSelectedToRes.intValue() == -1) {
            showDialogJouer(j1Select(), j2Select());
        }else{
            showMsg("Vous devez choisir uniquement un Joueur et son adversaire sans attribué de résultat.");
        }  
    }

    private void showDeleteDialog(Match match) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Demande de confirmation");
        alert.setHeaderText("Suppression du match entre " + match.toString());
        alert.setContentText("Souhaitez-vous supprimer ce match ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            tournoiList.deleteMatch(this.numLineSelectedTournoi.intValue(), match);
        }
    }
    
    private void showMsg(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de sélection");
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public List<Joueur> lsJoueurs(Joueur joueur) {
        return tournoiSelected().joueursFilters(joueur);
    }
    
    public void showDialogJouer(Joueur j1, Joueur j2) {
        if(j1.equals(null) || j2.equals(null))
            throw new RuntimeException();
        else{
            QuestionsList questionsList = new QuestionsList();
            Stage primaryStage = new Stage();
            primaryStage.initOwner(stage);
            primaryStage.initModality(Modality.WINDOW_MODAL);
            ViewModelQuestion viewModelQuestions = new ViewModelQuestion(stage, questionsList, this);
            ViewQuestion view = new ViewQuestion(primaryStage, viewModelQuestions, j1, j2);
            primaryStage.show();
        }
    }
    
    //tournois selectionner
    public Tournoi tournoiSelected(){
        return tournoiList.SelectedLineTournoi(numLineSelectedTournoi.intValue());
    }
    
    //match selectionner
    public Match selectedMatch(){
        return tournoiList.SelectedLineMatch(numLineSelectedTournoi.intValue(), numLineSelectedMatch.intValue());
    }
    
    //Objets selectionnés dans les combobox
    
    public Joueur j1Select(){
        return tournoiList.SelectedLineJoueur(numLineSelectedTournoi.intValue(), numLineSelectedToJ1.intValue());
    }
    
    public Joueur j2Select(){
        return lsJoueurs(j1Select()).get(numLineSelectedToJ2.intValue());
    }
    
    public Result resSelect(){
        return tournoiList.SelectedLineResult(numLineSelectedToRes.intValue());
    }

    public void modifier(Joueur j1, Joueur j2, Result res) {
        if(numLineSelectedToJ1.intValue() == -1 || numLineSelectedToJ2.intValue() == -1 || numLineSelectedToRes.intValue() == -1 || j1.equals(j2)){
            showMsg("Vous devez choisir deux joueurs différents et vous assuré que tous les champs sont remplis.");
        }else{
            Match m = new Match(j1, j2, res);
            if(numLineSelectedMatch.intValue() > -1){
               tournoiList.deleteMatch(numLineSelectedTournoi.intValue(), selectedMatch());
               //tournoiList.addMatch(numLineSelectedTournoi.intValue(), numLineSelectedMatch.get() , new Match(null, null, null));
                tournoiList.addMatch(numLineSelectedTournoi.intValue(), m);
                btnadddesable.set(false);
                btnjouerdesable.set(false);
                btndeldesable.set(true);
                btnmoddesable.set(true);
                btnanndesable.set(true);
            }
        }
    }

    public void confirm() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Demande de confirmation");
        alert.setContentText("Voulez vous vraiment nous quitter?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage.close();
        }
    }

    public void tools() {
        btnanndesable.set(false);
    }
    
    public int getNbrePartie(Joueur j){
        return tournoiList.NbrePartie(j, numLineSelectedTournoi.get());
    }
}
