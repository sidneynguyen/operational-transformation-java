package com.sidneynguyen.otjava;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class ServerGraphTest {
    @Test
    public void applyClientOperation1() throws Exception {
        Document document1 = new Document("");

        ServerGraph graph = new ServerGraph("1234");
        Operation clientOp1 = new Operation();
        clientOp1.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "A", 1));

        OperationNode node = graph.insertClientOperation(clientOp1, "abcd", "1234");
        Operation resultOp1 = graph.applyClientOperation(node);

        document1.applyOperation(resultOp1);

        assertEquals("A", document1.getData());
    }

    @Test
    public void applyClientOperation2() throws Exception {
        Document document1 = new Document("");

        ServerGraph graph = new ServerGraph("1234");
        Operation clientOp1 = new Operation();
        clientOp1.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "A", 1));

        OperationNode node1 = graph.insertClientOperation(clientOp1, "abcd", "1234");
        Operation resultOp1 = graph.applyClientOperation(node1);

        document1.applyOperation(resultOp1);

        assertEquals("A", document1.getData());

        Operation clientOp2 = new Operation();
        clientOp2.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, "A", 1));
        clientOp2.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 1, "B", 1));

        OperationNode node2 = graph.insertClientOperation(clientOp2, "xyz", "abcd");
        Operation resultOp2 = graph.applyClientOperation(node2);

        document1.applyOperation(resultOp2);

        assertEquals("AB", document1.getData());
    }

    @Test
    public void applyClientOperation3() throws Exception {
        Document document1 = new Document("");

        ServerGraph graph = new ServerGraph("1234");
        Operation clientOp1 = new Operation();
        clientOp1.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "A", 1));

        OperationNode node1 = graph.insertClientOperation(clientOp1, "abcd", "1234");
        Operation resultOp1 = graph.applyClientOperation(node1);

        document1.applyOperation(resultOp1);

        assertEquals("A", document1.getData());

        Operation clientOp2 = new Operation();
        clientOp2.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, "A", 1));
        clientOp2.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 1, "C", 1));

        OperationNode node2 = graph.insertClientOperation(clientOp2, "xyz", "abcd");
        Operation resultOp2 = graph.applyClientOperation(node2);

        document1.applyOperation(resultOp2);

        assertEquals("AC", document1.getData());

        Operation clientOp3 = new Operation();
        clientOp3.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, "A", 1));
        clientOp3.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 1, "B", 1));

        OperationNode node3 = graph.insertClientOperation(clientOp3, "54321", "abcd");
        Operation resultOp3 = graph.applyClientOperation(node3);

        document1.applyOperation(resultOp3);

        assertEquals("ABC", document1.getData());
    }

    @Test
    public void applyClientOperation4() throws Exception {
        Document document1 = new Document("");

        ServerGraph graph = new ServerGraph("1234");
        Operation clientOp1 = new Operation();
        clientOp1.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "A", 1));

        OperationNode node1 = graph.insertClientOperation(clientOp1, "abcd", "1234");
        Operation resultOp1 = graph.applyClientOperation(node1);

        document1.applyOperation(resultOp1);

        assertEquals("A", document1.getData());

        Operation clientOp2 = new Operation();
        clientOp2.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, "A", 1));
        clientOp2.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 1, "C", 1));

        OperationNode node2 = graph.insertClientOperation(clientOp2, "xyz", "abcd");
        Operation resultOp2 = graph.applyClientOperation(node2);

        document1.applyOperation(resultOp2);

        assertEquals("AC", document1.getData());

        Operation clientOp3 = new Operation();
        clientOp3.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, "A", 1));
        clientOp3.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 1, "B", 1));

        OperationNode node3 = graph.insertClientOperation(clientOp3, "54321", "abcd");
        Operation resultOp3 = graph.applyClientOperation(node3);

        document1.applyOperation(resultOp3);

        assertEquals("ABC", document1.getData());

        Operation clientOp4 = new Operation();
        clientOp4.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "D", 1));

        OperationNode node4 = graph.insertClientOperation(clientOp4, "www", "1234");
        Operation resultOp4 = graph.applyClientOperation(node4);

        document1.applyOperation(resultOp4);

        assertEquals("ABCD", document1.getData());
    }

}