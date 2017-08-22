package com.sidneynguyen.otjava;

import java.util.HashMap;
import java.util.LinkedList;

public class Client {
    private String document;
    private DocumentState currentState;
    private HashMap<String, DocumentState> stateMap;
    private Operation buffer;
    private LinkedList<Operation> serverOpsQueue;

    public Client(String document) {
        this.document = document;
        currentState = new DocumentState("1-1");
        stateMap = new HashMap<>();
    }

    public void receive(Operation serverOp) {
        serverOpsQueue.addLast(serverOp);
    }

    public void performClientOp(Operation clientOp) {
        //document = Operation.applyOperation(document, clientOp);
        String nextKey = (currentState.getRow() + 1) + "-" + (currentState.getPosition());
        DocumentState nextState = new DocumentState(nextKey);
        currentState = nextState;
        stateMap.put(nextKey, nextState);
    }

    public void performServerOp(Operation serverOp) {
        //document = Operation.applyOperation(document, serverOp);
        String nextKey = (currentState.getRow() + 1) + "-" + (currentState.getPosition() + 1);
        DocumentState nextState = new DocumentState(nextKey);
        currentState = nextState;
        stateMap.put(nextKey, nextState);
    }
}
