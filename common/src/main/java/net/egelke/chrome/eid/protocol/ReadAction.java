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
    
    private File[] files;
    
    public File[] getFiles() { return files; }
    public void setFiles(File[] value) { files = value; }
}
