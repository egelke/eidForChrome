/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.egelke.chrome.eid;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author bryan_000
 */
public class Main {
    
    public static void main(String [] args) throws IOException {
        DataInputStream dis = new DataInputStream(System.in);
        DataOutputStream dos = new DataOutputStream(System.out);
        
        try {
            int len = dis.readInt();
            byte[] buffer = new byte[len];

            int count = 0;
            while (count < len) {
                int read = dis.read(buffer, count, len - count);
                if (read < 0) throw new IOException("Premature end of stream");
                count += read;
            }

            //Lets start with a basic echo

            dos.write(len);
            dos.write(buffer);
            dos.flush();
        } finally {
            dis.close();
            dos.close();
        }
    }
}
