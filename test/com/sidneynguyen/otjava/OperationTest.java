package com.sidneynguyen.otjava;

import org.junit.Test;

import static org.junit.Assert.*;

public class OperationTest {
    @Test
    public void simplify() throws Exception {
        Operation operation = new Operation();
        operation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "a", 1));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 1, "b", 1));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 2, "c", 1));
        operation.simplify();

        Operation expectedOperation = new Operation();
        expectedOperation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "abc", 3));

        assertEquals(true, operation.equals(expectedOperation));
    }

    @Test
    public void equals() throws Exception {
        Operation operation = new Operation();
        operation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "abc", 3));

        Operation expectedOperation = new Operation();
        expectedOperation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "abc", 3));

        assertEquals(true, operation.equals(expectedOperation));
    }

}