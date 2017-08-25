/**
 * Contains two operations.
 * Used to return two operations.
 */
public class OperationPair {
    private Operation clientOperation;
    private Operation serverOperation;

    public OperationPair(Operation clientOperation, Operation serverOperation) {
        this.clientOperation = clientOperation;
        this.serverOperation = serverOperation;
    }

    public Operation getClientOperation() {
        return clientOperation;
    }

    public Operation getServerOperation() {
        return serverOperation;
    }
}
