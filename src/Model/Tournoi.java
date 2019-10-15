/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tournoi {

    //Un tournoi est constitué d'un nom, d'une liste de joueurs et d'une liste de matchs
    private final String name;
    private ObservableList<Joueur> lsJoueur;
    private ObservableList<Match> lsMatch;
    
    //déclaration de la liste qu'on affichera dans le combobox2
    private final ObservableList<Joueur> lsJoueurFilters = FXCollections.observableArrayList();

    
    public Tournoi(String name){
        this.name = name;
        this.lsJoueur = FXCollections.observableArrayList();//juste spécifier le type de Liste qu'on dois utiliser
        this.lsMatch = FXCollections.observableArrayList();//juste spécifier le type de Liste qu'on dois utiliser

    }


    /**
     * @return the name
     */

    public String getName() {
        return name;
    }

    /**
     * @return the lsJoueur
     */
    public ObservableList<Joueur> getLsJoueur() {
        return lsJoueur;
    }

    /**
     * @param lsJoueur the lsJoueur to set
     */
    public void setLsJoueur(ObservableList<Joueur> lsJoueur) {
        this.lsJoueur = lsJoueur;
    }

    /**
     * @return the lsMatchs
     */
    public ObservableList<Match> getLsMatchs() {
        return lsMatch;
    }

    /**
     * @param lsMatchs the lsMatchs to set
     */
    public void setLsMatchs(ObservableList<Match> lsMatchs) {
        this.lsMatch = lsMatchs;
    }
    
    //Ajouter un joueur
    public void addJoueur(Joueur joueur) {
        this.getLsJoueur().add(joueur);
    }

    //Ajouter un match
    public void addMatch(Match match) {
        this.getLsMatchs().add(match);
    }

    //supprimer un match
    public void deleteMatch(Match match) {
        this.getLsMatchs().remove(match);
    }

    //Renvoie la taille de ma liste de matchs
    public int lsMatchSize() {
        return getLsMatchs().size();
    }
    //renvoie un Match
    public Match getMatch(int index) {
//        System.out.println("index : " + index);
        return getLsMatchs().get(index);
    }
    
    //renvoie Un Joueur
    public Joueur getJoueur(int index) {
        return getLsJoueur().get(index);
    }

    public List<Joueur> joueursFilters(Joueur joueurSelected){
        //Pour eviter Que la liste du combobox2 ne se multiplie à chaque fois qu'on selectionne un joueur dans le premier combobox1
       lsJoueurFilters.clear();
       
       //Remplie lsJoueurFilters avec les joueurs de la liste lsJoueur
       for(Joueur joueur : lsJoueur){
           lsJoueurFilters.add(joueur);
       }
       
       //Retir le joueur selectionner dans le combobox1
       lsJoueurFilters.remove(joueurSelected);
       
       //Pour chaque match déjà joueur, retirer tous adversaires du joueur sélectionner dans le combobox1
       for(Match m : lsMatch){
           if(m.getJoueur1().equals(joueurSelected)){
               lsJoueurFilters.remove(m.getJoueur2());
           }else if(m.getJoueur2().equals(joueurSelected)){
               lsJoueurFilters.remove(m.getJoueur1());
           }
       }
       
       //reourner la liste des joueurs à afficher dans le combobox2 
       return lsJoueurFilters ;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
