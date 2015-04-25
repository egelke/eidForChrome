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
public class SignAction extends Action {


    public enum Key {
        AUTHENTICATION,
        NON_REPUDIATION
    };
    
    public enum Scheme {
        RAW,
        PSS,
        PKCS1
    }
    
    public enum HashAlgorithm {
        SHA1,
        SHA256,
        SHA512
    }
    
    private Key key;
    private Scheme scheme;
    private HashAlgorithm hashAlgorithm;
    private byte[] value;
    
    /**
     * Key to use
     * @return 
     */
    public Key getKey() { return key; }

    public void setKey(Key key) { this.key = key; }

    /**
     * Schema to apply.
     * @return 
     */
    public Scheme getScheme() { return scheme; }

    public void setScheme(Scheme value) { this.scheme = value; }

    /**
     * Hashing algorithm that was applied.
     * @return 
     */
    public HashAlgorithm getHashAlgorithm() { return hashAlgorithm; }

    public void setHashAlgorithm(HashAlgorithm value) { this.hashAlgorithm = value; }

    /**
     * The value to sign (with applying the scheme).
     * @return 
     */
    public byte[] getValue() { return value; }

    public void setValue(byte[] value) { this.value = value; }
}
