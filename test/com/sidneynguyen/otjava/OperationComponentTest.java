package com.sidneynguyen.otjava;

import org.junit.Test;

import static org.junit.Assert.*;

public class OperationComponentTest {
    @Test
    public void subOperationRetain() throws Exception {
        OperationComponent component =
                new OperationComponent(OperationComponent.OP_COMP_RETAIN, 1, null, 5);
        OperationComponent component1 = component.subOperation(0, 2);
        OperationComponent component2 = component.subOperation(2, 5);

        assertEquals(component1.getPosition(), 1);
        assertEquals(component1.getLength(), 2);

        assertEquals(component2.getPosition(), 3);
        assertEquals(component2.getLength(), 3);
    }

    @Test
    public void subOperationInsert() throws Exception {
        OperationComponent component =
                new OperationComponent(OperationComponent.OP_COMP_INSERT, 1, "abcde", 5);
        OperationComponent component1 = component.subOperation(0, 2);
        OperationComponent component2 = component.subOperation(2, 5);

        assertEquals(component1.getPosition(), 1);
        assertEquals(component1.getLength(), 2);
        assertEquals(component1.getValue(), "ab");

        assertEquals(component2.getPosition(), 3);
        assertEquals(component2.getLength(), 3);
        assertEquals(component2.getValue(), "cde");
    }

    @Test
    public void subOperationDelete() throws Exception {
        OperationComponent component =
                new OperationComponent(OperationComponent.OP_COMP_DELETE, 1, "abcde", 5);
        OperationComponent component1 = component.subOperation(0, 2);
        OperationComponent component2 = component.subOperation(2, 5);

        assertEquals(component1.getPosition(), 1);
        assertEquals(component1.getLength(), 2);
        assertEquals(component1.getValue(), "ab");

        assertEquals(component2.getPosition(), 3);
        assertEquals(component2.getLength(), 3);
        assertEquals(component2.getValue(), "cde");
    }
}