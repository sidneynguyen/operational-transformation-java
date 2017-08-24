package com.sidneynguyen.otjava;

import java.util.LinkedList;
import java.util.UUID;

public class ClientDriver {
    private Document document;

    private ClientGraph clientGraph;
    private LinkedList<Operation> clientOperations;
    private LinkedList<ServerOperation> serverOperations;

    private OperationNode sentOperation;

    public ClientDriver(String data, String key, String uuid) {
        document = new Document(data);
        clientGraph = new ClientGraph(key, uuid);

        clientOperations = new LinkedList<>();
        serverOperations = new LinkedList<>();
    }

    public void sendClientOperataion() {

    }

    public void enqueueClientOperation(Operation operation) {
        clientOperations.addLast(operation);
    }

    public void enqueueServerOperation(ServerOperation operation) {
        serverOperations.addLast(operation);
    }

    public void processChange() {
        if (!serverOperations.isEmpty()) {
            ServerOperation serverOperation = serverOperations.removeFirst();
            clientGraph.insertServerOperation(serverOperation.getOperation(), serverOperation.getKey(),
                    serverOperation.getParentKey(), serverOperation.getUuid());
            Operation operation = clientGraph.applyServerOperation();
            if (operation != null) {
                document.applyOperation(operation);
            }
        } else if (!clientOperations.isEmpty()) {
            Operation operation = clientGraph.insertClientOperation(clientOperations.removeFirst(),
                    clientGraph.getCurrentClientNode().getHashKey());
            document.applyOperation(operation);
        }
    }

    public Document getDocument() {
        return document;
    }
}
