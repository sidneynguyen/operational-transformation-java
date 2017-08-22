package com.sidneynguyen.otjava;

public class OperationComponent {
    public static final int OP_COMP_RETAIN = 0;
    public static final int OP_COMP_INSERT = 1;
    public static final int OP_COMP_DELETE = 2;

    private int operationType;
    private int position;
    private String value;
    private int length;

    public OperationComponent(int operationType, int position, String value, int length) {
        this.operationType = operationType;
        this.position = position;
        this.value = value;
        this.length = length;
    }

    public int getOperationType() {
        return operationType;
    }

    public int getPosition() {
        return position;
    }

    public String getValue() {
        return value;
    }

    public int getLength() {
        return length;
    }

    public OperationComponent subOperation(int start, int end) {
        if (length < end - start || start < 0 || end > length) {
            throw new IndexOutOfBoundsException();
        }
        if (operationType == OP_COMP_RETAIN) {
            return new OperationComponent(operationType, position + start, value, end - start);
        }
        return new OperationComponent(operationType, position + start, value.substring(start, end),
                end - start);
    }
}