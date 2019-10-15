
package Model;
import element.Elem;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Random;


public class QuestionsList{
    
    private final ObservableList<Question> questions = FXCollections.observableArrayList();
    private final ObservableList<Question> allQuestions = FXCollections.observableArrayList();
    private final ObservableList<Question> questionsChoisie = FXCollections.observableArrayList();
    private final ObservableList<Categorie> categorie = FXCollections.observableArrayList();    
    private final ObservableList<Elem> lsElement = FXCollections.observableArrayList();
        
    public QuestionsList (){ 
        addCategorie(new Categorie("Toutes les Questions", 0, 0, null, null, null, false));
        element.Elements.loadElemsFromFile("Questions.json").
                forEach(e -> getElement(e));
    }
    
    private void getElement(Elem e){
        if(e.subElems != null){
            addCategorie(new Categorie(e.name, e.points, e.numCorrectResponse, e.responses, e.subElems, e.hint, false));
            e.subElems.forEach(el -> {
                getElement(el);
            });
        }else{
            Random r = new Random();
            int a = r.nextInt(5);
            if(e.points == 3 && a == 0){
                addAllQuestions(new Question(e.name, e.points, e.numCorrectResponse, e.responses, e.subElems, e.fakeHint, false));
            }else{
                addAllQuestions(new Question(e.name, e.points, e.numCorrectResponse, e.responses, e.subElems, e.hint, true));
            }
        }       
    }
    
    public Question getQuestionLine(int num){
        return questions.get(num);
    }
    
    public int getQuestionpoint(int num){
        return getQuestionLine(num).getPoints();
    }

    public ObservableList<Question> getlsQuestions() {
        return questions;
    }
    
    public void addQuestion(Question q){
        questions.add(q);
    }
    public void addAllQuestions(Question q){
        allQuestions.add(q);
    }
    
    public int questionListSize() {
        return questions.size();
    }
    
    public void addQuestionToListQuestionsChoisie(Question q){
        questionsChoisie.add(q);
        questions.remove(q); 
    }
    
    public void removeAllQuestionsChoisie(){
        questions.addAll(questionsChoisie);
        questionsChoisie.removeAll(questionsChoisie);
    }
    
    public IntegerProperty sommePointQuestions(ObservableList<Question> question){
        int somme = 0;
        somme = question.stream().map(q -> q.getPoints()).reduce(0, (a, b) -> a + b);
        IntegerProperty some = new SimpleIntegerProperty(somme);
        return some;
    } 
    
    //-----------------------------------Questions choisie---------------------------------------
    
    public int getQuestionChoisiepoint(int num){
        return getQuestionChoisieLine(num).getPoints();
    }
    
    public ObservableList<Question> getlsQuestionsChoisie() {
        return questionsChoisie;
    }
    
    public void addQuestionChoisie(Question q){
        questionsChoisie.add(q);
    }
    
    public Question getQuestionChoisieLine(int num){
        return questionsChoisie.get(num);
    }
    
    public int questionChoisieListSize() {
        return questionsChoisie.size();
    }    

    public void removeQuestionToListQuestionsChoisie(Question q) {
        questionsChoisie.remove(q);
    }
    
    
    
    //------------------------------------------Categorie------------------------------
    
    public ObservableList<Categorie> getlsCategorie() {
        return categorie;
    }
    
    public ObservableList<Question> getlsQuestionCategorie(Categorie c){
        List<Elem> el = c.getSubCategorie();
        if(c.getName().compareTo("Toutes les Categories") == 0 || c.getName().compareTo("Toutes les Questions") == 0){
            for(Question q : allQuestions){
                questions.add(q);
            }  
        }else{
            for(Elem e: el){
                addQuestionByCategory(e);
            }
        }
        
        for(Question s: questionsChoisie){
            if(questions.contains(s))
                questions.remove(s);
        }
        
        return questions;
    }
    
    public void addCategorie(Categorie c){
        categorie.add(c);
    }
    
    //---------------------------------------Elements------------------------------------
    
    public void addElement(Elem e){
        lsElement.add(e);
    }

    private void addQuestionByCategory(Elem e) {
        Random r = new Random();
        int a = r.nextInt(5);
        Question q = null;
        if(e.subElems == null){
            if(e.points == 3 && a == 0){
                q = new Question(e.name, e.points, e.numCorrectResponse, e.responses, e.subElems, e.fakeHint, false);
                questions.add(q);
                
            }else{
                q = new Question(e.name, e.points, e.numCorrectResponse, e.responses, e.subElems, e.hint, true);
                questions.add(q);
            }
       
        }else{
            e.subElems.forEach(el -> {
                addQuestionByCategory(el);
            });
        }
    }
    
    //--------------------------------------AllQuestions------------------------------------------
    
    public Question getAllQuestionLine(int num){
        return allQuestions.get(num);
    }
    
    public ObservableList<Question> getlsAllQuestions() {
        return allQuestions;
    }
    
    public int getSommeTotalQuestionsChoisie(){
        int somme = 0;
        for(Question q: questionsChoisie){
            somme += q.getPoints();
        }
        return somme;
    }
    
    public int getSommeTotalQuestions(){
       int somme = 0;
        for(Question q: allQuestions){
            somme += q.getPoints();
        }
        return somme;
    }
    
    public int getSommeRest(){
        int a = getSommeTotalQuestions();
        int b = getSommeTotalQuestionsChoisie();
        return a - b;
    }
}
