import java.util.LinkedList;

/**
 * Implements a driver for the server.
 */
public class ServerDriver {
    private Document document;
    private ServerGraph serverGraph;
    private LinkedList<ServerOperation> clientOperations;

    public ServerDriver(String data, String key) {
        document = new Document(data);
        serverGraph = new ServerGraph(key);
        clientOperations = new LinkedList<>();
    }

    /**
     * Processes the next operation in queue.
     */
    public void processChange() {
        if (!clientOperations.isEmpty()) {
            ServerOperation clientOperation = clientOperations.removeFirst();
            OperationNode node = serverGraph.insertClientOperation(clientOperation.getOperation(), clientOperation.getKey(), clientOperation.getParentKey());
            Operation operation = serverGraph.applyClientOperation(node);
            document.applyOperation(operation);
        }
    }

    /**
     * Sends the server operation to all clients.
     * @return  the server operation to send
     */
    public ServerOperation sendServerOperationToClient() {
        return serverGraph.getCurrentOperationToSend();
    }

    public void enqueueClientOperation(ServerOperation operation) {
        clientOperations.addLast(operation);
    }

    public Document getDocument() {
        return document;
    }
}
