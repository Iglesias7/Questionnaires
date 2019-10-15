/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

public class Responses {
    protected Question question;
    protected String  falseResponse;
    
    public Responses(Question startQuestion, String falseResponse) {
        question = startQuestion;
        this.falseResponse = falseResponse;
    }
  
    public Question getCurrentQuestion() {
        return question;
    }

    public String getResponseReading() {
        return falseResponse;
    }
}
