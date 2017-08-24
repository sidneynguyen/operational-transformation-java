package com.sidneynguyen.otjava;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void mainTest1() throws Exception {
        ClientDriver clientDriver = new ClientDriver("", "1234");
        ServerDriver serverDriver = new ServerDriver("", "1234");
        clientDriver.enqueueClientOperation(clientDriver.getOperationFromEdit("AB"));
        clientDriver.processChange();
        assertEquals("AB", clientDriver.getDocument().getData());

        ServerOperation op1 = clientDriver.sendClientOperationToServer();
        serverDriver.enqueueClientOperation(op1);
        serverDriver.processChange();
        assertEquals("AB", serverDriver.getDocument().getData());

        clientDriver.enqueueServerOperation(serverDriver.sendServerOperationToClient());
        clientDriver.processChange();

        assertEquals("AB", clientDriver.getDocument().getData());
        assertEquals("AB", serverDriver.getDocument().getData());
    }

    @Test
    public void mainTest2() throws Exception {
        ClientDriver clientDriver = new ClientDriver("AB", "1234");
        ServerDriver serverDriver = new ServerDriver("AB", "1234");
        clientDriver.enqueueClientOperation(clientDriver.getOperationFromEdit("ABCD"));
        clientDriver.processChange();
        assertEquals("ABCD", clientDriver.getDocument().getData());

        ServerOperation op1 = clientDriver.sendClientOperationToServer();
        serverDriver.enqueueClientOperation(op1);
        serverDriver.processChange();
        assertEquals("ABCD", serverDriver.getDocument().getData());

        clientDriver.enqueueServerOperation(serverDriver.sendServerOperationToClient());
        clientDriver.processChange();

        assertEquals("ABCD", clientDriver.getDocument().getData());
        assertEquals("ABCD", serverDriver.getDocument().getData());
    }

    @Test
    public void mainTest3() throws Exception {
        ClientDriver clientDriver1 = new ClientDriver("AB", "1234");
        ClientDriver clientDriver2 = new ClientDriver("AB", "1234");
        ServerDriver serverDriver = new ServerDriver("AB", "1234");

        clientDriver1.enqueueClientOperation(clientDriver1.getOperationFromEdit("ABCD"));
        clientDriver1.processChange();

        clientDriver2.enqueueClientOperation(clientDriver2.getOperationFromEdit("ABXY"));
        clientDriver2.processChange();

        serverDriver.enqueueClientOperation(clientDriver1.sendClientOperationToServer());
        serverDriver.processChange();
        ServerOperation res1 = serverDriver.sendServerOperationToClient();

        clientDriver1.enqueueServerOperation(res1);
        clientDriver1.processChange();

        clientDriver2.enqueueServerOperation(res1);
        clientDriver2.processChange();

        serverDriver.enqueueClientOperation(clientDriver2.sendClientOperationToServer());
        serverDriver.processChange();
        ServerOperation res2 = serverDriver.sendServerOperationToClient();

        clientDriver1.enqueueServerOperation(res2);
        clientDriver1.processChange();

        clientDriver2.enqueueServerOperation(res2);
        clientDriver2.processChange();

        assertEquals("ABCXDY", serverDriver.getDocument().getData());
        assertEquals("ABCXDY", clientDriver1.getDocument().getData());
        assertEquals("ABCXDY", clientDriver2.getDocument().getData());
    }
}