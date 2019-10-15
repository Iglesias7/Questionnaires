/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.LinkedList;
import java.util.Queue;


public class CareTaker {
    private final Queue<Memento> mementos = new LinkedList<>();
    
    public CareTaker() {}

    public void gardeMemento(Memento mem) {
        mementos.add(mem);
    }

    public Memento getMemento() {
        return mementos.poll();
    }
    
    public Queue<Memento> getMementos(){
        return mementos;
    }
}
