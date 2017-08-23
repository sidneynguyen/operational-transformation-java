package com.sidneynguyen.otjava;

import java.util.Hashtable;
import java.util.UUID;

public class ClientGraph {
    private Hashtable<String, OperationNode> operationNodes;
    private OperationNode currentClientNode;
    private OperationNode currentServerNode;
    private OperationNode rootNode;

    public ClientGraph(OperationNode rootNode) {
        this.rootNode = rootNode;
        operationNodes = new Hashtable<>();
        operationNodes.put(rootNode.getHashKey(), rootNode);
        currentClientNode = rootNode;
        currentServerNode = rootNode;
    }

    public OperationNode insertClientOperation(Operation operation, String parentKey) {
        if (!currentClientNode.getHashKey().equals(parentKey)) {
            throw new RuntimeException("Operation is out-of-date");
        }
        String key = UUID.randomUUID().toString();
        OperationNode node = new OperationNode(key);
        node.setParentNodeFromClientOperation(currentClientNode);
        currentClientNode.setClientNode(node);
        currentClientNode.setClientOperation(operation);
        operationNodes.put(key, node);
        currentClientNode = node;
        return node;
    }

    public OperationNode insertServerOperation(Operation operation, String parentKey) {
        OperationNode parentNode = operationNodes.get(parentKey);
        if (parentNode == null) {
            throw new RuntimeException("Parent node does not exist");
        }
        String key = UUID.randomUUID().toString();
        OperationNode node = new OperationNode(key);
        node.setParentNodeFromServerOperation(parentNode);
        parentNode.setServerNode(node);
        parentNode.setServerOperation(operation);
        operationNodes.put(key, node);
        if (currentServerNode.getHashKey().equals(parentKey)) {
            currentServerNode = node;
        }
        return node;
    }

    // TODO
    public OperationNode applyServerOperation(OperationNode node) {
        if (operationNodes.get(node.getHashKey()) == null) {
            throw new RuntimeException("Server node does not exist");
        }
        OperationNode parentNode = node.getParentNodeFromServerOperation();
        Operation clientOperation = parentNode.getClientOperation();
        if (clientOperation == null) {
            if (currentServerNode.getHashKey().equals(node.getHashKey())
                    && currentClientNode.getHashKey().equals(parentNode.getHashKey())) {
                currentClientNode = node;
                return currentServerNode;
            } else {
                throw new RuntimeException("Corrupted graph structure");
            }
        }
        Transformer transformer = new Transformer();
        OperationNode currNode = node;
        Operation serverOperation = parentNode.getServerOperation();
        OperationNode clientNode = parentNode.getClientNode();
        while (!parentNode.getHashKey().equals(currentClientNode.getHashKey())) {
            /*Operation clientPrime = transformer.transform(clientOperation, serverOperation);
            Operation serverPrime = transformer.transform(clientOperation, serverOperation);

            clientNode.setServerOperation(serverPrime);
            currNode.setClientOperation(clientPrime);
            String key = UUID.randomUUID().toString();
            OperationNode nextNode = new OperationNode(key);
            clientNode.setServerNode(nextNode);
            currNode.setClientNode(nextNode);
            nextNode.setParentNodeFromServerOperation(clientNode);
            nextNode.setParentNodeFromClientOperation(currNode);
            currNode = nextNode;
            parentNode = currNode.getParentNodeFromServerOperation();
            */
        }
        currentClientNode = currNode;
        currentServerNode = currNode;
        return currNode;
    }
}
