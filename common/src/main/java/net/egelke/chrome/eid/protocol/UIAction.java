/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.egelke.chrome.eid.protocol;

/**
 *
 * @author bryan_000
 */
public class UIAction extends Action {
    
    private int id;
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String value) {
        this.message = value;
    }
    
    
}
