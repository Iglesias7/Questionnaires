/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Model.TournoiList;
import ViewModel.ViewModel;
import View.ViewGlobale;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        TournoiList text = new TournoiList();   
        ViewModel modelview = new ViewModel(primaryStage, text);
        ViewGlobale view = new ViewGlobale(primaryStage, modelview);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
