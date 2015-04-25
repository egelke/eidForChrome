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
public class ReadAction extends Action {
    public enum File {
        IDENTITY,
        ADDRESS,
        PHOTO
    }
    
    private File file;
    
    public File getFile() { return file; }
    public void setFile(File value) { file = value; }
}
