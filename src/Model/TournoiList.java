/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.List;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TournoiList extends Observable {

    /*...............Enums de Résultats*...................*/
    public enum Result{
        GAIN_JOUEUR1 , GAIN_JOUEUR2 , MATCH_NULL
    }

    /*..........Les attributs.............*/
    private final ObservableList<Tournoi> lsTournoi = FXCollections.observableArrayList();
    private final ObservableList<Result> lsResult = FXCollections.observableArrayList();
    
    
    public TournoiList() {
        initData();
    }

    
    /*------------- Les méthodes de Tournoi -------------------------*/
    
    //Renvoie le tournoi spécifier à la position numLineSelectedTournoi (Besoin pour ajouter un tournoi, un match et supprimer un match.
    public Tournoi SelectedLineTournoi(int lineTournoi) {
        return lsTournoi.get(lineTournoi);
    }

    //Renvoie la taille de la liste de Tournoi
    public int lsTournoiSize() {
        return lsTournoi.size();
    }
    
    //AJouter un Tournoi
    public boolean addTournoi(Tournoi tournoi) {
        lsTournoi.add(tournoi);
        return true;
    }
    
    //Renvoie la liste de tournoi
    public ObservableList<Tournoi> getLsTournoi() {
        return lsTournoi;
    }
    
    
    /*------------- Les méthodes de Matchs -------------------------*/
    

    //Renvoie le Match spécifier à la position numLineSelectedMatch(Besoin pour Update un match)
    public Match SelectedLineMatch(int lineTournoi, int lineMatch) {
        return SelectedLineTournoi(lineTournoi).getMatch(lineMatch);
    }
    
    public boolean addMatch(int lineTournoi, int index, Match match) {
        SelectedLineTournoi(lineTournoi).getLsMatchs().add(index, match);
        return true;
    }
    
    public boolean addMatch(int lineTournoi, Match match) {
        SelectedLineTournoi(lineTournoi).getLsMatchs().add(match);
        return true;
    }
    
    //Renvoie la taille de la liste de Matchs
    public int lsMatchSize(int lineTournoi) {
        return SelectedLineTournoi(lineTournoi).lsMatchSize();
    }
    
    //Renvoie la liste de Match associé à un tournoi
    public ObservableList<Match> getLsMatch(int lineTournoi) {
        return SelectedLineTournoi(lineTournoi).getLsMatchs();
    }
    
    //suprimer un match
    public boolean deleteMatch(int lineTournoi, Match match) {
        SelectedLineTournoi(lineTournoi).deleteMatch(match);
        return true;
    }
    
    //Modifier un match
    public boolean updateMatch(int lineTournoi, int lineMatch, Match match) {
        SelectedLineMatch(lineTournoi, lineMatch).updateMatch(match);
        return true;
    }
    
    
    
    /*------------- Les méthodes de Joueur -------------------------*/
    
    
    //Renvoie un Joueur
    public Joueur SelectedLineJoueur(int lineTournoi, int lineJoueur) {
        return SelectedLineTournoi(lineTournoi).getJoueur(lineJoueur);
    }
    
    public Result SelectedLineResult(int lineRes) {
        return toList().get(lineRes);
    }
    
    public List<Result> toList() {
        lsResult.addAll(Result.GAIN_JOUEUR1, Result.GAIN_JOUEUR2, Result.MATCH_NULL);
        return lsResult;
    }
    
    //Renvoie la liste de Joueur associé à un tournoi
    public ObservableList<Joueur> getLsJoueurs(int lineTournoi) {
        return SelectedLineTournoi(lineTournoi).getLsJoueur();
    }
    

  
    
    public boolean addJoueur(int lineTournoi, Joueur joueur) {
        SelectedLineTournoi(lineTournoi).getLsJoueur().add(joueur);
        return true;
    }
 
    
    
    /*----------------------------------------------------------------------------*/

    //Initialisation
    private void initData() {
        /*-----------------Premier Tournoi-----------------*/
        
        Tournoi tournoi1 = new Tournoi("MTN ELITE ONE");
        
        Joueur joueur1 = new Joueur("Alice");
        Joueur joueur2 = new Joueur("Fernand");
        Joueur joueur3 = new Joueur("Gaby");
        Joueur joueur4 = new Joueur("Hugues");
        Joueur joueur5 = new Joueur("Elise");

        tournoi1.addJoueur(joueur1);
        tournoi1.addJoueur(joueur2);
        tournoi1.addJoueur(joueur3);
        tournoi1.addJoueur(joueur4);
        tournoi1.addJoueur(joueur5);

        tournoi1.addMatch(new Match(joueur4, joueur1, Result.GAIN_JOUEUR1));
        
        
 /*-----------------Deuxième Tournoi-----------------*/
 
        Tournoi tournoi2 = new Tournoi("MTN ELITE TWO");
        
        Joueur joueur6 = new Joueur("Iglesias");
        Joueur joueur7 = new Joueur("Idi");
        Joueur joueur8 = new Joueur("Gildas");
        Joueur joueur9 = new Joueur("Hermann");
        Joueur joueur10 = new Joueur("Geraud");

        tournoi2.addJoueur(joueur6);
        tournoi2.addJoueur(joueur7);
        tournoi2.addJoueur(joueur8);
        tournoi2.addJoueur(joueur9);
        tournoi2.addJoueur(joueur10);
        
        tournoi2.addMatch(new Match(joueur6, joueur8, Result.GAIN_JOUEUR2));
        

        addTournoi(tournoi1);
        addTournoi(tournoi2);
    }

    public int NbrePartie(Joueur p, int lineTournoi){
        ObservableList<Match> listMatch = getLsMatch(lineTournoi);
        int cpt = 0;
        for(Match m: listMatch){
            if(m.getJoueur1().getName().compareTo(p.getName()) == 0 || m.getJoueur2().getName().compareTo(p.getName()) == 0){
                ++cpt;
            }
        }
        
       return cpt;
    }
}
