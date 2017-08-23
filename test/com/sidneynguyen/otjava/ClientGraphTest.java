package com.sidneynguyen.otjava;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class ClientGraphTest {
    @Test
    public void insertClientOperation() {
        String parentKey = UUID.randomUUID().toString();
        OperationNode rootNode = new OperationNode(parentKey);
        ClientGraph graph = new ClientGraph(rootNode);
        Operation clientOp = new Operation();
        clientOp.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "abc", 3));
        OperationNode currNode = graph.insertClientOperation(clientOp, parentKey);

        assertEquals(graph.getCurrentClientNode().getHashKey(), currNode.getHashKey());
    }

    @Test
    public void insertServerOperation() {
        String parentKey = UUID.randomUUID().toString();
        OperationNode rootNode = new OperationNode(parentKey);
        ClientGraph graph = new ClientGraph(rootNode);
        Operation serverOp = new Operation();
        serverOp.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "abc", 3));
        OperationNode currNode = graph.insertClientOperation(serverOp, parentKey);

        assertEquals(graph.getCurrentClientNode().getHashKey(), currNode.getHashKey());
    }


    @Test
    public void applyServerOperation() throws Exception {
        Document document = new Document("go");
        String parentKey = UUID.randomUUID().toString();
        OperationNode rootNode = new OperationNode(parentKey);
        ClientGraph graph = new ClientGraph(rootNode);
        Operation clientOp = new Operation();
        clientOp.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 2, "a", 1));
        OperationNode currClientNode = graph.insertClientOperation(clientOp, parentKey);

        Operation serverOp = new Operation();
        serverOp.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 2, "t", 1));
        OperationNode currServerNode = graph.insertServerOperation(serverOp, parentKey);

        OperationNode currNode = graph.applyServerOperation(currServerNode);

        assertEquals(graph.getCurrentClientNode(), currNode);
        assertEquals(graph.getCurrentServerNode(), currNode);
    }

}