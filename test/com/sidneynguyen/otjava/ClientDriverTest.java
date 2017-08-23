package com.sidneynguyen.otjava;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class ClientDriverTest {
    @Test
    public void processChange() throws Exception {
        ClientDriver driver = new ClientDriver("go", "ABC");

        Operation clientOp = new Operation();
        clientOp.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, "go", 2));
        clientOp.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 2, "a", 1));

        Operation serverOp = new Operation();
        serverOp.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, "go", 2));
        serverOp.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 2, "t", 1));
        ServerOperation serverOperation = new ServerOperation(serverOp, UUID.randomUUID().toString(), "ABC");

        driver.enqueueClientOperation(clientOp);
        driver.processChange();

        assertEquals("goa", driver.getDocument().getData());

        driver.enqueueServerOperation(serverOperation);
        driver.processChange();
        assertEquals("goat", driver.getDocument().getData());
    }
}