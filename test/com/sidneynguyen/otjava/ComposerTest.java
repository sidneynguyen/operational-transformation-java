package com.sidneynguyen.otjava;

import org.junit.Test;

import static org.junit.Assert.*;

public class ComposerTest {
    @Test
    public void compose1() throws Exception {
        Document document1 = new Document("");
        Document document2 = new Document("");

        Operation operation1 = new Operation();
        operation1.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "A", 1));

        Operation operation2 = new Operation();
        operation2.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, null, 1));
        operation2.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 1, "B", 1));

        document1.applyOperation(operation1);
        document1.applyOperation(operation2);
        assertEquals("AB", document1.getData());

        Composer composer = new Composer();
        document2.applyOperation(composer.compose(operation1, operation2));
        assertEquals("AB", document2.getData());
    }

    @Test
    public void compose2() throws Exception {
        Document document1 = new Document("");
        Document document2 = new Document("");

        Operation operation1 = new Operation();
        operation1.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "A", 1));

        Operation operation2 = new Operation();
        operation2.add(new OperationComponent(OperationComponent.OP_COMP_DELETE, 0, "A", 1));

        document1.applyOperation(operation1);
        document1.applyOperation(operation2);
        assertEquals("", document1.getData());

        Composer composer = new Composer();
        document2.applyOperation(composer.compose(operation1, operation2));
        assertEquals("", document2.getData());
    }

    @Test
    public void compose3() throws Exception {
        Document document1 = new Document("A");
        Document document2 = new Document("A");

        Operation operation1 = new Operation();
        operation1.add(new OperationComponent(OperationComponent.OP_COMP_DELETE, 0, "A", 1));

        Operation operation2 = new Operation();
        operation2.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "B", 1));

        document1.applyOperation(operation1);
        document1.applyOperation(operation2);
        assertEquals("B", document1.getData());

        Composer composer = new Composer();
        document2.applyOperation(composer.compose(operation1, operation2));
        assertEquals("B", document2.getData());
    }

    @Test
    public void compose4() throws Exception {
        Document document1 = new Document("A");
        Document document2 = new Document("A");

        Operation operation1 = new Operation();
        operation1.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, "A", 1));

        Operation operation2 = new Operation();
        operation2.add(new OperationComponent(OperationComponent.OP_COMP_DELETE, 0, "A", 1));

        document1.applyOperation(operation1);
        document1.applyOperation(operation2);
        assertEquals("", document1.getData());

        Composer composer = new Composer();
        document2.applyOperation(composer.compose(operation1, operation2));
        assertEquals("", document2.getData());
    }

}