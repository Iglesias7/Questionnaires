/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import ViewModel.ViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewGlobale extends VBox{

    private static final int SPACING = 10;
    private final HBox haut = new HBox(), bas = new HBox(), lbtour = new HBox(), lbIns = new HBox();
    private final HBox imgButton = new HBox();
    
    private final Label lbTournoi = new Label();
    private final Label lbInsrit = new Label();
    private final Label lbMatchs = new Label();
    private final IntegerProperty numLineSelectedTournoi = new SimpleIntegerProperty(-1);
    private final IntegerProperty numLineSelectedMatch = new SimpleIntegerProperty(-1);
    private final IntegerProperty numLineSelectedJoueur = new SimpleIntegerProperty(-1);
    
    private final ViewModel viewmodel;
    public final ViewTournoi viewtournoi;
    public final ViewJoueur viewjoueur ; 
    public final ViewMatch viewmatch; 
    

    public ViewGlobale(Stage primaryStage, ViewModel viewmodel) {
        this.viewmodel = viewmodel;
        viewtournoi = new ViewTournoi(viewmodel);
        viewmatch = new ViewMatch(primaryStage, viewmodel);
        viewjoueur = new ViewJoueur(viewmodel);
        
        
        lbTournoi.setText("Tournois");//label tournoi
        
        lbtour.setSpacing(SPACING);//espacement du hbox lbtour
        lbTournoi.setTextFill(Color.WHITE);
        lbtour.getChildren().addAll(lbTournoi);//Ajout du label lbtournoi dans le HBox lbtour
        
        haut.getChildren().add(viewtournoi);//Ajout de la classe view_Tournoi dans le HBox haut
        
        lbInsrit.setText("Joueurs");//label des joueurs
        lbInsrit.setTextFill(Color.WHITE);
        lbMatchs.setText("Liste des Matchs");//label des matchs
        lbMatchs.setTextFill(Color.WHITE);
        lbIns.setSpacing(170);//espacement entre les deux label
        lbIns.getChildren().addAll(lbInsrit, lbMatchs);//Ajout des deux label dans le HBox lbIns
 
        bas.setSpacing(SPACING);
        bas.getChildren().addAll(viewjoueur, viewmatch);//Ajout de ma classe View_Joueur et du VBox dans le HBox bas
        
        this.setStyle("-fx-background-color: cornflowerblue");
        this.setPadding(new Insets(SPACING));
        this.setSpacing(10);
        this.getChildren().addAll(lbtour, haut, lbIns, bas);//Ajout de tous mes HBox dans la VBox window

        
        imgButton.setSpacing(100);
//        imgButton.getChildren().addAll(image0, image1, image2, image3);
        
        configListener();
        configBindings();
                
        Scene scene = new Scene(this, 700, 400);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("League des champions de Questions");
        primaryStage.setScene(scene);
    }    
    //-------------------- Config Listener -------------------------------------------
    
    public void configListener(){
        numLineSelectedTournoi.addListener((o, old, newV) -> {
            viewtournoi.getListViewTournoiModel().select(newV.intValue());
            if(newV.intValue() > 0)
                viewmatch.resetComboBox();
        });
        
        numLineSelectedMatch.addListener((o, old, newV) -> {
            if(numLineSelectedMatch.intValue() != -1)
                viewmatch.setComboBoxValue(viewmodel.selectedMatch());
        });
        
        
    }
    
    //--------------------------- Config Binding ----------------------------------------
    private void configBindings() {
        configDataBindings();
        configActionsBindings();
        configViewModelBindings();
    }

    private void configDataBindings() {
        viewtournoi.getLsTournoi().itemsProperty().bind(viewmodel.lsTournoiProperty());
        viewjoueur.getLsJoueurs().itemsProperty().bindBidirectional(viewmodel.lsJoueurProperty());
        viewmatch.getLsMatch().itemsProperty().bind(viewmodel.lsMatchProperty());
        
        viewjoueur.getLsJoueurs().visibleProperty().bind(viewmodel.getVisibleMatch());
        viewmatch.getLsMatch().visibleProperty().bind(viewmodel.getVisibleMatch());
    }

    private void configActionsBindings() {
        numLineSelectedTournoi.bind(viewmodel.numLineSelectedTournoiProperty());
        numLineSelectedMatch.bind(viewmodel.numLineSelectedMatchProperty());
        numLineSelectedJoueur.bind(viewmodel.numLineSelectedJoueurProperty());
    }

    private void configViewModelBindings() {
        viewmodel.lineSelectionTournoi(viewtournoi.getListViewTournoiModel().selectedIndexProperty());
        viewmodel.lineSelectionMatch(viewmatch.getListViewMatchModel().selectedIndexProperty());
        viewmodel.lineSelectionJoueur(viewjoueur.getListViewJoueurModel().selectedIndexProperty());
    }
}
