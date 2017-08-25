/**
 * Models a node in a graph of operations.
 */
public class OperationNode {
    private String hashKey;
    private Operation clientOperation;
    private Operation serverOperation;
    private OperationNode clientNode;
    private OperationNode serverNode;
    private OperationNode parentNodeFromServerOperation;
    private OperationNode parentNodeFromClientOperation;

    public OperationNode(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getHashKey() {
        return hashKey;
    }

    public Operation getClientOperation() {
        return clientOperation;
    }

    public void setClientOperation(Operation clientOperation) {
        this.clientOperation = clientOperation;
    }

    public Operation getServerOperation() {
        return serverOperation;
    }

    public void setServerOperation(Operation serverOperation) {
        this.serverOperation = serverOperation;
    }

    public OperationNode getClientNode() {
        return clientNode;
    }

    public void setClientNode(OperationNode clientNode) {
        this.clientNode = clientNode;
    }

    public OperationNode getServerNode() {
        return serverNode;
    }

    public void setServerNode(OperationNode serverNode) {
        this.serverNode = serverNode;
    }

    public OperationNode getParentNodeFromServerOperation() {
        return parentNodeFromServerOperation;
    }

    public void setParentNodeFromServerOperation(OperationNode parentNodeFromServerOperation) {
        this.parentNodeFromServerOperation = parentNodeFromServerOperation;
    }

    public OperationNode getParentNodeFromClientOperation() {
        return parentNodeFromClientOperation;
    }

    public void setParentNodeFromClientOperation(OperationNode parentNodeFromClientOperation) {
        this.parentNodeFromClientOperation = parentNodeFromClientOperation;
    }

    public void setHashKey(String key) {
        hashKey = key;
    }
}
