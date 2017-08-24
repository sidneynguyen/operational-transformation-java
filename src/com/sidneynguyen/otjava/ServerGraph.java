package com.sidneynguyen.otjava;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.UUID;

public class ServerGraph {
    private Hashtable<String, OperationNode> operationNodes;
    private ServerOperation currentOperationToSend;
    private Transformer transformer;

    public ServerGraph(String key) {
        OperationNode rootNode = new OperationNode(key);
        operationNodes = new Hashtable<>();
        operationNodes.put(rootNode.getHashKey(), rootNode);
        transformer = new Transformer();
    }

    public OperationNode insertClientOperation(Operation operation, String key, String parentKey) {
        OperationNode parentNode = operationNodes.get(parentKey);
        if (parentNode == null) {
            throw new RuntimeException("Operation is out-of-date");
        }
        OperationNode node = new OperationNode(key);
        node.setParentNodeFromClientOperation(parentNode);
        parentNode.setClientNode(node);
        parentNode.setClientOperation(operation);
        return node;
    }

    public Operation applyClientOperation(OperationNode node) {
        OperationNode clientNode = node;
        OperationNode parentNode = node.getParentNodeFromClientOperation();
        OperationNode serverNode = parentNode.getServerNode();
        while (serverNode != null) {
            OperationNode nextNode = new OperationNode(node.getHashKey());
            OperationPair pair = transformer.transform(parentNode.getClientOperation(), parentNode.getServerOperation());

            serverNode.setClientOperation(pair.getClientOperation());
            serverNode.setClientNode(nextNode);
            nextNode.setParentNodeFromClientOperation(serverNode);

            clientNode.setServerOperation(pair.getServerOperation());
            clientNode.setServerNode(nextNode);
            nextNode.setParentNodeFromServerOperation(clientNode);

            clientNode = nextNode;
            parentNode = clientNode.getParentNodeFromClientOperation();
            serverNode = parentNode.getServerNode();
        }
        Operation operation = parentNode.getClientOperation();
        parentNode.setServerNode(clientNode);
        parentNode.setServerOperation(operation);
        clientNode.setParentNodeFromServerOperation(parentNode);
        currentOperationToSend = new ServerOperation(operation, clientNode.getHashKey(), parentNode.getHashKey());
        operationNodes.put(clientNode.getHashKey(), clientNode);
        return operation;
    }

    public ServerOperation getCurrentOperationToSend() {
        return currentOperationToSend;
    }
}
