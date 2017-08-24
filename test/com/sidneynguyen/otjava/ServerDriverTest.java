package com.sidneynguyen.otjava;

import org.junit.Test;

import static org.junit.Assert.*;

public class ServerDriverTest {
    @Test
    public void processChange1() throws Exception {
        ServerDriver driver = new ServerDriver("", "1234");
        Operation op1 = new Operation();
        op1.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "A", 1));
        driver.enqueueClientOperation(new ServerOperation(op1, "1", "1234"));
        driver.processChange();

        assertEquals("A", driver.getDocument().getData());
    }

    @Test
    public void processChange2() throws Exception {
        ServerDriver driver = new ServerDriver("", "1234");
        Operation op1 = new Operation();
        op1.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "A", 1));
        driver.enqueueClientOperation(new ServerOperation(op1, "1", "1234"));
        driver.processChange();

        assertEquals("A", driver.getDocument().getData());

        Operation op2 = new Operation();
        op2.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "B", 1));
        driver.enqueueClientOperation(new ServerOperation(op2, "2", "1234"));
        driver.processChange();

        assertEquals("AB", driver.getDocument().getData());
    }

    @Test
    public void processChange3() throws Exception {
        ServerDriver driver = new ServerDriver("", "1234");
        Operation op1 = new Operation();
        op1.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "A", 1));
        driver.enqueueClientOperation(new ServerOperation(op1, "1", "1234"));
        driver.processChange();

        assertEquals("A", driver.getDocument().getData());

        Operation op2 = new Operation();
        op2.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "B", 1));
        driver.enqueueClientOperation(new ServerOperation(op2, "2", "1234"));
        driver.processChange();

        assertEquals("AB", driver.getDocument().getData());

        Operation op3 = new Operation();
        op3.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, 0, "C", 1));
        driver.enqueueClientOperation(new ServerOperation(op3, "3", "1234"));
        driver.processChange();

        assertEquals("ABC", driver.getDocument().getData());
    }
}