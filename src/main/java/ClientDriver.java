import java.util.LinkedList;
import java.util.UUID;

/**
 * Implements a driver for the client.
 */
public class ClientDriver {
    private Document document;

    private ClientGraph clientGraph;
    private LinkedList<Operation> clientOperations;
    private LinkedList<ServerOperation> serverOperations;

    private EditDistanceCalculator editDistanceCalculator;

    public ClientDriver(String data, String key) {
        document = new Document(data);
        clientGraph = new ClientGraph(key);

        clientOperations = new LinkedList<>();
        serverOperations = new LinkedList<>();

        editDistanceCalculator = new EditDistanceCalculator();
    }

    /**
     * Sends a composite client operation to the server.
     * @return  the operation sent
     */
    public ServerOperation sendClientOperationToServer() {
        // TODO: may not need this loop
        // Apply all server operations first
        while (!serverOperations.isEmpty()) {
            processChange();
        }

        // create the operation to send
        Operation operation = clientGraph.generateClientOperationForServer();
        clientGraph.setSentOperationKey(clientGraph.getCurrentClientNode().getHashKey());
        return new ServerOperation(operation, clientGraph.getCurrentClientNode().getHashKey(),
                clientGraph.getCurrentServerNode().getHashKey());
    }

    /**
     * Returns an operation that turns the current document into the new string.
     * @param edit  the new string
     * @return      the operation
     */
    public Operation getOperationFromEdit(String edit) {
        int[][] e = editDistanceCalculator.getLevenshteinMatrix(document.getData(), edit);
        return editDistanceCalculator.getEdits(e, document.getData(), edit);
    }

    /**
     * Applies operations in queue. Prioritizes server operations.
     * // TODO: Needs more abstraction. Should not use queue.
     */
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

    public void enqueueClientOperation(Operation operation) {
        clientOperations.addLast(operation);
    }

    public void enqueueServerOperation(ServerOperation operation) {
        serverOperations.addLast(operation);
    }

    public boolean canProcess() {
        return !clientOperations.isEmpty() || !serverOperations.isEmpty();
    }

    public boolean canSendOperationToServer() {
        return clientGraph.getSentOperationKey() == null;
    }

    public Document getDocument() {
        return document;
    }
}
