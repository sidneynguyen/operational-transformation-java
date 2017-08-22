package com.sidneynguyen.otjava;

public class DocumentState {
    private String key;
    public DocumentState(String key) {
        this.key = key;
    }

    public int getRow() {
        return Integer.parseInt(key.substring(0, key.indexOf("-")));
    }

    public int getPosition() {
        return Integer.parseInt(key.substring(key.indexOf("-") + 1, key.length()));
    }
}