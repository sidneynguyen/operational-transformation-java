package com.sidneynguyen.otjava;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class ClientDriverTest {
    @Test
    public void processChange1() throws Exception {
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

    @Test
    public void processChange2() throws Exception {
        ClientDriver driver = new ClientDriver("go", "ABC");

        Operation serverOp = new Operation();
        serverOp.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, "go", 2));
        serverOp.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 2, "t", 1));
        ServerOperation serverOperation = new ServerOperation(serverOp, UUID.randomUUID().toString(), "ABC");

        driver.enqueueServerOperation(serverOperation);
        driver.processChange();

        Operation clientOp = new Operation();
        clientOp.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, "go", 2));
        clientOp.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 2, "a", 1));
        clientOp.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 3, "t", 1));

        driver.enqueueClientOperation(clientOp);
        driver.processChange();

        assertEquals("goat", driver.getDocument().getData());
    }

    @Test
    public void processChange3() throws Exception {
        ClientDriver driver = new ClientDriver("", "ABC");

        Operation clientOp1 = new Operation();
        clientOp1.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "A", 1));

        driver.enqueueClientOperation(clientOp1);
        driver.processChange();

        assertEquals("A", driver.getDocument().getData());

        Operation clientOp2 = new Operation();
        clientOp2.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, "A", 1));
        clientOp2.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 1, "B", 1));

        driver.enqueueClientOperation(clientOp2);
        driver.processChange();

        assertEquals("AB", driver.getDocument().getData());


        Operation serverOp1 = new Operation();
        serverOp1.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "C", 1));
        ServerOperation serverOperation1 = new ServerOperation(serverOp1, "XYZ", "ABC");

        driver.enqueueServerOperation(serverOperation1);
        driver.processChange();

        assertEquals("ABC", driver.getDocument().getData());

        Operation serverOp2 = new Operation();
        serverOp2.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, 0, "C", 1));
        serverOp2.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 1, "D", 1));
        ServerOperation serverOperation2 = new ServerOperation(serverOp2, "WWW", "XYZ");

        driver.enqueueServerOperation(serverOperation2);
        driver.processChange();


        assertEquals("ABCD", driver.getDocument().getData());
    }
}