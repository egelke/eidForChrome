/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.egelke.chrome.eid.protocol;

/**
 *
 * @author brouckaertb
 */
public class Response extends Action {
    
    private byte[][] data;
    
    public byte[][] getData() { return this.data; }
    public void setData(byte[][] value) { this.data = value; }
}
