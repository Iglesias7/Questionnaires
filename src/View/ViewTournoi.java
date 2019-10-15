/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import Model.Joueur;
import Model.Match;
import Model.Tournoi;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Model.TournoiList;
import ViewModel.ViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableView;

/**
 *
 * @author IGLESIAS
 */
public class ViewTournoi extends HBox {
    
    private final ListView<Tournoi> lsTournoi = new ListView<>();
    private final IntegerProperty numLineToSelectedTournoi = new SimpleIntegerProperty(-1);
    private final ViewModel viewmodel;
    
    public ViewTournoi(ViewModel viewmodel) {
        this.viewmodel = viewmodel;
        configtournoi();   
    }
    
    public void configtournoi(){
        lsTournoi.setPrefWidth(850);
//        lsTournoi.setStyle("-fx-background-color: black;");
//        lsTournoi.setStyle("-fx-background-radius: 25px; ");
        this.getChildren().addAll(lsTournoi);
    }

    

    public ListView<Tournoi> getLsTournoi() {
        return lsTournoi;
    }
    
    public SelectionModel<Tournoi> getListViewTournoiModel() {
        return lsTournoi.getSelectionModel();
    }
}