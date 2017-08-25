import java.util.Hashtable;
import java.util.UUID;

/**
 * Implements a graph for storing and transforming operations.
 */
public class ClientGraph {
    private Hashtable<String, OperationNode> operationNodes;
    private OperationNode currentClientNode;
    private OperationNode currentServerNode;
    private Transformer transformer;
    private Composer composer;
    private String sentOperationKey;

    public ClientGraph(String key) {
        OperationNode rootNode = new OperationNode(key);
        operationNodes = new Hashtable<>();
        operationNodes.put(rootNode.getHashKey(), rootNode);
        currentClientNode = rootNode;
        currentServerNode = rootNode;
        transformer = new Transformer();
        composer = new Composer();
    }

    /**
     * Generates an operation to send to the server.
     * @return  an operation to send to the server
     */
    public Operation generateClientOperationForServer() {
        // start with current server node, then bridge to client node by composing operations inbetween
        OperationNode node = currentServerNode;
        Operation operation = node.getClientOperation();
        node = node.getClientNode();
        while (node.getClientNode() != null) {
            operation = composer.compose(operation, node.getClientOperation());
            node = node.getClientNode();
        }
        operation.simplify();
        return operation;
    }

    /**
     * Inserts a new client operation node into the graph.
     * @param operation     the client operation
     * @param parentKey     the parent key of the operation
     * @return              the operation
     */
    public Operation insertClientOperation(Operation operation, String parentKey) {
        // client operations should always be up-to-date
        if (!currentClientNode.getHashKey().equals(parentKey)) {
            throw new RuntimeException("Operation is out-of-date");
        }
        OperationNode node = new OperationNode(UUID.randomUUID().toString());
        node.setParentNodeFromClientOperation(currentClientNode);
        currentClientNode.setClientNode(node);
        currentClientNode.setClientOperation(operation);
        operationNodes.put(node.getHashKey(), node);

        // current client node must be this new node
        currentClientNode = node;
        return operation;
    }

    /**
     * Inserts a new server operation node into the graph.
     * @param operation
     * @param key
     * @param parentKey
     */
    public void insertServerOperation(Operation operation, String key, String parentKey) {
        // server operations should always be up-to-date
        if (!currentServerNode.getHashKey().equals(parentKey)) {
            throw new RuntimeException("Operation is out-of-date");
        }

        // if this is the transformed operation of an operation sent by the client, update the graph
        if (key.equals(sentOperationKey)) {
            currentServerNode = operationNodes.get(key);
            if (currentServerNode.getServerNode() != null) {
                while (currentServerNode.getServerNode() != null) {
                    currentServerNode = currentServerNode.getServerNode();
                }
                currentServerNode.setHashKey(key);
            }
            sentOperationKey = null;
            return;
        }

        OperationNode node = new OperationNode(key);
        node.setParentNodeFromServerOperation(currentServerNode);
        currentServerNode.setServerNode(node);
        currentServerNode.setServerOperation(operation);
        operationNodes.put(node.getHashKey(), node);

        // current server node must be this node
        currentServerNode = node;
    }

    /**
     * Transforms the current server node to get the operation to apply to the client document.
     * @return  the operation to apply to the client document
     */
    public Operation applyServerOperation() {
        // server operation already applied
        if (currentServerNode.getClientNode() != null) {
            return null;
        }
        // current server node is already up-to-date with current client node
        if (currentServerNode.getHashKey().equals(currentClientNode.getHashKey())) {
            return null;
        }

        // transform until current client node is reached
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

    public String getSentOperationKey() {
        return sentOperationKey;
    }

    public void setSentOperationKey(String sentOperationKey) {
        this.sentOperationKey = sentOperationKey;
    }
}
