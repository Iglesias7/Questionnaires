/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.TournoiList.Result;

public class Match {
    private Joueur joueur1;
    private Joueur joueur2;
    private Result résultats;

    public Match(Joueur j1, Joueur j2, Result res) {
        this.joueur1 = j1;
        this.joueur2 = j2;
        this.résultats = res;
    }

   
    
    @Override
    public String toString(){
        return getJoueur1().toString() + " et " + getJoueur2().toString() ; 
    }

    /**
     * @return the joueur1
     */
    public Joueur getJoueur1() {
        return joueur1;
    }

    /**
     * @param joueur1 the joueur1 to set
     */
    public void setJoueur1(Joueur joueur1) {
        this.joueur1 = joueur1;
    }

    /**
     * @return the joueur2
     */
    public Joueur getJoueur2() {
        return joueur2;
    }

    /**
     * @param joueur2 the joueur2 to set
     */
    public void setJoueur2(Joueur joueur2) {
        this.joueur2 = joueur2;
    }

    /**
     * @return the résultats
     */
    public Result getRésultats() {
        return résultats;
    }


    /**
     * @param résultats the résultats to set
     */
    public void setRésultats(Result résultats) {
        this.résultats = résultats;
    }

    //Pour Modifier un match
    public void updateMatch(Match match) {
        
        this.setJoueur1(match.getJoueur1());
        this.setJoueur2(match.getJoueur2());
        this.setRésultats(match.getRésultats());
        System.out.println(match.getJoueur1());
    }
}
