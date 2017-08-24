package com.sidneynguyen.otjava;

import java.util.Hashtable;
import java.util.UUID;

public class ClientGraph {
    private Hashtable<String, OperationNode> operationNodes;
    private OperationNode currentClientNode;
    private OperationNode currentServerNode;
    private Transformer transformer;

    public ClientGraph(String key) {
        OperationNode rootNode = new OperationNode(key);
        operationNodes = new Hashtable<>();
        operationNodes.put(rootNode.getHashKey(), rootNode);
        currentClientNode = rootNode;
        currentServerNode = rootNode;
        transformer = new Transformer();
    }

    public Operation insertClientOperation(Operation operation, String parentKey) {
        if (!currentClientNode.getHashKey().equals(parentKey)) {
            throw new RuntimeException("Operation is out-of-date");
        }
        OperationNode node = new OperationNode(UUID.randomUUID().toString());
        node.setParentNodeFromClientOperation(currentClientNode);
        currentClientNode.setClientNode(node);
        currentClientNode.setClientOperation(operation);
        operationNodes.put(node.getHashKey(), node);
        currentClientNode = node;
        return operation;
    }

    public void insertServerOperation(Operation operation, String key, String parentKey) {
        if (!currentServerNode.getHashKey().equals(parentKey)) {
            throw new RuntimeException("Operation is out-of-date");
        }
        OperationNode node = new OperationNode(key);
        node.setParentNodeFromServerOperation(currentServerNode);
        currentServerNode.setServerNode(node);
        currentServerNode.setServerOperation(operation);
        operationNodes.put(node.getHashKey(), node);
        currentServerNode = node;
    }

    public Operation applyServerOperation() {
        if (currentServerNode.getClientNode() != null) {
            return null;
        }
        OperationNode serverNode = currentServerNode;
        OperationNode parentNode = currentServerNode.getParentNodeFromServerOperation();
        OperationNode clientNode = parentNode.getClientNode();
        while (!parentNode.getHashKey().equals(currentClientNode.getHashKey())) {
            OperationNode nextNode = new OperationNode(UUID.randomUUID().toString());
            operationNodes.put(nextNode.getHashKey(), nextNode);
            OperationPair pair = transformer.transform(parentNode.getClientOperation(), parentNode.getServerOperation());

            clientNode.setServerOperation(pair.getServerOperation());
            clientNode.setServerNode(nextNode);
            nextNode.setParentNodeFromServerOperation(clientNode);

            serverNode.setClientOperation(pair.getClientOperation());
            serverNode.setClientNode(nextNode);
            nextNode.setParentNodeFromClientOperation(serverNode);

            serverNode = nextNode;
            parentNode = serverNode.getParentNodeFromServerOperation();
            clientNode = parentNode.getClientNode();
        }
        currentClientNode = serverNode;
        return currentClientNode.getParentNodeFromServerOperation().getServerOperation();
    }

    public OperationNode getCurrentClientNode() {
        return currentClientNode;
    }

    public OperationNode getCurrentServerNode() {
        return currentServerNode;
    }
}
