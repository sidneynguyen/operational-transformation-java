package com.sidneynguyen.otjava;

import java.util.ArrayList;

public class Operation {
    private ArrayList<OperationComponent> operations;

    public Operation(ArrayList<OperationComponent> operations) {
        this.operations = operations;
    }

    public ArrayList<OperationComponent> getOperations() {
        return operations;
    }
}
