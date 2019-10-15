/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Match;
import Model.Joueur;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Model.TournoiList;
import Model.TournoiList.Result;
import ViewModel.ViewModel;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ViewMatch extends VBox {
    private static final int TEXTSIZE = 400, SPACING = 10;
    private final TableView<Match> lsMatch = new TableView<>();
    
    private final HBox match = new HBox();
    private final HBox tbMatch = new HBox();
    private final VBox btn = new VBox();
    private final HBox grid = new HBox(), Boutons = new HBox();
    
    private final Label lbj1 = new Label();
    private final Label lbj2 = new Label();
    private final Label lbres = new Label();
    
    private final ComboBox<Joueur> comboj1 = new ComboBox<Joueur>();
    private final ComboBox<Joueur> comboj2 = new ComboBox<Joueur>();
    private final ComboBox<Result> combores = new ComboBox<Result>();

    
    private final ImageView btImgPlay = new ImageView();
    private final ImageView btImgAdd = new ImageView();
    private final ImageView btImgDell = new ImageView();
    private final ImageView btImgQuit = new ImageView();
    private final ImageView btImgMod = new ImageView();
    private final ImageView btImgReset = new ImageView();

    private final ViewModel viewmodel;
    private final Stage primaryStage;
    
    public ViewMatch(Stage primaryStage, ViewModel viewmodel) {
        this.viewmodel = viewmodel;
        this.primaryStage = primaryStage;
        
        configmatch();
        configbutton();
        configListener();
        configBinding();
    }
    
    public void configmatch(){  
        getLsMatch().setPrefWidth(530);
        tbMatch.getChildren().addAll(getLsMatch());
        btn.getChildren().addAll(btImgAdd, btImgMod, btImgDell, btImgQuit);
        btn.setSpacing(20);
        btn.setPadding(new Insets(SPACING));
        match.getChildren().addAll(tbMatch, btn);
    }

    public void configbutton(){
        Boutons.setSpacing(SPACING);      
        
        btImgPlay.setImage(new Image("images/Start_30px.png"));
        btImgAdd.setImage(new Image("images/Add_30px.png"));
        btImgDell.setImage(new Image("images/Trash_30px.png"));
        btImgQuit.setImage(new Image("images/Shutdown_30px.png"));
        btImgMod.setImage(new Image("images/Edit File_30px_1.png"));
        btImgReset.setImage(new Image("images/Sync_30px_2.png"));
        
        TableColumn<Match, Joueur> Joueur1 = new TableColumn<Match, Joueur>("Joueur1");
        Joueur1.setMinWidth(60);
        Joueur1.setCellValueFactory(new PropertyValueFactory<>("Joueur1"));
 
        TableColumn<Match, Joueur> Joueur2 = new TableColumn<Match, Joueur>("Joueur2");
        Joueur2.setMinWidth(60);
        Joueur2.setCellValueFactory(new PropertyValueFactory<>("Joueur2"));
        
        TableColumn<Match, Result> Résultats = new TableColumn<Match, Result>("Résultats");
        Résultats.setMinWidth(180);
        Résultats.setCellValueFactory(new PropertyValueFactory<>("Résultats"));
 
        getLsMatch().getColumns().add(Joueur1);
        getLsMatch().getColumns().add(Joueur2);
        getLsMatch().getColumns().add(Résultats);
 
        Joueur1.setSortType(TableColumn.SortType.DESCENDING);
        Joueur2.setSortable(false);
        
        btImgAdd.setCursor(Cursor.HAND);
//        btAdd.setMinWidth(80);	
        
        btImgPlay.setCursor(Cursor.HAND);
//        btJouer.setMinWidth(80);
        
        btImgDell.setCursor(Cursor.HAND);
//        btDel.setMinWidth(80);
        
        btImgReset.setCursor(Cursor.HAND);
//        btReset.setMinWidth(80);
        
        btImgMod.setCursor(Cursor.HAND);
//        btMod.setMinWidth(80);

        btImgQuit.setCursor(Cursor.HAND);
//        btQuit.setMinWidth(80);
        
        comboj1.setCursor(Cursor.HAND);
        comboj1.setPromptText("Joueur1");
        comboj1.setMinWidth(100);
        
        comboj2.setPromptText("Joueur2");
        comboj2.setCursor(Cursor.HAND);
        comboj2.setMinWidth(100);
        
        combores.setPromptText("Resultats");
        combores.setCursor(Cursor.HAND);
        combores.setMinWidth(130);
        
        Boutons.getChildren().addAll(comboj1, comboj2, combores, btImgReset, btImgPlay);
        this.setSpacing(10);
        this.getChildren().addAll(match, Boutons);
    }
    
    //------------------ Configuration des ComboBox ------------------------------------
    
    //Deselectionne les combobox
    public void clearSelection() {
        getListViewJoueur1().clearSelection();
        comboj2.setValue(null);
        getListViewRes().clearSelection();
    }

    //reinitialise les données des combobox
    public void resetComboBox() {
        getListViewJoueur1().select(null);
//        getListViewJoueur2().select(null);
        getListViewRes().select(null);
        comboj1.getItems().setAll(viewmodel.lsJoueurs(null));
        comboj2.getItems().clear();
        combores.getItems().setAll(TournoiList.Result.values()); 
        
        
    }

    //permet de remplir les combobox lors de la selection d'un match
    public void setComboBoxValue(Match match) {
        getListViewJoueur1().select(match.getJoueur1());
        getListViewJoueur2().select(match.getJoueur2());
        getListViewRes().select(match.getRésultats());
    }

 
    //------------------- Selection line des 4 listes------------------------------------
    
    public SelectionModel<Joueur> getListViewJoueur1(){
        return comboj1.getSelectionModel();
    }

    public SelectionModel<Joueur> getListViewJoueur2() {
        return comboj2.getSelectionModel();
    }

    public SelectionModel<Result> getListViewRes() {
        return combores.getSelectionModel();
    }
    
    public SelectionModel<Match> getListViewMatchModel() {
        return getLsMatch().getSelectionModel();
    }

   //--------------------- Config Listener ---------------------------------------

    public void configListener() {
        configComboBox();
        configbtJouer();
        configbtAdd();
        configbtDel();
        configbtQuit();
        configbtReset();
        configbtMod();
    }
    
    //Cette methode est appelé à chaque fois qu'on selectionne un joueur dans le combobox1
    private void configComboBox() {
        getListViewJoueur1().selectedIndexProperty().addListener(o -> {
            Joueur joueur = getListViewJoueur1().getSelectedItem();
            comboj2.getItems().clear();
            comboj2.getItems().setAll(viewmodel.lsJoueurs(joueur));
            viewmodel.tools();
        });
        
    }

    //Cette methode est appelé à chaque fois qu'on click sur le bouton jouer
    private void configbtJouer() {
        btImgPlay.setOnMouseClicked(e -> {
            viewmodel.PlayMatch();
            clearSelection();
        });
    }

    //Cette methode est appelé à chaque fois qu'on click sur le bouton Ajouter
    private void configbtAdd() {
        btImgAdd.setOnMouseClicked(e -> {
            viewmodel.addMatch();
            clearSelection();
        });
    }
    
    //Cette methode est appelé à chaque fois qu'on click sur le bouton supprimer
    private void configbtDel() {
        btImgDell.setOnMouseClicked(e -> {
            viewmodel.deleteMatch();
            clearSelection();
        }); 
    }
    
    //Cette methode est appelé à chaque fois qu'on click sur le bouton Quiter
    private void configbtQuit() {
        btImgQuit.setOnMouseClicked(e -> {
            viewmodel.confirm();
        }); 
    }
    
    //Cette methode est appelé à chaque fois qu'on click sur le bouton Annuler
    private void configbtReset() {
        btImgReset.setOnMouseClicked(e -> {
            clearSelection();
        }); 
    }
    
    //Cette methode est appelé à chaque fois qu'on click sur le bouton modifier
    private void configbtMod() {
        btImgMod.setOnMouseClicked(e -> {
            viewmodel.modifier(getListViewJoueur1().getSelectedItem(), getListViewJoueur2().getSelectedItem(),getListViewRes().getSelectedItem());
            clearSelection();
        }); 
    }
    
    //--------------------------- Config Binding ----------------------------------------------
    private void configBinding() {
        configDataBindings();
        configJoueuREsBindings();
    }

    private void configDataBindings() {
        btImgAdd.disableProperty().bind(viewmodel.BtnadddesableProperty());
        btImgPlay.disableProperty().bind(viewmodel.BtnjouerdesableProperty());
        btImgDell.disableProperty().bind(viewmodel.BtndeldesableProperty());
        btImgMod.disableProperty().bind(viewmodel.BtnmoddesableProperty());
        btImgReset.disableProperty().bind(viewmodel.BtnanndesableProperty());  
    }
    
    private void configJoueuREsBindings() {
        viewmodel.lineSelectionJ1(getListViewJoueur1().selectedIndexProperty());
        viewmodel.lineSelectionJ2(getListViewJoueur2().selectedIndexProperty());
        viewmodel.lineSelectionRes(getListViewRes().selectedIndexProperty());  
    }
    
     /**
     * @return the lsMatch
     */
    public TableView<Match> getLsMatch() {
        return lsMatch;
    }
}
