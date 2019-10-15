/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewModel;

import Model.CareTaker;
import Model.Joueur;
import Model.MementoResponse;
import Model.Question;
import Model.QuestionsList;
import Model.TournoiList;
import Model.TournoiList.Result;
import java.util.Optional;
import java.util.Random;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;


public class ViewModelJouer{
    private final QuestionsList questionsList;
    private final Stage primaryStage ;
    private final ViewModel viewModel;
    
    private final IntegerProperty numLineSelectedResponseChoisie = new SimpleIntegerProperty(1);
    private final IntegerProperty nbQuestion = new SimpleIntegerProperty(1);
    private final IntegerProperty pointQuestion = new SimpleIntegerProperty();
    private final IntegerProperty pointGagner = new SimpleIntegerProperty(0);
    private final IntegerProperty pointPasse = new SimpleIntegerProperty();
    
    private final ObjectProperty<String> maQuestion = new SimpleObjectProperty<>();
    private final ObjectProperty<Question> question = new SimpleObjectProperty<>();
    private final ObjectProperty<Result> result = new SimpleObjectProperty<>(); 
    
    private final BooleanProperty visibleText = new SimpleBooleanProperty(false);  
    private final BooleanProperty visible = new SimpleBooleanProperty(false);
    private final BooleanProperty labelfausseResponseVisibleText = new SimpleBooleanProperty(false);
    private final BooleanProperty droitDeRetour = new SimpleBooleanProperty(false);
    private final BooleanProperty fausseResponseExist = new SimpleBooleanProperty(false);
    private final BooleanProperty q1 = new SimpleBooleanProperty(false);
    private final BooleanProperty q2 = new SimpleBooleanProperty(false);
    private final BooleanProperty q3 = new SimpleBooleanProperty(false);
    private final BooleanProperty q4 = new SimpleBooleanProperty(false);
    
    private final StringProperty labelfausseResponseText = new SimpleStringProperty();
    private final StringProperty hintText = new SimpleStringProperty();
    private final StringProperty labelq1 = new SimpleStringProperty();
    private final StringProperty labelq2 = new SimpleStringProperty();
    private final StringProperty labelq3 = new SimpleStringProperty();
    private final StringProperty labelq4 = new SimpleStringProperty();
    
    private final SimpleListProperty<Question> lsQuestionsChoisie;
        
    private  MementoResponse m ;
    private final CareTaker careTaker = new CareTaker();;

    public ViewModelJouer(Stage primaryStage,ViewModel viewModel, QuestionsList questions, SimpleListProperty<Question> lsQuestionsChoisie){       
        this.primaryStage = primaryStage;
        this.questionsList = questions;
        this.viewModel = viewModel;
        this.lsQuestionsChoisie = lsQuestionsChoisie;
        configApplicativeLogic();
    }

    private void configApplicativeLogic() {
        int num = nbQuestion.intValue();
        if(num == 1 && lsQuestionsChoisie.sizeProperty().get() != 0){
            setQuestion(0);
            setVisible();
        }
        
        numLineSelectedResponseChoisie.addListener((o, old, newValue) -> {
            int index = newValue.intValue();
            nbQuestion.set(nbQuestion.getValue() + 1);
            if(index > 0 && index <= lsQuestionsChoisie.sizeProperty().getValue()){
                setQuestion(index - 1);
                setVisible();
            }
        });
    }
    
    //Méthode permettant de charger le texte du hint et de permettre la visibilité du Hint et du label Hint
    private void setVisible(){
        if(question.get().getPoints() == 3){
            visible.set(true);
            hintText.set(question.get().getHint());
        }else{
            visible.set(false);
        }
    }
    
    //Methode permettant de charger les questions et les radioButton
    private void setQuestion(int index){
            question.set(lsQuestionsChoisie.get(index));
            maQuestion.set(question.get().getName());
            pointQuestion.set(question.get().getPoints());
            setRadioButtton(question);
    }
    
    //Methode permettant de charger les radioButton
    private void setRadioButtton(ObjectProperty<Question> qt){
        labelq1.set(qt.get().getResponses().get(0));
        labelq2.set(qt.get().getResponses().get(1));
        labelq3.set(qt.get().getResponses().get(2));
        labelq4.set(qt.get().getResponses().get(3));
    }

    //Méthode permettant le retour sur une question à la quelle on a mal répondue
    private void returnBack(){
        m.undo(careTaker.getMemento());
        question.set(m.getCurrentQuestion());
        if(question.get() != null){
            maQuestion.set(question.get().getName());
            pointQuestion.set(question.get().getPoints());
            setRadioButtton(question);
            setVisible();
//            selectRadioButton(m.getResponseReading());
            labelfausseResponseText.set(m.getResponseReading());
        }
    }
    
    //Méthode appelé tant que numLineSelectedResponseChoisie.getValue() < lsQuestionsChoisie.sizeProperty().getValue()
    private void next(){
//        q1.setValue(false);
//        q2.setValue(false);
//        q3.setValue(false);
//        q4.setValue(false);
        if(careTaker.getMementos() == null){
            fausseResponseExist.set(false);
        }
        if(fausseResponseExist.get() && droitDeRetour.get() && random() == 0){
            returnBack();
            droitDeRetour.set(false);
            labelfausseResponseVisibleText.set(true);
        }else{
            numLineSelectedResponseChoisie.set(numLineSelectedResponseChoisie.intValue() + 1);
            labelfausseResponseVisibleText.set(false);
        }
    }
    
    //Calcul la probabilité de 20%
    private int random(){
        Random r = new Random();
        return r.nextInt(5);
    }
    
    //Méthode appelé à chaque fois que l'on clique sur le bouttn Validé de la vue
    public void valider(RadioButton r, Joueur j1, Joueur j2) {
        if(r != null){
            visibleText.set(false);
            checkResponse(r);
            score(j1, j2);
            if(numLineSelectedResponseChoisie.getValue() < lsQuestionsChoisie.sizeProperty().getValue()){
                next();
            }
        } 
    }
    
    //Cette méthode renvoie la bonne réponse de la question actuelle
    public String responseCorrect(){
        int i = question.get().getNumCorrectResponse() - 1;
        return question.get().getResponses().get(i);
    }

    //Vérifie si une Réponse est correcte ou pas
    public void checkResponse(RadioButton r) {
        pointPasse.set(pointPasse.intValue() + question.get().getPoints());//A chaque click sur valider on calcul le nombre de points qui est passé
        if(responseCorrect().compareTo(r.getText()) == 0){//compare la réponse de la question avec la réponse que l'utilisateur a rentré
            droitDeRetour.set(true);
            //si pointQuestion.intValue() != question.get().getPoints(), cela suppose qu'on a cliqué sur le hint
            if(pointQuestion.intValue() != question.get().getPoints() && question.get().getBooleanHint() == true){
                pointGagner.set(pointGagner.intValue() + 1);//si c'est un hint, il gagne 1 points
            }else{//si c'est un fakeHint où s'il n'a pas cliqué sur le hint, il gagne e nombre de points qui se trouve dans pointQuestion.intValue()
                pointGagner.set(pointGagner.intValue() + pointQuestion.intValue());
            }
        }else{
            droitDeRetour.set(false);
            fausseResponseExist.set(true);
            memento(r.getText());
        }
    }
    
    //Permet de mémoriser une question à la quelle on a mal répondu 
    private void memento(String text) {
        m = new MementoResponse(question.get(), text);
        careTaker.gardeMemento(m.createMemento());
    }

    //Calcul du score d'un joueur. Cette méthode est appelé chaque fois que l'on clique sur le boutton Validé
    private void score(Joueur j1, Joueur j2) {
        int val = getTotalPoint().get();       
        int res = pointGagner.getValue();
        int nbPointRestant = val - pointPasse.intValue();
        
        if(res > val/2){
            result.set(TournoiList.Result.GAIN_JOUEUR2);
            addMatch(j1, j2);
            show("Congratulation. Vous avez gagner");
            primaryStage.close();
        }else if(res < val / 2 && (nbPointRestant + res) < val / 2){
            result.set(TournoiList.Result.GAIN_JOUEUR1);
            addMatch(j1, j2);
            show("Vous avez perdu. Bonne chance pour la suite");
            primaryStage.close();// && nbPointRestant == val / 2
        }else if(res == val / 2 && (numLineSelectedResponseChoisie.getValue() == lsQuestionsChoisie.sizeProperty().getValue())){
            result.set(TournoiList.Result.MATCH_NULL);
            addMatch(j1, j2);
            show("Match null");
            primaryStage.close();
        }
    }
    
    //Bindé dans la view avec le label totalPints
    public IntegerProperty getTotalPoint(){
        IntegerProperty points = new SimpleIntegerProperty();
        for(Question q: lsQuestionsChoisie){
            points.set(points.intValue() + q.getPoints());
        } 
        return points;
    }
    
    //permet d'ajouter un match dans la liste des match dans ViewGlobale
    public void addMatch(Joueur j1, Joueur j2){
        viewModel.addMatchJouer(j1, j2, result.getValue());
    }

    //alert permettant d'afficher le résultat d'un match
    public void show(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("League des champions de questions");
        alert.setHeaderText("Résultat du match");
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    //Methode appelé lors d'un click sur l'imageView du hint
    public void showHint() {
        pointQuestion.set(pointQuestion.get() - 1);
        visibleText.set(true);
        visible.set(false);
    }

    //Alert de confirmation pour arreter un Match
    public void showStopMatch() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Demande de confirmation");
        alert.setHeaderText("Arret du match en cours");
        alert.setContentText("Souhaitez-vous vraiment arreter ce match ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            primaryStage.close();
        }
    }
    
    //************************Les getters**********************
    public SimpleListProperty<Question> lsQuestionChoisieProperty() {
        return new SimpleListProperty<>(questionsList.getlsQuestionsChoisie());
    }

    public ObjectProperty<String> maQuestionProperty() {
        return maQuestion;
    }

    public IntegerProperty pointQuestionProperty() {
        return pointQuestion;
    }

    public IntegerProperty nbQuestionProperty() {
        return nbQuestion;
    }
    
    public IntegerProperty numLineSelectedResponseChoisieProperty() {
        return numLineSelectedResponseChoisie;
    }

    public StringProperty hintTextProperty() {
        return hintText;
    }

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
    
    public IntegerProperty PointGagnerProperty() {
        return pointGagner;
    }
    
    public ReadOnlyIntegerProperty nombreQuestionsChoisie(){       
        return lsQuestionsChoisie.sizeProperty(); 
    }
    
    public void lineSelectionQuestion(ReadOnlyIntegerProperty numlineQuestion){
        numLineSelectedResponseChoisie.bind(numlineQuestion);
    }

    public BooleanProperty labelfausseResponseVisibleTextProperty() {
        return labelfausseResponseVisibleText;
    }

    public StringProperty labelfausseResponseTextProperty() {
        return labelfausseResponseText;
    }
    
    public BooleanProperty hintVisibleTextProperty(){
        return visible;
    }
    
    public BooleanProperty labelHintVisibleTextProperty(){
        return visibleText;
    }

    public BooleanProperty q1Property() {
        return q1;
    }

    public BooleanProperty q2Property() {
        return q2;
    }

    public BooleanProperty q3Property() {
        return q3;
    }

    public BooleanProperty q4Property() {
        return q4;
    } 
    
    
    //    private void selectRadioButton(String response){
//        int cpt = -1, res = cpt;
//        for(String re : question.get().getResponses()){
//            ++cpt;
//            if(re.compareTo(response) == 0){
//                res = cpt;
//                System.out.println(res);
//            }
//        }
//        
//        switch(res){
//            case 0:
//                q1.setValue(true);
//                break;
//            case 1:
//                q2.setValue(true);
//                break;
//            case 2:
//                q3.setValue(true);
//                break;
//            case 3: 
//                q4.setValue(true);
//                break;
//        }
//        
//        if(response.compareTo(labelq1.get()) == 0){
//            q1.setValue(true);
//            q2.setValue(false);
//            q3.setValue(false);
//            q4.setValue(false);
//            System.out.println("ok1");
//        }else if(response.compareTo(labelq2.get()) == 0){
//            q1.setValue(false);
//            q2.setValue(true);
//            q3.setValue(false);
//            q4.setValue(false);
//            System.out.println("ok2");
//        }else if(response.compareTo(labelq3.get()) == 0){
//            q1.setValue(false);
//            q2.setValue(false);
//            q3.setValue(true);
//            q4.setValue(false);
//            System.out.println("ok3");
//        }else if(response.compareTo(labelq4.get()) == 0){
//            q1.setValue(false);
//            q2.setValue(false);
//            q3.setValue(false);
//            q4.setValue(true);
//            System.out.println("ok4");
//        }
//    }
}
