/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Joueur;
import Model.Match;
import ViewModel.ViewModel;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.layout.VBox;

public class ViewJoueur extends VBox{

    private final ListView<Joueur> lsJoueurs = new ListView<>();
    private final ViewModel viewmodel;
//    private final IntegerProperty numLineToSelectedTournoi = new SimpleIntegerProperty(-1);

    public ListView<Joueur> getLsJoueurs() {
        return lsJoueurs;
    }
    
     public SelectionModel<Joueur> getListViewJoueurModel() {
        return getLsJoueurs().getSelectionModel();
    }
    
    public ViewJoueur(ViewModel viewmodel) {
        this.viewmodel = viewmodel;
        lsJoueurs.setPrefWidth(200);
        this.getChildren().add(lsJoueurs);
        
    }
}
