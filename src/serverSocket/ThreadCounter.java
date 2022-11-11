/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSocket;

/**
 * Is the class that contains thread counter
 * @author Aritz
 */
public class ThreadCounter {
    
    private Integer count;

    public synchronized Integer getCount() {
        return count;
    }

    public synchronized void setCount(Integer count) {
        this.count = count;
    }
}
