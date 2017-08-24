package com.sidneynguyen.otjava;

import java.util.LinkedList;

public class ServerDriver {
    private Document document;

    private ServerGraph serverGraph;
    private LinkedList<ServerOperation> clientOperations;

    public ServerDriver(String data, String key) {
        document = new Document(data);
        serverGraph = new ServerGraph(key);

        clientOperations = new LinkedList<>();
    }

    public void enqueueClientOperation(ServerOperation operation) {
        clientOperations.addLast(operation);
    }

    public void processChange() {
        if (!clientOperations.isEmpty()) {
            ServerOperation clientOperation = clientOperations.removeFirst();
            OperationNode node = serverGraph.insertClientOperation(clientOperation.getOperation(), clientOperation.getKey(), clientOperation.getParentKey());
            Operation operation = serverGraph.applyClientOperation(node);
            document.applyOperation(operation);
        }
    }

    public ServerOperation sendServerOperationToClient() {
        return serverGraph.getCurrentOperationToSend();
    }

    public Document getDocument() {
        return document;
    }
}
