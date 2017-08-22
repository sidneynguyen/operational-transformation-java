package com.sidneynguyen.otjava;

import java.util.ArrayList;

public class Transformer {
    public Operation transform(Operation clientOperation, Operation serverOperation) {
        ArrayList<OperationComponent> operations = new ArrayList<>();
        ArrayList<OperationComponent> clientOperations = clientOperation.getOperations();
        ArrayList<OperationComponent> serverOperations = serverOperation.getOperations();
        int position = 0;
        int index = 0;
        OperationComponent clientComponent = clientOperations.get(index);
        OperationComponent serverComponent = serverOperations.get(index);
        if (clientComponent.getPosition() != position || serverComponent.getPosition() != position) {
            throw new RuntimeException("Invalid operations");
        }
        if (clientComponent.getLength() > serverComponent.getLength()) {

        } else if (clientComponent.getLength() < serverComponent.getLength()) {

        } else {

        }
        return null;
    }
}
