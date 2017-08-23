package com.sidneynguyen.otjava;

import java.util.ArrayList;

public class Operation {
    private ArrayList<OperationComponent> operationComponents;

    public Operation(ArrayList<OperationComponent> operationComponents) {
        this.operationComponents = operationComponents;
    }

    public Operation() {
        operationComponents = new ArrayList<>();
    }

    public Operation(Operation operation) {
        operationComponents = new ArrayList<>(operation.getOperationComponents());
    }

    public ArrayList<OperationComponent> getOperationComponents() {
        return operationComponents;
    }

    public OperationComponent set(int index, OperationComponent component) {
        return operationComponents.set(index, component);
    }

    public boolean add(OperationComponent component) {
        return operationComponents.add(component);
    }

    public void add(int index, OperationComponent component) {
        operationComponents.add(index, component);
    }

    public OperationComponent get(int index) {
        return operationComponents.get(index);
    }

    public int size() {
        return operationComponents.size();
    }

    public void shiftPositions(int start, int amount) {
        if (start < 0 || start >= operationComponents.size()) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = start; i < operationComponents.size(); i++) {
            operationComponents.get(i).shiftPosition(amount);
        }
    }

    public OperationComponent remove(int index) {
        return operationComponents.remove(index);
    }
}
