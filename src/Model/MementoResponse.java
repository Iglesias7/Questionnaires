/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

public class MementoResponse extends UndoableResponses{

    public MementoResponse(Question question, String falseResponse) {
        super(question, falseResponse);
    }

    public Memento createMemento() {
        return new MementoImpl(question, falseResponse);
    }
    
    private void setMemento(Memento mem) {
        MementoImpl memento = (MementoImpl) mem;
        question = memento.getQuestion();
        falseResponse = memento.getNumResponse();
    }
    
    @Override
    public void undo(Memento m) {
        setMemento(m);
    }
    
    private static class MementoImpl implements Memento { 

        private Question question;
        private String  falseResponse;

        private MementoImpl(Question question, String falseResponse) {
            this.question = question;
            this.falseResponse = falseResponse;
        }

        private Question getQuestion() {
            return question;
        }

        private String getNumResponse() {
            return falseResponse;
        }
    }
}
