package com.sidneynguyen.otjava;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransformerTest {
    @Test
    public void transform() throws Exception {
        Document document1 = new Document("ABCDEFGHIJK");
        Document document2 = new Document("ABCDEFGHIJK");
        Operation clientOperation = new Operation();
        clientOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, null, 4));
        clientOperation.add(new OperationComponent(OperationComponent.OP_COMP_DELETE, 4, "EFGHI", 5));
        clientOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 4, null, 2));

        Operation serverOperation = new Operation();
        serverOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, null, 3));
        serverOperation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 3, "VWXYZ", 5));
        serverOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 8, null, 8));

        Transformer transformer = new Transformer();
        OperationPair pair = transformer.transform(clientOperation, serverOperation);

        document1.applyOperation(clientOperation);
        document1.applyOperation(pair.getServerOperation());

        document2.applyOperation(serverOperation);
        document2.applyOperation(pair.getClientOperation());

        assertEquals("ABCVWXYZDJK", document1.getData());
        assertEquals("ABCVWXYZDJK", document2.getData());
    }
}