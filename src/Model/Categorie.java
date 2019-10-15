package Model;

import element.Elem;
import Model.Question;
import java.util.ArrayList;
import java.util.List;
import Model.Elems;

public class Categorie implements Elems {
    private final String name;
    private final int point; 
    private final int numReponse;
    private List<String> reponses = new ArrayList<>();   
    private List<Elem> subCategorie = new ArrayList<>() ;
    private final String hint;
    private final boolean booleanHint;
    
    public Categorie(String nom, int point, int numReponse, List<String> reponse, List<Elem> subCategorie, String hint, boolean booleanHint){
        this.name = nom;
        this.point = point; 
        this.numReponse = numReponse;
        this.reponses = reponse;
        this.subCategorie = subCategorie;
        this.hint = hint;
        this.booleanHint = booleanHint;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPoints() {
        return point;
    }

    @Override
    public int getNumCorrectResponse() {
        return numReponse;
    }

    @Override
    public List<String> getResponses() {
        return reponses;
    }

    @Override
    public List<Elem> getSubCategorie() {
        return subCategorie;
    }
    
    @Override
    public String getHint() {
        return hint;
    }

    @Override
    public boolean getBooleanHint() {
        return booleanHint;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
