package com.incture.taguig.models;

import java.util.ArrayList;
import java.util.HashMap;

public class BaseMessage {
    private String message = "";
    private String type = "";
    private ArrayList<HashMap<String,String>> actions;
    private boolean visible;

    public BaseMessage() {
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BaseMessage(String message, String type) {
        this.message = message;
        this.type = type;
    }

    public BaseMessage(String message, String type,ArrayList<HashMap<String,String>> objects) {
        this.message = message;
        this.type = type;
        this.actions=objects;
        visible=true;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public ArrayList<HashMap<String, String>> getActions() {
        return actions;
    }

    public void setActions(ArrayList<HashMap<String, String>> actions) {
        this.actions = actions;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}