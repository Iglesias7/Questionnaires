/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

public abstract class UndoableResponses extends Responses {
    
    public UndoableResponses(Question question, String falseResponse) {
        super(question, falseResponse);
    }
    
    public abstract void undo(Memento memento);
}
