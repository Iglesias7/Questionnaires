/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import element.Elem;
import java.util.List;

/**
 *
 * @author IGLESIAS
 */
public interface Elems {
    public String getName();
    public int getPoints();
    public int getNumCorrectResponse();
    public List<String> getResponses();
    public List<Elem> getSubCategorie();
    public String getHint();
    public boolean getBooleanHint();
}
