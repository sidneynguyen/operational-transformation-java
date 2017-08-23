package com.sidneynguyen.otjava;

import org.junit.Test;

import static org.junit.Assert.*;

public class DocumentTest {
    @Test
    public void applyOperation1() throws Exception {
        Document document = new Document("ABCDEFGHI");
        Operation operation = new Operation();
        operation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, null, 3));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_DELETE, 3, "DE", 2));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 3, "OK", 2));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 5, null, 4));
        document.applyOperation(operation);

        assertEquals("ABCOKFGHI", document.getData());
    }

    @Test
    public void applyOperation2() throws Exception {
        Document document = new Document("ABCDEFGHI");
        Operation operation = new Operation();
        operation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, null, 3));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 3, "OK", 2));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_DELETE, 5, "DE", 2));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 5, null, 4));
        document.applyOperation(operation);

        assertEquals("ABCOKFGHI", document.getData());
    }

    @Test
    public void applyOperation3() throws Exception {
        Document document = new Document("ABCDEFGHI");
        Operation operation = new Operation();
        operation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, null, 3));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 3, "OK", 2));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 5, null, 3));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_DELETE, 8, "GH", 2));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 8, null, 1));
        document.applyOperation(operation);

        assertEquals("ABCOKDEFI", document.getData());
    }

    @Test
    public void applyOperation4() throws Exception {
        Document document1 = new Document("ABCDEFGHIJK");
        Operation clientOperation = new Operation();
        clientOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, null, 4));
        clientOperation.add(new OperationComponent(OperationComponent.OP_COMP_DELETE, 4, "EFGHI", 5));
        clientOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 4, null, 2));
        document1.applyOperation(clientOperation);

        assertEquals("ABCDJK", document1.getData());

        Document document2 = new Document("ABCDEFGHIJK");
        Operation serverOperation = new Operation();
        serverOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, null, 3));
        serverOperation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 3, "VWXYZ", 5));
        serverOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 8, null, 8));
        document2.applyOperation(serverOperation);

        assertEquals("ABCVWXYZDEFGHIJK", document2.getData());
    }
}