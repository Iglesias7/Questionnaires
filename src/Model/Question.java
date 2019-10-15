/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import element.Elem;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question implements Elems {
    private final String nom;
    private final int point; 
    private final int numReponse;
    private List<String> reponses = new ArrayList<>();
    private  List<Elem> subCategorie = new ArrayList<>() ;
    private final String hint;
    private final boolean booleanHint;
    
    public Question(Question q){
        this(q.nom, q.point, q.numReponse, q.reponses, q.subCategorie, q.hint, q.booleanHint);
    }
    
    public Question(String nom, int point, int numReponse, List<String> reponse, List<Elem> subCategorie, String hint, boolean booleanHint){
        this.nom = nom;
        this.numReponse = numReponse;
        this.point = point;
        this.reponses = reponse;
        this.subCategorie = subCategorie;
        this.hint = hint;
        this.booleanHint = booleanHint;
    }
    
    @Override
    public List<Elem> getSubCategorie() {
        return subCategorie;
    }
    
    @Override
    public String getName() {
        return nom;
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
    public String getHint() {
        return hint;
    }
    
    @Override
    public String toString(){
        return nom ;
    } 
    
    @Override
    public boolean getBooleanHint() {
        return booleanHint;
    }
    
    @Override
    public boolean equals(Object o){
        if (o instanceof Question){
            Question that = (Question) o;
            return this.getName().equals(that.getName());
        }
        return false;     
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.nom);
        return hash;
    }
}
