package com.sidneynguyen.otjava;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class ClientGraphTest {
    @Test
    public void applyServerOperation() throws Exception {
        ClientGraph graph = new ClientGraph(UUID.randomUUID().toString());
        Operation clientOp = new Operation();
        clientOp.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 2, "a", 1));
        graph.insertClientOperation(clientOp, graph.getCurrentClientNode().getHashKey());

        Operation serverOp = new Operation();
        serverOp.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 2, "t", 1));
        graph.insertServerOperation(serverOp, UUID.randomUUID().toString(),
                graph.getCurrentServerNode().getHashKey());

        Operation operation = graph.applyServerOperation();

        assertEquals(graph.getCurrentClientNode().getParentNodeFromServerOperation().getServerOperation(), operation);
    }

    @Test
    public void generateClientOperationForServer() throws Exception {

    }

}