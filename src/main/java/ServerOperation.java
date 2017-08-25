/**
 * Models an operation sent to or sent by the server.
 */
public class ServerOperation {
    private Operation operation;
    private String key;
    private String parentKey;

    public ServerOperation(Operation operation, String key, String parentKey) {
        this.operation = operation;
        this.key = key;
        this.parentKey = parentKey;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getKey() {
        return key;
    }

    public String getParentKey() {
        return parentKey;
    }
}
