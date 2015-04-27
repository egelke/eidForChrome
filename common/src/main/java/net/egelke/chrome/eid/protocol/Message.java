/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.egelke.chrome.eid.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


public class Message<T extends Action> {
    public enum Type {
        EID_INPUT,
        EID_OUTPUT
    };
    
    @JsonProperty
    private Type type;
    
    @JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = StartAction.class, name = "START"),
        @JsonSubTypes.Type(value = ReadAction.class, name = "READ"),
        @JsonSubTypes.Type(value = SignAction.class, name = "SIGN"),
        @JsonSubTypes.Type(value = UIAction.class, name = "UI"),
        @JsonSubTypes.Type(value = Response.class, name = "RESPONSE")
    })
    private T action;
    
    public Type getType() { return type; }
    public void setType(Type value) { type = value; }
    
    public T getAction() { return action; }
    public void setAction(T value) { action = value; }
}
