package com.sidneynguyen.otjava;

import java.util.LinkedList;
import java.util.UUID;

public class ClientDriver {
    private Document document;

    private ClientGraph clientGraph;
    private LinkedList<Operation> clientOperations;
    private LinkedList<ServerOperation> serverOperations;

    private OperationNode sentOperation;

    private EditDistanceCalculator editDistanceCalculator;

    public ClientDriver(String data, String key) {
        document = new Document(data);
        clientGraph = new ClientGraph(key);

        clientOperations = new LinkedList<>();
        serverOperations = new LinkedList<>();

        editDistanceCalculator = new EditDistanceCalculator();
    }

    public ServerOperation sendClientOperationToServer() {
        Operation operation = clientGraph.generateClientOperationForServer();
        clientGraph.setSentOperationKey(clientGraph.getCurrentClientNode().getHashKey());
        return new ServerOperation(operation, clientGraph.getCurrentClientNode().getHashKey(),
                clientGraph.getCurrentServerNode().getHashKey());
    }

    public Operation getOperationFromEdit(String edit) {
        int[][] e = editDistanceCalculator.getLevenshteinMatrix(document.getData(), edit);
        return editDistanceCalculator.getEdits(e, document.getData(), edit);
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
                    serverOperation.getParentKey());
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

    public void processChange(String key) {
        if (!serverOperations.isEmpty()) {
            ServerOperation serverOperation = serverOperations.removeFirst();
            clientGraph.insertServerOperation(serverOperation.getOperation(), serverOperation.getKey(),
                    serverOperation.getParentKey());
            Operation operation = clientGraph.applyServerOperation();
            if (operation != null) {
                document.applyOperation(operation);
            }
        } else if (!clientOperations.isEmpty()) {
            Operation operation = clientGraph.insertClientOperation(clientOperations.removeFirst(),
                    clientGraph.getCurrentClientNode().getHashKey(), key);
            document.applyOperation(operation);
        }
    }

    public Document getDocument() {
        return document;
    }
}
